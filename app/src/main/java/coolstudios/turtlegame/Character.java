package coolstudios.turtlegame;

import android.graphics.Bitmap;

/**
 * Created by WilliamRandazzo on 12/1/2014.
 */
public class Character {
    Bitmap image;
    final float speed = 10;
    float x, y, desX, desY;

    //Empty Constructor, sets everything to zero.
    public Character() {
        x = 0;
        y = 0;
        desX = 0;
        desY = 0;
    }

    //general constructor, allows you to set all of a character's variables.
    public Character(float xPos, float yPos, Bitmap newImage) {
        image = newImage;
        x = xPos;
        y = yPos;
    }

    public Bitmap getImage() {
        return image;
    }

    public float getDesX() {
        return desX;
    }

    public float getDesY() {
        return desY;
    }

    public float getSpeed() {
        return speed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    public void move(float newX, float newY)
    {
        desX = newX;
        desY = newY;
    }
}