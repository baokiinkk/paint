package com.company.xuli.xuliduongve;

import java.awt.*;
import java.util.ArrayList;

public class HinhHoc {
    public boolean[][] nextDrawing;
    public Color[][] nextPoint;
    public Color chooseColor;
    public Point2D start;             // điểm bắt đầu để vẽ hình
    public Point2D end;               // điểm kết thúc để vẽ hình
    public Point2D center;            // tâm của hình
    public lineMode mode;             // chế độ nét vẽ
    public double alpha;              // góc xoay hiện tại của hình so với hình gốc, hình gốc sẽ có góc = 0
    public com.company.Button tag;    // loại hình

    // khởi tạo các thông số cơ bản của hình
    public HinhHoc(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        this.chooseColor = chooseColor;
        this.nextDrawing = nextDrawing;
        this.nextPoint = nextPoint;
        this.center = new Point2D();
        this.alpha = 0;
    }

    public void MidpointLine(Point2D start, Point2D end, lineMode Mode) {
        // tính khoảng cách 2 điểm
        float x = start.X, y = start.Y, temp = (Math.abs(end.X - start.X) >= Math.abs(end.Y - start.Y)) ? Math.abs(end.X - start.X) : Math.abs(end.Y - start.Y);

        // lm tron len điểm đó
        int tx = (int) (x + 0.5);
        int ty = (int) (y + 0.5);

        // lưu vị trí điểm và màu của điểm đó vào danh sách

        if (MyFunction.isSafe(nextPoint, tx, ty)) {
            nextDrawing[tx][ty] = true;
            nextPoint[tx][ty] = chooseColor;
        }


        // duyệt khoảng cách 2 điểm.
        //System.out.println("run");
        for (int i = 0; i < temp; i++) {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (end.X - start.X) / temp;
            y += (end.Y - start.Y) / temp;

            // lm tròn lên điểm đó
            tx = (int) (x + 0.5);
            ty = (int) (y + 0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty) && MyFunction.chooseMode(i, Mode)) {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
            if (Mode == lineMode.ARROW) {
                Vector2D v = new Vector2D(end, start);
                if (v.length() != 0) {
                    int l = 5;
                    Point2D c = v.kTimesUnit(end, l);
                    double alpha = v.alphaOXY();
                    //System.out.println("alpha: " + alpha);
                    Point2D c1 = new Point2D(c.X, c.Y + l);
                    Point2D c2 = new Point2D(c.X, c.Y - l);
                    MidpointLine(end, c1.rotatePoint(c, alpha), lineMode.DEFAULT);
                    MidpointLine(end, c2.rotatePoint(c, alpha), lineMode.DEFAULT);
                }
            }
        }
    }
    public void drawZigZag(ArrayList<Point2D> diem){
        System.out.println(diem.size());
        for(int i=0;i<diem.size()-1;i++){
            System.out.println(diem.get(i).X + "," + diem.get(i).Y + "--" + diem.get(i+1).X + "," + diem.get(i+1).Y);
            MidpointLine(diem.get(i), diem.get(i+1), this.mode);
        }
    }
    public void drawingCircle(Point2D start, Point2D end, lineMode mode) {

        if (start.X != -1 && start.Y != -1) {
            int r = Math.abs(start.X - end.X);
            int x = 0;
            int y = r;
            if (MyFunction.chooseMode(x, mode))
                Draw8Point(x, y, start.X, start.Y);
            while (x <= y) {
                x++;
                y = (int) (Math.round(Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2))) + 0.5);
                if (MyFunction.chooseMode(x, mode))
                    Draw8Point(x, y, start.X, start.Y);
            }
        }
    }
    public void drawEllipse(Point2D start, Point2D end,lineMode mode) {
//
        //pt elip (x^2/a^2 + y^2/b^2 = 1) => y = sqrt(1 - (x^2/a^2))* b^2)
        // tam  (xstart;ystart)
        //truc
        double a = Math.abs(start.X - end.X);
        double b = Math.abs(start.Y - end.Y);

        int x1_c = 0;
        while(a>x1_c)
        {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //IV
            x1_c++;
            double y1_c = Math.sqrt((a*a*b*b - b*b*x1_c*x1_c)/(a*a*1.0));
            double y2_c = start.Y - y1_c;
            double tx_c = start.X - x1_c;

            int ty1_c = (int) (y1_c + 0.5);
            int ty2_c = (int) (y2_c + 0.5);
            int x2_c = (int) (tx_c + 0.5);

            Quadrant(x1_c + start.X,ty1_c +start.Y,x2_c,ty2_c,mode);

        }
        int y1_r = 0;
        while(y1_r<b)
        {
            y1_r++;
            double x1_r = Math.sqrt((a*a*b*b - a*a*y1_r*y1_r)/(b*b*1.0));
            double x2_r = start.X - x1_r;
            double ty_r = start.Y - y1_r;

            int tx1_r = (int) (x1_r + 0.5);
            int tx2_r = (int) (x2_r + 0.5);
            int y2_r = (int) (ty_r + 0.5);

            Quadrant(tx1_r+start.X,y1_r+start.Y,tx2_r,y2_r, mode);
        }
    }

    public void Quadrant(int x1,int y1,int x2, int y2,lineMode mode)
    {
        //ve cac duong cong ung voi cac goc phan tu
        if (MyFunction.isSafe(nextPoint, x1,y1) && MyFunction.chooseMode(x1, mode)) {
            nextDrawing[x1][y1] = true;
            nextPoint[x1][y1] = chooseColor;
        } // IV
        if (MyFunction.isSafe(nextPoint, x1,y2) && MyFunction.chooseMode(x1, mode)) {
            nextDrawing[x1][y2] = true;
            nextPoint[x1][y2] = chooseColor;
        } //I
        if (MyFunction.isSafe(nextPoint,x2,y1) && MyFunction.chooseMode(x2, mode)) {
            nextDrawing[x2][y1] = true;
            nextPoint[x2][y1] = chooseColor;
        }//III
        if (MyFunction.isSafe(nextPoint, x2,y2) && MyFunction.chooseMode(x2, mode)) {
            nextDrawing[x2][y2] = true;
            nextPoint[x2][y2] = chooseColor;
        }//II
    }

    public void putPixel(int x, int y) {
        float x1 = x, y1 = y;
        int tx = (int) (x1 + 0.5);
        int ty = (int) (y1 + 0.5);
        if (MyFunction.isSafe(nextPoint, tx, ty)) {
            nextDrawing[tx][ty] = true;
            nextPoint[tx][ty] = chooseColor;
        }
    }

    public void Draw8Point(int x, int y, int xd, int yd) {
        putPixel(x + xd, y + yd);
        putPixel(-x + xd, y + yd);
        putPixel(x + xd, -y + yd);
        putPixel(-x + xd, -y + yd);
        putPixel(y + xd, x + yd);
        putPixel(-y + xd, x + yd);
        putPixel(y + xd, -x + yd);
        putPixel(-y + xd, -x + yd);
    }

    public void Draw4Point(int x, int y, int xd, int yd) {
        putPixel(x + xd, y + yd);
        putPixel(x + xd, -y + yd);
        putPixel(-x + xd, -y + yd);
        putPixel(-x + xd, y + yd);
    }

}
