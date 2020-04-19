package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

import com.company.myFunction.*;

public class paint<Width> extends JFrame {
    //TEST COLOR
    Random _rnd = new Random();
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
    private boolean[][] drawingBoard = new boolean[Width][Height]; //150x217
    private Color[][] colorBoard = new Color[Width][Height]; //150x217

    //biến màu đang chọn
    private Color chooseColor = Color.BLACK;

    // chứa các điểm đã set
    private ArrayList<Pair<Integer, Integer>> previousPoint = new ArrayList <Pair <Integer, Integer> > ();
    // chứa các điểm dùng để quay lại
    private ArrayList<Pair<Integer, Integer>> undoPoint = new ArrayList <Pair <Integer, Integer> > ();

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
    private JButton button3;                // chưa nghĩ ra
    private JButton mouseButton;            // nút vô dụng nhất, không có gì cả
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton circleButton;           // vẽ hình tròn
    private JButton eraseButton;            // xóa 1 vùng nhỏ
    private JButton AAAButton;            // chưa nghĩ ra
    private JComboBox comboBox1;            // cho ngầu
    private JRadioButton radioButton1;      // cho ngầu
    private JSpinner spinner1;              // cho ngầu
    private JSlider slider1;                // kéo cho vui tay
    private JTable table1;                  // thằng Bảo khai dư

    // hàm chính
    public paint() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close
        this.setTitle("Paint V1.0");
        this.setVisible(true); // set hiện hay k
        this.setSize(1280,800);
        this.setResizable(false);
        clearButton.addActionListener(new myButton());
        lineButton.addActionListener(new myButton());
        pencilButton.addActionListener(new myButton());
        mouseButton.addActionListener(new myButton());
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
            g.setColor(Color.lightGray); // set màu nền cho nền vẽ
            g.fillRect(0,0, 1280, 800); // kích thước nền vẽ

            // set màu cho các pixel
            for (int i = 0; i < 217; i++)
            {
                for (int j = 0; j < 150; j++)
                {
                    // nếu bản trạng thái tồn tại thì set ô pixel đó màu đen còn ko thì màu trắng
                    if (drawingBoard[i][j])
                        g.setColor(colorBoard[i][j]);
                    else
                        g.setColor(Color.WHITE);
                    // cai lol này éo pk
                    g.fillRect(spacing + i*rectSize, spacing + j*rectSize, rectSize - spacing, rectSize - spacing);
                }
            }
        }
    }

    // set điểm pixel này đã được chọn
    private void setPoint(int cordX, int cordY, Color color)
    {
        cordX /= rectSize;
        cordY /= rectSize;
        if (cordX >= 0 && cordX < Width && cordY >= 0 && cordY < Height)
        {
            drawingBoard[cordX][cordY] = true;
            colorBoard[cordX][cordY] = color;
        }

    }
    // kiểm tra pixel đã chọn chưa
    private boolean isSettedPoint(int cordX, int cordY)
    {
        cordX /= rectSize;
        cordY /= rectSize;
        if (cordX >= 0 && cordX < Width && cordY >= 0 && cordY < Height)
            return (drawingBoard[cordX][cordY]);
        return false;
    }
    // bỏ chọn pixel đó
    private void clearPoint(int cordX, int cordY)
    {
        cordX /= rectSize;
        cordY /= rectSize;
        if (cordX >= 0 && cordX < Width && cordY >= 0 && cordY < Height)
        {
            drawingBoard[cordX][cordY] = false;
            colorBoard[cordX][cordY] = Color.WHITE;
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

        // nếu như điểm này chưa được chọn thì set chọn điểm đó và lưu vào mảng preiouspoint
        if (!isSettedPoint(tx, ty))
        {
            setPoint(tx, ty, chooseColor);
            previousPoint.add(new Pair <Integer, Integer> (tx, ty));
        }

        // duyệt khoảng cách 2 điểm.
        for(int i = 0; i < temp; i++)
        {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2-x1)/temp;
            y += (y2-y1)/temp;

            // lm tròn lên điểm đó
            tx = (int)(x+0.5);
            ty = (int)(y+0.5);

            // kiểm tra nếu điểm đó chưa được chọn thì sẽ set điểm đó và lưu vào bảng vẽ đường thẳng
            if (!isSettedPoint(tx, ty))
            {
                setPoint(tx, ty, chooseColor);
                previousPoint.add(new Pair <Integer, Integer> (tx, ty));
            }
        }
    }


    private void clearAll()
    {
        //Arrays.fill(drawingBoard, false);
        for (int i = 0; i < 217; i++) {
            for (int j = 0; j < 150; j++)
            {
                drawingBoard[i][j] = false;
                colorBoard[i][j] = Color.WHITE;
            }
        }
    }

    private void clearPrevious()
    {
        for (Pair<Integer, Integer> point : previousPoint)
        {
            clearPoint(point.getKey(), point.getValue());
        }
        previousPoint.clear();
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
                    mX = mouseEvent.getX();
                    mY = mouseEvent.getY();
                    float r = _rnd.nextFloat();
                    float g = _rnd.nextFloat();
                    float b = _rnd.nextFloat();
                    chooseColor = new Color(r, g, b);
                    if(xStart != -1 && yStart !=-1)
                        MidpointLine(xStart,yStart,mX,mY);
                    xStart = mX;
                    yStart = mY;
                    repaint();
                    break;
                }
                case LINE: // vẽ đường thẳng
                {
                    clearPrevious();
                    mX = mouseEvent.getX();
                    mY = mouseEvent.getY();
                    if(xStart != -1 && yStart !=-1)
                        MidpointLine(xStart,yStart,mX,mY);
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
                    xStart = mouseEvent.getX();
                    yStart= mouseEvent.getY();
                    previousPoint.clear();
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
    public class myButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String nameButton = actionEvent.getActionCommand();
            System.out.println(nameButton);
            switch (nameButton)
            {
                case "Mouse":
                {
                    choose = Button.MOUSE;
                    repaint();
                    break;
                }
                case "Clear":
                {
                    choose = Button.CLEAR;
                    clearAll();
                    repaint();
                    break;
                }
                case "Line":
                {
                    xStart=-1;
                    yStart=-1;
                    choose = Button.LINE;
                    firstClick = true;
                    previousPoint.clear();
                    System.out.println(choose);
                    break;
                }
                case "Pencil":
                {
                    xStart=-1;
                    yStart=-1;
                    choose=Button.PENCIL;
                    System.out.println(choose);
                    break;
                }
            }
        }
    }
}
