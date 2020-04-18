package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.util.Pair;
import java.util.ArrayList;
import com.company.Button;

public class paint  extends JFrame {
    private int mX = -1;
    private int mY = -1;
    private int spacing = 1;
    private int rectSize = 5;
    private Button choose = Button.MOUSE;
    private int xStart=-1;
    private int yStart=-1;
    private boolean firstClick = false;
    private boolean[][] drawingBoard = new boolean[220][155]; //150x217
    private ArrayList<Pair<Integer, Integer>> previousPoint = new ArrayList <Pair <Integer, Integer> > ();

    private JPanel activity_main;
    private JPanel leftPanel;
    private JButton duongThang;
    private JPanel mainArea;
    private JPanel drawArea;
    private JButton clearButton;
    private JButton button5;
    private JButton button6;
    private JTable table1;

    public paint() {
        this.setContentPane(activity_main);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Paint V1.0");
        this.setVisible(true);
        this.setSize(1280,800);
        this.setResizable(false);
        clearButton.addActionListener(new myButton());
        duongThang.addActionListener(new myButton());
    }

    private void createUIComponents() {
        drawArea = new Board();
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        clearButton = new JButton("clearButton");

    }


    public class Board extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.lightGray);
            g.fillRect(0,0, 1280, 800);
            //System.out.println(mX + "===" + mY);
            for (int i = 0; i < 217; i++)
            {
                for (int j = 0; j < 150; j++)
                {
                    if (drawingBoard[i][j] == true)
                    {
                        g.setColor(Color.BLACK);
                    }
                    else
                    {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(spacing + i*rectSize, spacing + j*rectSize, rectSize - spacing, rectSize - spacing);
                }
            }
        }
    }


    private void setPoint(int cordX, int cordY)
    {
        drawingBoard[cordX/(rectSize)][cordY/(rectSize)] = true;

    }
    private boolean isSettedPoint(int cordX, int cordY)
    {
        return drawingBoard[cordX/(rectSize)][cordY/(rectSize)];
    }

    private void clearPoint(int cordX, int cordY)
    {
        drawingBoard[cordX/(rectSize)][cordY/(rectSize)] = false;
    }
    private void MidpointLine(int x1, int y1, int x2, int y2)
    {
        float x=x1,y=y1,temp=(Math.abs(x2-x1)>=Math.abs(y2-y1))?Math.abs(x2-x1):Math.abs(y2-y1);
        int tx = (int)(x+0.5);
        int ty = (int)(y+0.5);
        if (!isSettedPoint(tx, ty))
        {
            setPoint(tx, ty);
            previousPoint.add(new Pair <Integer, Integer> (tx, ty));
        }

        for(int i=0;i<temp;i++)
        {
            x += (x2-x1)/temp;
            y += (y2-y1)/temp;
            tx = (int)(x+0.5);
            ty = (int)(y+0.5);
            if (!isSettedPoint(tx, ty))
            {
                setPoint(tx, ty);
                previousPoint.add(new Pair <Integer, Integer> (tx, ty));
            }
        }
        System.out.println(previousPoint.size());
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

    public class Move implements MouseMotionListener
    {

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

    public  class Click implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            switch (choose)
            {
                case LINE:
                {
                    xStart = mouseEvent.getX();
                    yStart= mouseEvent.getY();
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
        }
    }
}
