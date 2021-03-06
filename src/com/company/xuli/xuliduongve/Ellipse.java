package com.company.xuli.xuliduongve;
import com.company.Button;
import java.awt.*;
import java.util.ArrayList;

public class Ellipse extends HinhHoc {
    private lineMode MODE;
    private sideMode SIDEMODE;
    private Point2D first;
    private Point2D second;
    private Point2D third;
    private Point2D fourth;
    private Point2D previousCenter;
    private double Major_rad;
    private double Minor_rad;
    private boolean formula;

    public Ellipse (boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.ELLIPSE;
        first = new Point2D();
        second = new Point2D();
        third = new Point2D();
        fourth = new Point2D();
        previousCenter = new Point2D();
    }

    public void setCircle (Point2D start, Point2D end, Point2D centerPoint, lineMode mode, sideMode SideMode) {
        this.centerPoint = centerPoint;
        SIDEMODE = SideMode;
        this.start = start;
        this.end = end;
        MODE = mode;
        formula = false;
        center.set(this.start.X, this.start.Y);
        previousCenter.set(center.X,center.Y);
        Minor_rad = Math.max(Math.abs(this.start.X - this.end.X), Math.abs(this.start.Y - this.end.Y));
        Major_rad = Minor_rad;
        //pt duong tron (x - a)^2 + (y - b)^2 = r^2 => y = sqrt(r^2 -(x - a)^2) +b
        //tam (a,b)
    }

    public void setElip (Point2D start, Point2D end, Point2D centerPoint, lineMode mode, sideMode SideMode) {
        this.centerPoint = centerPoint;
        this.start = start;
        this.end = end;
        MODE = mode;
        SIDEMODE = SideMode;
        formula = true;
        center.set(this.start.X, this.start.Y);
        previousCenter.set(center.X,center.Y);
        Major_rad = Math.abs(this.start.X - this.end.X);
        Minor_rad = Math.abs(this.start.Y - this.end.Y);
        //pt elip (x^2/a^2 + y^2/b^2 = 1) => y = sqrt(1 - (x^2/a^2))* b^2)
        // tam  (xstart;ystart)
    }

    @Override
    public void saveCoord (String[][] coord) {
        center.saveCoord(coord);
        new Point2D((int) Math.round(center.X + Major_rad), center.Y).saveCoord(coord);
        if (Major_rad > Minor_rad) {
            new Point2D(center.X, (int) Math.round(center.Y + Minor_rad)).saveCoord(coord);
        }
    }

