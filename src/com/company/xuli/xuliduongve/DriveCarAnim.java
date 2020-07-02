package com.company.xuli.xuliduongve;

import java.awt.*;

public class DriveCarAnim extends HinhHoc{

    public DriveCarAnim(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        new Line(nextDrawing,nextPoint,Color.BLACK).MidpointLine(new Point2D(0,nextPoint[0].length/3)
                ,new Point2D(nextPoint.length,nextPoint[0].length/3),lineMode.DEFAULT);
        new Line(nextDrawing,nextPoint,Color.BLACK).MidpointLine(new Point2D(nextPoint.length/3,nextPoint[0].length/3),
                new Point2D(nextPoint.length/8,nextPoint[0].length), lineMode.DEFAULT);
        new Line(nextDrawing,nextPoint,Color.BLACK).MidpointLine(new Point2D(nextPoint.length*2/3,nextPoint[0].length/3),
                new Point2D(nextPoint.length*7/8,nextPoint[0].length), lineMode.DEFAULT);
        MyFunction.paintColor(nextPoint,nextDrawing,new Point2D(0,nextPoint[0].length/3+1),new Color(14, 153, 88));
        MyFunction.paintColor(nextPoint,nextDrawing,new Point2D(nextPoint.length*2/3+5,nextPoint[0].length/3+5),new Color(14, 153, 88));
    }
}
