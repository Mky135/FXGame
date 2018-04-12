package game;

import game.controller.MainController;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;

import static game.controller.MainController.KEYBOARD_MOVEMENT_DELTA;
import static game.controller.MainController.m_circle;
import static game.controller.MainController.transition;


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
    private static Scene startScene;

    /**
     * The scene to be displayed when the user is playing
     * <p>
     * Contains nodes that let the user guess a letter, view how many lives they have left, and continue to the leaderboard
     */
    public static Scene mainScene;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

//    private Circle circle;
    public static Group group;

    public Main() throws IOException {}

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Keep a reference to the window
        window = primaryStage;

        primaryStage.setTitle("Game");

        // Build all of the scenes
        Parent startRoot = FXMLLoader.load(getClass().getResource("startingScreen.fxml"));
        Parent mainRoot = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));

//        circle  = MainController.circle;
//        circle = createCircle();

        group = new Group(m_circle, mainRoot);

        startScene = new Scene(startRoot);
        mainScene = new Scene(group,600, 400, Color.BLACK);

        moveCircleOnKeyPress();
        moveCircleOnMousePress();

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

    private void moveCircleOnKeyPress()
    {
        //TODO: Make it so it accelerates
        mainScene.setOnKeyPressed(event -> {
            switch(event.getCode())
            {
                case UP:
                    m_circle.setCenterY(m_circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
                    break;
                case RIGHT:
                    m_circle.setCenterX(m_circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
                    break;
                case DOWN:
                    m_circle.setCenterY(m_circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA);
                    break;
                case LEFT:
                    m_circle.setCenterX(m_circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA);
                    break;
            }
        });
    }

    private void moveCircleOnMousePress()
    {
        mainScene.setOnMousePressed(event -> {
            if (!event.isControlDown()) {
                m_circle.setCenterX(event.getSceneX());
                m_circle.setCenterY(event.getSceneY());
            } else {
                transition.setToX(event.getSceneX() - m_circle.getCenterX());
                transition.setToY(event.getSceneY() - m_circle.getCenterY());
                transition.playFromStart();
            }
        });
    }

}
