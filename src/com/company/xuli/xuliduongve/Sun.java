package com.company.xuli.xuliduongve;

import java.awt.*;

public class Sun extends HinhHoc {
    private Point2D sLine1, eLine1;
    private Point2D sLine2, eLine2;
    private Line line1, line2;

    public Sun(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        this.center = new Point2D();
        this.start = new Point2D();
        sLine1 = new Point2D();
        eLine1 = new Point2D();
        sLine2 = new Point2D();
        eLine2 = new Point2D();
        line1 = new Line(nextDrawing, nextPoint, chooseColor);
        line2 = new Line(nextDrawing, nextPoint, chooseColor);
    }

    public void setSun(Point2D Start, Point2D End) {
        this.center.set(Start);
        this.start.set(End);
        this.sLine1.set(start.X, start.Y);
        this.eLine1.set(start.X - 10, start.Y);
        this.sLine2.set(sLine1.rotatePoint(center, Math.toRadians(45)));
        this.eLine2.set(new Point2D(start.X - 6, start.Y).rotatePoint(center, Math.toRadians(45)));
    }

    public void draw(int angle) {
        //System.out.println(angle);
        line1.setLine(sLine1.rotatePoint(center, Math.toRadians(angle)),
                eLine1.rotatePoint(center, Math.toRadians(angle)), center, lineMode.DEFAULT, sideMode.TETRAD);
        line1.draw();
        line2.setLine(sLine2.rotatePoint(center, Math.toRadians(angle)),
                eLine2.rotatePoint(center, Math.toRadians(angle)), center, lineMode.DEFAULT, sideMode.TETRAD);
        line2.draw();
    }
}
