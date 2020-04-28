package com.company.xuli.xuliduongve;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel
{
    private boolean[][] nextDrawing;
    private Color[][] nextPoint;
    private Color[][] drawingBoard;
    private int spacing;
    private int rectSize;
    private int width;
    private int height;
    public Board(boolean[][] nextDrawing,Color[][] nextPoint,Color[][] drawingBoard, int width, int height, int spacing,int rectSize)
    {
        this.drawingBoard = drawingBoard;
        this.nextDrawing = nextDrawing;
        this.nextPoint = nextPoint;
        this.rectSize = rectSize;
        this.spacing = spacing;
        this.width = width;
        this.height = height;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //g.setColor(Color.lightGray); // set màu nền cho nền vẽ
        g.setColor(new Color(235, 235, 235)); // set màu nền cho nền vẽ
        g.fillRect(0,0, 1280, 800); // kích thước nền vẽ

        // set màu cho các pixel
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
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
