package game.controller;

import game.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private void makeRandom()
    {
        Main.mainCircleMover.setRandom(!Main.mainCircleMover.getRandom());
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
