package game.util;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Random;

import static game.controller.MainController.m_circle;
import static game.controller.MainController.transition;

public class NodeMover
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
    private static int KEYBOARD_MOVEMENT_CHANGE = 2;

    /**
     * Used to make the circle go to random positions
     */
    private static boolean random = false;

    private Scene scene;

    private Node node;

    public NodeMover(){}

    public NodeMover(Scene scene, Node node)
    {
        this.scene = scene;
        this.node = node;

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
        });
    }

    private void nodeUp(boolean add)
    {
        m_circle.setCenterY(m_circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterY() + m_circle.getRadius()) <= 0)
        { m_circle.setCenterY(scene.getHeight() + m_circle.getRadius()/2); }

        if(add)
            KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void nodeDown(boolean add)
    {
        m_circle.setCenterY(m_circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterY() - m_circle.getRadius()) >= scene.getHeight())
        { m_circle.setCenterY(-m_circle.getRadius()/2); }

        if(add)
            KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void nodeRight()
    {
        m_circle.setCenterX(m_circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterX() - m_circle.getRadius()) >= scene.getWidth())
        { m_circle.setCenterX(-m_circle.getRadius()/2); }
        KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE;
    }

    private void nodeLeft()
    {
        m_circle.setCenterX(m_circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA);
        if((m_circle.getCenterX() + m_circle.getRadius()) <= 0)
        { m_circle.setCenterX(scene.getWidth() + m_circle.getRadius()/2); }
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

    public void moveNodeRandom()
    {
        Random r = new Random();
        new Thread(() ->{
            while(random)
            {
                m_circle.setCenterY(r.nextInt((int) scene.getHeight()));
                m_circle.setCenterX(r.nextInt((int) scene.getWidth()));
                try { Thread.sleep(100); }
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
