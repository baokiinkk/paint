package com.company.xuli.xuliduongve;

import java.nio.channels.Pipe;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

public class Vector2D {
    public double X;
    public double Y;

    public Vector2D() {

    }

    public Vector2D(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public Vector2D(Point2D start, Point2D end) {
        this.X = end.X - start.X;
        this.Y = end.Y - start.Y;
    }

    public Vector2D(Vector2D other) {
        this.X = other.X;
        this.Y = other.Y;
    }

    public void set(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public Vector2D unit() {
        Vector2D res = new Vector2D(0, 0);
        if (this.length() > 0) {
            res.set(this.X / this.length(), this.Y / this.length());
        }
        return res;
    }

    public double alphaOXY() {
        return atan2(this.Y, this.X);
    }

    public double alphaVector(Vector2D a) {
        return (atan2(-this.Y, this.X) + atan2(a.Y, a.X));
    }

    public Vector2D kTimesUnit(int k) {
        Vector2D res = this.unit();
        res.X *= k;
        res.Y *= k;
        return res;
    }

    public Point2D kTimesUnit(Point2D start, int k) {
        Vector2D vec = this.unit();
        Point2D res = new Point2D((int) (start.X + vec.X * k + 0.5), (int) (start.Y + vec.Y * k + 0.5));
        return res;
    }


    public double length() {
        return sqrt(X * X + Y * Y);
    }
}
