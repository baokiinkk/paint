package com.company.xuli.xuliduongve;

import java.awt.*;

import static java.lang.Math.sqrt;

public class Line extends HinhHoc {

    public Line(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }

    public void MidpointLine(int x1, int y1, int x2, int y2) {
        // tính khoảng cách 2 điểm
        float x = x1, y = y1, temp = (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) ? Math.abs(x2 - x1) : Math.abs(y2 - y1);

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
            x += (x2 - x1) / temp;
            y += (y2 - y1) / temp;

            // lm tròn lên điểm đó
            tx = (int) (x + 0.5);
            ty = (int) (y + 0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty)) {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
    }

    public void gach(int x1, int y1, int x2, int y2) {
        // tính khoảng cách 2 điểm
        float x = x1, y = y1, temp = (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) ? Math.abs(x2 - x1) : Math.abs(y2 - y1);

        // lm tron len điểm đó
        int tx = (int) (x + 0.5);
        int ty = (int) (y + 0.5);

        for (int i = 1; i < temp; i++) {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2 - x1) / temp;
            y += (y2 - y1) / temp;

            // lm tròn lên điểm đó
            tx = (int) (x + 0.5);
            ty = (int) (y + 0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty) && (i % 5 != 0)) {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
    }

    public void chamGach(int x1, int y1, int x2, int y2) {
        // tính khoảng cách 2 điểm
        float x = x1, y = y1, temp = (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) ? Math.abs(x2 - x1) : Math.abs(y2 - y1);

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
        for (int i = 1; i < temp; i++) {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2 - x1) / temp;
            y += (y2 - y1) / temp;

            // lm tròn lên điểm đó
            tx = (int) (x + 0.5);
            ty = (int) (y + 0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty) && (i % 6 == 1 || i % 6 == 2 || i % 6 == 3 || i % 6 == 5)) {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
    }

    public void haichamGach(int x1, int y1, int x2, int y2) {
        // tính khoảng cách 2 điểm
        float x = x1, y = y1, temp = (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) ? Math.abs(x2 - x1) : Math.abs(y2 - y1);

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
        for (int i = 1; i < temp; i++) {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2 - x1) / temp;
            y += (y2 - y1) / temp;

            // lm tròn lên điểm đó
            tx = (int) (x + 0.5);
            ty = (int) (y + 0.5);

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty) && (i % 9 == 1 || i % 9 == 2 || i % 9 == 3 || i % 9 == 4 || i % 9 == 4 || i % 9 == 6 || i % 9 == 8)) {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
    }

    public void muiTen(int x1, int y1, int x2, int y2) {

        float x = x1, y = y1, temp = (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) ? Math.abs(x2 - x1) : Math.abs(y2 - y1);

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
        int x3=0,y3=0;
        for (int i = 0; i < temp; i++) {
            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
            x += (x2 - x1) / temp;
            y += (y2 - y1) / temp;

            // lm tròn lên điểm đó
            tx = (int) (x + 0.5);
            ty = (int) (y + 0.5);
            if(i==temp-3)
            {
                x3=tx;
                y3=ty;
            }

            // lưu vị trí điểm và màu của điểm đó vào danh sách
            if (MyFunction.isSafe(nextPoint, tx, ty)) {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
        }
        double l = 3;
        double delta = (-2 * x2 + (-1.0 / (y1 - y2))) * (-2 * x2 + (-1.0 / (y1 - y2))) - 4 * (x2 * x2 + y3 - (-1.0 / (y1 - y2)) * x3 - l * l);
        double b = (-2 * x2 + (-1.0 / (y1 - y2)));
        double xxx = (-b - sqrt(delta)) / 2;
        double xx = (-b + sqrt(delta)) / 2;
        double yyy = (-1.0 / (y1 - y2)) * xxx + y3 - (-1.0 / (y1 - y2) * x3);
        double yy = (-1.0 / (y1 - y2)) * xx + y3 - (-1.0 / (y1 - y2) * x3);

//
//    MidpointLine(x1, y1, x2, y2);

    MidpointLine(x2, y2, (int)xxx, (int)yyy);

    MidpointLine(x2, y2, (int)xx, (int)yy);
    }
}
