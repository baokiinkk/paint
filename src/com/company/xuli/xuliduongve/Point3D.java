package com.company.xuli.xuliduongve;

public class Point3D extends Point2D {
    public int Z;

    public Point3D() {

    }

    public Point3D(int x, int y, int z) {
        super();
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    public void set(Point3D other) {
        this.X = other.X;
        this.Y = other.Y;
        this.Z = other.Z;
    }

    public Point2D to2D() {
        Point2D res = new Point2D();
        int tmpX = (int) (X - Math.round(Z * Math.cos(Math.toRadians(45))));
        int tmpY = (int) (Y - Math.round(Z * Math.sin(Math.toRadians(45))));
        res.set(tmpX + this.Width / 2, -tmpY + this.Height / 2);
        //System.out.println(X +"/" + Y +"->" + res.X +"/"+res.Y);
        return res;
    }

    public void set(int x, int y, int z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
}
