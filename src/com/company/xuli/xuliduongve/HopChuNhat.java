package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;

public class HopChuNhat extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private Point2D E;
    private Point2D F;
    private Point2D G;
    private Point2D H;
    private lineMode MODE;

    //khởi tạo hình bình hành
    public HopChuNhat(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.ZIGZAG;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
        E = new Point2D();
        F = new Point2D();
        G = new Point2D();
        H = new Point2D();
    }

    private void setHopChuNhat(Point2D start, Point2D end) {
        int d = (end.Y - start.Y) / 2;
        int a = (Math.abs(end.X - start.X)) * 2;
        A.set(end.X, start.Y - d);
        B.set(start.X, start.Y);
        C.set(start.X, end.Y + d);
        D.set(end.X, end.Y);
        E.set(end.X + a, start.Y - d);
        F.set(start.X + a, start.Y);
        G.set(start.X + a, end.Y + d);
        H.set(end.X + a, end.Y);
    }

    public void setHopChuNhat(Point2D start, Point2D end, lineMode mode) {
        this.setHopChuNhat(start, end);
        this.start = start;
        this.end = end;
        MODE = mode;
    }

    public void draw() {
        A = A.rotatePoint(center, this.alpha);
        B = B.rotatePoint(center, this.alpha);
        C = C.rotatePoint(center, this.alpha);
        D = D.rotatePoint(center, this.alpha);
        E = E.rotatePoint(center, this.alpha);
        F = F.rotatePoint(center, this.alpha);
        G = G.rotatePoint(center, this.alpha);
        H = H.rotatePoint(center, this.alpha);
//        if( B.X <= D.X && B.Y >= D.Y){
//            super.MidpointLine(A, B, MODE);
//            super.MidpointLine(B, C, MODE);
//            super.MidpointLine(C, D, MODE);
//            super.MidpointLine(D, A, MODE);
//            super.MidpointLine(E, F, MODE.DASH);
//            super.MidpointLine(F, G, MODE.DASH);
//            super.MidpointLine(G, H, MODE);
//            super.MidpointLine(H, E, MODE);
//            super.MidpointLine(A, E, MODE);
//            super.MidpointLine(B, F, MODE.DASH);
//            super.MidpointLine(C, G, MODE);
//            super.MidpointLine(D, H, MODE);
//        }
        //else
            if( B.X <= D.X && B.Y < D.Y){
            super.MidpointLine(A, B, MODE);
            super.MidpointLine(B, C, MODE);
            super.MidpointLine(C, D, MODE.DASH);
            super.MidpointLine(D, A, MODE.DASH);
            super.MidpointLine(E, F, MODE);
            super.MidpointLine(F, G, MODE);
            super.MidpointLine(G, H, MODE);
            super.MidpointLine(H, E, MODE);
            super.MidpointLine(A, E, MODE);
            super.MidpointLine(B, F, MODE);
            super.MidpointLine(C, G, MODE);
            super.MidpointLine(D, H, MODE.DASH);
//        }
//        else if( B.X > D.X && B.Y < D.Y){
//            super.MidpointLine(A, B, MODE);
//            super.MidpointLine(B, C, MODE);
//            super.MidpointLine(C, D, MODE);
//            super.MidpointLine(D, A, MODE);
//            super.MidpointLine(E, F, MODE);
//            super.MidpointLine(F, G, MODE);
//            super.MidpointLine(G, H, MODE.DASH);
//            super.MidpointLine(H, E, MODE.DASH);
//            super.MidpointLine(A, E, MODE);
//            super.MidpointLine(B, F, MODE);
//            super.MidpointLine(C, G, MODE);
//            super.MidpointLine(D, H, MODE.DASH);
//        }
//        else if( B.X > D.X && B.Y >= D.Y){
//            super.MidpointLine(A, B, MODE.DASH);
//            super.MidpointLine(B, C, MODE.DASH);
//            super.MidpointLine(C, D, MODE);
//            super.MidpointLine(D, A, MODE);
//            super.MidpointLine(E, F, MODE);
//            super.MidpointLine(F, G, MODE);
//            super.MidpointLine(G, H, MODE);
//            super.MidpointLine(H, E, MODE);
//            super.MidpointLine(A, E, MODE);
//            super.MidpointLine(B, F, MODE.DASH);
//            super.MidpointLine(C, G, MODE);
//            super.MidpointLine(D, H, MODE);
        }
        }
}
