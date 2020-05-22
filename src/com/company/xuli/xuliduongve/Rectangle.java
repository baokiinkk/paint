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

    public void setRectangle(Point2D start, Point2D end, lineMode mode) {
        A.set(start.X, start.Y);              // (start) A-----------------B
        B.set(end.X, start.Y);                //         |                 |
        C.set(end.X, end.Y);                  //         |                 |
        D.set(start.X, end.Y);                //         D-----------------C  (end)
        MODE = mode;
        center.set((start.X + end.X) / 2, (start.Y + end.Y) / 2);
    }

    public void rotate(double alpha) {
        System.out.println(alpha);
        Point2D tmpA = A.rotatePoint(center, alpha);
        Point2D tmpB = B.rotatePoint(center, alpha);
        Point2D tmpC = C.rotatePoint(center, alpha);
        Point2D tmpD = D.rotatePoint(center, alpha);
        super.MidpointLine(tmpA, tmpB, MODE);
        super.MidpointLine(tmpB, tmpC, MODE);
        super.MidpointLine(tmpC, tmpD, MODE);
        super.MidpointLine(tmpD, tmpA, MODE);
    }

    public void setSquare(Point2D start, Point2D end, lineMode mode)
    {
        double d = Math.sqrt((start.X-end.X)*(start.X-end.X) + (start.Y-end.Y)*(start.Y-end.Y));
        double a = d/Math.sqrt(2);
        end.set(start.X+a, start.Y+a);
        //end.set();
        MODE = mode;
        A.set(start.X, start.Y);              // (start) A-----------------B
        B.set(end.X, start.Y);                //         |                 |
        C.set(end.X, end.Y);                  //         |                 |
        D.set(start.X, end.Y);                //         D-----------------C  (end)
    }

    public void draw() {
        super.MidpointLine(A, B, MODE);
        super.MidpointLine(B, C, MODE);
        super.MidpointLine(C, D, MODE);
        super.MidpointLine(D, A, MODE);
    }
}
