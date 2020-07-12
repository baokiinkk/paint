package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;
import java.util.ArrayList;

public class Triangle extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D tmpA;
    private Point2D tmpB;
    private Point2D tmpC;
    private sideMode SIDEMODE;
    private lineMode MODE;

    public Triangle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.TRIANGLE;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        tmpA = new Point2D();
        tmpB = new Point2D();
        tmpC = new Point2D();
    }

    private void setTriangle(Point2D start, Point2D end) {
        A.set(start.X, start.Y);
        B.set(start.X + (start.X - end.X), end.Y);
        C.set(end.X, end.Y);
    }

    @Override
    public void saveCoord(String[][] coord) {
        A.saveCoord(coord);
        B.saveCoord(coord);
        C.saveCoord(coord);
    }

    public void setTriangle(Point2D start, Point2D end, lineMode mode, sideMode SideMode, Point2D centerpoint) {
        this.setTriangle(start, end);
        this.start = start;
        this.end = end;
        this.centerPoint =centerpoint;
        MODE = mode;
        SIDEMODE = SideMode;
        center.set(start.X, start.Y + ((2.0 / 3.0) * Math.abs(start.Y - end.Y)));
    }

    // xoay và vẽ hình hiện tại 1 góc alpha
    // đây là xoay nháp, tức là chỉ xoay hình ảo để người dùng canh góc xoay, khi nhả chuột sẽ áp dụng vào hình gốc
    public void rotate(double alpha) {

        tmpA = A.rotatePoint(center, this.alpha + alpha);
        tmpB = B.rotatePoint(center, this.alpha + alpha);
        tmpC = C.rotatePoint(center, this.alpha + alpha);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBA = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCA = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBA.get(i), MODE);
            super.MidpointLine(tmpBA.get(i), tmpCA.get(i), MODE);
            super.MidpointLine(tmpCA.get(i), tmpAA.get(i), MODE);
        }
    }

    // khi xoay nháp sẽ có góc alpha lệch so với góc ban đầu, khi xoay xong, hàm này sẽ + góc vừa xoay vào góc hiện tại
    // ví dụ xoay hình gốc 1 góc 20 độ, thì sau khi xoay nháp xong, góc xoay gốc sẽ được cộng thêm 20 độ
    public void applyRotate(double alpha) {
        //System.out.println(this.alpha + " " + alpha);
        this.alpha += alpha;
    }



    public void setTri(Point2D start, Point2D end, lineMode mode, sideMode SideMode, Point2D centerpoint) {
        SIDEMODE = SideMode;
        MODE = mode;
        A.set(start.X, start.Y);
        B.set(start.X+(start.X-end.X),end.Y );
        C.set(end.X, end.Y);
        Point2D tamB = C.rotatePoint(A, 60*3.14/180);
        A.set(start.X, start.Y);
        B.set(tamB);
        C.set(end.X, end.Y);
        this.start = start;
        this.end = end;
        this.centerPoint =centerpoint;
        center.set(end.X,start.Y-( 2/3*(start.Y-end.Y)));
    }

    /*public void draw() {
        Point2D tmpA = A.rotatePoint(center, this.alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha);
        Point2D tmpC = C.rotatePoint(center, this.alpha);
        super.MidpointLine(tmpA, tmpB, MODE);
        super.MidpointLine(tmpB, tmpC, MODE);
        super.MidpointLine(tmpC, tmpA, MODE);
    }*/

    public void draw(){
        tmpA = A.rotatePoint(center, this.alpha);
        tmpB = B.rotatePoint(center, this.alpha);
        tmpC = C.rotatePoint(center, this.alpha);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBA = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCA = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBA.get(i), MODE);
            super.MidpointLine(tmpBA.get(i), tmpCA.get(i), MODE);
            super.MidpointLine(tmpCA.get(i), tmpAA.get(i), MODE);
        }
    }

    public void move(Vector2D a) {
        tmpA = A.rotatePoint(center, this.alpha);
        tmpB = B.rotatePoint(center, this.alpha);
        tmpC = C.rotatePoint(center, this.alpha);

        tmpA = tmpA.moveVector(a);
        tmpB = tmpB.moveVector(a);
        tmpC = tmpC.moveVector(a);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpAA.get(i), MODE);
        }
    }

    public void applyMove(Vector2D a) {
        A = A.moveVector(a);
        B = B.moveVector(a);
        C = C.moveVector(a);
        tmpA = tmpA.moveVector(a);
        tmpB = tmpB.moveVector(a);
        tmpC = tmpC.moveVector(a);
        center = center.moveVector(a);
        draw();
    }
    public void SymOY()
    {
        tmpA = tmpA.VerticalSymetry(tmpA);
        tmpB = tmpB.VerticalSymetry(tmpB);
        tmpC = tmpC.VerticalSymetry(tmpC);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpAA.get(i), MODE);
        }
        draw();
    }
    public void SymOX()
    {
        tmpA = tmpA.HonrizontalSymetry(tmpA);
        tmpB = tmpB.HonrizontalSymetry(tmpB);
        tmpC = tmpC.HonrizontalSymetry(tmpC);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpAA.get(i), MODE);
        }
        draw();
    }
    public void SymP(Point2D pointSym)
    {
        tmpA = tmpA.PointSymetry(tmpA,pointSym);
        tmpB = tmpB.PointSymetry(tmpB,pointSym);
        tmpC = tmpC.PointSymetry(tmpC,pointSym);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpAA.get(i), MODE);
        }
        draw();
    }

    public void draw(Color color){
        draw();
        MyFunction.paintColor(nextPoint,nextDrawing,center,color);
    }
}