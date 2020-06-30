package com.company.xuli.xuliduongve;

import javax.swing.*;
import java.awt.*;

public class Select extends JPanel {
    Point2D start,end;
    public Select(Point2D start,Point2D end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(start.X,start.Y,Math.abs(end.X-start.X),Math.abs(end.Y-start.Y));
    }
}
