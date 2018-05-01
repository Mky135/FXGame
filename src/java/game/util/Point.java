package game.util;


public class Point extends java.awt.Point
{
    private double x;
    private double y;

    Point()
    {
        this.x = 0;
        this.y = 0;
    }

    Point(double x, double y)
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

    public void setPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }


    double getAngle(double x1, double y1, double x2, double y2)
    {
        double deltaX = x1 - x2;
        double deltaY = y1 - y2;
        double angle;
        if(deltaX > 0)
        {
            angle = Math.toDegrees(Math.atan(deltaY/deltaX));
        }
        else
        {
            angle = 180 + Math.toDegrees(Math.atan(deltaY/deltaX));
        }

        return angle;
    }

    @Override
    public String toString()
    {
        return "Point[x=" + (int)x + ",y=" + (int)y + "]";
    }

}
