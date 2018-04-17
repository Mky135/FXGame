package game.controller;

import game.Main;
import game.util.SettingsHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
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
    private TextField tickSpeed;

    @FXML
    private TextField circleSize;

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
        setTickSpeed();
        setCircleSize();
        Main.switchToScene(Main.mainScene, "Game");
    }

    private void setTickSpeed()
    {
        SettingsHandler.settings.modifyLine(SettingsHandler.settings.getLine(2), getNumberFormat("Tick Speed", tickSpeed.getText()));
    }

    private void setCircleSize()
    {
        SettingsHandler.settings.modifyLine(SettingsHandler.settings.getLine(3), getNumberFormat("Circle Size", circleSize.getText()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        circleColorPicker.setValue(Main.settingsHandler.getCircleColor());
        backgroundColorPicker.setValue(Main.settingsHandler.getBackGroundColor());
        tickSpeed.setText(String.valueOf(Main.settingsHandler.getNumberFromLine(2,"Tick Speed: ")));
        circleSize.setText(String.valueOf(Main.settingsHandler.getNumberFromLine(3, "Circle Size: ")));
    }

    private String getColorFormat(String start, Color color)
    {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();

        return start + ": r:"+r+" g:"+g+" b:"+b+";";
    }

    public String getNumberFormat(String beginning, String value)
    {
        return beginning + ": " + Integer.valueOf(value) + ";";
    }

}
