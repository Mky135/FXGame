package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.BitSet;

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

    public static int KEYBOARD_MOVEMENT_DELTA = 10;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //    private Circle circle;
    public static Group group;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Game");

        // Build all of the scenes
        Parent startRoot = FXMLLoader.load(getClass().getResource("startingScreen.fxml"));
        Parent mainRoot = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));

//        circle  = MainController.circle;
//        circle = createCircle();

        group = new Group(m_circle, mainRoot);

        startScene = new Scene(startRoot);
        mainScene = new Scene(group, screenSize.width, screenSize.height, Color.BLACK);
        primaryStage.setX(0);
        primaryStage.setY(0);

        moveCircleOnKeyPress();
        moveCircleOnMousePress();
        stopOnKeyReleased();

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
    public static void switchToScene(Scene scene)
    {
        window.setScene(scene);
    }

    public static void main(String[] args) { launch(args); }

    private BitSet keyboardBitSet = new BitSet();

    private void moveCircleOnKeyPress()
    {
        System.out.println("Current Keys: ");

        mainScene.setOnKeyPressed(event -> {
            keyboardBitSet.set(event.getCode().ordinal(), true);

            for( KeyCode keyCode: KeyCode.values())
            {
                if(keyboardBitSet.get(keyCode.ordinal()))
                    System.out.println(keyCode.toString());
            }

            if(event.getCode() == KeyCode.UP && event.getCode() == KeyCode.RIGHT)
            {
                circleUp();
                circleRight();
            }

            if(event.getCode() == KeyCode.UP && event.getCode() == KeyCode.LEFT)
            {
                circleUp();
                circleLeft();
            }

            if(event.getCode() == KeyCode.DOWN && event.getCode() == KeyCode.RIGHT)
            {
                circleDown();
                circleRight();
            }

            if(event.getCode().equals(KeyCode.DOWN) && event.getCode().equals(KeyCode.LEFT))
            {
            }

            if(event.getCode() == KeyCode.UP)
            { circleUp(); }
            if(event.getCode() == KeyCode.DOWN)
            { circleDown(); }
            if(event.getCode() == KeyCode.RIGHT)
            { circleRight(); }
            if(event.getCode() == KeyCode.LEFT)
            { circleLeft(); }

        });
    }

    void circleUp()
    {
        m_circle.setCenterY(m_circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterY() + m_circle.getRadius()) <= 0)
        { m_circle.setCenterY(mainScene.getHeight() + m_circle.getRadius()); }
        KEYBOARD_MOVEMENT_DELTA += 10;
    }

    void circleDown()
    {
        m_circle.setCenterY(m_circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterY() - m_circle.getRadius()) >= mainScene.getHeight())
        { m_circle.setCenterY(-m_circle.getRadius()); }
        KEYBOARD_MOVEMENT_DELTA += 10;
    }

    void circleRight()
    {
        m_circle.setCenterX(m_circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterX() - m_circle.getRadius()) >= mainScene.getWidth())
        { m_circle.setCenterX(-m_circle.getRadius()); }
        KEYBOARD_MOVEMENT_DELTA += 10;
    }

    void circleLeft()
    {
        m_circle.setCenterX(m_circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterX() + m_circle.getRadius()) <= 0)
        { m_circle.setCenterX(mainScene.getWidth() + m_circle.getRadius()); }
        KEYBOARD_MOVEMENT_DELTA += 10;
    }

    public void stopOnKeyReleased()
    {
        mainScene.setOnKeyReleased(event -> {
            KEYBOARD_MOVEMENT_DELTA = 10;
            keyboardBitSet.clear();
        });
    }

    private void moveCircleOnMousePress()
    {
        mainScene.setOnMousePressed(event -> {
            if(!event.isControlDown())
            {
                m_circle.setCenterX(event.getSceneX());
                m_circle.setCenterY(event.getSceneY());
            }
            else
            {
                transition.setToX(event.getSceneX() - m_circle.getCenterX());
                transition.setToY(event.getSceneY() - m_circle.getCenterY());
                transition.playFromStart();
            }
        });
    }

}
