package com.company.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.company.Button;
import com.company.xuli.xuliduongve.*;
import com.company.xuli.xuliduongve.Rectangle;

import static com.company.Button.*;

public class Paint extends JFrame implements ActionListener {

    // size ảnh
    private static int Width = 258;     //220
    private static int Height = 188;    //155
    Color colors = Color.BLACK;

    //biến trạng thái chọn loại bảng vẽ, true là vẽ 2D, false là vẽ 3D
    private boolean currentBoardState = true;

    //bảng trạng thái các pixel
    private Color[][] drawingBoard = new Color[Width][Height]; //150x217
    private JButton zigzagButton;           // vẽ đường gấp khúc
    private JButton colorButton;                  // chưa nghĩ ra
    private JButton circleButton;           // vẽ hình tròn

    //kích thước nét vẽ
    private int sizeLine = 1;
    //kich thuoc pixel va khoang cách giữa các pixel
    private int rectSize = 4;
    private int spacing = 1;
    private JButton eraseButton;            // xóa 1 vùng nhỏ

    // trạng thái nút đang chọn
    private com.company.Button choose = PENCIL;

    //trạng thái chuột ------------------------------------------------
    // khi kéo thả, startXY là điểm đầu tiên click chuột vào, mouseXY là điểm hiện tại
    private Point2D startXY;
    private Point2D mouseXY;

    // biến xác định click chuột đầu trong 2 lần click
    private boolean firstClick = false;
    //----------------------------------------------------------------
    private JSlider sizeSlider;                // kéo cho vui tay


    //biến màu đang chọn
    private Color chooseColor = Color.BLACK;

    //biến chứa chế độ đường thẳng đang chọn
    private lineMode chooseLineMode = lineMode.DEFAULT;
    private lineMode[] lineModeArr = {lineMode.DEFAULT, lineMode.DASH, lineMode.DOT, lineMode.DASHDOT, lineMode.DASHDOTDOT, lineMode.ARROW};
    // chứa các điểm đã set
    private Color[][] nextPoint = new Color[Width][Height]; //150x217
    private boolean[][] nextDrawing = new boolean[Width][Height]; //150x217
    // chứa các điểm dùng để quay lại
    private Color[][] undoPoint = new Color[Width][Height]; //150x217

    // khai báo biến
    private JPanel activity_main;
    private JPanel leftPanel;
    private JButton lineButton;
    private JPanel mainArea;
    private JPanel drawArea;
    private JButton clearButton;            // xóa sạch
    private JButton pencilButton;           // đè là vẽ
    private JButton undoButton;             // xóa thao tác vừa làm
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton redoButton;            // nút vô dụng nhất, không có gì cả
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton colorBox;              // chưa nghĩ ra
    private JButton ellipseButton;
    private JButton rotateButton;
    private JLabel showSize;
    private JComboBox comboBox1;
    private JButton Import;
    private JButton Export;
    private JButton settingButton;

    // hàm chính
    public void run() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close
        this.setTitle("Paint V1.0");
        this.setSize(1280, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true); // set hiện hay k

