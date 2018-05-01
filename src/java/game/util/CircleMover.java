package game.util;

import game.Main;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.*;

import game.util.CircleMap.Direction;

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

    Circle[] circles;
    private CircleMap circleMap;

    private Map<Circle, Boolean> circleEnabled = new HashMap<>();
    private Map<Circle, TranslateTransition> circleTranslationMap = new HashMap<>();

    private Map<Circle, CircleHandler> circleHandlerMap = new HashMap<>();
    private ArrayList<Circle> blockers;

    public CircleMover(Scene scene, CircleHandler[] circleHandlers, ArrayList<Circle> blockers, Circle... circles)
    {
        this.scene = scene;
        this.circles = circles;
        this.blockers = blockers;
        circleMap = new CircleMap();

        for(Circle circle : circles)
        {
            TranslateTransition transition = createTranslateTransition(circle);
            circleTranslationMap.put(circle, transition);
            circleEnabled.put(circle, true);
            circleMap.put(circle, Direction.NONE);
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

            Point prevCircle = new Point();

            for(Circle circle : circleEnabled.keySet())
            {
                prevCircle.setPoint(circle.getCenterX(), circle.getCenterY());

                if(circleEnabled.get(circle))
                {
                    if(event.getCode() == KeyCode.UP)
                    {
                        up = true;
                        move(true, circle, Direction.UP);
                    }
                    if(event.getCode() == KeyCode.DOWN)
                    {
                        down = true;
                        move(true, circle, Direction.DOWN);
                    }
                    if(event.getCode() == KeyCode.RIGHT)
                    {
                        right = true;
                        move(true, circle, Direction.RIGHT);
                    }
                    if(event.getCode() == KeyCode.LEFT)
                    {
                        left = true;
                        move(true, circle, Direction.LEFT);
                    }

                    if(up && right)
                    {
                        move(false, circle, Direction.UP);
                        move(true, circle, Direction.RIGHT);
                    }
                    if(up && left)
                    {
                        move(false, circle, Direction.UP);
                        move(true, circle, Direction.LEFT);
                    }
                    if(down && right)
                    {
                        move(false, circle, Direction.DOWN);
                        move(true, circle, Direction.RIGHT);
                    }
                    if(down && left)
                    {
                        move(false, circle, Direction.DOWN);
                        move(true, circle, Direction.LEFT);
                    }
                    circleHandlerMap.get(circle).updateDropShadow();
                }
            }
            toggleCircle(event.getCode().toString());
        });
    }

    private void move(boolean add, Circle circle, Direction direction)
    {
        for(int i = 0; i < KEYBOARD_MOVEMENT_DELTA; i++)
        {
            for(Direction dirc : circleMap.get(circle))
            {
                if(dirc != direction)
                {
                    switch(direction)
                    {
                        case UP:
                            circle.setCenterY(circle.getCenterY() - 1);
                            break;
                        case DOWN:
                            circle.setCenterY(circle.getCenterY() + 1);
                            break;
                        case RIGHT:
                            circle.setCenterX(circle.getCenterX() + 1);
                            break;
                        case LEFT:
                            circle.setCenterX(circle.getCenterX() - 1);
                            break;
                    }

                    if(hitBlocker(circle))
                    {
                        if(circleMap.get(circle).length <= 1)
                        {
                            circleMap.replace(circle, direction);
                        }
                        else
                        {
                            circleMap.addDirection(circle, direction);
                        }
                    }
                }
                else if(!hitBlocker(circle))
                {
                    if(circleMap.get(circle).length <= 1)
                    {
                        circleMap.replace(circle, Direction.NONE);
                    }
                    else
                    {
                        circleMap.removeDirection(circle, direction);
                    }
                }
            }
        }

        switch(direction)
        {
            case UP:
                if((circle.getCenterY() + circle.getRadius()) <= 0)
                { circle.setCenterY(scene.getHeight() + circle.getRadius() / 2); }
                break;
            case DOWN:
                if((circle.getCenterY() - circle.getRadius()) >= scene.getHeight())
                { circle.setCenterY(-circle.getRadius() / 2); }
                break;
            case LEFT:
                if((circle.getCenterX() + circle.getRadius()) <= 0)
                { circle.setCenterX(scene.getWidth() + circle.getRadius() / 2); }
                break;
            case RIGHT:
                if((circle.getCenterX() - circle.getRadius()) >= scene.getWidth())
                { circle.setCenterX(-circle.getRadius() / 2); }
                break;
        }
        if((circle.getCenterY() + circle.getRadius()) <= 0)
        { circle.setCenterY(scene.getHeight() + circle.getRadius() / 2); }

        if(add)
        { KEYBOARD_MOVEMENT_DELTA += KEYBOARD_MOVEMENT_CHANGE; }
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
            }
        }
    }

    //(x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2
    private boolean hitBlocker(Circle circle)
    {
        for(Circle blocker : blockers)
        {
            double deltaX = Math.pow((blocker.getCenterX() - circle.getCenterX()), 2);
            double deltaY = Math.pow((blocker.getCenterY() - circle.getCenterY()), 2);
            double deltaR = Math.pow((circle.getRadius() + blocker.getRadius()), 2);

            if(deltaX + deltaY <= deltaR)
            {
//                circle.setCenterX();
                return true;
            }
        }
        return false;
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

    public Circle[] getCircles()
    {
        return circles;
    }


}