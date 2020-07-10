package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;
import java.util.ArrayList;

public class Pyramid extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private Point2D S;
    private Point2D K;
    private Point2D O;
    private double disHon;
    private double disVer;
    private lineMode MODE;

    public Pyramid(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.PYRAMID;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
        S = new Point2D();
        K = new Point2D();
        O = new Point2D();
    }

    private void setPyramid(Point2D start, Point2D end) {
        O.set(start.X,end.Y);
        disHon = Math.abs(start.X - end.X);
        disVer = ((disHon*2)/ (Math.sqrt(2)+1)) /2;
        S.set(start.X, start.Y);
        K.set(end.X,end.Y);
        B.set(end.X + disVer, end.Y - disVer);
        C.set(end.X - disVer, end.Y + disVer);
        if(K.X > O.X)
        {
            D.set(end.X - disVer - disHon*2 , end.Y + disVer);
            A.set(end.X + disVer - disHon*2 , end.Y - disVer);
        }
        else
        {
            A.set(end.X + disVer + disHon*2 , end.Y - disVer);
            D.set(end.X - disVer + disHon*2 , end.Y + disVer);
        }
    }

    // set đầy đủ thông hình chữ nhật, gồm loại nét vẽ, các đỉnh, và tâm HCN
    public void setPyramid(Point2D start, Point2D end, lineMode mode) {
        this.setPyramid(start, end);
        this.start = start;
        this.end = end;
        MODE = mode;
    }
    public void draw() {
//        super.MidpointLine(B,D,MODE);
        super.MidpointLine(S,A,lineMode.DASH);
        super.MidpointLine(S,B,MODE);
        super.MidpointLine(S,C,MODE);
        super.MidpointLine(S,D,MODE);
        super.MidpointLine(B,C,MODE);
        super.MidpointLine(C,D,MODE);
//        super.MidpointLine(A,C,MODE);
        if(K.Y>S.Y)
        {
            super.MidpointLine(A,B,lineMode.DASH);
            super.MidpointLine(A,D,lineMode.DASH);
        }
        else
        {
            super.MidpointLine(A,B,MODE);
            super.MidpointLine(A,D,MODE);
        }
    }
}

