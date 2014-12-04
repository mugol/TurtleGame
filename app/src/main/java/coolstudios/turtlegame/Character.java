package coolstudios.turtlegame;

import android.graphics.Point;

/**
 * Created by WilliamRandazzo on 12/1/2014.
 */
public class Character
{
    int size;
    int speed;
    Point position;
    boolean isAlive;
    Point destination;

    //Empty Constructor, sets everything to zero.
    public Character()
    {
        size = 0;
        speed = 0;
        position = new Point(0,0);
        destination = new Point(0,0);
        isAlive = false;
    }

    //general constructor, allows you to set all of a character's variables.
    public Character(int newSize, int newSpeed, int xPos, int yPos, boolean deadOrAlive)
    {
        isAlive = deadOrAlive;
        size = newSize;
        speed = newSpeed;
        position = new Point(xPos, yPos);
        destination = new Point(0,0);
    }


    //SET AND GET METHODS -------------------------------------------------------------------
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }


    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean getAlive()
    {
        return isAlive;
    }

    //--------------------------------------------------------------------------------------

    public void move(Point goHere)
    {
        destination = goHere;
    }

}
