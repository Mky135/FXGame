package game.controller;

import game.Main;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{

    public static Circle m_circle;

    public static TranslateTransition transition;

    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    @FXML
    private void makeRandom()
    {
        Main.mainCircleMover.setRandom(!Main.mainCircleMover.getRandom());
        Main.mainCircleMover.moveNodeRandom();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        m_circle = createCircle();
        transition = createTranslateTransition(m_circle);
    }

    private Circle createCircle() {
        final Circle circle = new Circle(150.0F, 200.0F, 100.0F, Main.settingsHandler.getCircleColor());
        circle.setStroke(Main.settingsHandler.getCircleColor());
        circle.setStrokeType(StrokeType.INSIDE);
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
}
