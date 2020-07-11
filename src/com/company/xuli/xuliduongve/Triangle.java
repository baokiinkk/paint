package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;
import java.util.ArrayList;

public class Triangle extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private sideMode SIDEMODE;
    private lineMode MODE;

    // khởi tạo hình chữ nhật
    public Triangle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.TRIANGLE;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
    }

    // set 4 điểm hình chữ nhật
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

    // set đầy đủ thông hình chữ nhật, gồm loại nét vẽ, các đỉnh, và tâm HCN
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

        Point2D tmpA = A.rotatePoint(center, this.alpha + alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha + alpha);
        Point2D tmpC = C.rotatePoint(center, this.alpha + alpha);
        ArrayList<Point2D> tmpAA = tmpA.chooseSideMode(centerPoint, tmpA, SIDEMODE);
        ArrayList<Point2D> tmpBA = tmpB.chooseSideMode(centerPoint, tmpB, SIDEMODE);
        ArrayList<Point2D> tmpCA = tmpC.chooseSideMode(centerPoint, tmpC, SIDEMODE);
        /*Point2D tmpA = A.rotatePoint(center, this.alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha);
        Point2D tmpC = C.rotatePoint(center, this.alpha);
        Point2D tmpD = D.rotatePoint(center, this.alpha);*/
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



    public void setTri(Point2D start, Point2D end, lineMode mode) {
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
        center.set(end.X,start.Y-( 2/3*(start.Y-end.Y)));
    }


    // vẽ hình chữ nhật dựa vào 4 điểm
    /*public void draw() {
        Point2D tmpA = A.rotatePoint(center, this.alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha);
        Point2D tmpC = C.rotatePoint(center, this.alpha);
        super.MidpointLine(tmpA, tmpB, MODE);
        super.MidpointLine(tmpB, tmpC, MODE);
        super.MidpointLine(tmpC, tmpA, MODE);
    }*/

    public void draw(){
        ArrayList<Point2D> tmpA = A.chooseSideMode(centerPoint, A, SIDEMODE);
        ArrayList<Point2D> tmpB = B.chooseSideMode(centerPoint, B, SIDEMODE);
        ArrayList<Point2D> tmpC = C.chooseSideMode(centerPoint, C, SIDEMODE);
        /*Point2D tmpA = A.rotatePoint(center, this.alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha);
        Point2D tmpC = C.rotatePoint(center, this.alpha);
        Point2D tmpD = D.rotatePoint(center, this.alpha);*/
        for(int i = 0;i<tmpA.size();i++)
        {
            super.MidpointLine(tmpA.get(i), tmpB.get(i), MODE);
            super.MidpointLine(tmpB.get(i), tmpC.get(i), MODE);
            super.MidpointLine(tmpC.get(i), tmpA.get(i), MODE);
        }
    }

    public void move(Vector2D a) {
        Point2D tmpA = A.rotatePoint(center, this.alpha);
        Point2D tmpB = B.rotatePoint(center, this.alpha);
        Point2D tmpC = C.rotatePoint(center, this.alpha);
        tmpA = tmpA.moveVector(a);
        tmpB = tmpB.moveVector(a);
        tmpC = tmpC.moveVector(a);
        super.MidpointLine(tmpA, tmpB, MODE);
        super.MidpointLine(tmpB, tmpC, MODE);
        super.MidpointLine(tmpC, tmpA, MODE);
    }

    public void applyMove(Vector2D a) {
        A = A.moveVector(a);
        B = B.moveVector(a);
        C = C.moveVector(a);
        center = center.moveVector(a);
        draw();
    }
    public void SymOY()
    {
        A = A.VerticalSymetry(A);
        B = B.VerticalSymetry(B);
        C = C.VerticalSymetry(C);
        super.MidpointLine(A, B, MODE);
        super.MidpointLine(B, C, MODE);
        super.MidpointLine(C, A, MODE);
    }
    public void SymOX()
    {
        A = A.HonrizontalSymetry(A);
        B = B.HonrizontalSymetry(B);
        C = C.HonrizontalSymetry(C);
        super.MidpointLine(A, B, MODE);
        super.MidpointLine(B, C, MODE);
        super.MidpointLine(C, A, MODE);
    }
    public void SymP(Point2D pointSym)
    {
        A = A.PointSymetry(A,pointSym);
        B = B.PointSymetry(B,pointSym);
        C = C.PointSymetry(C,pointSym);
        super.MidpointLine(A, B, MODE);
        super.MidpointLine(B, C, MODE);
        super.MidpointLine(C, A, MODE);
    }

    public void draw(Color color){
        draw();
        MyFunction.paintColor(nextPoint,nextDrawing,center,color);
    }
}