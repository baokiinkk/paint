package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;
import java.util.ArrayList;

public class Rectangle extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private Point2D tmpA;
    private Point2D tmpB;
    private Point2D tmpC;
    private Point2D tmpD;
    private lineMode MODE;
    private sideMode SIDEMODE;

    // khởi tạo hình chữ nhật
    public Rectangle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.RECTANGLE;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
        tmpA = new Point2D();
        tmpB = new Point2D();
        tmpC = new Point2D();
        tmpC = new Point2D();
    }

    // set 4 điểm hình chữ nhật
    private void setRectangle(Point2D start, Point2D end) {
        A.set(start.X, start.Y);              // (start) A-----------------B
        B.set(end.X, start.Y);                //         |                 |
        C.set(end.X, end.Y);                  //         |                 |
        D.set(start.X, end.Y);                //         D-----------------C  (end)
    }


    public void saveCoord(String[][] coord) {
        A.rotatePoint(center, this.alpha).saveCoord(coord);
        B.rotatePoint(center, this.alpha).saveCoord(coord);
        C.rotatePoint(center, this.alpha).saveCoord(coord);
        D.rotatePoint(center, this.alpha).saveCoord(coord);

    }

    // set đầy đủ thông hình chữ nhật, gồm loại nét vẽ, các đỉnh, và tâm HCN
    public void setRectangle(Point2D start, Point2D end,Point2D centerPoint, lineMode mode, sideMode SideMode) {
        this.centerPoint = centerPoint;
        SIDEMODE = SideMode;
        this.setRectangle(start, end);
        this.start = start;
        this.end = end;
        MODE = mode;
        center.set((start.X + end.X) / 2, (start.Y + end.Y) / 2);
    }

    // xoay và vẽ hình hiện tại 1 góc alpha
    // đây là xoay nháp, tức là chỉ xoay hình ảo để người dùng canh góc xoay, khi nhả chuột sẽ áp dụng vào hình gốc
    public void rotate(double alpha) {

        tmpA = A.rotatePoint(center, this.alpha + alpha);
        tmpB = B.rotatePoint(center, this.alpha + alpha);
        tmpC = C.rotatePoint(center, this.alpha + alpha);
        tmpD = D.rotatePoint(center, this.alpha + alpha);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        ArrayList<Point2D> tmpDD = tmpD.chooseSideMode(centerPoint, tmpD, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpDD.get(i), MODE);
            super.MidpointLine(tmpDD.get(i), tmpAA.get(i), MODE);
        }
    }

    // khi xoay nháp sẽ có góc alpha lệch so với góc ban đầu, khi xoay xong, hàm này sẽ + góc vừa xoay vào góc hiện tại
    // ví dụ xoay hình gốc 1 góc 20 độ, thì sau khi xoay nháp xong, góc xoay gốc sẽ được cộng thêm 20 độ
    public void applyRotate(double alpha) {
        //System.out.println(this.alpha + " " + alpha);
        this.alpha += alpha;
    }


    // set hình vuông, lấy độ dài cạnh bằng min độ dài vector AB và AD
    // có vector AB và AD => các điểm của hình vuông
    public void setSquare(Point2D start, Point2D end,Point2D centerPoint, lineMode mode, sideMode SideMode) {
        //end.set();
        SIDEMODE = SideMode;
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
        this.centerPoint = centerPoint;
        center.set((start.X + end.X) / 2, (start.Y + end.Y) / 2);
    }

    // vẽ hình chữ nhật dựa vào 4 điểm
   /* public void draw() {
        Point2D tmpA = A.rotatePoint(center, this.alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha);
        Point2D tmpC = C.rotatePoint(center, this.alpha);
        Point2D tmpD = D.rotatePoint(center, this.alpha);
        super.MidpointLine(tmpA, tmpB, MODE);
        super.MidpointLine(tmpB, tmpC, MODE);
        super.MidpointLine(tmpC, tmpD, MODE);
        super.MidpointLine(tmpD, tmpA, MODE);
    }*/

    public void draw(){
        tmpA = A.rotatePoint(center, this.alpha);
        tmpB = B.rotatePoint(center, this.alpha);
        tmpC = C.rotatePoint(center, this.alpha);
        tmpD = D.rotatePoint(center, this.alpha);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        ArrayList<Point2D> tmpDD = tmpD.chooseSideMode(centerPoint, tmpD, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpDD.get(i), MODE);
            super.MidpointLine(tmpDD.get(i), tmpAA.get(i), MODE);
        }
    }

    public void move(Vector2D a) {
        tmpA = A.rotatePoint(center, this.alpha);
        tmpB = B.rotatePoint(center, this.alpha);
        tmpC = C.rotatePoint(center, this.alpha);
        tmpD = D.rotatePoint(center, this.alpha);
        tmpA = tmpA.moveVector(a);
        tmpB = tmpB.moveVector(a);
        tmpC = tmpC.moveVector(a);
        tmpD = tmpD.moveVector(a);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        ArrayList<Point2D> tmpDD = tmpD.chooseSideMode(centerPoint, tmpD, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpDD.get(i), MODE);
            super.MidpointLine(tmpDD.get(i), tmpAA.get(i), MODE);
        }
    }

    public void applyMove(Vector2D a) {
        A = A.moveVector(a);
        B = B.moveVector(a);
        C = C.moveVector(a);
        D = D.moveVector(a);
        tmpA = tmpA.moveVector(a);
        tmpB = tmpB.moveVector(a);
        tmpC = tmpC.moveVector(a);
        tmpD = tmpD.moveVector(a);
        center = center.moveVector(a);
        draw();
    }
    public void SymOY()
    {
        tmpA = tmpA.VerticalSymetry(tmpA);
        tmpB = tmpB.VerticalSymetry(tmpB);
        tmpC = tmpC.VerticalSymetry(tmpC);
        tmpD = tmpD.VerticalSymetry(tmpD);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        ArrayList<Point2D> tmpDD = tmpD.chooseSideMode(centerPoint, tmpD, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpDD.get(i), MODE);
            super.MidpointLine(tmpDD.get(i), tmpAA.get(i), MODE);
        }
        draw();
    }
    public void SymOX()
    {
        tmpA = tmpA.HonrizontalSymetry(tmpA);
        tmpB = tmpB.HonrizontalSymetry(tmpB);
        tmpC = tmpC.HonrizontalSymetry(tmpC);
        tmpD = tmpD.HonrizontalSymetry(tmpD);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        ArrayList<Point2D> tmpDD = tmpD.chooseSideMode(centerPoint, tmpD, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpDD.get(i), MODE);
            super.MidpointLine(tmpDD.get(i), tmpAA.get(i), MODE);
        }
        draw();
    }
    public void SymP(Point2D pointSym)
    {

        tmpA = tmpA.PointSymetry(tmpA,pointSym);
        tmpB = tmpB.PointSymetry(tmpB,pointSym);
        tmpC = tmpC.PointSymetry(tmpC,pointSym);
        tmpD = tmpD.PointSymetry(tmpD,pointSym);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBB = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCC = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        ArrayList<Point2D> tmpDD = tmpD.chooseSideMode(centerPoint, tmpD, SIDEMODE);
        for(int i = 0;i<tmpAA.size();i++)
        {
            super.MidpointLine(tmpAA.get(i), tmpBB.get(i), MODE);
            super.MidpointLine(tmpBB.get(i), tmpCC.get(i), MODE);
            super.MidpointLine(tmpCC.get(i), tmpDD.get(i), MODE);
            super.MidpointLine(tmpDD.get(i), tmpAA.get(i), MODE);
        }
        draw();
    }

    public void draw(Color color){
        draw();
        MyFunction.paintColor(nextPoint,nextDrawing,center,color);
    }
}
