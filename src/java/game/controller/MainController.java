package game.controller;

import game.Main;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private void makeRandom()
    {
        Main.mainCircleMover.setRandom(!Main.mainCircleMover.getRandom());
        Main.mainCircleMover.moveNodeRandom();
    }

    @FXML
    private void moveToSettings()
    {
        Main.switchToScene(Main.settingsScene, "Settings");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }



}
