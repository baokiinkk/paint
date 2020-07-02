package com.company.xuli.xuliduongve;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Animal extends HinhHoc {
    ArrayList<Point2D> diem, diem2, diemColor;
    ArrayList<Color> colorAnimal;
    Ellipse elp = new Ellipse(nextDrawing, nextPoint, chooseColor);
    public Animal(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);

    }
    public void create(Point2D start,Point2D end, lineMode mode){
        this.mode = mode;
        this.start = start;
        this.end = end;
        diem =  new ArrayList<>();
        diem2 =  new ArrayList<>();
        diemColor = new ArrayList<>();
        colorAnimal = new ArrayList<>();
        diem.add(new Point2D(-1,-1));
        diem.add(new Point2D(start.X,start.Y-27));
        diem.add(new Point2D(start.X+11,start.Y-26));
        diem.add(new Point2D(start.X+28,start.Y-35));
        diem.add(new Point2D(start.X+36,start.Y-29));
        diem.add(new Point2D(start.X+20,start.Y-17));
        diem.add(new Point2D(start.X+33,start.Y-4));
        diem.add(new Point2D(start.X,start.Y));
        diem.add(new Point2D(start.X+5,start.Y+2));
        diem.add(new Point2D(start.X+17,start.Y+7));
        diem.add(new Point2D(start.X+25,start.Y+10));
        diem.add(new Point2D(start.X+46,start.Y+12));
        diem.add(new Point2D(start.X+12,start.Y+11));
        diem.add(new Point2D(start.X,start.Y+15));
        diem.add(new Point2D(start.X+17,start.Y+21));
        diem.add(new Point2D(start.X,start.Y+26));
        diem.add(new Point2D(start.X+7,start.Y+32));
        diem.add(new Point2D(start.X+18,start.Y+36));
        diem.add(new Point2D(start.X,start.Y+38));
        diem.add(new Point2D(start.X,start.Y+49));
        diem.add(new Point2D(start.X+6,start.Y+49));
        diem.add(new Point2D(start.X+13,start.Y+9));
        for (int i = 0; i < diem.size(); i++)
        {
            diem2.add(new Point2D(2*start.X - (diem.get(i).X), diem.get(i).Y));
        }

        diemColor.add(new Point2D(28,-31));
        diemColor.add(new Point2D(28,-22));
        diemColor.add(new Point2D(13,-17));
        diemColor.add(new Point2D(22,-7));
        diemColor.add(new Point2D(31,16));
        diemColor.add(new Point2D(20,11));
        diemColor.add(new Point2D(14,9));
        diemColor.add(new Point2D(6,15));
        diemColor.add(new Point2D(3,24));
        diemColor.add(new Point2D(4,31));

        colorAnimal.add(new Color(156, 157, 159));  //
        colorAnimal.add(new Color(65, 65, 67));
        colorAnimal.add(new Color(230, 231, 233));
        colorAnimal.add(new Color(148, 149, 153));
        colorAnimal.add(new Color(65, 65, 67));
        colorAnimal.add(new Color(37, 37, 39));
        colorAnimal.add(Color.RED);
        colorAnimal.add(new Color(88, 89, 91));
        colorAnimal.add(new Color(189, 189, 191));
        colorAnimal.add(Color.BLACK);
    }
    public void draw()
    {
        System.out.println("88888: " + colorAnimal.size());
        drawList(diem);
        drawList(diem2);
        for (int i = 0; i < diemColor.size(); i++)
        {
            MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(start.X + diemColor.get(i).X, start.Y + diemColor.get(i).Y), colorAnimal.get(i));
            MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(start.X - diemColor.get(i).X, start.Y + diemColor.get(i).Y), colorAnimal.get(i));
        }

    }
    public void drawList(ArrayList<Point2D> listDiem)
    {
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(2),listDiem.get(3),listDiem.get(4), listDiem.get(2))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(15),listDiem.get(16),listDiem.get(18))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(1),listDiem.get(2),listDiem.get(5),listDiem.get(7))));
        //drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(8),listDiem.get(9),listDiem.get(12), listDiem.get(8))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(7),listDiem.get(10),listDiem.get(14), listDiem.get(7))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(7),listDiem.get(11),listDiem.get(17), listDiem.get(7))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(2),listDiem.get(5)
                ,listDiem.get(6),listDiem.get(4))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(7),listDiem.get(5)
                ,listDiem.get(6),listDiem.get(11))));
        drawZigZag(new ArrayList<Point2D>(Arrays.asList(listDiem.get(13),listDiem.get(17)
                ,listDiem.get(20),listDiem.get(19))));

        drawingCircle(listDiem.get(21), new Point2D(listDiem.get(21).X+2,listDiem.get(21).Y),mode);
//        elp.setCircle(listDiem.get(21), new Point2D(listDiem.get(21).X+2,listDiem.get(21).Y),mode);
//        elp.draw();
    }
}