package game.util;

import game.Main;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CircleMover
{
    /**
     * Used for movement using the keys
     * <p>
     * vel
     */
    private static int KEYBOARD_MOVEMENT_DELTA = 10;

    /**
     * Local variable telling it how much to change by
     * <p>
     * acc
     */
    private static int KEYBOARD_MOVEMENT_CHANGE = 4;

    /**
     * Used to make the circle go to random positions
     */
    private static boolean random = false;

    private final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    private static int tickSpeed;

    private Scene scene;

    private Map<Circle, Boolean> circleEnabled = new HashMap<>();
    private Map<Circle, TranslateTransition> circleTranslationMap = new HashMap<>();

    private Map<Circle, CircleHandler> circleHandlerMap = new HashMap<>();

    public CircleMover(Scene scene, CircleHandler[] circleHandlers, Circle... circles)
    {
        this.scene = scene;

        for(Circle circle : circles)
        {
            circleEnabled.put(circle, true);
        }
        for(Circle circle : circles)
        {
            TranslateTransition transition = createTranslateTransition(circle);
            circleTranslationMap.put(circle, transition);
        }
        for(int i = 0; i < circleHandlers.length; i++)
        {
            circleHandlerMap.put(circles[i], circleHandlers[i]);
        }

        moveNodeOnMousePress();
        moveOnKeyPress();
        stopOnKeyReleased();
        moveCircleRandom();
    }

    private TranslateTransition createTranslateTransition(Circle circle)
    {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(t -> {
            circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
            circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
            circle.setTranslateX(0);
            circle.setTranslateY(0);
        });
        return transition;
    }

    private boolean up = false;
    private boolean right = false;
    private boolean down = false;
    private boolean left = false;

    private void moveOnKeyPress()
    {
        scene.setOnKeyPressed(event -> {
            for(Circle circle : circleEnabled.keySet())
            {
                if(circleEnabled.get(circle))
                {
                    if(event.getCode() == KeyCode.UP)
                    {
                        up = true;
                        nodeUp(true, circle);
                    }
                    if(event.getCode() == KeyCode.DOWN)
                    {
                        down = true;
                        nodeDown(true, circle);
                    }
                    if(event.getCode() == KeyCode.RIGHT)
                    {
                        right = true;
                        nodeRight(circle);
                    }
                    if(event.getCode() == KeyCode.LEFT)
                    {
                        left = true;
                        nodeLeft(circle);
                    }

                    if(up && right)
                    {
                        nodeUp(false, circle);
                        nodeRight(circle);
                    }
                    if(up && left)
                    {
                        nodeUp(false, circle);
                        nodeLeft(circle);
                    }
                    if(down && right)
                    {
                        nodeDown(false, circle);
                        nodeRight(circle);
                    }
                    if(down && left)
                    {
                        nodeDown(false, circle);
                        nodeLeft(circle);
                    }

                    circleHandlerMap.get(circle).updateDropShadow();
                }
            }
            toggleCircle(event.getCode().toString());
        });
    }

    private void nodeUp(boolean add, Circle circle)
    {
        circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterY() + circle.getRadius()) <= 0)
        { circle.setCenterY(scene.getHeight() + circle.getRadius() / 2); }

        if(add)
        { KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE; }
    }

    private void nodeDown(boolean add, Circle circle)
    {
        circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterY() - circle.getRadius()) >= scene.getHeight())
        { circle.setCenterY(-circle.getRadius() / 2); }

        if(add)
        { KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE; }
    }

    private void nodeRight(Circle circle)
    {
        circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterX() - circle.getRadius()) >= scene.getWidth())
        { circle.setCenterX(-circle.getRadius() / 2); }
        KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void nodeLeft(Circle circle)
    {
        circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterX() + circle.getRadius()) <= 0)
        { circle.setCenterX(scene.getWidth() + circle.getRadius() / 2); }
        KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void stopOnKeyReleased()
    {
        scene.setOnKeyReleased(event -> {
            KEYBOARD_MOVEMENT_DELTA = 10;

            switch(event.getCode())
            {
                case UP:
                    up = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
                case DOWN:
                    down = false;
                    break;
                case LEFT:
                    left = false;
                    break;
            }
        });
    }

    private void moveNodeOnMousePress()
    {
        scene.setOnMousePressed(event -> {
            for(Circle circle : circleEnabled.keySet())
            {
                if(circleEnabled.get(circle))
                {
                    if(!event.isControlDown())
                    {
                        circle.setCenterX(event.getSceneX());
                        circle.setCenterY(event.getSceneY());
                    }
                    else
                    {
                        circleTranslationMap.get(circle).setToX(event.getSceneX() - circle.getCenterX());
                        circleTranslationMap.get(circle).setToY(event.getSceneY() - circle.getCenterY());
                        circleTranslationMap.get(circle).playFromStart();
                        circleHandlerMap.get(circle).updateDropShadow();
                    }
                }
            }
        });
    }

    private void moveCircleRandom()
    {
        tickSpeed = Main.settingsHandler.getNumberFromLine(2, "Tick Speed: ");
        Random r = new Random();

        new Thread(() -> {
            while(random)
            {
                for(Circle circle : circleEnabled.keySet())
                {
                    if(circleEnabled.get(circle))
                    {
                        circle.setCenterY(r.nextInt((int) scene.getHeight()));
                        circle.setCenterX(r.nextInt((int) scene.getWidth()));
                        circleHandlerMap.get(circle).updateDropShadow();
                        try { Thread.sleep(tickSpeed); }
                        catch(InterruptedException e) { e.printStackTrace(); }
                    }
                }
            }
        }).start();
    }

    private void toggleCircle(String keyCode)
    {
        if(keyCode.contains("DIGIT"))
        {
            int numCode = Integer.valueOf(keyCode.substring(keyCode.length() - 1));
            if(numCode <= circleEnabled.size())
            {
                int i = 0;
                for(Circle circle : circleEnabled.keySet())
                {
                    if(i == numCode - 1)
                    {
                        circleEnabled.replace(circle, circleEnabled.get(circle), !circleEnabled.get(circle));
                    }
                    i++;
                }
                i = 0;
            }
        }
    }

    public void setRandom(boolean value)
    {
        random = value;
        moveCircleRandom();
    }

    public boolean getRandom()
    {
        return random;
    }
}
