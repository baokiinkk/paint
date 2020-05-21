package com.company.xuli.xuliduongve;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Board extends JPanel {
    public static HinhHoc now;
    private static boolean[][] nextDrawing;
    private static Color[][] nextPoint;
    private int spacing;
    private int rectSize;
    private static Color[][] drawingBoard;
    private static int width;
    private static int height;
    private static Stack<Color[][]> undoBoard;

    private int OX;
    private int OY;
    private boolean showAxis;
    private static Stack<Color[][]> redoBoard;


    public Board(boolean[][] nextDrawing, Color[][] nextPoint, Color[][] drawingBoard, int width, int height, int spacing, int rectSize) {
        Board.drawingBoard = drawingBoard;
        Board.nextDrawing = nextDrawing;
        Board.nextPoint = nextPoint;
        this.rectSize = rectSize;
        this.spacing = spacing;
        Board.width = width;
        Board.height = height;
        OX = spacing + width / 2 * rectSize + (rectSize - spacing) / 2;
        OY = spacing + height / 2 * rectSize + (rectSize - spacing) / 2;
        undoBoard = new Stack<Color[][]>();
        redoBoard = new Stack<Color[][]>();
        showAxis = false;
    }

    public static void rotateNow(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(now.center, start);
        Vector2D b = new Vector2D(now.center, end);
        double alpha = a.alphaVector(b);
        //System.out.println(alpha);
        switch (now.tag) {
            case RECTANGLE: {
                ((Rectangle) now).rotate(alpha);
                break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        //g.setColor(Color.lightGray); // set màu nền cho nền vẽ
        g.setColor(new Color(235, 235, 235)); // set màu nền cho nền vẽ
        g.fillRect(0, 0, 1280, 800); // kích thước nền vẽ
        // set màu cho các pixel
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++) {
                // nếu bản trạng thái tồn tại thì set ô pixel đó màu đen còn ko thì màu trắng
                if (nextDrawing[i][j])
                    g.setColor(nextPoint[i][j]);
                else
                    g.setColor(drawingBoard[i][j]);
                g.fillRect(spacing + i * rectSize, spacing + j * rectSize, rectSize - spacing, rectSize - spacing);
            }
        }

        if (showAxis) {
            g.setColor(Color.RED);
            g.drawLine(0, OY, 1280, OY);
            g.drawLine(OX, 0, OX, 800);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (drawingBoard[i][j].equals(Color.WHITE) == false)
                    {
                        g.setColor(drawingBoard[i][j]);
                        int x = spacing + i * rectSize;
                        int y = spacing + j * rectSize;
                        if (y < 15)
                            y = 15;
                        if (x + 40 > (width - 4) * rectSize)
                            x -= 40;
                        //System.out.println(x + " " + y);
                        g.drawString("(" + (i - OX / rectSize) + ", " + (-(j - OY / rectSize)) + ")", x, y);
                    }
                }
            }
        }
    }

    public static void setNowHinhHoc(HinhHoc other) {
        now = new HinhHoc(nextDrawing, nextPoint, other.chooseColor);
        now = other;
    }

    public static void applyNow() {
        redoBoard.clear();
        Color[][] tmpBoard = new Color[width][height];
        MyFunction.storePointColor(drawingBoard, tmpBoard);
        undoBoard.push(tmpBoard);
        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
        MyFunction.clearArr(nextDrawing);
    }

    public static void undo() {
        if (!undoBoard.empty()) {
            Color[][] tmpBoard = new Color[width][height];
            MyFunction.storePointColor(drawingBoard, tmpBoard);
            redoBoard.push(tmpBoard);
            MyFunction.storePointColor(undoBoard.pop(), drawingBoard);
        }
    }

    public static void redo() {
        if (!redoBoard.empty()) {
            Color[][] tmpBoard = new Color[width][height];
            MyFunction.storePointColor(drawingBoard, tmpBoard);
            undoBoard.push(tmpBoard);
            MyFunction.storePointColor(redoBoard.pop(), drawingBoard);
        }
    }

    public static boolean ableUndo() {
        return (!undoBoard.empty());
    }

    public static boolean ableRedo() {
        return (!redoBoard.empty());
    }

    public void hideAxis() {
        showAxis = false;
    }

    public void showAxis() {
        showAxis = true;
    }

    public static void drawNow() {
        switch (now.tag) {
            case RECTANGLE: {
                ((Rectangle) now).draw();
                break;
            }
        }
    }

}
