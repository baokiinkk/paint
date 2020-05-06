package com.company.main;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

import com.company.xuli.xuliduongve.*;
import com.company.xuli.xuliduongve.Rectangle;

import static com.company.Button.*;

public class Paint extends JFrame implements ActionListener {

    // size ảnh
    private static int Width = 220;     //220
    private static int Height = 155;    //155
    Color colors = Color.BLACK;
    //toa do mouse
    private int mX = -1;
    private int rectSize = 5;
    //bảng trạng thái các pixel
    private Color[][] drawingBoard = new Color[Width][Height]; //150x217
    private JButton zigzagButton;           // vẽ đường gấp khúc
    private JButton colorButton;                  // chưa nghĩ ra
    private JButton circleButton;           // vẽ hình tròn
    private int mY = -1;

    //kích thước nét vẽ
    private int sizeLine = 1;
    //kich thuoc pixel va khoang cách giữa các pixel
    private int spacing = 1;
    private JButton eraseButton;            // xóa 1 vùng nhỏ

    // trạng thái nút đang chọn
    private com.company.Button choose = MOUSE;

    // biến phụ vị trí trỏ chuột
    private int xStart = -1;
    private int yStart = -1;
    // biến xác định click chuột đầu giữa 2 lần click, (click để chọn)
    private boolean firstClick = false;
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
    private JCheckBox axesCheckBox;
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton mouseButton;            // nút vô dụng nhất, không có gì cả
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton colorBox;              // chưa nghĩ ra
    private JButton button1;
    private JButton button2;
    private JLabel showSize;
    private JComboBox comboBox1;

    // hàm chính
    public void run() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close
        this.setTitle("Paint V1.0");
        this.setVisible(true); // set hiện hay k
        this.setSize(1280, 800);
        this.setResizable(false);
        clearButton.addActionListener(this);
        lineButton.addActionListener(this);
        pencilButton.addActionListener(this);
        mouseButton.addActionListener(this);
        colorButton.addActionListener(this);
        undoButton.addActionListener(this);
        paintButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseLineMode = lineModeArr[comboBox1.getSelectedIndex()];
                System.out.println(chooseLineMode);
            }
        });
        //sizeSlider.addChangeListener((ChangeListener) this);
        showSize.setText("Size: " + sizeLine);
        MyFunction.clearArr(drawingBoard);
        MyFunction.clearArr(undoPoint);
    }

    // hàm custom cho các thành phần trong form
    private void createUIComponents() {
        drawArea = new Board(nextDrawing, nextPoint, drawingBoard, Width, Height, spacing, rectSize);
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        comboBox1 = new JComboBox<lineMode>(lineModeArr);
        //new HinhHoc(nextDrawing, nextPoint, chooseColor);
        //
        //clearButton = new JButton("clearButton");

    }

    // click button
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String nameButton = actionEvent.getActionCommand();
        System.out.println(nameButton);
        switch (nameButton) {

            case "Mouse": {
                choose = MOUSE;
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
                xStart = -1;
                yStart = -1;
                choose = LINE;
                break;
            }
            case "Rectangle": {
                xStart = -1;
                yStart = -1;
                choose = RECTANGLE;

                break;
            }
            case "Pencil": {
                xStart = -1;
                yStart = -1;
                choose = PENCIL;
                MyFunction.clearArr(nextPoint);
                System.out.println(choose);
                break;
            }
            case "Color": {
                Color c = JColorChooser.showDialog(this, "choose color", Color.RED);
                if (c != null)
                    chooseColor = c;
                //System.out.println(colors);
                colorBox.setBackground(chooseColor);
                colorBox.setForeground(chooseColor);

                break;
            }
            case "Undo": {
                MyFunction.storePointColor(undoPoint, drawingBoard);
                repaint();
                break;
            }
            case "Paint": {
                choose = PAINT;
                MyFunction.storePointColor(drawingBoard, nextPoint);
                MyFunction.clearArr(nextDrawing);
                break;
            }
            case "Button1": {
                sizeLine--;
                showSize.setText("Size: " + sizeLine);
                break;
            }
            case "Button2": {
                sizeLine++;
                showSize.setText("Size: " + sizeLine);
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
                    mX = mouseEvent.getX() / rectSize;
                    mY = mouseEvent.getY() / rectSize;
                    if (xStart != -1 && yStart != -1)
                        new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(xStart, yStart, mX, mY, lineMode.DEFAULT);
                    xStart = mX;
                    yStart = mY;
                    drawArea.repaint();
                    break;
                }
                case LINE: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX() / rectSize;
                    mY = mouseEvent.getY() / rectSize;
                    if (xStart != -1 && yStart != -1) {
                        new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(xStart, yStart, mX, mY, chooseLineMode);
                    }
                    repaint();
                    break;
                }
                case RECTANGLE: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1) {
                        new Rectangle(nextDrawing, nextPoint, chooseColor).PaintRectangle(xStart, yStart, mX, mY, chooseLineMode);
                    }
                    repaint();
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
            switch (choose)
            {
                case LINE: // vẽ đường thẳng
                {
                    // lấy tọa độ điểm bắt đầu
                    xStart = mouseEvent.getX()/rectSize;
                    yStart= mouseEvent.getY()/rectSize;
                    MyFunction.clearArr(nextPoint);
                    break;
                }
                case RECTANGLE: // vẽ hinh cn
                {
                    // lấy tọa độ điểm bắt đầu
                    xStart = mouseEvent.getX() / rectSize;
                    yStart = mouseEvent.getY() / rectSize;
                    MyFunction.clearArr(nextPoint);
                    break;
                }
                case PAINT: {
                    System.out.println("Pressed");
                    mX = mouseEvent.getX() / rectSize;
                    mY = mouseEvent.getY() / rectSize;
                    MyFunction.paintColor(nextPoint, nextDrawing, mX, mY, chooseColor);
                    break;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            switch (choose)
            {
                case PENCIL: // vẽ đường thẳng
                {
                    //nothing
                    xStart = -1;
                    yStart = -1;
                    System.out.println("Released");
                    MyFunction.storePointColor(drawingBoard, undoPoint);
                    MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    MyFunction.clearArr(nextDrawing);
                    break;
                }
                case LINE:
                {
                    MyFunction.storePointColor(drawingBoard, undoPoint);
                    MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    MyFunction.clearArr(nextDrawing);
                    repaint();
                    break;
                }
                case RECTANGLE:
                {
                    MyFunction.storePointColor(drawingBoard, undoPoint);
                    MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    MyFunction.clearArr(nextDrawing);
                    repaint();
                    break;
                }
                case PAINT:
                {
                    MyFunction.storePointColor(drawingBoard, undoPoint);
                    MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    MyFunction.storePointColor(drawingBoard, nextPoint);
                    MyFunction.clearArr(nextDrawing);
                    repaint();
                    break;
                }
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
