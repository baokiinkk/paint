package com.company.xuli.xuliduongve;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tree extends HinhHoc {
    Rectangle thanCay;
    ArrayList<Ellipse> tanCay;

    public Tree(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor,Point2D start,Point2D end) {
        super(nextDrawing, nextPoint, chooseColor);
        this.start = start;
        this.end = end;
        this.center.set((start.X + end.X) / 2, (start.Y + end.Y) / 2);
        thanCay =  new Rectangle(nextDrawing,nextPoint,chooseColor);
        tanCay = new ArrayList<>();
        Point2D startThanCay =  new Point2D(center.X-(end.X-start.X)/8,center.Y);
        Point2D endThanCay =  new Point2D(center.X+(end.X-start.X)/8,end.Y);
        thanCay.setRectangle(startThanCay,endThanCay,lineMode.DEFAULT);
        for(int i=0;i<1;i++){
            tanCay.add(new Ellipse(nextDrawing,nextPoint,chooseColor));
        }
        tanCay.get(0).setCircle(new Point2D(center.X,start.Y+(center.Y-start.Y)/2),
                new Point2D(center.X,center.Y),lineMode.DEFAULT);
//        Random rd = new Random();
//        for(int i=1;i<tanCay.size();i++){
//            Point2D tmpStart = new Point2D(start.X+rd.nextInt(end.X-start.X),start.Y+rd.nextInt(center.Y-start.Y));
//
//            Point2D tmpEnd = new Point2D(tmpStart.X,tmpStart.Y+ (endThanCay.X-startThanCay.X) +rd.nextInt((center.X-start.X)/2));
//            tanCay.get(i).setCircle(tmpStart,tmpEnd,lineMode.DEFAULT);
//        }
    }

    public void draw(){
        if(!start.equal(end))
        {
            for(int i=0;i<tanCay.size();i++){
                tanCay.get(i).draw(new Color(0,150 + new Random().nextInt(100), 8));

            }
            thanCay.draw(new Color(177, 119, 46));
        }
    }
}
