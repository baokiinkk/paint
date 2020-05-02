package com.company.xuli.xuliduongve;

import java.awt.*;

public class Line extends HinhHoc {

    public Line(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }
    public void MidpointLine(int x1, int y1, int x2, int y2)
    {
        // tính khoảng cách 2 điểm
        float x=x1,y=y1,temp=(Math.abs(x2-x1)>=Math.abs(y2-y1))?Math.abs(x2-x1):Math.abs(y2-y1);

        // lm tron len điểm đó
        int tx = (int)(x+0.5);
        int ty = (int)(y+0.5);

        // lưu vị trí điểm và màu của điểm đó vào danh sách

        if (MyFunction.isSafe(nextPoint, tx, ty))
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
                if (MyFunction.isSafe(nextPoint, tx, ty))
                {
                    nextDrawing[tx][ty] = true;
                    nextPoint[tx][ty] = chooseColor;
                }
        }
    }

    public void gach(int x1, int y1, int x2, int y2)
    {
        // tính khoảng cách 2 điểm
        float x=x1,y=y1,temp=(Math.abs(x2-x1)>=Math.abs(y2-y1))?Math.abs(x2-x1):Math.abs(y2-y1);

        // lm tron len điểm đó
        int tx = (int)(x+0.5);
        int ty = (int)(y+0.5);

        // lưu vị trí điểm và màu của điểm đó vào danh sách

        if (MyFunction.isSafe(nextPoint, tx, ty))
        {
            nextDrawing[tx][ty] = true;
            nextPoint[tx][ty] = chooseColor;
        }


        // duyệt khoảng cách 2 điểm.
        //System.out.println("run");
        for(int i = 1; i < temp; i++)
        {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2-x1)/temp;
            y += (y2-y1)/temp;

            // lm tròn lên điểm đó
            tx = (int)(x+0.5);
            ty = (int)(y+0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty) &&( i%5 == 1 || i%5==2 || i%5==3 || i%5==4) )
            {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
    }
    public void chamGach(int x1, int y1, int x2, int y2)
    {
        // tính khoảng cách 2 điểm
        float x=x1,y=y1,temp=(Math.abs(x2-x1)>=Math.abs(y2-y1))?Math.abs(x2-x1):Math.abs(y2-y1);

        // lm tron len điểm đó
        int tx = (int)(x+0.5);
        int ty = (int)(y+0.5);

        // lưu vị trí điểm và màu của điểm đó vào danh sách

        if (MyFunction.isSafe(nextPoint, tx, ty))
        {
            nextDrawing[tx][ty] = true;
            nextPoint[tx][ty] = chooseColor;
        }


        // duyệt khoảng cách 2 điểm.
        //System.out.println("run");
        for(int i = 1; i < temp; i++)
        {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2-x1)/temp;
            y += (y2-y1)/temp;

            // lm tròn lên điểm đó
            tx = (int)(x+0.5);
            ty = (int)(y+0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty) &&( i%6 == 1 || i%6==2 || i%6==3 || i%6==5) )
            {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
    }
    public void haichamGach(int x1, int y1, int x2, int y2)
    {
        // tính khoảng cách 2 điểm
        float x=x1,y=y1,temp=(Math.abs(x2-x1)>=Math.abs(y2-y1))?Math.abs(x2-x1):Math.abs(y2-y1);

        // lm tron len điểm đó
        int tx = (int)(x+0.5);
        int ty = (int)(y+0.5);

        // lưu vị trí điểm và màu của điểm đó vào danh sách

        if (MyFunction.isSafe(nextPoint, tx, ty))
        {
            nextDrawing[tx][ty] = true;
            nextPoint[tx][ty] = chooseColor;
        }


        // duyệt khoảng cách 2 điểm.
        //System.out.println("run");
        for(int i = 1; i < temp; i++)
        {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2-x1)/temp;
            y += (y2-y1)/temp;

            // lm tròn lên điểm đó
            tx = (int)(x+0.5);
            ty = (int)(y+0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty) &&( i%9 == 1 || i%9==2 || i%9==3 || i%9==4|| i%9==4 || i%9==6 || i%9==8) )
            {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
    }
    public void muiTen(int x1, int y1, int x2, int y2)
    {
        MidpointLine(x1,y1,x2,y2);
        MidpointLine(x2,y2,x2-3,y2+3);
        MidpointLine(x2,y2,x2-3,y2-3);
    }
}
