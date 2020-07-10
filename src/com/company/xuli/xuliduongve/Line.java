package com.company.xuli.xuliduongve;

import java.awt.*;
import java.util.ArrayList;

import com.company.xuli.xuliduongve.Point2D;


public class Line extends HinhHoc {
    private ArrayList<Point2D> BunchOfStart;
    private ArrayList<Point2D> BunchOfEnd;
    private Point2D tmpStart;
    private Point2D tmpEnd;

    public Line(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tmpStart = new Point2D();
        tmpEnd = new Point2D();
        BunchOfStart = new ArrayList<Point2D>();
        BunchOfEnd = new ArrayList<Point2D>();
    }

    @Override
    public void saveCoord(String[][] coord) {
        //System.out.println(BunchOfStart.size());
        for (int i = 0; i < BunchOfStart.size(); i++) {
            BunchOfStart.get(i).saveCoord(coord);
            BunchOfEnd.get(i).saveCoord(coord);
        }

    }

    public void draw(Point2D start, Point2D end, Point2D centerPoint, lineMode MODE, sideMode SIDEMODE) {

        BunchOfStart = tmpStart.chooseSideMode(centerPoint, start, SIDEMODE);
        BunchOfEnd = tmpEnd.chooseSideMode(centerPoint, end, SIDEMODE);
        for (int i = 0; i < BunchOfStart.size(); i++) {
            super.MidpointLine(BunchOfStart.get(i), BunchOfEnd.get(i), MODE);
        }

    }
}