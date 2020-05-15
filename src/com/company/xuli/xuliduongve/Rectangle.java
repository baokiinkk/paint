package com.company.xuli.xuliduongve;

import java.awt.*;

public class Rectangle extends Line {
    public Rectangle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
    }
    public void PaintRectangle(int xStart, int yStart, int mX, int mY, lineMode MODE) {
        super.MidpointLine(xStart, yStart, mX, yStart, MODE);
        super.MidpointLine(xStart, mY, xStart, yStart, MODE);
        super.MidpointLine(mX, yStart, mX, mY, MODE);
        super.MidpointLine(mX, mY, xStart, mY, MODE);
    }
}
