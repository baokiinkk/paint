package com.company.xuli.xuliduongve;

import java.awt.*;
import java.util.ArrayList;

import com.company.Button;
import com.company.xuli.xuliduongve.Point2D;


public class Line extends HinhHoc {
    private ArrayList<Point2D> BunchOfStart;
    private ArrayList<Point2D> BunchOfEnd;
    private Point2D tmpStart;
    private Point2D tmpEnd;
    private Point2D center;
    private Point2D A;
    private Point2D B;
    private lineMode MODE;
    private sideMode SIDEMODE;
    private Point2D centerPoint;

    public Line(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.LINE;
        tmpStart = new Point2D();
        tmpEnd = new Point2D();
        A = new Point2D();
        B = new Point2D();
        BunchOfStart = new ArrayList<Point2D>();
        BunchOfEnd = new ArrayList<Point2D>();
        center = new Point2D();
        centerPoint = new Point2D();
    }

    @Override
    public void saveCoord(String[][] coord) {
        //System.out.println(BunchOfStart.size());
        for (int i = 0; i < BunchOfStart.size(); i++) {
            BunchOfStart.get(i).saveCoord(coord);
            BunchOfEnd.get(i).saveCoord(coord);
        }

    }

    public void setLine(Point2D start, Point2D end, Point2D centerPoint, lineMode mode, sideMode sideMode) {
        this.start = start;
        this.end = end;
        this.centerPoint = centerPoint;
        MODE = mode;
        SIDEMODE = sideMode;
        center.set((start.X+end.X) /2, (start.Y + end.Y) / 2);
        A.set(start.X,start.Y);
        B.set(end.X,end.Y);
    }

    public void draw() {
        BunchOfStart = tmpStart.chooseSideMode(centerPoint, this.start, SIDEMODE);
        BunchOfEnd = tmpEnd.chooseSideMode(centerPoint, this.end, SIDEMODE);
        for (int i = 0; i < BunchOfStart.size(); i++) {
            super.MidpointLine(BunchOfStart.get(i), BunchOfEnd.get(i), MODE);
        }

    }

    public void rotate(double alpha) {
        System.out.println("bugg");
        Point2D tmpA = A.rotatePoint(center, this.alpha + alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha + alpha);
        super.MidpointLine(tmpA,tmpB,MODE);
    }

    // khi xoay nháp sẽ có góc alpha lệch so với góc ban đầu, khi xoay xong, hàm này sẽ + góc vừa xoay vào góc hiện tại
    // ví dụ xoay hình gốc 1 góc 20 độ, thì sau khi xoay nháp xong, góc xoay gốc sẽ được cộng thêm 20 độ
    public void applyRotate(double alpha) {
        //System.out.println(this.alpha + " " + alpha);
        this.alpha += alpha;
    }

    public void move(Vector2D a) {
        Point2D tmpA = this.start.rotatePoint(center, this.alpha);
        Point2D tmpB = this.end.rotatePoint(center, this.alpha);
        tmpA = tmpA.moveVector(a);
        tmpB = tmpB.moveVector(a);

        setLine(tmpA,tmpB,centerPoint,MODE,SIDEMODE);
        draw();
    }

    public void applyMove(Vector2D a) {
        this.start = start.moveVector(a);
        this.end = end.moveVector(a);
        center = center.moveVector(a);
        draw();
    }
    public void SymOY()
    {
        this.start = this.start.VerticalSymetry(start);
        this.end = this.end.VerticalSymetry(end);
        setLine(this.start,this.end,centerPoint,MODE,SIDEMODE);
        draw();
    }
    public void SymOX()
    {
        this.start = this.start.HonrizontalSymetry(start);
        this.end = this.end.HonrizontalSymetry(end);
        setLine(this.start,this.end,centerPoint,MODE,SIDEMODE);
        draw();
    }
    public void SymP(Point2D pointSym)
    {
        this.start = this.start.PointSymetry(start,pointSym);
        this.end = this.end.PointSymetry(end,pointSym);
        setLine(this.start,this.end,centerPoint,MODE,SIDEMODE);
        draw();
    }

    public void draw(Color color){
        draw();
        MyFunction.paintColor(nextPoint,nextDrawing,center,color);
    }
}