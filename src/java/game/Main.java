package game;

import game.util.CircleMover;
import game.util.SettingsHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

import static game.controller.MainController.m_circle;

public class Main extends Application
{
    /**
     * The Stage on which all of our scenes are displayed. By keeping a reference to it, we can change scenes at any time.
     */
    private static Stage window;

    /**
     * The scene to be displayed when the user first opens the game.
     * Contains Nodes that lets the user input if they want to move to settings screen or not
     */
    private static Scene startScene;

    /**
     * The scene to be displayed when the user is playing
     * <p>
     * Contains nodes that lets the user play the game
     */
    public static Scene mainScene;

    /**
     * The scene to be displayed when the user is playing
     * <p>
     * Contains nodes that lets the user manage settings
     */
    public static Scene settingsScene;

    /**
     * Gives size of whole screen
     */
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Sets the CircleMover to be in the mainScreen controlling the circle
     */
    public static CircleMover mainCircleMover;

    public static SettingsHandler settingsHandler;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Start");

        //Builds settings handler
        settingsHandler = new SettingsHandler();

        // Build all of the scenes
        Parent startRoot = FXMLLoader.load(getClass().getResource("startingScreen.fxml"));
        Parent mainRoot = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("settingsScreen.fxml"));

        Group group = new Group(m_circle, mainRoot);

        startScene = new Scene(startRoot);
        mainScene = new Scene(group, screenSize.width, screenSize.height, settingsHandler.getBackGroundColor());
        settingsScene = new Scene(settingsRoot);
        mainCircleMover = new CircleMover(mainScene, m_circle);

        // Keep a reference to the window
        window = primaryStage;

        // Display the window
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    /**
     * Switch from the any scene to another
     *
     * @param scene switch to that scene
     */
    public static void switchToScene(Scene scene, String name)
    {
        window.setX(0);
        window.setY(0);
        window.setTitle(name);
        window.setScene(scene);
    }

    public static void loadMain()
    {

    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
