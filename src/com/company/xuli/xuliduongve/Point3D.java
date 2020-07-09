package com.company.xuli.xuliduongve;

import com.company.main.Paint;

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
        int tmpX = (int) (X - Math.round(Y * Math.cos(Math.toRadians(45))));
        int tmpY = (int) (Z - Math.round(Y * Math.sin(Math.toRadians(45))));
        res.set(tmpX + this.Width / 2, -tmpY + this.Height / 2);
        //System.out.println(X +"/" + Y +"->" + res.X +"/"+res.Y);
        return res;
    }

    public void set(int x, int y, int z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    @Override
    public void saveCoord(String[][] coord) {
        //super.saveCoord(coord);
        String pos = "";
        pos = "(" + X + ", " + Y + "," + Z + ")";

        coord[this.to2D().X][this.to2D().Y] = pos;
    }
}
