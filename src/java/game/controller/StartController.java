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
    private void switchToMainScene(ActionEvent event)
    {
        Main.switchToScene(Main.mainScene);
    }

    @FXML
    private void exit(ActionEvent event)
    {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
