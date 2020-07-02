package com.company.xuli.xuliduongve;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Board extends JPanel {

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
        boardState = true;
        showAxis = false;
        showCoord = false;
        gridColor = new Color(235, 235, 235);
        recStart = new Point2D(-1, -1);
        recEnd = new Point2D(-1, -1);
    }

    // tính góc tạo bởi 2 vector sau đó xoay, xoay này là xoay nháp, tức là xoay làm mẫu để người dùng
    // xác định góc thích hợp
    public static void rotateNow(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(now.center, start);
        Vector2D b = new Vector2D(now.center, end);
        double alpha = a.alphaVector(b);

        System.out.println(alpha);
        switch (now.tag) {
            case RECTANGLE: {
                ((Rectangle) now).rotate(alpha);
                break;
            }
            case ELLIPSE:{
                ((Ellipse) now).rotate(alpha);
                break;
            }
        }
    }

    public static void moveNow(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(start, end);
        //System.out.println("---" + now.tag);
        switch (now.tag) {
            case RECTANGLE: {
                ((Rectangle) now).move(a);
                break;
            }
            case ELLIPSE:{
                ((Ellipse) now).move(a);
                break;
            }
            case SELECT:{
                ((Select) now).move(a);
                break;
            }
        }
    }

    // tính góc tạo bởi 2 vector sau đó xoay, xoay này là xoay thật :v, xem hàm trên sẽ rõ
    public static void applyRotate(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(now.center, start);
        Vector2D b = new Vector2D(now.center, end);
        double alpha = a.alphaVector(b);
        //System.out.println(alpha);
        switch (now.tag) {
            case RECTANGLE: {
                ((Rectangle) now).applyRotate(alpha);
                break;
            }
            case ELLIPSE: {
                ((Ellipse) now).applyRotate(alpha);
                break;
            }
        }
    }

    public static void applyMove(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(start, end);
        //System.out.println(alpha);
        switch (now.tag) {
            case RECTANGLE: {
                ((Rectangle) now).applyMove(a);
                break;
            }
            case ELLIPSE: {
                ((Ellipse) now).applyMove(a);
                break;
            }
            case SELECT:{
                ((Select) now).applyMove(a);
                break;
            }
        }
    }

    // đặt hình vẽ hiện tại
    // hình vừa vẽ xong sẽ được lưu vào biến now để thao tác xoay, zoom,...
    public static void setNowHinhHoc(HinhHoc other) {
        now = new HinhHoc(nextDrawing, nextPoint, other.chooseColor);
        now = other;
    }

    // set vị trí của ô vuông cục gôm
    public static void drawErase(Point2D vitri) {
        erase = vitri;
        drawErase = true;
    }

    // sau khi xoay xong hình, apply dùng để ghim hình vừa xoay vào drawingBoard
    public static void applyNow() {
        redoBoard.clear();
        Color[][] tmpBoard = new Color[width][height];
        MyFunction.storePointColor(drawingBoard, tmpBoard);
        undoBoard.push(tmpBoard);
        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
        MyFunction.clearArr(nextDrawing);
    }

    // mỗi lần vẽ xong sẽ ghim hình vừa vẽ vào drawingBoard, hàm previousDo sẽ gỡ ghim hình vừa
    // vẽ để có thể mang ra xoay, zoom,..
    public static void previousDo() {
        if (!undoBoard.empty()) {
            Color[][] tmpBoard = new Color[width][height];
            MyFunction.storePointColor(drawingBoard, tmpBoard);
            MyFunction.storePointColor(undoBoard.pop(), drawingBoard);
        }
    }

    public static void nextDo() {
        Color[][] tmpBoard = new Color[width][height];
        MyFunction.storePointColor(drawingBoard, tmpBoard);
        undoBoard.push(tmpBoard);
    }

    // quay lại trạng thái trước đó, push trạng thái hiện tại vào redo
    public static void undo() {
        if (!undoBoard.empty()) {
            Color[][] tmpBoard = new Color[width][height];
            MyFunction.storePointColor(drawingBoard, tmpBoard);
            redoBoard.push(tmpBoard);
            MyFunction.storePointColor(undoBoard.pop(), drawingBoard);
        }
    }

    // đặt trạng thái vừa undo thành trạng thái hiện tại
    public static void redo() {
        if (!redoBoard.empty()) {
            Color[][] tmpBoard = new Color[width][height];
            MyFunction.storePointColor(drawingBoard, tmpBoard);
            undoBoard.push(tmpBoard);
            MyFunction.storePointColor(redoBoard.pop(), drawingBoard);
        }
    }

    //hàm kiểm tra xem có thể Undo nữa hay không
    public static boolean ableUndo() {
        return (!undoBoard.empty());
    }

    // tương tự hàm trên nhưng là redo
    public static boolean ableRedo() {
        return (!redoBoard.empty());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println("repaint");
        //g.setColor(Color.lightGray); // set màu nền cho nền vẽ
        g.setColor(new Color(235, 235, 235)); // set màu nền cho nền vẽ
        g.setColor(gridColor);
        g.fillRect(0, 0, 1280, 800); // kích thước nền vẽ
        // set màu cho các pixel
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // nếu bản trạng thái tồn tại thì set ô pixel đó màu đen còn ko thì màu trắng
                if (nextDrawing[i][j]) {
                    g.setColor(nextPoint[i][j]);
                } else {
                    g.setColor(drawingBoard[i][j]);
                }
                g.fillRect(spacing + i * rectSize, spacing + j * rectSize, rectSize - spacing, rectSize - spacing);
            }
        }
        //System.out.println("Board: " + boardState +"  " + showAxis);
        if (boardState) // trạng thái bảng vẽ 2D
        {
            if (showAxis) // hiện trục tọa độ
            {
                g.setColor(Color.RED);
                g.drawLine(0, OY, 1280, OY);
                g.drawLine(OX, 0, OX, 800);
            }

//            if (showCoord)  // hiện tọa độ
//            {
//                for (int i = 0; i < width; i++) {
//                    for (int j = 0; j < height; j++) {
//                        if (coordBoard[i][j] == true) {
//                            g.setColor(drawingBoard[i][j]);
//                            int x = spacing + i * rectSize;
//                            int y = spacing + j * rectSize;
//                            if (y < 15)
//                                y = 15;
//                            if (x + 40 > (width - 4) * rectSize)
//                                x -= 40;
//                            //System.out.println(x + " " + y);
//                            g.drawString("(" + (i - OX / rectSize) + ", " + (-(j - OY / rectSize)) + ")", x, y);
//                        }
//                    }
//                }
//            }
        } else {
            if (showAxis) {
                g.setColor(Color.RED);
                g.drawLine(1280, OY, OX, OY);
                g.drawLine(OX, 0, OX, OY);
                g.drawLine(OX - 800, OY + 800, OX, OY);


                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.pink);

                float[] dashingPattern1 = {2f, 2f};
                Stroke stroke1 = new BasicStroke(2f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 3.0f);

                g2d.setStroke(stroke1);
                g2d.drawLine(0, OY, OX, OY);
                g2d.drawLine(OX, 800, OX, OY);
                g2d.drawLine(OX + 800, OY - 800, OX, OY);


            }
        }


        // vẽ ô vuông của cục gôm
        if (drawErase) {
            g.setColor(Color.BLACK);
            g.drawRect((erase.X - 1) * rectSize, (erase.Y - 1) * rectSize, rectSize * 3, rectSize * 3);
            drawErase = false;
        }

