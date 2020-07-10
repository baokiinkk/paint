
package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;
import java.util.ArrayList;

public class Tetrahedron extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;

    private Point3D A3;
    private Point3D B3;
    private Point3D C3;
    private Point3D D3;
    private Point3D Center3D;

    private lineMode MODE;

    public Tetrahedron(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.TETRAHEDRON;
        start = new Point2D();
        end = new Point2D();
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();

        A3 = new Point3D();
        B3 = new Point3D();
        C3 = new Point3D();
        D3 = new Point3D();
        Center3D = new Point3D();
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

    public void setTetrahedron3D(Point3D Start, Point3D End, lineMode mode) {
        int radOut = (int) Math.round(Math.sqrt(Math.pow(Start.X - End.X, 2) + Math.pow(Start.Y - End.Y, 2)));
        int edge = (int) Math.round(Math.sqrt(Math.pow(radOut, 2) - Math.pow(radOut / 2, 2)));
        Center3D.set(Start.X, Start.Y, End.Z);
        MODE = mode;
        A3.set(Start);
        B3.set(Center3D.X - edge, Center3D.Y - radOut / 2, Center3D.Z);
        C3.set(Center3D.X + edge, Center3D.Y - radOut / 2, Center3D.Z);
        D3.set(Center3D.X, Center3D.Y + radOut, Center3D.Z);
        this.start.set(A3.to2D());
        this.end.set(C3.to2D());
        A.set(A3.to2D());
        B.set(B3.to2D());
        C.set(C3.to2D());
        D.set(D3.to2D());
    }

    @Override
    public void saveCoord(String[][] coord) {
        Center3D.saveCoord(coord);
        A3.saveCoord(coord);
        B3.saveCoord(coord);
        C3.saveCoord(coord);
        D3.saveCoord(coord);
    }

    public void draw() {
        super.MidpointLine(A, B, MODE);
        super.MidpointLine(A, C, MODE);
        super.MidpointLine(C, D, MODE);
        super.MidpointLine(B, D, MODE);
        if (end.Y > start.Y) {
            super.MidpointLine(A, D, MODE);
            super.MidpointLine(B, C, lineMode.DASH);
        }
        else
        {
            super.MidpointLine(A,D,lineMode.DASH);
            super.MidpointLine(B,C,MODE);
        }
    }
}
