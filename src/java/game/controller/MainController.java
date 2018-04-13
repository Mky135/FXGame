package game.controller;

import game.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    public Button butt;

    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    @FXML
    private void click()
    {
        butt.setText("clicked");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        m_circle = createCircle();
        transition = createTranslateTransition(m_circle);
    }

    private Circle createCircle() {
        final Circle circle = new Circle(150.0F, 200.0F, 100.0F, Color.DODGERBLUE);
        circle.setStroke(Color.DODGERBLUE);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setOpacity(0.5);
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
