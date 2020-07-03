package com.company.xuli.xuliduongve;

import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Point2D {
    public int X;
    public int Y;
    private int Width = 282;
    private int Height = 177;

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

    public Point2D moveVector(Vector2D vec) {
        Point2D res = new Point2D();
        res.set(this.X + vec.X, this.Y + vec.Y);
        return res;
    }

    // kiểm tra điểm hiện tại có bằng điểm Other hay không
    public boolean equal(Point2D other) {
        return (this.X == other.X && this.Y == other.Y);
    }

    //public Point2D
    public Point2D VerticalSymetry(Point2D other) {
        if (other.X >= Width / 2)
            other.X = Width / 2 - (other.X - Width / 2);
        else
            other.X = Width / 2 + (Width / 2 - other.X);
        return other;
    }

    public Point2D HonrizontalSymetry(Point2D other) {
        if (other.Y >= Height / 2) {
            other.Y = Height / 2 - (other.Y - Height / 2);
        } else {
            other.Y = Height / 2 + (Height / 2 - other.Y);
        }
        return other;
    }

    public Point2D PointSymetry(Point2D other, Point2D pointSym) {
        if (other.Y >= pointSym.Y) {
            other.Y = pointSym.Y - (other.Y - pointSym.Y);
        } else {
            other.Y = pointSym.Y + (pointSym.Y - other.Y);
        }

        if(other.X >= pointSym.X) {
            other.X = pointSym.X - (other.X - pointSym.X);
        }
        else {
            other.X = pointSym.X + (pointSym.X - other.X);
        }
        return other;
    }

    public ArrayList<Point2D> chooseSideMode(Point2D pointSym, Point2D pointRotate, sideMode MODE) {
        switch (MODE) {
            case DEFAULT: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                tmpPoint.add(pointRotate);
                return tmpPoint;
            }
            case DYAD: {
                int step = 0;
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                for(int i = 0;i<2;i++)
                {

                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 180;
                }
                return tmpPoint;
            }
            case TRIAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                int step = 0;
                for(int i = 0;i<3;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 120;
                }
                return tmpPoint;
            }
            case TETRAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                int step = 0;
                for(int i = 0;i<4;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 90;
                }
                return tmpPoint;
            }
            case PENTAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                int step = 0;
                for(int i = 0;i<5;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 72;
                }
                return tmpPoint;
            }
            case HEXAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                int step = 0;
                for(int i = 0;i<6;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 60;
                }
                return tmpPoint;
            }
            case HEPTAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                double step = 0;
                for(int i = 0;i<7;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= (360.0/7.0);
                }
                return tmpPoint;
            }
            case OCTAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                int step = 0;
                for(int i = 0;i<8;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 45;
                }
                return tmpPoint;
            }
            case NONAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                int step = 0;
                for(int i = 0;i<9;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 40;
                }
                return tmpPoint;
            }
            case DECAD: {
                ArrayList<Point2D> tmpPoint = new ArrayList<>();
                int step = 0;
                for(int i = 0;i<10;i++)
                {
                    tmpPoint.add(pointRotate.rotatePoint(pointSym,Math.toRadians(step)));
                    step+= 36;
                }
                return tmpPoint;
            }
        }
        return new ArrayList<>();
    }

    //public Point2D
}