//        if (drawSelect) {
//            g.setColor(Color.BLACK);
//            g.drawRect((erase.X - 1) * rectSize, (erase.Y - 1) * rectSize, rectSize * 3, rectSize * 3);
//            drawSelect = false;
//        }



        if (!isNotSelecting()) {
            g.setColor(Color.BLACK);
            //System.out.println("ccccccccc");
            g.drawRect(recStart.X * rectSize, recStart.Y * rectSize,
                    Math.abs(recStart.X - recEnd.X)*rectSize, Math.abs(recStart.Y - recEnd.Y)*rectSize);
            recStart.set(-1, -1);
            recEnd.set(-1, -1);

        }

    }

    // hàm set ẩn/hiện trục tọa độ
    public void setShowAxis(boolean set) {
        showAxis = set;
    }

    public void setShowCoord(boolean set) {
        showCoord = set;
    }

    public void setBoardState(boolean set) {
        boardState = set;
    }

    private boolean isNotSelecting() {
        return (recStart.equal(new Point2D(-1, -1)) && recEnd.equal(new Point2D(-1, -1)));
    }

    public static void select(Point2D start, Point2D end){
        recStart.set(start);
        recEnd.set(end);
    }
    // hàm vẽ biến hình hiện tại (now)
    // hình như bị dư :v
//    public static void drawNow() {
//        switch (now.tag) {
//            case RECTANGLE: {
//                ((Rectangle) now).draw();
//                break;
//            }
//        }
//    }

    public static void setGridColor(Color tmp) {
        gridColor = tmp;
    }

    public static Color getGridColor() {
        return gridColor;
    }
//    public static void setBackgroundColor(Color tmp)
//    {
//
//    }

    public static HinhHoc now;              // biến lưu hình vừa vẽ xong, khi thực hiện các thao tác move, zoom,... sẽ thực hiện trên biến này
    private static boolean[][] nextDrawing; // bảng này và bảng dưới dùng để lưu trạng thái nháp, trạng thái nháp là trạng thái lúc kéo vẽ 1 hình nào đó, VD vẽ đường thẳng
    private static Color[][] nextPoint;     // bảng này lưu màu nháp
    private static Color[][] drawingBoard;  // bảng vẽ chính thức.
    private static boolean[][] coordBoard;  // bảng này sẽ lưu các tọa độ của hình
    private static int width;   // độ rộng của bảng vẽ
    private static int height;  // độ cao của bảng vẽ
    private static Stack<Color[][]> undoBoard;  // stack lưu các trạng thái của bảng vẽ để undo
    private static Stack<Color[][]> redoBoard;  // stack lưu .... để redo
    private static Point2D erase;           // vị trí của cục gôm
    private static boolean drawErase;       // biến để xác định có hiện cục gôm hay không
    private boolean boardState; // biến xác định trạng thái của bảng vẽ, true là 2D, false là 3D
    private static Color gridColor; // biến xác định màu của lưới
    private int spacing;    // khoảng cách giữa 2 pixel
    private int rectSize;   // tổng độ rộng của pixel và spacing
    private int OX;     // trục tọa độ OX
    private int OY;     // trục tọa độ OY
    private boolean showAxis;   // biến xác định hiện/ẩn trục tọa độ
    private boolean showCoord;  // biến xác định hiện/ẩn tọa độ
    private static Point2D recStart;   // điểm bắt đầu của vùng chọn
    private static Point2D recEnd;     // điểm kết thúc của vùng chọn
}
