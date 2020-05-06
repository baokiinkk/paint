package com.company.xuli.xuliduongve;

import java.awt.*;

public class Rectangle extends Line {
    public Rectangle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);

    }
    public void PaintRectangle(int xStart, int yStart, int mX, int mY)
    {
        super.MidpointLine(xStart, yStart, mX, yStart, false);
        super.MidpointLine(xStart, yStart, xStart, mY, false);
        super.MidpointLine(mX, yStart, mX, mY, false);
        super.MidpointLine(mX, mY, xStart, mY, false);
    }

}
