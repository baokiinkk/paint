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
    public com.company.Button tag;// loại hình
    public ArrayList<Point2D> listPointCoord;
    public ArrayList<Color> listPointColor;

    // khởi tạo các thông số cơ bản của hình
    public HinhHoc(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        this.chooseColor = chooseColor;
        this.nextDrawing = nextDrawing;
        this.nextPoint = nextPoint;
        this.center = new Point2D();
        this.alpha = 0;
        listPointCoord = new ArrayList<>();
        listPointColor = new ArrayList<>();
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


    private void putPixel(int x, int y) {
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