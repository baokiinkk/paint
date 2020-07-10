package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;

public class Conical extends HinhHoc {
    private lineMode MODE;
    private Point2D first;
    private Point2D second;
    private Point2D third;
    private Point2D fourth;
    private double Major_rad;
    private double Minor_rad;
    private Point2D centerElip;
    private Point3D top3D;
    private Point3D center3D;
    private Point3D rad3D;

    //khởi tạo hình hộp chữ nhật
    public Conical(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.PYRAMID;
        first = new Point2D();
        second = new Point2D();
        third = new Point2D();
        fourth = new Point2D();
        centerElip = new Point2D();
    }

    public void setConical(Point2D start, Point2D end, lineMode mode) {
        this.start = start;
        this.end = end;
        MODE = mode;
        centerElip.set(start.X, end.Y);
        Major_rad = Math.abs(centerElip.X - this.end.X);
        Minor_rad = Math.abs(start.Y - end.Y) / 8;
    }

    public void setConical3D(Point3D Start, Point3D End, lineMode mode) {
        this.top3D = new Point3D(Start.X, Start.Y, Start.Z);
        this.center3D = new Point3D(Start.X, Start.Y, End.Z);

        this.start = Start.to2D();
        MODE = mode;
        centerElip.set(center3D.to2D());
        int dis = (int) Math.round(Math.sqrt(Math.pow(Start.X - End.X, 2) + Math.pow(Start.Y - End.Y, 2)));
        this.rad3D = new Point3D(center3D.X + dis, center3D.Y, center3D.Z);
        this.end = rad3D.to2D();
        Major_rad = dis;
        Minor_rad = Math.abs(Start.Z - End.Z) / 8;
    }

    @Override
    public void saveCoord(String[][] coord) {
        top3D.saveCoord(coord);
        center3D.saveCoord(coord);
        rad3D.saveCoord(coord);
    }

    public void drawElip(boolean checkDash) {
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                    - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));

            double y2_c = centerElip.Y - y1_c;
            double tx_c = centerElip.X - x1_c;

            int ty1_c = (int) (y1_c + 0.5);
            int ty2_c = (int) (y2_c + 0.5);
            int x2_c = (int) (tx_c + 0.5);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + centerElip.X, ty2_c);
            second.set(x2_c,ty2_c);
            third.set(x2_c, ty1_c + centerElip.Y);
            fourth.set(x1_c +  centerElip.X, ty1_c + centerElip.Y);
            if(checkDash)
                QuadrantEllipse(first,second,third,fourth, MODE);
            else
                Quadrant(first,second,third,fourth,MODE);
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r < Minor_rad) {
            double x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad *Minor_rad
                    - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            double x2_r = centerElip.X - x1_r;
            double ty_r = centerElip.Y - y1_r;

            int tx1_r = (int) (x1_r + 0.5);
            int tx2_r = (int) (x2_r + 0.5);
            int y2_r = (int) (ty_r + 0.5);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + centerElip.X, y2_r);
            second.set(tx2_r,y2_r);
            third.set(tx2_r, y1_r + centerElip.Y);
            fourth.set(tx1_r +  centerElip.X, y1_r + centerElip.Y);

            if(checkDash)
                QuadrantEllipse(first,second,third,fourth, MODE);
            else
                Quadrant(first,second,third,fourth,MODE);
            y1_r++;
        }
    }

    public void draw() {
        if (end.Y > start.Y)
            drawElip(true);
        else
            drawElip(false);
        super.MidpointLine(start, end, MODE);
        super.MidpointLine(start, new Point2D(end.X > centerElip.X ? centerElip.X - (int) Major_rad : centerElip.X + (int) Major_rad, end.Y), MODE);

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
