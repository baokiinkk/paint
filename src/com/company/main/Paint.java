package com.company.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.company.xuli.xuliduongve.*;
import com.company.xuli.xuliduongve.Rectangle;

import static com.company.Button.*;

public class Paint<Width> extends JFrame implements ActionListener
{

    // hàm chính
    public void run() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close
        this.setTitle("Paint V1.0");
        this.setVisible(true); // set hiện hay k
        this.setSize(1280,800);
        this.setResizable(false);
        clearButton.addActionListener(this);
        lineButton.addActionListener(this);
        pencilButton.addActionListener(this);
        mouseButton.addActionListener(this);
        colorButton.addActionListener(this);
        undoButton.addActionListener(this);
        paintButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        muiTenButton.addActionListener(this);
        a2chamGachButton.addActionListener(this);
        MyFunction.clearArr(drawingBoard);
        MyFunction.clearArr(undoPoint);
    }

    // hàm custom cho các thành phần trong form
    private void createUIComponents() {
        drawArea = new Board(nextDrawing,nextPoint,drawingBoard,spacing,rectSize);
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        new HinhHoc(nextDrawing,nextPoint,chooseColor);
        //clearButton = new JButton("clearButton");

    }

    // di chuột
    public class Move implements MouseMotionListener
    {

        // nắm kéo thả chuột
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            switch (choose)
            {
                case PENCIL:
                {
                    MyFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1) {
                        new Line(nextDrawing,nextPoint,chooseColor).gach(xStart,yStart,mX,mY);
                    }
                    repaint();
                    break;
                }
                case CIRCLE:
                {
                    MyFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1) {
                        new Line(nextDrawing,nextPoint,chooseColor).haichamGach(xStart,yStart,mX,mY);
                    }
                    repaint();
                    break;
                }
                case ZIGZAG:
                {
                    MyFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1) {
                        new Line(nextDrawing,nextPoint,chooseColor).muiTen(xStart,yStart,mX,mY);
                    }
                    repaint();
                    break;
                }
                case LINE: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1) {
                        new Line(nextDrawing,nextPoint,chooseColor).MidpointLine(xStart,yStart,mX,mY);
                    }
                    repaint();
                    break;
                }
                case PAINT: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1) {
                        new Line(nextDrawing,nextPoint,chooseColor).chamGach(xStart,yStart,mX,mY);
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
                        new Rectangle(nextDrawing,nextPoint,chooseColor).PaintRectangle(xStart,yStart,mX,mY);
                    }
                    repaint();
                    break;
                }
            }

        }

        // di chuột
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            switch (choose)
            {
                case LINE: // vẽ đường thẳng
                {
                    // nothing
                    break;
                }
            }

        }
    }


    // click chuột
    public  class Click implements MouseListener
    {

        // click chuột vào trạng thái nào
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            switch (choose)
            {
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
                case CIRCLE: // vẽ đường thẳng
                {
                    // lấy tọa độ điểm bắt đầu
                    xStart = mouseEvent.getX()/rectSize;
                    yStart= mouseEvent.getY()/rectSize;
                    MyFunction.clearArr(nextPoint);
                    break;
                }
                case ZIGZAG: // vẽ đường thẳng
                {
                    // lấy tọa độ điểm bắt đầu
                    xStart = mouseEvent.getX()/rectSize;
                    yStart= mouseEvent.getY()/rectSize;
                    MyFunction.clearArr(nextPoint);
                    break;
                }
                case PENCIL: // vẽ đường thẳng
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
                    xStart = mouseEvent.getX()/rectSize;
                    yStart= mouseEvent.getY()/rectSize;
                    MyFunction.clearArr(nextPoint);
                    break;
                }
                case PAINT:
                {
                    xStart = mouseEvent.getX()/rectSize;
                    yStart= mouseEvent.getY()/rectSize;
                    MyFunction.clearArr(nextPoint);
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
                    MyFunction.storePointColor(drawingBoard, undoPoint);
                    MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    MyFunction.clearArr(nextDrawing);
                    repaint();
                    break;
                }
                case CIRCLE: // vẽ đường thẳng
                {
                    //nothing
                    MyFunction.storePointColor(drawingBoard, undoPoint);
                    MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    MyFunction.clearArr(nextDrawing);
                    repaint();
                    break;
                }
                case ZIGZAG: // vẽ đường thẳng
                {
                    //nothing
                    MyFunction.storePointColor(drawingBoard, undoPoint);
                    MyFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    MyFunction.clearArr(nextDrawing);
                    repaint();
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
            case "netGach": {
                xStart = -1;
                yStart = -1;
                choose = PENCIL;
                MyFunction.clearArr(nextPoint);
                System.out.println(choose);
                break;
            }
            case "Color":
            {
                Color c = JColorChooser.showDialog(this,"choose color",Color.RED);
                if(c != null)
                    chooseColor = c;
                //System.out.println(colors);
                colorBox.setBackground(chooseColor);
                colorBox.setForeground(chooseColor);

                break;
            }
            case "Undo":
            {
                MyFunction.storePointColor(undoPoint, drawingBoard);
                repaint();
                break;
            }
            case "chamGach":
            {
                xStart = -1;
                yStart = -1;
                choose = PAINT;
                break;
            }
            case "muiTen":
            {
                xStart = -1;
                yStart = -1;
                choose = ZIGZAG;
                break;
            }
            case "2chamGach":
            {
                xStart = -1;
                yStart = -1;
                choose = CIRCLE;
                break;
            }
        }
    }



    Color colors = Color.BLACK;
    // size ảnh
    private static int Width = 220;
    private static int Height = 155;
    //toa do mouse
    private int mX = -1;
    private int mY = -1;

    //kich thuoc pixel va khoang cách giữa các pixel
    private int spacing = 1;
    private int rectSize = 5;

    // trạng thái nút đang chọn
    private com.company.Button choose = MOUSE;

    // biến phụ vị trí trỏ chuột
    private int xStart=-1;
    private int yStart=-1;
    // biến xác định click chuột đầu giữa 2 lần click, (click để chọn)
    private boolean firstClick = false;

    //bảng trạng thái các pixel
    private Color[][] drawingBoard = new Color[Width][Height]; //150x217

    //biến màu đang chọn
    private Color chooseColor = Color.BLACK;

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
    private JButton muiTenButton;           // vẽ đường gấp khúc
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton colorButton;                  // chưa nghĩ ra
    private JButton mouseButton;            // nút vô dụng nhất, không có gì cả
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton a2chamGachButton;           // vẽ hình tròn
    private JButton colorBox;              // chưa nghĩ ra
    private JSlider slider1;                // kéo cho vui tay
}
