package coolstudios.turtlegame;

import android.graphics.Point;

/**
 * Created by WilliamRandazzo on 11/10/2014.
 */
public class Unit
{
    private int speed;
    private Point position;
    private int size;

    public Unit()
    {
        speed = 0;
        position = new Point(-1,-1);
        size = 0;
    }
    public Unit(int unitSpeed, int xPos, int yPos, int unitSize)
    {
        speed = unitSpeed;
        position = new Point(xPos, yPos);
        size = unitSize;
    }
    
}
