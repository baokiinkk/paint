package com.company.xuli.xuliduongve;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Point2D {
    public int X;
    public int Y;

    public Point2D() {
    }

    public Point2D(int x, int y) {
        super();
        this.X = x;
        this.Y = y;
    }

    public Point2D(Point2D other) {
        this.X = other.X;
        this.Y = other.Y;
    }

    public void set(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void set(double X, double Y) {
        this.X = (int) X;
        this.Y = (int) Y;
    }

    public void set(Point2D other) {
        this.X = other.X;
        this.Y = other.Y;
    }

//    public Point2D rotate(Point2D center, double alpha) {
//        Point2D res = new Point2D();
//        res.set((center.X + this.X * cos(alpha) - this.Y * sin(alpha) + 0.5), (center.Y + this.X * sin(alpha) + this.Y * cos(alpha) + 0.5));
//        return res;
//    }

    // xoay điểm hiện tại quanh tâm center với 1 góc alpha
    public Point2D rotatePoint(Point2D center, double alpha) {
        Point2D res = new Point2D();
        res.set((center.X + (this.X - center.X) * cos(alpha) - (this.Y - center.Y) * sin(alpha) + 0.5), (center.Y + (this.X - center.X) * sin(alpha) + (this.Y - center.Y) * cos(alpha) + 0.5));
        return res;
    }

    //public Point2D
}
