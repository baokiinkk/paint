package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;

public class paint  extends JFrame {
    public int mX = -1;
    public int mY = -1;
    public boolean[][] drawingBoard = new boolean[800][1280];
    private JPanel activity_main;
    private JPanel leftPanel;
    private JButton button3;
    private JPanel mainArea;
    private JPanel drawArea;
    private JButton button4;
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

        Move move = new Move();
        drawArea.addMouseMotionListener(move);
        Click click = new Click();
        drawArea.addMouseListener(click);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        drawArea = new Board();

    }


    public class Board extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            int spacing = 1;
            int rectSize = 5;
            g.setColor(Color.lightGray);
            g.fillRect(0,0, 1280, 800);

            for (int i = 0; i < 1280/rectSize; i++)
            {
                for (int j = 0; j < 800/rectSize; j++)
                {
                    g.setColor(Color.WHITE);
                    if (mX >= (i*rectSize) && mX < (spacing + (i+1)*rectSize))
                    {
                        System.out.println(mX + "===" + mY);

                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(spacing + i*rectSize, spacing + j*rectSize, rectSize - spacing, rectSize - spacing);
                }
            }
        }
    }
    public class Move implements MouseMotionListener
    {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();
            System.out.println(mX + ", " + mY);
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
}
