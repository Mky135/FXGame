package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application
{
    /**
     * The Stage on which all of our scenes are displayed. By keeping a reference to it, we can change scenes at any time.
     */
    private static Stage window;

    /**
     * The scene to be displayed when the user first opens the game.
     * Contains Nodes that let the user input their name
     */
    public static Scene startScene;

    /**
     * The scene to be displayed when the user is playing
     * <p>
     * Contains nodes that let the user guess a letter, view how many lives they have left, and continue to the leaderboard
     */
    public static Scene mainScene;

    public static Scene nameScreen;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Keep a reference to the window
        window = primaryStage;

        primaryStage.setTitle("Game");

        // Build all of the scenes
        Parent mainRoot = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Parent startRoot = FXMLLoader.load(getClass().getResource("startingScreen.fxml"));
//        Parent nameRoot = FXMLLoader.load(getClass().getResource("nameScreen.fxml"));

        startScene = new Scene(startRoot, 600, 400);
        mainScene = new Scene(mainRoot);
//        nameScreen = new Scene(nameRoot);

        // Display the window
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    /**
     * Switch from the any scene to another
     * @param scene switch to that scene
     */
    public static void switchToScene(Scene scene)
    {
        window.setScene(scene);
    }

    public static void main(String[] args) { launch(args); }


}
