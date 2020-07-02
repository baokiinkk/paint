package com.company.xuli.xuliduongve;

import javax.swing.*;
import java.awt.*;

public class Erase extends JPanel {
    Point2D A; // tọa độ của cục tẩy
    int size;
    public Erase(Point2D a,int size) {
        A = a;
        this.size=size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(A.X,A.Y,size,size);
    }
}
