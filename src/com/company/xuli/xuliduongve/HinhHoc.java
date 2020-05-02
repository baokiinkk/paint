package com.company.xuli.xuliduongve;

import java.awt.*;

public class HinhHoc {
    public boolean[][] nextDrawing;
    public Color[][] nextPoint;
    public Color chooseColor;
    public HinhHoc(boolean[][] nextDrawing,Color[][] nextPoint,Color chooseColor)
    {
        this.chooseColor=chooseColor;
        this.nextDrawing=nextDrawing;
        this.nextPoint=nextPoint;
    }
}