    // xoay và vẽ hình hiện tại 1 góc alpha
    // đây là xoay nháp, tức là chỉ xoay hình ảo để người dùng canh góc xoay, khi nhả chuột sẽ áp dụng vào hình gốc
    public void rotate (double alpha) {

        int x1_c = 0;
//        temp = (Math.abs(end.X - start.X) >= Math.abs(end.Y - start.Y)) ? Math.abs(end.X - start.X) : Math.abs(end.Y - start.Y);
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if (formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

//            double tx_c = start.X - x1_c;
//            double y2_c = start.Y - y1_c;
            double y2_c = previousCenter.Y - y1_c;
            double tx_c = previousCenter.X - x1_c;

            int ty1_c = (int) (Math.round(y1_c));
            int ty2_c = (int) Math.round(y2_c);
            int x2_c = (int) Math.round(tx_c);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            /*first.set(x1_c + start.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + start.Y);
            fourth.set(x1_c + start.X, ty1_c + start.Y);*/
            first.set(x1_c + previousCenter.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + previousCenter.Y);
            fourth.set(x1_c + previousCenter.X, ty1_c + previousCenter.Y);

            first = first.rotatePoint(center, this.alpha + alpha);
            second = second.rotatePoint(center, this.alpha + alpha);
            third = third.rotatePoint(center, this.alpha + alpha);
            fourth = fourth.rotatePoint(center, this.alpha + alpha);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }

//            Quadrant(first,second,third,fourth, MODE);
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r <= Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            /*double x2_r = start.X - x1_r;
            double ty_r = start.Y - y1_r;*/
            double x2_r = previousCenter.X - x1_r;
            double ty_r = previousCenter.Y - y1_r;

            int tx1_r = (int) Math.round(x1_r);
            int tx2_r = (int) Math.round(x2_r);
            int y2_r = (int) Math.round(ty_r);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            /*first.set(tx1_r + start.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + start.Y);
            fourth.set(tx1_r + start.X, y1_r + start.Y);*/
            first.set(tx1_r + previousCenter.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + previousCenter.Y);
            fourth.set(tx1_r + previousCenter.X, y1_r + previousCenter.Y);

            first = first.rotatePoint(center, this.alpha + alpha);
            second = second.rotatePoint(center, this.alpha + alpha);
            third = third.rotatePoint(center, this.alpha + alpha);
            fourth = fourth.rotatePoint(center, this.alpha + alpha);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }

//            Quadrant(first, second,third,fourth, MODE);
            y1_r++;
        }

    }

    // khi xoay nháp sẽ có góc alpha lệch so với góc ban đầu, khi xoay xong, hàm này sẽ + góc vừa xoay vào góc hiện tại
    // ví dụ xoay hình gốc 1 góc 20 độ, thì sau khi xoay nháp xong, góc xoay gốc sẽ được cộng thêm 20 độ
    public void applyRotate (double alpha) {
        //System.out.println(this.alpha + " " + alpha);
        this.alpha += alpha;
        previousCenter.set(center.X,center.Y);
    }

    public void draw () {
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if (formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

            double y2_c = start.Y - y1_c;
            double tx_c = start.X - x1_c;

            int ty1_c = (int) Math.round(y1_c);
            int ty2_c = (int) Math.round(y2_c);
            int x2_c = (int) Math.round(tx_c);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + start.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + start.Y);
            fourth.set(x1_c + start.X, ty1_c + start.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r < Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = start.X - x1_r;
            double ty_r = start.Y - y1_r;

            int tx1_r = (int) Math.round(x1_r);
            int tx2_r = (int) Math.round(x2_r);
            int y2_r = (int) Math.round(ty_r);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + start.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + start.Y);
            fourth.set(tx1_r + start.X, y1_r + start.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            y1_r++;
        }

    }


    public void Quadrant (Point2D first, Point2D second, Point2D third, Point2D fourth, lineMode mode) {

        //ve cac duong cong ung voi cac goc phan tu
        if (MyFunction.isSafe(nextPoint, first.X, first.Y) && MyFunction.chooseMode(first.X, mode)) {
            nextDrawing[first.X][first.Y] = true;
            nextPoint[first.X][first.Y] = chooseColor;
        } //I
        if (MyFunction.isSafe(nextPoint, second.X, second.Y) && MyFunction.chooseMode(second.X, mode)) {
            nextDrawing[second.X][second.Y] = true;
            nextPoint[second.X][second.Y] = chooseColor;
        }//II
        if (MyFunction.isSafe(nextPoint, third.X, third.Y) && MyFunction.chooseMode(third.X, mode)) {
            nextDrawing[third.X][third.Y] = true;
            nextPoint[third.X][third.Y] = chooseColor;
        }//III
        if (MyFunction.isSafe(nextPoint, fourth.X, fourth.Y) && MyFunction.chooseMode(fourth.X, mode)) {
            nextDrawing[fourth.X][fourth.Y] = true;
            nextPoint[fourth.X][fourth.Y] = chooseColor;
        } // IV
    }

    /* public void draw() {
         int x1_c = 0;
         while (Major_rad > x1_c) {
             //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
             double y1_c;
             if (formula)
                 y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                         - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
             else
                 y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

             double y2_c = center.Y - y1_c;
             double tx_c = center.X - x1_c;

             int ty1_c = (int) Math.round(y1_c);
             int ty2_c = (int) Math.round(y2_c);
             int x2_c = (int) Math.round(tx_c);

             //a = (x1_c, ty1_c);
             //b = (x2_c,ty2_c);

             first.set(x1_c + center.X, ty2_c);
             second.set(x2_c, ty2_c);
             third.set(x2_c, ty1_c + center.Y);
             fourth.set(x1_c + center.X, ty1_c + center.Y);

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
                 x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                         - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
             else
                 x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

             double x2_r = center.X - x1_r;
             double ty_r = center.Y - y1_r;

             int tx1_r = (int) Math.round(x1_r);
             int tx2_r = (int) Math.round(x2_r);
             int y2_r = (int) Math.round(ty_r);

             //a = (tx1_r, y1_r);
             //b = (tx2_r,y2_r);

             first.set(tx1_r + center.X, y2_r);
             second.set(tx2_r, y2_r);
             third.set(tx2_r, y1_r + center.Y);
             fourth.set(tx1_r + center.X, y1_r + center.Y);

             first = first.rotatePoint(center, this.alpha);
             second = second.rotatePoint(center, this.alpha);
             third = third.rotatePoint(center, this.alpha);
             fourth = fourth.rotatePoint(center, this.alpha);

             Quadrant(first, second, third, fourth, MODE);
             y1_r++;
         }
     }
 */
    public void move (Vector2D a) {
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if (formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

            double y2_c = center.Y - y1_c;
            double tx_c = center.X - x1_c;

            int ty1_c = (int) Math.round(y1_c);
            int ty2_c = (int) Math.round(y2_c);
            int x2_c = (int) Math.round(tx_c);

            first.set(x1_c + center.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + center.Y);
            fourth.set(x1_c + center.X, ty1_c + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.moveVector(a);
            second = second.moveVector(a);
            third = third.moveVector(a);
            fourth = fourth.moveVector(a);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r <= Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = center.X - x1_r;
            double ty_r = center.Y - y1_r;

            int tx1_r = (int) Math.round(x1_r);
            int tx2_r = (int) Math.round(x2_r);
            int y2_r = (int) Math.round(ty_r);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + center.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + center.Y);
            fourth.set(tx1_r + center.X, y1_r + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.moveVector(a);
            second = second.moveVector(a);
            third = third.moveVector(a);
            fourth = fourth.moveVector(a);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            y1_r++;
        }
    }

    public void applyMove (Vector2D a) {
        first = first.moveVector(a);
        second = second.moveVector(a);
        third = third.moveVector(a);
        fourth = fourth.moveVector(a);
        center = center.moveVector(a);
        draw();
    }

    public void draw (Color color) {
        draw();
        //System.out.println(nextPoint[center.X][center.Y]);
        //MyFunction.clearArr(nextDrawing);
        MyFunction.paintColor(nextPoint, nextDrawing, start, color);
    }

    public void SymOX () {
        draw();
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if (formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

            double y2_c = center.Y - y1_c;
            double tx_c = center.X - x1_c;

            int ty1_c = (int) Math.round(y1_c);
            int ty2_c = (int) Math.round(y2_c);
            int x2_c = (int) Math.round(tx_c);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + center.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + center.Y);
            fourth.set(x1_c + center.X, ty1_c + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.HonrizontalSymetry(first);
            second = second.HonrizontalSymetry(second);
            third = third.HonrizontalSymetry(third);
            fourth = fourth.HonrizontalSymetry(fourth);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r < Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = center.X - x1_r;
            double ty_r = center.Y - y1_r;

            int tx1_r = (int) Math.round(x1_r);
            int tx2_r = (int) Math.round(x2_r);
            int y2_r = (int) Math.round(ty_r);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + center.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + center.Y);
            fourth.set(tx1_r + center.X, y1_r + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.HonrizontalSymetry(first);
            second = second.HonrizontalSymetry(second);
            third = third.HonrizontalSymetry(third);
            fourth = fourth.HonrizontalSymetry(fourth);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            y1_r++;
        }
    }

    public void SymOY () {
        draw();
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if (formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

            double y2_c = center.Y - y1_c;
            double tx_c = center.X - x1_c;

            int ty1_c = (int) Math.round(y1_c);
            int ty2_c = (int) Math.round(y2_c);
            int x2_c = (int) Math.round(tx_c);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + center.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + center.Y);
            fourth.set(x1_c + center.X, ty1_c + center.Y);
            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.VerticalSymetry(first);
            second = second.VerticalSymetry(second);
            third = third.VerticalSymetry(third);
            fourth = fourth.VerticalSymetry(fourth);


            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r < Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = center.X - x1_r;
            double ty_r = center.Y - y1_r;

            int tx1_r = (int) Math.round(x1_r);
            int tx2_r = (int) Math.round(x2_r);
            int y2_r = (int) Math.round(ty_r);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + center.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + center.Y);
            fourth.set(tx1_r + center.X, y1_r + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.VerticalSymetry(first);
            second = second.VerticalSymetry(second);
            third = third.VerticalSymetry(third);
            fourth = fourth.VerticalSymetry(fourth);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            y1_r++;
        }

    }

    public void SymP (Point2D pointSym) {
        draw();
        int x1_c = 0;
        while (Major_rad > x1_c) {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //II
            double y1_c;
            if (formula)
                y1_c = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Minor_rad * Minor_rad * x1_c * x1_c) / (Major_rad * Major_rad * 1.0));
            else
                y1_c = Math.sqrt(Major_rad * Major_rad - x1_c * x1_c);

            double y2_c = center.Y - y1_c;
            double tx_c = center.X - x1_c;

            int ty1_c = (int) Math.round(y1_c);
            int ty2_c = (int) Math.round(y2_c);
            int x2_c = (int) Math.round(tx_c);

            //a = (x1_c, ty1_c);
            //b = (x2_c,ty2_c);

            first.set(x1_c + center.X, ty2_c);
            second.set(x2_c, ty2_c);
            third.set(x2_c, ty1_c + center.Y);
            fourth.set(x1_c + center.X, ty1_c + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.PointSymetry(first, pointSym);
            second = second.PointSymetry(second, pointSym);
            third = third.PointSymetry(third, pointSym);
            fourth = fourth.PointSymetry(fourth, pointSym);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            x1_c++;
        }
        int y1_r = 0;
        while (y1_r < Minor_rad) {
            double x1_r;
            if (formula)
                x1_r = Math.sqrt((Major_rad * Major_rad * Minor_rad * Minor_rad
                        - Major_rad * Major_rad * y1_r * y1_r) / (Minor_rad * Minor_rad * 1.0));
            else
                x1_r = Math.sqrt(Minor_rad * Minor_rad - y1_r * y1_r);

            double x2_r = center.X - x1_r;
            double ty_r = center.Y - y1_r;

            int tx1_r = (int) Math.round(x1_r);
            int tx2_r = (int) Math.round(x2_r);
            int y2_r = (int) Math.round(ty_r);

            //a = (tx1_r, y1_r);
            //b = (tx2_r,y2_r);

            first.set(tx1_r + center.X, y2_r);
            second.set(tx2_r, y2_r);
            third.set(tx2_r, y1_r + center.Y);
            fourth.set(tx1_r + center.X, y1_r + center.Y);

            first = first.rotatePoint(center, this.alpha);
            second = second.rotatePoint(center, this.alpha);
            third = third.rotatePoint(center, this.alpha);
            fourth = fourth.rotatePoint(center, this.alpha);

            first = first.PointSymetry(first, pointSym);
            second = second.PointSymetry(second, pointSym);
            third = third.PointSymetry(third, pointSym);
            fourth = fourth.PointSymetry(fourth, pointSym);

            ArrayList<Point2D> tmpFirst = first.chooseSideMode(centerPoint, first, SIDEMODE);
            ArrayList<Point2D> tmpSecond = second.chooseSideMode(centerPoint, second, SIDEMODE);
            ArrayList<Point2D> tmpThird = third.chooseSideMode(centerPoint, third, SIDEMODE);
            ArrayList<Point2D> tmpFourth = fourth.chooseSideMode(centerPoint, fourth, SIDEMODE);
            for (int i = 0; i < tmpFirst.size(); i++) {
                Quadrant(tmpFirst.get(i), tmpSecond.get(i), tmpThird.get(i), tmpFourth.get(i), MODE);
            }
            y1_r++;
        }
    }

}