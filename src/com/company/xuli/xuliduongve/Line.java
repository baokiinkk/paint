package com.company.xuli.xuliduongve;

import java.awt.*;


public class Line extends HinhHoc {

    public Line(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }

//    public void MidpointLine(Point2D start, Point2D end, lineMode Mode) {
//        // tính khoảng cách 2 điểm
//        float x = start.X, y = start.Y, temp = (Math.abs(end.X - start.X) >= Math.abs(end.Y - start.Y)) ? Math.abs(end.X - start.X) : Math.abs(end.Y - start.Y);
//
//        // lm tron len điểm đó
//        int tx = (int) (x + 0.5);
//        int ty = (int) (y + 0.5);
//
//        // lưu vị trí điểm và màu của điểm đó vào danh sách
//
//        if (MyFunction.isSafe(nextPoint, tx, ty)) {
//            nextDrawing[tx][ty] = true;
//            nextPoint[tx][ty] = chooseColor;
//        }
//
//
//        // duyệt khoảng cách 2 điểm.
//        //System.out.println("run");
//        for (int i = 0; i < temp; i++) {
//            // điểm kế tiếp nằm trên đường thẳng = điểm hiện tại + khoảng cách tọa độ x hoặc y / khoảng cách điểm
//            x += (end.X - start.X) / temp;
//            y += (end.Y - start.Y) / temp;
//
//            // lm tròn lên điểm đó
//            tx = (int) (x + 0.5);
//            ty = (int) (y + 0.5);
//
//            // lưu vị trí điểm và màu của điểm đó vào danh sách
//            if (MyFunction.isSafe(nextPoint, tx, ty) && MyFunction.chooseMode(i, Mode)) {
//                nextDrawing[tx][ty] = true;
//                nextPoint[tx][ty] = chooseColor;
//            }
//            if (Mode == lineMode.ARROW) {
//                Vector2D v = new Vector2D(end, start);
//                if (v.length() != 0) {
//                    int l = 5;
//                    Point2D c = v.kTimesUnit(end, l);
//                    double alpha = v.alphaOXY();
//                    //System.out.println("alpha: " + alpha);
//                    Point2D c1 = new Point2D(0, l);
//                    Point2D c2 = new Point2D(0, -l);
//                    MidpointLine(end, c1.rotate(c, alpha), lineMode.DEFAULT);
//                    MidpointLine(end, c2.rotate(c, alpha), lineMode.DEFAULT);
//                }
//            }
//        }
//    }

}
