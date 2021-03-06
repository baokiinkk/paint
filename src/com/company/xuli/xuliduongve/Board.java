package com.company.xuli.xuliduongve;

import com.company.main.Paint;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Board extends JPanel {

    private static String[][] coordBoard;  // bảng này sẽ lưu các tọa độ của hình

    private static int width;   // độ rộng của bảng vẽ
    private static int height;  // độ cao của bảng vẽ

    // tính góc tạo bởi 2 vector sau đó xoay, xoay này là xoay nháp, tức là xoay làm mẫu để người dùng
    // xác định góc thích hợp
    public static void rotateNow(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(now.center, start);
        Vector2D b = new Vector2D(now.center, end);
        double alpha = a.alphaVector(b);

        //System.out.println(alpha);
        switch (now.tag) {
            case LINE:{
                ((Line) now).rotate(alpha);
                break;
            }
            case RECTANGLE: {
                ((Rectangle) now).rotate(alpha);
                break;
            }
            case ELLIPSE:{
                ((Ellipse) now).rotate(alpha);
                break;
            }
            case TRIANGLE:{
                ((Triangle) now).rotate(alpha);
                break;
            }
            case PARALLELOGRAM:{
                ((Parallelogram) now).rotate(alpha);
                break;
            }
        }
    }

    public static void moveNow(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(start, end);
        //System.out.println("---" + now.tag);
        switch (now.tag) {
            case LINE:{
                ((Line) now).move(a);
                break;
            }
            case RECTANGLE: {
                ((Rectangle) now).move(a);
                break;
            }
            case ELLIPSE:{
                ((Ellipse) now).move(a);
                break;
            }
            case TRIANGLE:{
                ((Triangle) now).move(a);
                break;
            }
            case PARALLELOGRAM:{
                ((Parallelogram) now).move(a);
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
            case LINE:{
                ((Line) now).applyRotate(alpha);
                break;
            }
            case RECTANGLE: {
                ((Rectangle) now).applyRotate(alpha);
                break;
            }
            case ELLIPSE: {
                ((Ellipse) now).applyRotate(alpha);
                break;
            }
            case TRIANGLE:{
                ((Triangle) now).applyRotate(alpha);
                break;
            }
            case PARALLELOGRAM:{
                ((Parallelogram) now).applyRotate(alpha);
                break;
            }
        }
    }

    public static void applyMove(Point2D start, Point2D end) {
        Vector2D a = new Vector2D(start, end);
        //System.out.println(alpha);
        switch (now.tag) {
            case LINE:{
                ((Line) now).applyMove(a);
                break;
            }
            case RECTANGLE: {
                ((Rectangle) now).applyMove(a);
                break;
            }
            case ELLIPSE: {
                ((Ellipse) now).applyMove(a);
                break;
            }
            case TRIANGLE:{
                ((Triangle) now).applyMove(a);
                break;
            }
            case PARALLELOGRAM: {
                ((Parallelogram) now).applyMove(a);
                break;
            }
            case SELECT: {
                ((Select) now).applyMove(a);
                break;
            }
        }
    }

    private static Stack<Color[][]> undoBoard;  // stack lưu các trạng thái của bảng vẽ để undo
    private static Stack<Color[][]> redoBoard;  // stack lưu .... để redo
    private static String[][] nextCoord;  // bảng này sẽ lưu các tọa độ của hình

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


    public static void nextDo() {
        Color[][] tmpBoard = new Color[width][height];
        MyFunction.storePointColor(drawingBoard, tmpBoard);
        undoBoard.push(tmpBoard);
    }

    private static Stack<String[][]> redoCoord;  // stack lưu .... để redo

    public Board(boolean[][] nextDrawing, Color[][] nextPoint, Color[][] drawingBoard, String[][] Coord, String[][] nCoord) {
        Board.drawingBoard = drawingBoard;
        Board.nextDrawing = nextDrawing;
        Board.nextPoint = nextPoint;
        this.rectSize = Paint.rectSize;
        this.spacing = Paint.spacing;
        coordBoard = Coord;
        nextCoord = nCoord;
        width = Paint.Width;
        height = Paint.Height;
        OX = spacing + width / 2 * rectSize + (rectSize - spacing) / 2;
        OY = spacing + height / 2 * rectSize + (rectSize - spacing) / 2;
        undoBoard = new Stack<Color[][]>();
        redoBoard = new Stack<Color[][]>();
        undoCoord = new Stack<String[][]>();
        redoCoord = new Stack<String[][]>();
        boardState = true;
        showAxis = false;
        showCoord = false;
        gridColor = new Color(235, 235, 235);
        recStart = new Point2D(-1, -1);
        recEnd = new Point2D(-1, -1);
    }

    //hàm kiểm tra xem có thể Undo nữa hay không
    public static boolean ableUndo() {
        return (!undoBoard.empty());
    }

    // tương tự hàm trên nhưng là redo
    public static boolean ableRedo() {
        return (!redoBoard.empty());
    }

    // sau khi xoay xong hình, apply dùng để ghim hình vừa xoay vào drawingBoard
    public static void applyNow() {
        redoBoard.clear();
        Color[][] tmpBoard = new Color[width][height];
        String[][] tmpCoord = new String[width][height];
        MyFunction.storePointColor(drawingBoard, tmpBoard);
        MyFunction.storePointColor(coordBoard, tmpCoord);
        undoBoard.push(tmpBoard);
        undoCoord.push(tmpCoord);
        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
        MyFunction.clearArr(nextDrawing);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (nextCoord[i][j] != null) {
                    coordBoard[i][j] = nextCoord[i][j];
                }
            }
        }
        MyFunction.clearArr(nextCoord);
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

    public static void select(Point2D start, Point2D end) {
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

    // mỗi lần vẽ xong sẽ ghim hình vừa vẽ vào drawingBoard, hàm previousDo sẽ gỡ ghim hình vừa
    // vẽ để có thể mang ra xoay, zoom,..
    public static void previousDo() {
        if (!undoBoard.empty()) {
            Color[][] tmpBoard = new Color[width][height];
            MyFunction.storePointColor(drawingBoard, tmpBoard);
            MyFunction.storePointColor(undoBoard.pop(), drawingBoard);
        }
        if (!undoCoord.empty()) {
            String[][] tmpCoord = new String[width][height];
            MyFunction.storePointColor(coordBoard, tmpCoord);
            MyFunction.storePointColor(undoCoord.pop(), coordBoard);

        }
    }

    // quay lại trạng thái trước đó, push trạng thái hiện tại vào redo
    public static void undo() {
        if (!undoBoard.empty()) {
            Color[][] tmpBoard = new Color[width][height];

            MyFunction.storePointColor(drawingBoard, tmpBoard);
            redoBoard.push(tmpBoard);
            MyFunction.storePointColor(undoBoard.pop(), drawingBoard);
        }
        if (!undoCoord.empty()) {
            String[][] tmpCoord = new String[width][height];
            MyFunction.storePointColor(coordBoard, tmpCoord);
            redoCoord.push(tmpCoord);
            MyFunction.storePointColor(undoCoord.pop(), coordBoard);
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
        if (!redoCoord.empty()) {
            String[][] tmpCoord = new String[width][height];
            MyFunction.storePointColor(coordBoard, tmpCoord);
            undoCoord.push(tmpCoord);
            MyFunction.storePointColor(redoCoord.pop(), coordBoard);
        }
    }

    private static Stack<String[][]> undoCoord;  // stack lưu các trạng thái của bảng vẽ để undo

    public static void SymOXNow() {
        if (now != null) {
            switch (now.tag) {
                case LINE:{
//                    ((Line) now).draw();
                    ((Line) now).SymOX();
                    break;
                }
                case RECTANGLE: {
//                    ((Rectangle) now).draw();
                    ((Rectangle) now).SymOX();
                    break;
                }
                case ELLIPSE: {
//                    ((Ellipse) now).draw();
                    ((Ellipse) now).SymOX();
                    break;
                }
                case TRIANGLE: {
//                    ((Triangle) now).draw();
                    ((Triangle) now).SymOX();
                    break;
                }
                case PARALLELOGRAM: {
//                    ((Parallelogram) now).draw();
                    ((Parallelogram) now).SymOX();
                    break;
                }
            }
        }
    }


    public static void SymOYNow() {
        if (now != null) {
            switch (now.tag) {
                case LINE:{
//                    ((Line) now).draw();
                    ((Line) now).SymOY();
                    break;
                }
                case RECTANGLE: {
//                    ((Rectangle) now).draw();
                    ((Rectangle) now).SymOY();
                    break;
                }
                case ELLIPSE: {
//                    ((Ellipse) now).draw();
                    ((Ellipse) now).SymOY();
                    break;
                }
                case TRIANGLE: {
//                    ((Triangle) now).draw();
                    ((Triangle) now).SymOY();
                    break;
                }
                case PARALLELOGRAM: {
//                    ((Parallelogram) now).draw();
                    ((Parallelogram) now).SymOY();
                    break;
                }
            }
        }
    }

    public static void SymPointNow(Point2D point) {
        if (now != null) {
            switch (now.tag) {
                case LINE:{
//                    ((Line) now).draw();
                    ((Line) now).SymP(point);
                    break;
                }
                case RECTANGLE: {
//                    ((Rectangle) now).draw();
                    ((Rectangle) now).SymP(point);
                    break;
                }
                case ELLIPSE: {
//                    ((Ellipse) now).draw();
                    ((Ellipse) now).SymP(point);
                    break;
                }
                case TRIANGLE: {
//                    ((Triangle) now).draw();
                    ((Triangle) now).SymP(point);
                    break;
                }
                case PARALLELOGRAM: {
//                    ((Parallelogram) now).draw();
                    ((Parallelogram) now).SymP(point);
                    break;
                }
            }
        }
    }

    public void setSpacing(int num) {
        spacing = num;
    }

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

    public void setRectSize(int num) {
        rectSize = num;
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
        } else {
            if (showAxis) {
                g.setColor(Color.RED);
                g.drawLine(1280, OY, OX, OY);
                g.drawLine(OX, 0, OX, OY);
                g.drawLine(OX - 800, OY + 800, OX, OY);


//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setColor(Color.pink);
//
//                float[] dashingPattern1 = {2f, 2f};
//                Stroke stroke1 = new BasicStroke(2f, BasicStroke.CAP_BUTT,
//                        BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 3.0f);
//
//                g2d.setStroke(stroke1);
//                g2d.drawLine(0, OY, OX, OY);
//                g2d.drawLine(OX, 800, OX, OY);
//                g2d.drawLine(OX + 800, OY - 800, OX, OY);


            }
        }

        if (showCoord)  // hiện tọa độ
        {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (coordBoard[i][j] != null) {
                        g.setColor(drawingBoard[i][j]);
                        if (drawingBoard[i][j].equals(Color.WHITE)) {
                            g.setColor(Color.BLACK);
                        }
                        int x = spacing + i * rectSize + 5;
                        int y = spacing + j * rectSize - 2;
                        if (y < 15)
                            y = 15;
                        if (x + coordBoard[i][j].length() * 6 > (width) * rectSize) {
                            x = (width) * rectSize - coordBoard[i][j].length() * 6;
                        }
                        //System.out.println(x + " " + y);
                        g.drawString(coordBoard[i][j], x, y);
                    }
                }
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
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.pink);

            float[] dashingPattern1 = {2f, 2f};
            Stroke stroke1 = new BasicStroke(2f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 3.0f);

            g2d.setStroke(stroke1);
            g2d.drawRect(recStart.X * rectSize, recStart.Y * rectSize,
                    Math.abs(recStart.X - recEnd.X) * rectSize, Math.abs(recStart.Y - recEnd.Y) * rectSize);
            recStart.set(-1, -1);
            recEnd.set(-1, -1);

        }

    }
}
