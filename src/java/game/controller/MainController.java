package game.controller;

import game.Main;
import game.util.CircleMover;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

import static game.Main.mainCircleMover;

public class MainController implements Initializable
{
    public static MainController INSTANCE;

    @FXML
    public Label circleLabel;

    @FXML
    private void makeRandom()
    {
        mainCircleMover.setRandom(!mainCircleMover.getRandom());
    }

    @FXML
    private void moveToSettings()
    {
        Main.switchToScene(Main.settingsScene, "Settings");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        INSTANCE = this;
    }


    public void setLabel()
    {
        Circle[] circles = mainCircleMover.getCircles();
        StringBuilder text = new StringBuilder();

        for(int i=0; i<circles.length; i++)
        {
            text.append("Circle ").append(i + 1).append(" = ").append(getCircle(circles[i])).append("\n");
        }

        System.out.println(text);

        circleLabel.setText(text.toString());
    }

    public String getCircle(Circle circle)
    {
        return "Circle[" +"radius=" + circle.getRadius() + ", color=" + circle.getStroke() + "]";
    }
}
