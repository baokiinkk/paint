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
    public Board(boolean[][] nextDrawing,Color[][] nextPoint,Color[][] drawingBoard,int spacing,int rectSize)
    {
        this.drawingBoard =drawingBoard;
        this.nextDrawing=nextDrawing;
        this.nextPoint=nextPoint;
        this.rectSize=rectSize;
        this.spacing=spacing;
    }
    public void cc()
    {

    }
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
