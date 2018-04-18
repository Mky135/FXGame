package game.util;

import game.Main;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CircleHandler extends Circle
{
    public  Circle circle;
    private DropShadow dropShadow = new DropShadow();
    public Point light = new Point(0, 1);

    public CircleHandler()
    {
        circle = createCircle();
        updateDropShadow();
    }

    private Circle createCircle()
    {
        final Circle circle = new Circle(150.0F, 200.0F, Main.settingsHandler.getNumberFromLine(3, "Circle Size: "), Main.settingsHandler.getCircleColor());
        circle.setStroke(Main.settingsHandler.getCircleColor());
        circle.setStrokeType(StrokeType.INSIDE);

        circle.setEffect(dropShadow);
        return circle;
    }

    public void updateDropShadow()
    {
        Point offset = getOffsetFromLighting();

        dropShadow.setRadius(circle.getRadius()/2);
        dropShadow.setOffsetX(offset.getX());
        dropShadow.setOffsetY(offset.getY());
        Color color = Main.settingsHandler.getCircleColor();
        dropShadow.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), .5));
        circle.setEffect(dropShadow);
    }

    private Point getOffsetFromLighting()
    {
        double radius = circle.getRadius()/2;
        double lX = light.getX();
        double lY = light.getY();
        double angle = light.getAngle(circle.getCenterX(), circle.getCenterY(), lX, lY);

        return new Point(radius * Math.cos(Math.toRadians(angle)), radius * Math.sin(Math.toRadians(angle)));
    }
}
