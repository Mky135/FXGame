package game.util;

import game.Main;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class CircleHandler extends Circle
{
    static TranslateTransition transition;
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);
    public Circle circle;
    private static DropShadow dropShadow = new DropShadow();
    public static Point light = new Point(0, 1);

    public CircleHandler()
    {
        circle = createCircle();
        transition = createTranslateTransition(circle);
        createTranslateTransition(circle);
        updateDropShadow(circle);
    }

    private Circle createCircle()
    {
        final Circle circle = new Circle(150.0F, 200.0F, Main.settingsHandler.getNumberFromLine(3, "Circle Size: "), Main.settingsHandler.getCircleColor());
        circle.setStroke(Main.settingsHandler.getCircleColor());
        circle.setStrokeType(StrokeType.INSIDE);

        circle.setEffect(dropShadow);
        return circle;
    }

    private TranslateTransition createTranslateTransition(final Circle circle)
    {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(t -> {
            circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
            circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
            circle.setTranslateX(0);
            circle.setTranslateY(0);
        });
        return transition;
    }

    public static void updateDropShadow(Circle circle)
    {
        Point offset = getOffsetFromLighting(circle);
//        System.out.println(offset);
        dropShadow.setRadius(circle.getRadius()/2);
        dropShadow.setOffsetX(offset.getX());
        dropShadow.setOffsetY(offset.getY());
        Color color = Main.settingsHandler.getCircleColor();
        dropShadow.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), .5));
        circle.setEffect(dropShadow);
    }

    private static Point getOffsetFromLighting(Circle circle)
    {
        double radius = circle.getRadius()/2;
        double lX = light.getX();
        double lY = light.getY();
        double angle = light.getAngle(circle.getCenterX(), circle.getCenterY(), lX, lY);

        return new Point(radius * Math.cos(Math.toRadians(angle)), radius * Math.sin(Math.toRadians(angle)));
    }
}
