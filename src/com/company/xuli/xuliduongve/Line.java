package com.company.xuli.xuliduongve;

import java.awt.*;
import java.util.ArrayList;

import com.company.xuli.xuliduongve.Point2D;


public class Line extends HinhHoc {

    public Line(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }

    public void draw(Point2D start, Point2D end, Point2D centerPoint, sideMode SIDEMODE)
    {
        Point2D tmpStart = new Point2D();
        Point2D tmpEnd = new Point2D();
        ArrayList<Point2D> BunchOfStart = tmpStart.chooseSideMode(centerPoint, start, SIDEMODE);
        ArrayList<Point2D> BunchOfEnd = tmpEnd.chooseSideMode(centerPoint, end, SIDEMODE);

        for(int i = 0; i< BunchOfStart.size();i++) {
            super.MidpointLine(BunchOfStart.get(i), BunchOfEnd.get(i), lineMode.DEFAULT);
        }

    }
}