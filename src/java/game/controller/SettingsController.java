package game.controller;

import game.Main;
import game.util.SettingsHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable
{
    @FXML
    private ColorPicker circleColorPicker;

    @FXML
    private ColorPicker backgroundColorPicker;

    @FXML
    private void setCircleColor()
    {
        SettingsHandler.settings.modifyLine(SettingsHandler.settings.getLine(0), getColorFormat("Circle", circleColorPicker.getValue()));
    }

    @FXML
    private void setBackgroundColor()
    {
        SettingsHandler.settings.modifyLine(SettingsHandler.settings.getLine(1), getColorFormat("Background", backgroundColorPicker.getValue()));
    }

    @FXML
    private void continueOnToMainScreen()
    {
        Main.switchToScene(Main.mainScene, "Game");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    private String getColorFormat(String start, Color color)
    {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();

        return start + ": r:"+r+" g:"+g+" b:"+b+";";
    }

}
