package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;

public class Rectangle extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private lineMode MODE;

    public Rectangle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.RECTANGLE;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
    }

    private void setRectangle(Point2D start, Point2D end) {
        A.set(start.X, start.Y);              // (start) A-----------------B
        B.set(end.X, start.Y);                //         |                 |
        C.set(end.X, end.Y);                  //         |                 |
        D.set(start.X, end.Y);                //         D-----------------C  (end)
    }

    public void setRectangle(Point2D start, Point2D end, lineMode mode) {
        this.setRectangle(start, end);
        this.start = start;
        this.end = end;
        MODE = mode;
        center.set((start.X + end.X) / 2, (start.Y + end.Y) / 2);
    }

    public void rotate(double alpha) {

        Point2D tmpA = A.rotatePoint(center, alpha);
        Point2D tmpB = B.rotatePoint(center, alpha);
        Point2D tmpC = C.rotatePoint(center, alpha);
        Point2D tmpD = D.rotatePoint(center, alpha);
        super.MidpointLine(tmpA, tmpB, MODE);
        super.MidpointLine(tmpB, tmpC, MODE);
        super.MidpointLine(tmpC, tmpD, MODE);
        super.MidpointLine(tmpD, tmpA, MODE);
    }

    public void applyRotate(double alpha) {
        double al = Math.toDegrees(this.alpha) + Math.toDegrees(alpha);
        if (al > 360) {
            al = -360 + (al - 360);
        }
        if (al < -360) {
            al = 360 - (-360 - al);
        }
        this.alpha = (Math.toRadians(al));
        System.out.println(alpha);

    }

    public void setSquare(Point2D start, Point2D end, lineMode mode) {
        //end.set();
        MODE = mode;
        A.set(start.X, start.Y);              // (start) A-----------------B
        B.set(end.X, start.Y);                //         |                 |
        C.set(end.X, end.Y);                  //         |                 |
        D.set(start.X, end.Y);                //         D-----------------C  (end)
        Vector2D AB = new Vector2D(A, B);
        Vector2D AD = new Vector2D(A, D);
        int d = (int) Math.min(AB.length(), AD.length());
        AB = AB.kTimesUnit(d);
        AD = AD.kTimesUnit(d);
        //System.out.println(AB.X + "-" +AB.Y + "....." + AD.X + "-" + AD.Y);
        end.set(A.X + AB.X, A.Y + AD.Y);
        A.set(start.X, start.Y);              // (start) A-----------------B
        B.set(end.X, start.Y);                //         |                 |
        C.set(end.X, end.Y);                  //         |                 |
        D.set(start.X, end.Y);                //         D-----------------C  (end)
        this.start = start;
        this.end = end;
        center.set((start.X + end.X) / 2, (start.Y + end.Y) / 2);
    }

    public void draw() {
        A = A.rotatePoint(center, this.alpha);
        B = B.rotatePoint(center, this.alpha);
        C = C.rotatePoint(center, this.alpha);
        D = D.rotatePoint(center, this.alpha);
        super.MidpointLine(A, B, MODE);
        super.MidpointLine(B, C, MODE);
        super.MidpointLine(C, D, MODE);
        super.MidpointLine(D, A, MODE);
    }
}
