package game.util;

import game.Main;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

import java.util.Random;
import static game.util.CircleHandler.transition;

public class CircleMover
{
    /**
     * Used for movement using the keys
     *
     * vel
     */
    private static int KEYBOARD_MOVEMENT_DELTA = 10;

    /**
     * Local variable telling it how much to change by
     *
     * acc
     */
    private static int KEYBOARD_MOVEMENT_CHANGE = 4;

    /**
     * Used to make the circle go to random positions
     */
    private static boolean random = false;

    private static int tickSpeed;

    private Scene scene;

    private Circle circle;

    public CircleMover(Scene scene, Circle circle)
    {
        this.scene = scene;
        this.circle = circle;

        moveNodeOnMousePress();
        moveOnKeyPress();
        stopOnKeyReleased();
        moveNodeRandom();
    }

    private boolean up = false;
    private boolean right = false;
    private boolean down = false;
    private boolean left = false;

    private void moveOnKeyPress()
    {
        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP)
            {
                up = true;
                nodeUp(true);
            }
            if(event.getCode() == KeyCode.DOWN)
            {
                down = true;
                nodeDown(true);
            }
            if(event.getCode() == KeyCode.RIGHT)
            {
                right = true;
                nodeRight();
            }
            if(event.getCode() == KeyCode.LEFT)
            {
                left = true;
                nodeLeft();
            }

            if(up && right)
            {
                nodeUp(false);
                nodeRight();
            }
            if(up && left)
            {
                nodeUp(false);
                nodeLeft();
            }
            if(down && right)
            {
                nodeDown(false);
                nodeRight();
            }
            if(down && left)
            {
                nodeDown(false);
                nodeLeft();
            }

            CircleHandler.updateDropShadow(circle);
        });
    }

    private void nodeUp(boolean add)
    {
        circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterY() + circle.getRadius()) <= 0)
        { circle.setCenterY(scene.getHeight() + circle.getRadius()/2); }

        if(add)
            KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void nodeDown(boolean add)
    {
        circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterY() - circle.getRadius()) >= scene.getHeight())
        { circle.setCenterY(-circle.getRadius()/2); }

        if(add)
            KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void nodeRight()
    {
        circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterX() - circle.getRadius()) >= scene.getWidth())
        { circle.setCenterX(-circle.getRadius()/2); }
        KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void nodeLeft()
    {
        circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA);
        if((circle.getCenterX() + circle.getRadius()) <= 0)
        { circle.setCenterX(scene.getWidth() + circle.getRadius()/2); }
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
            if(!event.isControlDown())
            {
                circle.setCenterX(event.getSceneX());
                circle.setCenterY(event.getSceneY());
            }
            else
            {
                transition.setToX(event.getSceneX() - circle.getCenterX());
                transition.setToY(event.getSceneY() - circle.getCenterY());
                transition.playFromStart();
                CircleHandler.updateDropShadow(circle);
            }
        });
    }

    public void moveNodeRandom()
    {
        tickSpeed = Main.settingsHandler.getNumberFromLine(2,"Tick Speed: ");
        Random r = new Random();
        new Thread(() ->{
            while(random)
            {
                circle.setCenterY(r.nextInt((int) scene.getHeight()));
                circle.setCenterX(r.nextInt((int) scene.getWidth()));
                CircleHandler.updateDropShadow(circle);
                try { Thread.sleep(tickSpeed); }
                catch(InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }

    public void setRandom(boolean value)
    {
        random = value;
    }

    public boolean getRandom()
    {
        return random;
    }
}
