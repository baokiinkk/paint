package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class paint  extends JFrame {
    public int mX = -1;
    public int mY = -1;
    public int spacing = 1;
    public int rectSize = 5;
    public boolean[][] drawingBoard = new boolean[220][155]; //150x217


    private JPanel activity_main;
    private JPanel leftPanel;
    private JButton button3;
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
    }

    private void createUIComponents() {
        drawArea = new Board();
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        clearButton = new JButton("clearButton");
        clearButton.addActionListener(new myButton());
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

    private void clearPoint(int cordX, int cordY)
    {
        drawingBoard[cordX/(rectSize)][cordY/(rectSize)] = true;
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

    public class Move implements MouseMotionListener
    {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();
            setPoint(mX, mY);
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }

    public static class Click implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

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
                clearAll();
                repaint();
            }
        }
    }
}
