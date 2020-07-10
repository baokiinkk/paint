package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;

public class Cube extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private Point2D E;
    private Point2D F;
    private Point2D G;
    private Point2D H;
    private lineMode MODE;
    private Point3D A3;
    private Point3D B3;
    private Point3D C3;
    private Point3D D3;
    private Point3D E3;
    private Point3D F3;
    private Point3D G3;
    private Point3D H3;

    //khởi tạo hình hộp chữ nhật
    public Cube(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.CUBE;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
        E = new Point2D();
        F = new Point2D();
        G = new Point2D();
        H = new Point2D();
    }

    private void setCube(Point2D start, Point2D end) {
        int d = (end.Y - start.Y) / 2;
        A.set(start.X, start.Y);
        B.set(end.X, start.Y);
        C.set(end.X, end.Y);
        D.set(start.X, end.Y);
        E.set(start.X+d, start.Y-d);
        F.set(end.X+d, start.Y-d);
        G.set(end.X+d, end.Y-d);
        H.set(start.X + d, end.Y - d);
    }

    public void setCube(Point2D start, Point2D end, lineMode mode) {
        this.setCube(start, end);
        this.start = start;
        this.end = end;
        MODE = mode;
    }

    public void setCube(Point3D Start, Point3D End) {
        Point3D tmpS = new Point3D(Start.X, Start.Y, Start.Z);
        Point3D tmpE = new Point3D(End.X, End.Y, End.Z);
        End.set(Math.max(tmpS.X, tmpE.X), Math.max(tmpS.Y, tmpE.Y), Math.max(tmpS.Z, tmpE.Z));
        Start.set(Math.min(tmpS.X, tmpE.X), Math.min(tmpS.Y, tmpE.Y), Math.min(tmpS.Z, tmpE.Z));

//        System.out.println(tmpS.X + " " + tmpS.Y + " " + tmpS.Z);
//        System.out.println(tmpE.X + " " + tmpE.Y + " " + tmpE.Z);
        MODE = lineMode.DEFAULT;

        A3 = new Point3D(Start.X, Start.Y, Start.Z);
        B3 = new Point3D(End.X, Start.Y, Start.Z);
        C3 = new Point3D(End.X, Start.Y, End.Z);
        D3 = new Point3D(Start.X, Start.Y, End.Z);
        E3 = new Point3D(Start.X, End.Y, Start.Z);
        F3 = new Point3D(End.X, End.Y, Start.Z);
        G3 = new Point3D(End.X, End.Y, End.Z);
        H3 = new Point3D(Start.X, End.Y, End.Z);
//

        A.set(A3.to2D());
        B.set(B3.to2D());
        C.set(C3.to2D());
        D.set(D3.to2D());
        E.set(E3.to2D());
        F.set(F3.to2D());
        G.set(G3.to2D());
        H.set(H3.to2D());
        //MODE = mode;
    }

    @Override
    public void saveCoord(String[][] coord) {
        //super.saveCoord(coord);
        A3.saveCoord(coord);
        B3.saveCoord(coord);
        C3.saveCoord(coord);
        D3.saveCoord(coord);
        E3.saveCoord(coord);
        F3.saveCoord(coord);
        G3.saveCoord(coord);
        H3.saveCoord(coord);
    }

    public void draw3D() {
        super.MidpointLine(A, B, lineMode.DASH);
        super.MidpointLine(A, D, lineMode.DASH);
        super.MidpointLine(A, E, lineMode.DASH);
        super.MidpointLine(C, D, MODE);
        super.MidpointLine(C, B, MODE);
        super.MidpointLine(D, H, MODE);
        super.MidpointLine(C, G, MODE);
        super.MidpointLine(B, F, MODE);
        super.MidpointLine(H, G, MODE);
        super.MidpointLine(G, F, MODE);
        super.MidpointLine(F, E, MODE);
        super.MidpointLine(E, H, MODE);
    }


    public void draw() {
//        A = A.rotatePoint(center, this.alpha);
//        B = B.rotatePoint(center, this.alpha);
//        C = C.rotatePoint(center, this.alpha);
//        D = D.rotatePoint(center, this.alpha);
//        E = E.rotatePoint(center, this.alpha);
//        F = F.rotatePoint(center, this.alpha);
//        G = G.rotatePoint(center, this.alpha);
//        H = H.rotatePoint(center, this.alpha);
        if (B.X <= D.X && B.Y >= D.Y) {
            super.MidpointLine(A, B, lineMode.DASH);
            super.MidpointLine(B, C, lineMode.DASH);
            super.MidpointLine(C, D, MODE);
            super.MidpointLine(D, A, MODE);
            super.MidpointLine(E, F, MODE);
            super.MidpointLine(F, G, MODE);
            super.MidpointLine(G, H, MODE);
            super.MidpointLine(H, E, MODE);
            super.MidpointLine(A, E, MODE);
            super.MidpointLine(B, F, lineMode.DASH);
            super.MidpointLine(C, G, MODE);
            super.MidpointLine(D, H, MODE);
        }
        else if( B.X <= D.X && B.Y < D.Y) {
            super.MidpointLine(A, B, MODE);
            super.MidpointLine(B, C, MODE);
            super.MidpointLine(C, D, MODE);
            super.MidpointLine(D, A, MODE);
            super.MidpointLine(E, F, MODE);
            super.MidpointLine(F, G, lineMode.DASH);
            super.MidpointLine(G, H, lineMode.DASH);
            super.MidpointLine(H, E, MODE);
            super.MidpointLine(A, E, MODE);
            super.MidpointLine(B, F, MODE);
            super.MidpointLine(C, G, lineMode.DASH);
            super.MidpointLine(D, H, MODE);
        }
        else if( B.X > D.X && B.Y < D.Y) {
            super.MidpointLine(A, B, MODE);
            super.MidpointLine(B, C, MODE);
            super.MidpointLine(C, D, MODE);
            super.MidpointLine(D, A, MODE);
            super.MidpointLine(E, F, MODE);
            super.MidpointLine(F, G, MODE);
            super.MidpointLine(G, H, lineMode.DASH);
            super.MidpointLine(H, E, lineMode.DASH);
            super.MidpointLine(A, E, MODE);
            super.MidpointLine(B, F, MODE);
            super.MidpointLine(C, G, MODE);
            super.MidpointLine(D, H, lineMode.DASH);
        }
        else if( B.X > D.X && B.Y >= D.Y){
            super.MidpointLine(A, B, lineMode.DASH);
            super.MidpointLine(B, C, MODE);
            super.MidpointLine(C, D, MODE);
            super.MidpointLine(D, A, lineMode.DASH);
            super.MidpointLine(E, F, MODE);
            super.MidpointLine(F, G, MODE);
            super.MidpointLine(G, H, MODE);
            super.MidpointLine(H, E, MODE);
            super.MidpointLine(A, E, lineMode.DASH);
            super.MidpointLine(B, F, MODE);
            super.MidpointLine(C, G, MODE);
            super.MidpointLine(D, H, MODE);
        }
    }
}