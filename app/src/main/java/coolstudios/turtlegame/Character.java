package coolstudios.turtlegame;

import android.graphics.Bitmap;

public class Character {
    private Bitmap image;
    private float x, y, desX, desY, speed, size;
    private boolean isAlive;

    //Empty Constructor, sets everything to zero.
    public Character() {
        x = 0;
        y = 0;
        desX = 0;
        desY = 0;
        speed = 0;
        isAlive = false;
        size = 0;
    }

    //general constructor, allows you to set all of a character's variables.
    public Character(float xPos, float yPos, float newDesX, float newDesY, float newSpeed, float newSize, Bitmap newImage) {
        image = newImage;
        x = xPos;
        y = yPos;
        desX = newDesX;
        desY = newDesY;
        speed = newSpeed;
        size = newSize;
        isAlive = true;
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

    public float getSize() {
        return size;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void move(float newX, float newY)
    {
        desX = newX;
        desY = newY;
    }
}