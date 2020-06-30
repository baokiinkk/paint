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

    public void drawEllipse(Point2D start, Point2D end, lineMode mode) {
//        int d= MyFunction.tinhKhoangCach2Diem(xStart,yStart,mX,mY);
//        int a=2*d, b=d;
//        //nhánh từ trên xuống
//        int x=0, y=b;
//        float a2= (float) Math.pow(a,2);
//        float b2= (float) Math.pow(b,2);
//        float p=  (2*((float)b2/a2)-2*b+1);
//        while(((float)b2/a2)*x<=y) {
//            if(x%2 == 0 )
//                draw4Point(x, y, xStart, yStart);
//            if(p<0)
//                p=p+2*((float)b2/a2)*(2*x+3);
//            else {
//                p=p-4*y+2*((float)b2/a2)*(2*x+3);
//                y--;
//            }
//            x++;
//        }
        //nhánh từ dưới lên
//         x=a;
//        y=0;
//        p=2*((float)a2/b2)-2*a+1;
//        while(((float)a2/b2)*y<=x){
//            draw4Point(x, y, xStart,yStart );
//            if(p<0)
//                p=p+2*((float)a2/b2)*(2*y+3);
//            else{
//                p=p-4*x+2*((float)a2/b2)*(2*y+3);
//                x--;
//            }
//            y++;
//        }

        double d = Math.abs(end.X - start.X);
        double r = Math.abs(end.Y - start.Y);
        int cx, cy;
        cx = start.X;
        cy = start.Y;
        double B = r;
        double A = d;
        double px = 0, py = 0;
        Point2D startDot = new Point2D(), endDot = new Point2D();
        for (int i = 60; i <= 240; i++) {
            double x, y;
            x = A * Math.sin(Math.toRadians(i) + 0.5);
            y = B * Math.cos(Math.toRadians(i) + 0.5);
            startDot.set(px + cx, py + cy);
            endDot.set(x + cx, y + cy);
            if (i != 60)
                // draw a line joining previous and new point .
                new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startDot, endDot, lineMode.DASH);
            // store the previous points
            px = x;
            py = y;
        }
        for (int i = 240; i <= 420; i++) {
            double x, y;
            x = A * Math.sin(Math.toRadians(i) + 0.5);
            y = B * Math.cos(Math.toRadians(i) + 0.5);
            if (i != 240)
            // draw a line joining previous and new point .
            {
                startDot.set(px + cx, py + cy);
                endDot.set(x + cx, y + cy);
                new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startDot, endDot, lineMode.DEFAULT);
            }
            // store the previous points
            px = x;
            py = y;
        }
        //pt ellipse
//        int a= (int) d;
//        int b= (int) d/2;
//        int x=0;
//        int y=a;
//        while(x<a)
//        {
//            x++;
//            y= (int) Math.sqrt(Math.abs(1-(Math.pow(x,2)/Math.pow(a,2)))*Math.pow(b,2));
//            Draw4Point(x,y,xStart,yStart);
//
//
//        }
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
