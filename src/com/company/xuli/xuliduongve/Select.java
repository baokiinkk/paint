package com.company.xuli.xuliduongve;

import javax.swing.*;
import java.awt.*;

public class Select extends HinhHoc {
    private Color[][] allPoint;

    public Select(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        allPoint = new Color[nextDrawing.length][nextDrawing[0].length];
    }

    public void SelectPoint(Point2D start, Point2D end, Color[][] selectPoint) {
        //System.out.println(start.X + " " + start.Y + "--" + end.X + " " + end.Y);
        for (int i = 0; i < selectPoint.length; i++) {
            for (int j = 0; j < selectPoint[0].length; j++) {
                if (i >= start.X && i <= end.X && j >= start.Y && j <= end.Y) {
                    listPointColor.add(selectPoint[i][j]);
                    listPointCoord.add(new Point2D(i, j));
                    allPoint[i][j] = Color.WHITE;
                } else
                    allPoint[i][j] = selectPoint[i][j];
            }
        }
//        for(int i= start.X; i<= end.X;i++)
//            for(int j= start.Y;j<=end.Y;j++){
//                if(MyFunction.isSafe(selectPoint, i, j)){
//                    listPointColor.add(selectPoint[i][j]);
//                    listPointCoord.add(new Point2D(i,j));
//                }
//            }
        //System.out.println("size: "+listPointColor.size());
    }

    private void hideSpace() {
        MyFunction.storePointColor(allPoint, nextPoint);
        for (int i = 0; i < allPoint.length; i++) {
            for (int j = 0; j < allPoint[0].length; j++) {
                nextDrawing[i][j] = true;
            }
        }
        for (int i = 0; i < listPointCoord.size(); i++) {
            Point2D tmp = new Point2D(listPointCoord.get(i));
            if (MyFunction.isSafe(nextPoint, tmp.X, tmp.Y)) {
                nextPoint[tmp.X][tmp.Y] = Color.white;
                nextDrawing[tmp.X][tmp.Y] = true;
            }
        }
    }

    public void move(Vector2D a) {
        //System.out.println("Moving");
        hideSpace();
        for (int i = 0; i < listPointCoord.size(); i++) {
            Point2D tmp = new Point2D(listPointCoord.get(i));
            tmp = tmp.moveVector(a);
            if (MyFunction.isSafe(nextPoint, tmp.X, tmp.Y)) {
                nextPoint[tmp.X][tmp.Y] = listPointColor.get(i);
                nextDrawing[tmp.X][tmp.Y] = true;
            }
        }
    }

    public void applyMove(Vector2D a) {
        //hideSpace();
        for (int i = 0; i < listPointCoord.size(); i++) {
            Point2D tmp = listPointCoord.get(i);
            tmp = tmp.moveVector(a);
            if (MyFunction.isSafe(nextPoint, tmp.X, tmp.Y)) {
                listPointCoord.get(i).set(tmp);
                nextDrawing[tmp.X][tmp.Y] = true;
            } else {
                listPointCoord.get(i).set(-1, -1);
            }
        }
        for (int i = 0; i < listPointColor.size(); i++) {
            if (listPointCoord.get(i).equal(new Point2D(-1, -1))) {
                listPointCoord.remove(i);
                listPointColor.remove(i);
            }
        }
    }
}
