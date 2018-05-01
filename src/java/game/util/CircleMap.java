package game.util;

import javafx.scene.shape.Circle;

import java.util.*;

public class CircleMap
{
    private ArrayList<Circle> circles;
    private ArrayList<ArrayList<Direction>> directions;

    CircleMap()
    {
        circles = new ArrayList<>();
        directions = new ArrayList<>();
    }

    public Direction[] get(Circle key)
    {
        Direction[] ds = new Direction[directions.get(circles.indexOf(key)).toArray().length];
        for(int i = 0; i < ds.length; i++)
        {
            ds[i] = directions.get(circles.indexOf(key)).get(i);
        }
        return ds;
    }

    public void put(Circle key, Direction... values)
    {
        circles.add(key);

        directions.add(circles.indexOf(key), new ArrayList<>(Arrays.asList(values)));
    }

    public void remove(Object key)
    {
        directions.remove(circles.indexOf(key));
        circles.remove(key);
    }

    public void clear()
    {
        circles.clear();
        directions.clear();
    }

    public Object[] keySet()
    {
        return circles.toArray();
    }

    public Object[] values()
    {
        return directions.toArray();
    }

    void replace(Circle circle, Direction... directions)
    {
        this.directions.set(circles.indexOf(circle), new ArrayList<>(Arrays.asList(directions)));
    }

    void addDirection(Circle key, Direction direction)
    {
        directions.get(circles.indexOf(key)).add(direction);
    }

    int indexOf(Circle key)
    {
        for(int i = 0; i < circles.size(); i++)
        {
            if(circles.get(i) == key)
            {
                return i;
            }
        }

        return 0;
    }

    public void removeDirection(Circle key, Direction direction)
    {
        for(int i=0; i< directions.get(circles.indexOf(key)).size(); i++)
        {
            if(directions.get(circles.indexOf(key)).get(i) == direction)
            {
                directions.get(circles.indexOf(key)).remove(i);
            }
        }
    }


    public enum Direction
    {
        UP, DOWN, LEFT, RIGHT, NONE
    }
}
