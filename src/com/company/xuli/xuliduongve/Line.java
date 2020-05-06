package com.company.xuli.xuliduongve;

import java.awt.*;

import com.company.xuli.xuliduongve.lineMode;
import com.company.xuli.xuliduongve.MyFunction.*;

import static java.lang.Math.*;


public class Line extends HinhHoc {

    public Line(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }

    public void MidpointLine(int x1, int y1, int x2, int y2, lineMode Mode) {
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
            if (MyFunction.isSafe(nextPoint, tx, ty) && MyFunction.chooseMode(i, Mode)) {
                nextDrawing[tx][ty] = true;
                nextPoint[tx][ty] = chooseColor;
            }
            if (Mode == lineMode.ARROW) {
                double vx = x1 - x2;
                double vy = y1 - y2;
                if (sqrt(vx * vx + vy * vy) != 0 && sqrt(vx * vx + vy * vy) != 0) {
                    double l = 5;
                    double ux = (vx / (sqrt(vx * vx + vy * vy)));
                    double uy = (vy / (sqrt(vx * vx + vy * vy)));
                    double cx = x2 + l * ux;
                    double cy = y2 + l * uy;
                    double alpha = atan2((y2 - y1), (x2 - x1));
                    // System.out.println("alpha: " + alpha);
                    double cx1 = 0;
                    double cy1 = l;
                    double cx2 = 0;
                    double cy2 = -l;

//cx1 = cx1*cos(alpha) - cy1*sin(alpha);
//cy1 = cx1*sin(alpha) - cy1*cos(alpha);

//cx2 = cx2*cos(alpha) - cy2*sin(alpha);
//cy2 = cx2*sin(alpha) - cy2*cos(alpha);


                    MidpointLine(x2, y2, (int) (cx + cx1 * cos(alpha) - cy1 * sin(alpha) + 0.5), (int) (cy + cx1 * sin(alpha) + cy1 * cos(alpha) + 0.5), lineMode.DEFAULT);
                    MidpointLine(x2, y2, (int) (cx + cx2 * cos(alpha) - cy2 * sin(alpha) + 0.5), (int) (cy + cx2 * sin(alpha) + cy2 * cos(alpha)), lineMode.DEFAULT);
                }
            }
        }
    }

}
