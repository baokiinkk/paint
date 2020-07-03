package com.company.Animation2D;

import com.company.xuli.xuliduongve.*;

import java.awt.*;
import java.util.Random;

public class Firework extends HinhHoc {
    private int height;
    private int radius;
    private int position;
    private int currentState;
    private int endState;
    private Color fireColor;
    private lineMode MODE;
    private float sizeFW;

    public Firework(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }

    public void initFirework(int height, int radius, int pos) {
        this.height = height;
        this.radius = radius;
        this.endState = height + radius;
        position = pos;
        //System.out.println(position);
        currentState = 0;

    }

    public void initFirework(int height, int radius, lineMode MODE, float sz) {
        this.height = height;
        this.radius = radius;
        this.endState = height + radius;
        this.sizeFW = sz;
        position = (new Random().nextInt(nextDrawing.length));
        //System.out.println(position);
        this.MODE = MODE;
        currentState = 0;
    }

    public void drawingState() {
        //System.out.print(currentState + ", ");
        if (currentState <= height) {
            //System.out.println(currentState);
//            MuiTen mt = new MuiTen(nextDrawing,nextPoint,chooseColor);
//            mt.setMuiTen(new Point2D(position,nextPoint[0].length - currentState - 1), sizeFW);
//            mt.draw();
            super.nextPoint[position][nextPoint[0].length - currentState - 1] = chooseColor;
            super.nextDrawing[position][nextPoint[0].length - currentState - 1] = true;
//            Point2D start = new Point2D(position, height);
//            Point2D end = new Point2D(position, nextPoint.length-currentState-1);
//            super.MidpointLine(start, end, lineMode.DEFAULT);
        } else if (currentState > height && currentState <= endState) {
            //System.out.println(currentState + "----" + height);
            Point2D start = new Point2D(position, nextPoint[0].length - height);
            for (int r = 0; r < currentState-height; r++)
            {
                Point2D tend = new Point2D(position + r, nextPoint[0].length - height);
                new Circle(nextDrawing, nextPoint, this.chooseColor).drawingCircle(start, tend, MODE);
            }
            //super.drawingCircle(start, end, lineMode.DEFAULT);
        }
    }

    public boolean isEnd() {
        //System.out.println(currentState);
        return (currentState == endState);
    }

    public void nextState() {
        // đổ màu nhạt dần khi nổ
        boolean flag = true;
        if (flag)
        {
            if (currentState > height)
            {
                int red = chooseColor.getRed();
                int green = chooseColor.getGreen();
                int blue = chooseColor.getBlue();
//                red = red + (255 - red) / (endState - currentState);
//                green = green + (255 - green) / (endState - currentState);
//                blue = blue + (255 - blue) / (endState - currentState);
                chooseColor = new Color(red, green, blue);
            }
        }
        else
        {
            if (currentState > height) {
                int red = chooseColor.getRed();
                int green = chooseColor.getGreen();
                int blue = chooseColor.getBlue();
                red = red - (red) / (endState - currentState);
                green = green - (green) / (endState - currentState);
                blue = blue - (blue) / (endState - currentState);
                chooseColor = new Color(red, green, blue);
            }
        }


        currentState++;
    }

    public int getRadius() {
        return radius;
    }
}
