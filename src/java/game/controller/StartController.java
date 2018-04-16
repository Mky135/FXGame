package game.controller;

import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable
{
    @FXML
    private void switchToMainScene()
    {
        Main.switchToScene(Main.mainScene, "Game");
    }

    @FXML
    private void switchToSettingsScreen()
    {
        Main.switchToScene(Main.settingsScene, "Settings");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
