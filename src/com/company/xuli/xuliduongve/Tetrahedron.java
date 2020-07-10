
package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;
import java.util.ArrayList;

public class Tetrahedron extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private lineMode MODE;

    public Tetrahedron(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.TETRAHEDRON;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
    }

    private void setTetrahedron(Point2D start, Point2D end) {
        int d=end.Y-start.Y;
        int b=end.X-start.X;
        A.set(start.X, start.Y);
        B.set(start.X+(start.X-end.X),end.Y );
        C.set(end.X, end.Y);
        if(start.X>=end.X)
            D.set(start.X-b/4+d/2,end.Y+d/2);
        else if(start.X<end.X)
            D.set(start.X+b/4-d/2,end.Y+d/2);
    }

    // set đầy đủ thông hình chữ nhật, gồm loại nét vẽ, các đỉnh, và tâm HCN
    public void setTetrahedron(Point2D start, Point2D end, lineMode mode) {
        this.setTetrahedron(start, end);
        this.start = start;
        this.end = end;
        MODE = mode;
    }
    public void draw() {
        super.MidpointLine(A,B,MODE);
        super.MidpointLine(A,C,MODE);
        super.MidpointLine(C,D,MODE);
        super.MidpointLine(B,D,MODE);
        if(end.Y>start.Y)
        {
            super.MidpointLine(A,D,MODE);
            super.MidpointLine(B,C,lineMode.DASH);
        }
        else
        {
            super.MidpointLine(A,D,lineMode.DASH);
            super.MidpointLine(B,C,MODE);
        }
    }
}
