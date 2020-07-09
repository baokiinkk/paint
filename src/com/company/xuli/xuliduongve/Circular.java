package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;

public class Circular extends HinhHoc {
    private lineMode MODE;
    private Point2D  first;
    private Point2D  second;
    private Point2D  third;
    private Point2D  fourth;
    private double   Major_rad;
    private double   Minor_rad;
    private Point2D      centerElipT;
    private Point2D      centerElipD;
    //khởi tạo hình hộp chữ nhật
    public Circular(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.GLOBULAR;
        first = new Point2D();
        second = new Point2D();
        third = new Point2D();
        fourth = new Point2D();
        centerElipT = new Point2D();
        centerElipD = new Point2D();

    }

    public void setCircular(Point2D start, Point2D end, lineMode mode) {
        this.start = start;
        this.end = end;
        MODE = mode;
        centerElipT.set(start.X,start.Y);
        centerElipD.set(start.X,end.Y);
        Major_rad = Math.abs(centerElipT.X - this.end.X);
        Minor_rad = Major_rad/3;
    }
    public void drawElip(Point2D point, boolean checkDash) {
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                    - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));

            double y2_c = point.Y - y1_c;
            double tx_c = point.X - x1_c;

            int ty1_c = (int) Math.round(y1_c);
            int ty2_c = (int) Math.round(y2_c);
            int x2_c = (int) Math.round(tx_c);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + point.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + point.Y);
            fourth.set(x1_c + point.X, ty1_c + point.Y);

            if (checkDash)
                QuadrantEllipse(first,second,third,fourth, MODE);
            else
                Quadrant(first,second,third,fourth,MODE);
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r < Minor_rad) {
            double x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                    - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            double x2_r = point.X - x1_r;
            double ty_r = point.Y - y1_r;

            int tx1_r = (int) Math.round(x1_r);
            int tx2_r = (int) Math.round(x2_r);
            int y2_r = (int) Math.round(ty_r);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + point.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + point.Y);
            fourth.set(tx1_r + point.X, y1_r + point.Y);

            if (checkDash)
                QuadrantEllipse(first,second,third,fourth, MODE);
            else
                Quadrant(first,second,third,fourth,MODE);

            y1_r++;
        }
    }

    public void draw() {
        if(end.Y > start.Y)
        {
            drawElip(centerElipT,false);
            drawElip(centerElipD,true);
        }
        else{
            drawElip(centerElipT,true);
            drawElip(centerElipD,false);
        }
        super.MidpointLine(new Point2D(centerElipT.X - (int) Major_rad,start.Y), new Point2D(centerElipT.X - (int) Major_rad,end.Y),MODE);
        super.MidpointLine(new Point2D(centerElipD.X + (int) Major_rad,start.Y), new Point2D(centerElipD.X + (int) Major_rad,end.Y),MODE);
    }

    public void QuadrantEllipse(Point2D first, Point2D second, Point2D third, Point2D fourth,lineMode mode)
    {

        //ve cac duong cong ung voi cac goc phan tu
        if (MyFunction.isSafe(nextPoint, first.X,first.Y) && (first.X % 5) != 0) {
            nextDrawing[first.X][first.Y] = true;
            nextPoint[first.X][first.Y] = chooseColor;
        } //I
        if (MyFunction.isSafe(nextPoint, second.X,second.Y) && (second.X % 5) != 0) {
            nextDrawing[second.X][second.Y] = true;
            nextPoint[second.X][second.Y] = chooseColor;
        }//II
        if (MyFunction.isSafe(nextPoint,third.X,third.Y)) {
            nextDrawing[third.X][third.Y] = true;
            nextPoint[third.X][third.Y] = chooseColor;
        }//III
        if (MyFunction.isSafe(nextPoint, fourth.X,fourth.Y)) {
            nextDrawing[fourth.X][fourth.Y] = true;
            nextPoint[fourth.X][fourth.Y] = chooseColor;
        } // IV
    }
    public void Quadrant(Point2D first, Point2D second, Point2D third, Point2D fourth,lineMode mode)
    {

        //ve cac duong cong ung voi cac goc phan tu
        if (MyFunction.isSafe(nextPoint, first.X,first.Y) && MyFunction.chooseMode(first.X, mode)) {
            nextDrawing[first.X][first.Y] = true;
            nextPoint[first.X][first.Y] = chooseColor;
        } //I
        if (MyFunction.isSafe(nextPoint, second.X,second.Y) && MyFunction.chooseMode(second.X, mode)) {
            nextDrawing[second.X][second.Y] = true;
            nextPoint[second.X][second.Y] = chooseColor;
        }//II
        if (MyFunction.isSafe(nextPoint,third.X,third.Y) && MyFunction.chooseMode(third.X, mode)) {
            nextDrawing[third.X][third.Y] = true;
            nextPoint[third.X][third.Y] = chooseColor;
        }//III
        if (MyFunction.isSafe(nextPoint, fourth.X,fourth.Y) && MyFunction.chooseMode(fourth.X, mode)) {
            nextDrawing[fourth.X][fourth.Y] = true;
            nextPoint[fourth.X][fourth.Y] = chooseColor;
        } // IV
    }
}