        clearButton.addActionListener(this);
        lineButton.addActionListener(this);
        pencilButton.addActionListener(this);
        redoButton.addActionListener(this);
        colorButton.addActionListener(this);
        undoButton.addActionListener(this);
        paintButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        ellipseButton.addActionListener(this);
        rotateButton.addActionListener(this);
        circleButton.addActionListener(this);
        ellipseButton.addActionListener(this);
        settingButton.addActionListener(this);

//        axisCheckBox2D.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent itemEvent) {
//                if (itemEvent.getSource() == axisCheckBox2D) {
//                    if (axisCheckBox2D.isSelected()) {
//                        ((Board) drawArea).showAxis();
//                    } else {
//                        //drawArea.hideAxis();
//                        ((Board) drawArea).hideAxis();
//                    }
//                    repaint();
//                }
//            }
//        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseLineMode = lineModeArr[comboBox1.getSelectedIndex()];
            }
        });
        //sizeSlider.addChangeListener((ChangeListener) this);
        MyFunction.clearArr(drawingBoard);
        MyFunction.clearArr(undoPoint);
    }

    // hàm custom cho các thành phần trong form
    private void createUIComponents() {
        drawArea = new Board(nextDrawing, nextPoint, drawingBoard, Width, Height, spacing, rectSize);
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        comboBox1 = new JComboBox<lineMode>(lineModeArr);
        mouseXY = new Point2D(-1, -1);
        startXY = new Point2D(-1, -1);

    }

    // click button
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String nameButton = actionEvent.getActionCommand();
        System.out.println(nameButton);
        switch (nameButton) {

            case "Redo": {
                Board.redo();
                undoButton.setEnabled(Board.ableUndo());
                redoButton.setEnabled(Board.ableRedo());
                repaint();
                break;
            }
            case "Clear": {
                //choose = Button.CLEAR;
                MyFunction.clearArr(drawingBoard);
                MyFunction.clearArr(nextPoint);
                repaint();
                break;
            }
            case "Line": {
                startXY.set(-1, -1);
//                xStart = -1;
//                yStart = -1;
                choose = LINE;
                break;
            }
            case "Rectangle": {
                startXY.set(-1, -1);
//                xStart = -1;
//                yStart = -1;
                choose = RECTANGLE;

                break;
            }
            case "Circle": {
                //startXY.set(-1, -1);
                startXY.X = -1;
                startXY.Y = -1;
//                xStart = -1;
//                yStart = -1;
                choose = Button.CIRCLE;
                break;
            }
            case "Pencil": {
                //startXY.set(-1, -1);
                startXY.X = -1;
                startXY.Y = -1;
//                xStart = -1;
//                yStart = -1;
                choose = PENCIL;
                MyFunction.clearArr(nextPoint);
                System.out.println(choose);
                break;
            }
            case "Color": {
                Color c = JColorChooser.showDialog(this, "Choose Color", chooseColor);
                if (c != null)
                    chooseColor = c;
                //System.out.println(colors);
                colorBox.setBackground(chooseColor);
                colorBox.setForeground(chooseColor);

                break;
            }
            case "Undo": {
                Board.undo();
                undoButton.setEnabled(Board.ableUndo());
                redoButton.setEnabled(Board.ableRedo());
                repaint();
                break;
            }
            case "Paint": {
                choose = PAINT;
                MyFunction.storePointColor(drawingBoard, nextPoint);
                MyFunction.clearArr(nextDrawing);
                break;
            }
            case "Ellipse": {
                startXY.set(-1, -1);
//                xStart = -1;
//                yStart = -1;
                choose = ELLIPSE;
                break;
            }
            case "Rotate": {
                startXY.set(-1, -1);
                choose = ROTATE;
//                sizeLine++;
//                showSize.setText("Size: " + sizeLine);
                break;
            }
            case "Setting": {
                System.out.println("Yes");
                Setting dialog = new Setting(currentBoardState);
                dialog.pack();
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
                currentBoardState = dialog.getState();
                System.out.println(currentBoardState);
                break;
            }
        }
    }

    // di chuột
    public class Move implements MouseMotionListener {

        // nắm kéo thả chuột
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            switch (choose) {
                case PENCIL: {
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    //if (xStart != -1 && yStart != -1)
                    if (startXY.X != -1 && startXY.Y != -1)
                        new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startXY, mouseXY, lineMode.DEFAULT);
                    startXY.set(mouseXY);
//                    xStart = mX;
//                    yStart = mY;
                    drawArea.repaint();
                    break;
                }
                case LINE: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    if (startXY.X != -1 && startXY.Y != -1)
                        new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startXY, mouseXY, chooseLineMode);
                    repaint();
                    break;
                }
                case RECTANGLE: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    if (startXY.X != -1 && startXY.Y != -1) {
                        Rectangle rec = new Rectangle(nextDrawing, nextPoint, chooseColor);
                        rec.setRectangle(startXY, mouseXY, chooseLineMode);
                        rec.draw();
                        Board.setNowHinhHoc(rec);
                    }
                    repaint();
                    break;
                }
                case CIRCLE: {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    if (startXY.X != -1 && startXY.Y != -1) {
                        new Circle(nextDrawing, nextPoint, chooseColor).drawingCircle(startXY, mouseXY, chooseLineMode);
                    }
                    repaint();
                    break;
                }
                case ELLIPSE: {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    if (startXY.X != -1 && startXY.Y != -1) {
                        if (mouseEvent.isShiftDown()) {
                            new Circle(nextDrawing, nextPoint, chooseColor).drawingCircle(startXY, mouseXY, chooseLineMode);
                        } else {
                            new Ellipse(nextDrawing, nextPoint, chooseColor).drawEllipse(startXY, mouseXY, chooseLineMode);
                        }
                    }
                    repaint();
                    break;
                }
                case ROTATE: {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    if (startXY.X != -1 && startXY.Y != -1) {
                        //System.out.println(Board.now.tag);
                        Board.rotateNow(startXY, mouseXY);
                        repaint();
                    }
                    break;
                }
            }

        }

        // di chuột
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            switch (choose) {
                case LINE: // vẽ đường thẳng
                {
                    // nothing
                    break;
                }
            }

        }
    }

    // click chuột
    public class Click implements MouseListener {

        // click chuột vào trạng thái nào
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            switch (choose) {
                case LINE: // vẽ đường thẳng
                {
                    // nothing
                    break;
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                switch (choose) {
                    case PENCIL: {
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        nextDrawing[mouseXY.X][mouseXY.Y] = true;
                        nextPoint[mouseXY.X][mouseXY.Y] = chooseColor;
                        startXY.set(mouseXY);
                        drawArea.repaint();
                        break;
                    }
                    case LINE: // vẽ đường thẳng
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                        xStart = mouseEvent.getX() / rectSize;
//                        yStart = mouseEvent.getY() / rectSize;
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case RECTANGLE: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case PAINT: {
                        System.out.println("Pressed");
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.paintColor(nextPoint, nextDrawing, mouseXY, chooseColor);
                        break;
                    }
                    case CIRCLE: {
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case ELLIPSE: {
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case ROTATE: {
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                }
            } else {
//                MyFunction.storePointColor(drawingBoard, nextPoint);
//                MyFunction.clearArr(nextDrawing);
//                repaint();
            }

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                switch (choose) {
                    case PENCIL: // vẽ đường thẳng
                    {
                        //nothing
                        startXY.set(-1, -1);
                        Board.applyNow();
                        break;
                    }
                    case LINE: {
                        MyFunction.storePointColor(drawingBoard, undoPoint);
                        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                        MyFunction.clearArr(nextDrawing);
                        repaint();
                        break;
                    }
                    case RECTANGLE: {
                        MyFunction.storePointColor(drawingBoard, undoPoint);
                        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                        MyFunction.clearArr(nextDrawing);
                        repaint();
                        break;
                    }
                    case PAINT: {
                        MyFunction.storePointColor(drawingBoard, undoPoint);
                        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                        MyFunction.storePointColor(drawingBoard, nextPoint);
                        MyFunction.clearArr(nextDrawing);
                        repaint();
                        break;
                    }
                    case CIRCLE:{
                        MyFunction.storePointColor(drawingBoard, undoPoint);
                        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                        MyFunction.clearArr(nextDrawing);
                        repaint();
                        break;
                    }
                    case ELLIPSE: {
                        MyFunction.storePointColor(drawingBoard, undoPoint);
                        MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                        MyFunction.clearArr(nextDrawing);
                        repaint();
                        break;
                    }
                }
                undoButton.setEnabled(Board.ableUndo());
                redoButton.setEnabled(Board.ableRedo());
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }


    }
}
