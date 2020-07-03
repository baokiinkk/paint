package com.company.xuli.xuliduongve;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DriveCarAnim extends HinhHoc{

    public DriveCarAnim(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        this.chooseColor = Color.BLACK;
        this.mode = lineMode.DEFAULT;
        MidpointLine(new Point2D(0, nextPoint[0].length / 3)
                , new Point2D(nextPoint.length, nextPoint[0].length / 3), lineMode.DEFAULT);
        MidpointLine(new Point2D(nextPoint.length / 3, nextPoint[0].length / 3),
                new Point2D(nextPoint.length / 8, nextPoint[0].length), lineMode.DEFAULT);
        MidpointLine(new Point2D(nextPoint.length * 2 / 3, nextPoint[0].length / 3),
                new Point2D(nextPoint.length * 7 / 8, nextPoint[0].length), lineMode.DEFAULT);

        Ellipse may1 = new Ellipse(nextDrawing, nextPoint, Color.WHITE);
        Ellipse may2 = new Ellipse(nextDrawing, nextPoint, Color.WHITE);
        Ellipse may3 = new Ellipse(nextDrawing, nextPoint, Color.WHITE);
        Ellipse sun = new Ellipse(nextDrawing, nextPoint, Color.RED);

        may1.setElip(new Point2D(6, 10), new Point2D(19, 15), lineMode.DEFAULT);
        may2.setElip(new Point2D(68, 5), new Point2D(84, 8), lineMode.DEFAULT);
        may3.setElip(new Point2D(179, 3), new Point2D(197, 6), lineMode.DEFAULT);
        sun.setCircle(new Point2D(268, 5), new Point2D(280, 10), lineMode.DEFAULT);

        Car chon = new Car(nextDrawing, nextPoint, chooseColor);
        chon.create(new Point2D(183, 137), new Point2D(-1, -1), lineMode.DEFAULT);

        drawZigZag(new ArrayList<Point2D>(Arrays.asList(new Point2D(0, 59), new Point2D(72, 14), new Point2D(173, 59))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(new Point2D(103, 28), new Point2D(144, 1), new Point2D(185, 20))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(new Point2D(138, 44), new Point2D(208, 8), new Point2D(281, 43))));
        // màu đất
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(0, nextPoint[0].length / 3 + 1), new Color(37, 156, 101, 200));
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(nextPoint.length * 2 / 3 + 5, nextPoint[0].length / 3 + 5), new Color(37, 156, 101, 199));
        //  màu núi
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(73, 17), new Color(234, 24, 67));
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(145, 5), new Color(180, 1, 16));
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(209, 13), new Color(234, 42, 97));
        // màu trời
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(104, 26), new Color(35, 210, 241));

        // màu đường lộ
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(96, 63), new Color(158, 156, 156, 255));

        // mau may sad
        may1.draw(Color.WHITE);
        may2.draw(Color.WHITE);
        may3.draw(Color.WHITE);
        sun.draw(Color.YELLOW);

        chon.draw();
    }
}
