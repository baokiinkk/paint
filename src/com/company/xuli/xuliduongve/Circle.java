package com.company.xuli.xuliduongve;

import java.awt.*;

public class Circle extends HinhHoc {
    public Circle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }
    public void drawingCircle(int xStart, int yStart, int mX, int mY,lineMode mode){

        if(xStart != -1 && yStart !=-1)
        {
            int r=Math.abs(xStart-mX);
            int x=0;
            int y=r;
            if(MyFunction.chooseMode(x,mode))
                Draw8Point(x, y, xStart, yStart);
            while(x<=y)
            {
                x++;
                y=(int)(Math.round(Math.sqrt(Math.pow(r,2)-Math.pow(x,2)))+0.5);
                if(MyFunction.chooseMode(x,mode))
                    Draw8Point(x,y,xStart,yStart);
            }
        }
    }
    private void putPixel(int x, int y)
    {
        float x1=x, y1=y;
        int tx = (int)(x1+0.5);
        int ty = (int)(y1+0.5);
        if (MyFunction.isSafe(nextPoint, tx, ty))
        {
            nextDrawing[tx][ty] = true;
            nextPoint[tx][ty] = chooseColor;
        }
    }
    public  void Draw8Point(int x, int y, int xd, int yd)
    {
        putPixel(x+xd,y+yd);
        putPixel(-x+xd,y+yd);
        putPixel(x+xd,-y+yd);
        putPixel(-x+xd,-y+yd);
        putPixel(y+xd,x+yd);
        putPixel(-y+xd,x+yd);
        putPixel(y+xd,-x+yd);
        putPixel(-y+xd,-x+yd);
    }
}
