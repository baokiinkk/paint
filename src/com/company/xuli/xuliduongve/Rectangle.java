package com.company.xuli.xuliduongve;

import java.awt.*;

public class Rectangle extends Line {
    public Rectangle(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);

    }
    public void PaintRectangle(int xStart, int yStart, int mX, int mY)
    {
        super.MidpointLine(xStart,yStart,mX,yStart);
        super. MidpointLine(xStart,yStart,xStart,mY);
        super. MidpointLine(mX,yStart,mX,mY);
        super. MidpointLine(mX,mY,xStart,mY);
    }

}
