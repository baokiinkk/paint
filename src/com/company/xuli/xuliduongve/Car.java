package com.company.xuli.xuliduongve;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Car extends HinhHoc {
    ArrayList<Point2D> diem, diem2, diemColor;
    ArrayList<Color> colorCar;
    Ellipse elp = new Ellipse(nextDrawing, nextPoint, chooseColor);

    public Car(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }

    public void create(Point2D start, Point2D end, lineMode mode) {
        this.mode = mode;
        this.start = start;
        this.end = end;
        diem = new ArrayList<>();
        diem2 = new ArrayList<>();
        diemColor = new ArrayList<>();
        colorCar = new ArrayList<>();
        diem.add(new Point2D(-1, -1));
        diem.add(new Point2D(start.X - 22, start.Y - 26));//1
        diem.add(new Point2D(start.X + 0, start.Y - 26));//2
        diem.add(new Point2D(start.X + 0, start.Y - 7));//3
        diem.add(new Point2D(start.X - 30, start.Y - 7));//4
        diem.add(new Point2D(start.X - 18, start.Y - 24));//5
        diem.add(new Point2D(start.X + 0, start.Y - 24));//6
        diem.add(new Point2D(start.X + 0, start.Y - 12));//7
        diem.add(new Point2D(start.X - 24, start.Y - 12));//8
        diem.add(new Point2D(start.X - 33, start.Y - 7));//9
        diem.add(new Point2D(start.X - 36, start.Y + 9));//10
        diem.add(new Point2D(start.X + 0, start.Y + 9));//11
        diem.add(new Point2D(start.X + 0, start.Y + 5));//12
        diem.add(new Point2D(start.X + 0, start.Y - 0));//13
        diem.add(new Point2D(start.X - 14, start.Y - 0));//14
        diem.add(new Point2D(start.X - 14, start.Y + 5));//15
        diem.add(new Point2D(start.X - 27, start.Y + 9));//16
        diem.add(new Point2D(start.X - 27, start.Y + 14));//17
        diem.add(new Point2D(start.X + 0, start.Y + 14));//18
        diem.add(new Point2D(start.X - 27, start.Y + 24));//19
        diem.add(new Point2D(start.X - 36, start.Y + 24));//20
        diem.add(new Point2D(start.X - 31, start.Y - 5));//21
        diem.add(new Point2D(start.X - 21, start.Y - 5));//22
        diem.add(new Point2D(start.X - 21, start.Y - 1));//23
        diem.add(new Point2D(start.X - 27, start.Y + 3));//24
        diem.add(new Point2D(start.X - 32, start.Y + 3));//25
        diem.add(new Point2D(start.X - 26, start.Y - 1));//26
        diem.add(new Point2D(start.X - 29, start.Y - 11));//27
        diem.add(new Point2D(start.X - 34, start.Y - 14));//28
        diem.add(new Point2D(start.X - 36, start.Y - 10));//29
        for (int i = 0; i < diem.size(); i++) {
            diem2.add(new Point2D(2 * start.X - (diem.get(i).X), diem.get(i).Y));
        }
        diemColor.add(new Point2D(0, -6));
        diemColor.add(new Point2D(0, -8));
        diemColor.add(new Point2D(0, -13));
        diemColor.add(new Point2D(-2, +13));
        diemColor.add(new Point2D(4, 3));
        diemColor.add(new Point2D(-29, 0));
        diemColor.add(new Point2D(-32, -10));
        diemColor.add(new Point2D(-32, +15));

        colorCar.add(new Color(227, 218, 0));
        colorCar.add(new Color(227, 218, 0));
        colorCar.add(new Color(166, 180, 203));
        colorCar.add(new Color(182, 175, 0));
        colorCar.add(new Color(247, 247, 247));
        colorCar.add(new Color(255, 5, 0));
        colorCar.add(new Color(0, 0, 0));
        colorCar.add(new Color(0, 0, 0));
    }

    public void draw() {
        //System.out.println("88888: " + colorCar.size());
        drawList(diem);
        drawList(diem2);
        for (int i = 0; i < diemColor.size(); i++) {
            MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(start.X + diemColor.get(i).X, start.Y + diemColor.get(i).Y), colorCar.get(i));
            MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(start.X - diemColor.get(i).X, start.Y + diemColor.get(i).Y), colorCar.get(i));
        }

    }

    public void drawList(ArrayList<Point2D> listDiem) {
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(2), listDiem.get(1), listDiem.get(4), listDiem.get(3))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(6), listDiem.get(5), listDiem.get(8), listDiem.get(7))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(27), listDiem.get(28), listDiem.get(29), listDiem.get(9))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(3), listDiem.get(9), listDiem.get(10), listDiem.get(11))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(13), listDiem.get(14), listDiem.get(15), listDiem.get(12))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(21), listDiem.get(22)
                , listDiem.get(23), listDiem.get(26), listDiem.get(24), listDiem.get(25), listDiem.get(21))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(11), listDiem.get(16)
                , listDiem.get(17), listDiem.get(18))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(10), listDiem.get(16)
                , listDiem.get(19), listDiem.get(20), listDiem.get(10))));

//        drawingCircle(listDiem.get(21), new Point2D(listDiem.get(21).X+2,listDiem.get(21).Y),mode);
//        elp.setCircle(listDiem.get(21), new Point2D(listDiem.get(21).X+2,listDiem.get(21).Y),mode);
//        elp.draw();
    }
}