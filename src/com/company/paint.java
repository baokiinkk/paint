package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.util.Pair;
import java.util.ArrayList;

public class paint  extends JFrame {
    //toa do mouse
    private int mX = -1;
    private int mY = -1;

    //kich thuoc pixel va khoang cách giữa các pixel
    private int spacing = 1;
    private int rectSize = 5;

    // trạng thái
    private Button choose = Button.MOUSE;

    private int xStart=-1;
    private int yStart=-1;
    private boolean firstClick = false;

    //bảng trạng thái các pixel
    private boolean[][] drawingBoard = new boolean[220][155]; //150x217
    // chứa các điểm đã set
    private ArrayList<Pair<Integer, Integer>> previousPoint = new ArrayList <Pair <Integer, Integer> > ();

    // khai báo biến
    private JPanel activity_main;
    private JPanel leftPanel;
    private JButton duongThang;
    private JPanel mainArea;
    private JPanel drawArea;
    private JButton clearButton;
    private JButton pencil;
    private JButton button6;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JComboBox comboBox1;
    private JRadioButton radioButton1;
    private JSpinner spinner1;
    private JSlider slider1;
    private JTable table1;

    // hàm chính
    public paint() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close
        this.setTitle("Paint V1.0");
        this.setVisible(true); // set hiện hay k
        this.setSize(1280,800);
        this.setResizable(false);
        clearButton.addActionListener(new myButton());
        duongThang.addActionListener(new myButton());
        pencil.addActionListener(new myButton() );
    }

    // hàm custom
    private void createUIComponents() {
        drawArea = new Board();
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        clearButton = new JButton("clearButton");

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
                    if (drawingBoard[i][j] == true)
                    {
                        g.setColor(Color.BLACK);
                    }
                    else
                    {
                        g.setColor(Color.WHITE);
                    }
                    // cai lol này éo pk
                    g.fillRect(spacing + i*rectSize, spacing + j*rectSize, rectSize - spacing, rectSize - spacing);
                }
            }
        }
    }

    // set điểm pixel này đã được chọn
    private void setPoint(int cordX, int cordY)
    {
        drawingBoard[cordX/(rectSize)][cordY/(rectSize)] = true;

    }
    // kiểm tra pixel đã chọn chưa
    private boolean isSettedPoint(int cordX, int cordY)
    {
        return drawingBoard[cordX/(rectSize)][cordY/(rectSize)];
    }
    // bỏ chọn pixel đó
    private void clearPoint(int cordX, int cordY)
    {
        drawingBoard[cordX/(rectSize)][cordY/(rectSize)] = false;
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
            setPoint(tx, ty);
            previousPoint.add(new Pair <Integer, Integer> (tx, ty));
        }

        // duyệt khoảng cách 2 điểm.
        for(int i=0;i<temp;i++)
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
                setPoint(tx, ty);
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
            if(choose == Button.PENCIL)
            {
                mX = mouseEvent.getX();
                mY = mouseEvent.getY();
                setPoint(mX, mY);
                repaint();
            }

        }

        // di chuột
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            if(choose == Button.LINE)
            {
                if (firstClick == true)
                {


                }
                else
                {
                    clearPrevious();
                    mX = mouseEvent.getX();
                    mY = mouseEvent.getY();
                    if(xStart != -1 && yStart !=-1)
                        MidpointLine(xStart,yStart,mX,mY);
                    repaint();
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
                    // lấy tọa độ điểm bắt đầu
                    xStart = mouseEvent.getX();
                    yStart= mouseEvent.getY();

                    // công tắc
                    if (firstClick == true)
                    {
                        firstClick = false;
                    }
                    else
                    {
                        firstClick = true;
                        previousPoint.clear();
                    }
                    break;
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();

            setPoint(mX, mY);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

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
            if (nameButton.equals("clearButton"))
            {
                choose = Button.CLEAR;
                clearAll();
                repaint();
            }
            else if(nameButton.equals("duongThang"))
            {
                xStart=-1;
                yStart=-1;
                choose = Button.LINE;
                firstClick = true;
                previousPoint.clear();
            }
            else if(nameButton.equals("pencil"))
            {
                choose=Button.PENCIL;
            }
        }
    }
}
