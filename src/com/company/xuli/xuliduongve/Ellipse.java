package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;

public class Ellipse extends HinhHoc {
    private lineMode MODE;
    private Point2D  first;
    private Point2D  second;
    private Point2D  third;
    private Point2D  fourth;
    private double   Major_rad;
    private double   Minor_rad;
    private boolean  formula;

    public Ellipse(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.ELLIPSE;
        first = new Point2D();
        second = new Point2D();
        third = new Point2D();
        fourth = new Point2D();
    }

    public void setCircle(Point2D start, Point2D end, lineMode mode) {
        this.start = start;
        this.end = end;
        MODE = mode;
        formula = false;
        center.set(start.X,start.Y);
        Minor_rad = Math.max(Math.abs(start.X - end.X), Math.abs(start.Y - end.Y));
        Major_rad = Minor_rad;
        //pt duong tron (x - a)^2 + (y - b)^2 = r^2 => y = sqrt(r^2 -(x - a)^2) +b
        //tam (a,b)
    }

    // set đầy đủ thông hình chữ nhật, gồm loại nét vẽ, các đỉnh, và tâm HCN
    public void setElip(Point2D start, Point2D end, lineMode mode) {
        this.start = start;
        this.end = end;
        MODE = mode;
        formula = true;
        center.set(this.start.X,this.start.Y);
        Major_rad = Math.abs(start.X - end.X);
        Minor_rad = Math.abs(start.Y - end.Y);
        //pt elip (x^2/a^2 + y^2/b^2 = 1) => y = sqrt(1 - (x^2/a^2))* b^2)
        // tam  (xstart;ystart)
    }

    // xoay và vẽ hình hiện tại 1 góc alpha
    // đây là xoay nháp, tức là chỉ xoay hình ảo để người dùng canh góc xoay, khi nhả chuột sẽ áp dụng vào hình gốc
    public void rotate(double alpha) {

        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if(formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad *Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad -x1_c * x1_c);

            double y2_c = center.Y - y1_c;
            double tx_c = center.X - x1_c;

            int ty1_c = (int) (y1_c + 0.5);
            int ty2_c = (int) (y2_c + 0.5);
            int x2_c = (int) (tx_c + 0.5);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + center.X, ty2_c);
            second.set(x2_c,ty2_c);
            third.set(x2_c, ty1_c + center.Y);
            fourth.set(x1_c +  center.X, ty1_c + center.Y);

            first = first.rotatePoint(center, this.alpha + alpha);
            second = second.rotatePoint(center, this.alpha + alpha);
            third = third.rotatePoint(center, this.alpha + alpha);
            fourth = fourth.rotatePoint(center, this.alpha + alpha);

            Quadrant(first,second,third,fourth, MODE);
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r <= Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad *Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = center.X - x1_r;
            double ty_r = center.Y - y1_r;

            int tx1_r = (int) (x1_r + 0.5);
            int tx2_r = (int) (x2_r + 0.5);
            int y2_r = (int) (ty_r + 0.5);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + center.X, y2_r);
            second.set(tx2_r,y2_r);
            third.set(tx2_r, y1_r + center.Y);
            fourth.set(tx1_r +  center.X, y1_r + center.Y);

            first = first.rotatePoint(center, this.alpha + alpha);
            second = second.rotatePoint(center, this.alpha + alpha);
            third = third.rotatePoint(center, this.alpha + alpha);
            fourth = fourth.rotatePoint(center, this.alpha + alpha);

            Quadrant(first, second,third,fourth, MODE);
            y1_r++;
        }

    }

    // khi xoay nháp sẽ có góc alpha lệch so với góc ban đầu, khi xoay xong, hàm này sẽ + góc vừa xoay vào góc hiện tại
    // ví dụ xoay hình gốc 1 góc 20 độ, thì sau khi xoay nháp xong, góc xoay gốc sẽ được cộng thêm 20 độ
    public void applyRotate(double alpha) {
        System.out.println(this.alpha + " " + alpha);
        this.alpha += alpha;
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

    public void draw() {

        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if(formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad *Minor_rad
                                    - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

            double y2_c = center.Y - y1_c;
            double tx_c = center.X - x1_c;

            int ty1_c = (int) (y1_c + 0.5);
            int ty2_c = (int) (y2_c + 0.5);
            int x2_c = (int) (tx_c + 0.5);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + center.X, ty2_c);
            second.set(x2_c,ty2_c);
            third.set(x2_c, ty1_c + center.Y);
            fourth.set(x1_c +  center.X, ty1_c + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            Quadrant(first,second,third,fourth, MODE);
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r < Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad *Minor_rad
                                    - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = center.X - x1_r;
            double ty_r = center.Y - y1_r;

            int tx1_r = (int) (x1_r + 0.5);
            int tx2_r = (int) (x2_r + 0.5);
            int y2_r = (int) (ty_r + 0.5);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + center.X, y2_r);
            second.set(tx2_r,y2_r);
            third.set(tx2_r, y1_r + center.Y);
            fourth.set(tx1_r +  center.X, y1_r + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            Quadrant(first,second,third,fourth, MODE);;
            y1_r++;
        }
    }

    public void move(Vector2D a) {
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if(formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad *Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad -x1_c * x1_c);

            double y2_c = center.Y - y1_c;
            double tx_c = center.X - x1_c;

            int ty1_c = (int) (y1_c + 0.5);
            int ty2_c = (int) (y2_c + 0.5);
            int x2_c = (int) (tx_c + 0.5);

            first.set(x1_c + center.X, ty2_c);
            second.set(x2_c,ty2_c);
            third.set(x2_c, ty1_c + center.Y);
            fourth.set(x1_c +  center.X, ty1_c + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.moveVector(a);
            second = second.moveVector(a);
            third = third.moveVector(a);
            fourth = fourth.moveVector(a);

            Quadrant(first,second,third,fourth, MODE);
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r <= Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad *Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = center.X - x1_r;
            double ty_r = center.Y - y1_r;

            int tx1_r = (int) (x1_r + 0.5);
            int tx2_r = (int) (x2_r + 0.5);
            int y2_r = (int) (ty_r + 0.5);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + center.X, y2_r);
            second.set(tx2_r,y2_r);
            third.set(tx2_r, y1_r + center.Y);
            fourth.set(tx1_r +  center.X, y1_r + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.moveVector(a);
            second = second.moveVector(a);
            third = third.moveVector(a);
            fourth = fourth.moveVector(a);

            Quadrant(first,second,third,fourth, MODE);
            y1_r++;
        }
    }

    public void applyMove(Vector2D a) {
        first = first.moveVector(a);
        second = second.moveVector(a);
        third = third.moveVector(a);
        fourth = fourth.moveVector(a);
        center = center.moveVector(a);
        draw();
    }
}