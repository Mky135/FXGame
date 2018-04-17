package game.util;

import game.Main;

public class Point extends java.awt.Point
{
    private double x = 0;
    private double y = 0;

    Point()
    {
        this.x = 0;
        this.y = 0;
    }

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX()
    {
        return x;
    }

    @Override
    public double getY()
    {
        return y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    double getAngle(double x1, double y1, double x2, double y2)
    {
        double angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
        System.out.println(angle);

        return angle;
    }

    Point getPoint(double x1, double y1, double x2, double y2)
    {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        return new Point(deltaX, deltaY);
    }

    @Override
    public String toString()
    {
        return "Point[x=" + (int)x + ",y=" + (int)y + "]";
    }

}
