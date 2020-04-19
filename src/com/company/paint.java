package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.util.Pair;
import java.util.ArrayList;

import com.company.myFunction.*;

public class paint<Width> extends JFrame implements ActionListener {
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
    private Button choose = Button.MOUSE;

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
    private JButton zigzagButton;           // vẽ đường gấp khúc
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton colorButton;                  // chưa nghĩ ra
    private JButton mouseButton;            // nút vô dụng nhất, không có gì cả
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton circleButton;           // vẽ hình tròn
    private JButton eraseButton;            // xóa 1 vùng nhỏ
    private JButton colorBox;              // chưa nghĩ ra
    private JSlider slider1;                // kéo cho vui tay
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    // hàm chính
    public paint() {
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
        myFunction.clearArr(drawingBoard);
    }

    // hàm custom cho các thành phần trong form
    private void createUIComponents() {
        drawArea = new Board();
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        //clearButton = new JButton("clearButton");

    }


    // quản lí các pixel
    public class Board extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            //g.setColor(Color.lightGray); // set màu nền cho nền vẽ
            g.setColor(new Color(235, 235, 235)); // set màu nền cho nền vẽ
            g.fillRect(0,0, 1280, 800); // kích thước nền vẽ

            // set màu cho các pixel
            for (int i = 0; i < 217; i++)
            {
                for (int j = 0; j < 150; j++)
                {
                    // nếu bản trạng thái tồn tại thì set ô pixel đó màu đen còn ko thì màu trắng
                    if (nextDrawing[i][j])
                        g.setColor(nextPoint[i][j]);
                    else
                        g.setColor(drawingBoard[i][j]);
                    // cai lol này éo pk
                    g.fillRect(spacing + i*rectSize, spacing + j*rectSize, rectSize - spacing, rectSize - spacing);
                }
            }
        }
    }



    // thuật vẽ đường thẳng
    private void MidpointLine(int x1, int y1, int x2, int y2)
    {
        // tính khoảng cách 2 điểm
        float x=x1,y=y1,temp=(Math.abs(x2-x1)>=Math.abs(y2-y1))?Math.abs(x2-x1):Math.abs(y2-y1);

        // lm tron len điểm đó
        int tx = (int)(x+0.5);
        int ty = (int)(y+0.5);

        // lưu vị trí điểm và màu của điểm đó vào danh sách
        if (myFunction.isSafe(nextPoint, tx, ty))
        {
            nextDrawing[tx][ty] = true;
            nextPoint[tx][ty] = chooseColor;
        }


        // duyệt khoảng cách 2 điểm.
        //System.out.println("run");
        for(int i = 0; i < temp; i++)
        {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2-x1)/temp;
            y += (y2-y1)/temp;

            // lm tròn lên điểm đó
            tx = (int)(x+0.5);
            ty = (int)(y+0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (myFunction.isSafe(nextPoint, tx, ty))
            {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
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
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1)
                        MidpointLine(xStart, yStart, mX, mY);
                    xStart = mX;
                    yStart = mY;
                    repaint();
                    break;
                }
                case LINE: // vẽ đường thẳng
                {
                    myFunction.clearArr(nextDrawing);
                    mX = mouseEvent.getX()/rectSize;
                    mY = mouseEvent.getY()/rectSize;
                    if(xStart != -1 && yStart !=-1) {
                        MidpointLine(xStart,yStart,mX,mY);
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
                    myFunction.clearArr(nextPoint);
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
                    xStart=-1;
                    yStart=-1;
                    System.out.println("Released");
                    myFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    myFunction.clearArr(nextDrawing);
                    break;
                }
                case LINE:
                {
                    myFunction.mergePointColor(nextPoint, nextDrawing, drawingBoard);
                    myFunction.clearArr(nextDrawing);
                    repaint();
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
                choose = Button.MOUSE;
                repaint();
                break;
            }
            case "Clear": {
                //choose = Button.CLEAR;
                myFunction.clearArr(drawingBoard);
                repaint();
                break;
            }
            case "Line": {
                xStart = -1;
                yStart = -1;
                choose = Button.LINE;
                myFunction.clearArr(nextPoint);
                System.out.println(choose);
                break;
            }
            case "Pencil": {
                xStart = -1;
                yStart = -1;
                choose = Button.PENCIL;
                myFunction.clearArr(nextPoint);
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

        }
    }
}
