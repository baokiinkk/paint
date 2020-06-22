package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;
public class Globe extends HinhHoc{
    private Point2D A;
    private lineMode MODE;
    public Globe(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        //tag = Button.CUBE;
        A = new Point2D();
    }
    private void setGlobe(Point2D start, Point2D end) {
        A = new Point2D();
    }
}
