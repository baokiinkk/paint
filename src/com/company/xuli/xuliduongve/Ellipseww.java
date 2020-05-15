package com.company.xuli.xuliduongve;

import java.awt.*;

public class Ellipseww extends HinhHoc {
    public Ellipseww(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }
    public void drawEllipse(int xStart, int yStart, int mX, int mY,lineMode mode){
//        int d= MyFunction.tinhKhoangCach2Diem(xStart,yStart,mX,mY);
//        int a=2*d, b=d;
//        //nhánh từ trên xuống
//        int x=0, y=b;
//        float a2= (float) Math.pow(a,2);
//        float b2= (float) Math.pow(b,2);
//        float p=  (2*((float)b2/a2)-2*b+1);
//        while(((float)b2/a2)*x<=y) {
//            if(x%2 == 0 )
//                draw4Point(x, y, xStart, yStart);
//            if(p<0)
//                p=p+2*((float)b2/a2)*(2*x+3);
//            else {
//                p=p-4*y+2*((float)b2/a2)*(2*x+3);
//                y--;
//            }
//            x++;
//        }
        //nhánh từ dưới lên
//         x=a;
//        y=0;
//        p=2*((float)a2/b2)-2*a+1;
//        while(((float)a2/b2)*y<=x){
//            draw4Point(x, y, xStart,yStart );
//            if(p<0)
//                p=p+2*((float)a2/b2)*(2*y+3);
//            else{
//                p=p-4*x+2*((float)a2/b2)*(2*y+3);
//                x--;
//            }
//            y++;
//        }

        double d=Math.abs(mX-xStart);
        double r = Math.abs(mY-yStart);
        int cx,cy;
        cx=xStart;
        cy =yStart;
        double B=r;
        double A=d;
        double px=0, py=0;
        for(int i=60; i<=240; i++)
        {
            double x,y;
            x = A * Math.sin(Math.toRadians(i)+0.5);
            y = B * Math.cos(Math.toRadians(i)+0.5);
            if (i != 60)
                // draw a line joining previous and new point .
                new Line(nextDrawing,nextPoint,chooseColor).MidpointLine((int)px + cx, (int)py + cy, (int)x + cx, (int)y + cy,lineMode.DOT);
            // store the previous points
            px = x;
            py = y;
        }
        for(int i=240; i<=420; i++)
        {
            double x,y;
            x = A * Math.sin(Math.toRadians(i)+0.5);
            y = B * Math.cos(Math.toRadians(i)+0.5);
            if (i != 240)
                // draw a line joining previous and new point .
                new Line(nextDrawing,nextPoint,chooseColor).MidpointLine((int)px + cx, (int)py + cy, (int)x + cx, (int)y + cy,lineMode.DEFAULT);
            // store the previous points
            px = x;
            py = y;
        }
        //pt ellipse
//        int a= (int) d;
//        int b= (int) d/2;
//        int x=0;
//        int y=a;
//        while(x<a)
//        {
//            x++;
//            y= (int) Math.sqrt(Math.abs(1-(Math.pow(x,2)/Math.pow(a,2)))*Math.pow(b,2));
//            Draw4Point(x,y,xStart,yStart);
//
//
//        }
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
    public  void Draw4Point(int x, int y, int xd, int yd)
    {
        putPixel(x+xd,y+yd);
        putPixel(x+xd,-y+yd);
        putPixel(-x+xd,-y+yd);
        putPixel(-x+xd,y+yd);
    }
}