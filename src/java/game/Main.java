package game;

import game.controller.MainController;
import game.util.CircleHandler;
import game.util.CircleMover;
import game.util.SettingsHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application
{
    /**
     * The Stage on which all of our scenes are displayed. By keeping a reference to it, we can change scenes at any time.
     */
    private static Stage window;

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

    public static SettingsHandler settingsHandler = new SettingsHandler();

    private static Circle m_circle;
    private static Circle e_circle;
    private static Circle e2_circle;
    private static Circle block;

    private static CircleHandler circleHandler;
    private static CircleHandler extraHandler;
    private static CircleHandler extra2Handler;

    private ArrayList<Circle> blockers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        circleHandler = new CircleHandler();
        extraHandler = new CircleHandler();
        extra2Handler = new CircleHandler();
        m_circle = circleHandler.circle;
        block = new Circle();
        block.setStroke(Color.WHITE);
        block.setFill(Color.WHITE);
        block.setRadius(50);
        block.setCenterY(500);
        block.setCenterX(500);
        e_circle = extraHandler.circle;
        e2_circle = extra2Handler.circle;

        blockers.add(block);
        primaryStage.setTitle("Start");

        // Build all of the scenes
        Parent startRoot = FXMLLoader.load(getClass().getResource("startingScreen.fxml"));
        Parent mainRoot = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("settingsScreen.fxml"));

        Group group = new Group(m_circle, e_circle, e2_circle, block, mainRoot);

        Scene startScene = new Scene(startRoot);
        mainScene = new Scene(group, screenSize.width, screenSize.height, settingsHandler.getBackGroundColor());
        settingsScene = new Scene(settingsRoot);
        CircleHandler[] circleHandlers = { circleHandler, extraHandler, extra2Handler };
        mainCircleMover = new CircleMover(mainScene, circleHandlers, blockers, m_circle, e_circle, e2_circle);

        mainScene.setOnMouseMoved(event -> {
            circleHandler.light.setX(event.getX());
            circleHandler.light.setY(event.getY());
            circleHandler.updateDropShadow();
            extraHandler.light.setX(event.getX());
            extraHandler.light.setY(event.getY());
            extraHandler.updateDropShadow();

            extra2Handler.light.setX(event.getX());
            extra2Handler.light.setY(event.getY());
            extra2Handler.updateDropShadow();
        });

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

        if(scene == mainScene)
        { loadMain(); }
    }

    private static void loadMain()
    {
        mainScene.setFill(settingsHandler.getBackGroundColor());

        e_circle.setFill(settingsHandler.getCircleColor());
        e2_circle.setFill(settingsHandler.getCircleColor());
        m_circle.setFill(settingsHandler.getCircleColor());

        e_circle.setStroke(settingsHandler.getCircleColor());
        e2_circle.setStroke(settingsHandler.getCircleColor());
        m_circle.setStroke(settingsHandler.getCircleColor());

        e_circle.setRadius(50);
        e2_circle.setRadius(200);
        m_circle.setRadius(settingsHandler.getNumberFromLine(3, "Circle Size: "));

        block.setStroke(Color.WHITE);

        MainController.INSTANCE.setLabel();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
