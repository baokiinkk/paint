package com.company.xuli.xuliduongve;

import java.awt.*;

public class Elip extends  Line {
    public Elip(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);

    }

    public void PaintElip(int xStart, int yStart, int mX, int mY, lineMode MODE) {
        //pt elip (x^2/a^2 + y^2/b^2 = 1) => y = sqrt(1 - (x^2/a^2))* b^2)
        // tam  (xstart;ystart)
        //truc
        double a = Math.abs(xStart - mX);
        double b = Math.abs(yStart - mY);

        int x1_c = 0;
        while(a>x1_c)
        {
            //double y1 = Math.sqrt((1.0 - ((i*i*1.0)/(a*a*1.0)))* (b*b)); //IV
            x1_c++;
            double y1_c = Math.sqrt((a*a*b*b - b*b*x1_c*x1_c)/(a*a*1.0));
            double y2_c = yStart - y1_c;
            double tx_c = xStart - x1_c;

            int ty1_c = (int) (y1_c + 0.5);
            int ty2_c = (int) (y2_c + 0.5);
            int x2_c = (int) (tx_c + 0.5);
//            System.out.println(tx + " " + y1 +" "+y2);
//            if (MyFunction.isSafe(nextPoint, x, ty1) ) {
//                nextDrawing[x+xStart][ty1+yStart] = true;
//                nextPoint[x+xStart][ty1+yStart] = chooseColor;
//            } // IV
//            if (MyFunction.isSafe(nextPoint, x, ty2)&& (x%4 != 0)) {
//                nextDrawing[x+xStart][ty2] = true;
//                nextPoint[x+xStart][ty2] = chooseColor;
//            } //I
//            if (MyFunction.isSafe(nextPoint, tx1, ty2)) {
//                nextDrawing[tx1][ty1+yStart] = true;
//                nextPoint[tx1][ty1+yStart] = chooseColor;
//            }//III
//            if (MyFunction.isSafe(nextPoint, tx1, ty2)&& (x%4 != 0)) {
//                nextDrawing[tx1][ty2] = true;
//                nextPoint[tx1][ty2] = chooseColor;
//            }//II
            Quadrant(x1_c + xStart,ty1_c +yStart,x2_c,ty2_c);

        }
        int y1_r = 0;
        while(y1_r<b)
        {
            y1_r++;
            double x1_r = Math.sqrt((a*a*b*b - a*a*y1_r*y1_r)/(b*b*1.0));
            double x2_r = xStart - x1_r;
            double ty_r = yStart - y1_r;

            int tx1_r = (int) (x1_r + 0.5);
            int tx2_r = (int) (x2_r + 0.5);
            int y2_r = (int) (ty_r + 0.5);
//            if (MyFunction.isSafe(nextPoint, tx1,y)) {
//                nextDrawing[tx1+xStart][y+yStart] = true;
//                nextPoint[tx1+xStart][y+yStart] = chooseColor;
//            } // IV
//            if (MyFunction.isSafe(nextPoint, tx2,y)) {
//                nextDrawing[tx2][y+yStart] = true;
//                nextPoint[tx2][y+yStart] = chooseColor;
//            } //I
//            if (MyFunction.isSafe(nextPoint,tx2,ty1) && tx2%4 != 0) {
//                nextDrawing[tx1+xStart][ty1] = true;
//                nextPoint[tx1+xStart][ty1] = chooseColor;
//            }//III
//            if (MyFunction.isSafe(nextPoint, tx2,ty1)&& tx2%4 != 0) {
//                nextDrawing[tx2][ty1] = true;
//                nextPoint[tx2][ty1] = chooseColor;
//            }//II
            Quadrant(tx1_r+xStart,y1_r+yStart,tx2_r,y2_r);
        }
    }

    public void Quadrant(int x1,int y1,int x2, int y2)
    {
        //ve cac duong cong ung voi cac goc phan tu
        if (MyFunction.isSafe(nextPoint, x1,y1)) {
            nextDrawing[x1][y1] = true;
            nextPoint[x1][y1] = chooseColor;
        } // IV
        if (MyFunction.isSafe(nextPoint, x1,y2)&& x1%4 != 0) {
            nextDrawing[x1][y2] = true;
            nextPoint[x1][y2] = chooseColor;
        } //I
        if (MyFunction.isSafe(nextPoint,x2,y1) ) {
            nextDrawing[x2][y1] = true;
            nextPoint[x2][y1] = chooseColor;
        }//III
        if (MyFunction.isSafe(nextPoint, x2,y2)&& x2%4 != 0) {
            nextDrawing[x2][y2] = true;
            nextPoint[x2][y2] = chooseColor;
        }//II
    }
}
