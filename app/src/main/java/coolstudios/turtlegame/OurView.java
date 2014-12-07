package coolstudios.turtlegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class OurView extends SurfaceView implements Runnable, View.OnTouchListener {

    Thread t = null;
    SurfaceHolder holder;
    boolean isItOK = false;
    Canvas c;
    Character crab;
    Character turtle;
    float temp;

    public OurView(Context context){
        super(context);
        holder = getHolder();

        //first spawn for the characters
        turtle = new Character(0, 0, 0, 0, BitmapFactory.decodeResource(getResources(), R.drawable.turtle));
        crab = new Character(75, 150, 1000, 150, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        setOnTouchListener(this);
    }

    public void run(){
        while (isItOK){
            if(!holder.getSurface().isValid()){
                continue;
            }

            c = holder.lockCanvas();
            c.drawARGB(255, 150, 150, 10);
            //moves the characters
            moveCharacter(turtle);
            moveCharacter(crab);

                //hit detection - right now the game just pauses when hit
                if(turtle.getX() >= crab.getX() && turtle.getX() <= (crab.getX() + crab.getImage().getWidth()) && turtle.getY() <= crab.getY() + crab.getImage().getHeight() && turtle.getY() >= crab.getY())
                {
                   isItOK = false;
                }

                //Respawns the crab on the other side of the ma
                if((crab.getX() > c.getWidth() || crab.getX() > c.getHeight()) || ((crab.getX() < 0 || crab.getY() < 0)))
                {
                    crab.setX(1);
                    temp = (float) (Math.random() * (c.getHeight() - 100));
                    crab.setY(temp);
                    crab.move(1000, temp);
                }
            draw(c);
            holder.unlockCanvasAndPost(c);
        }
    }

    public void draw(Canvas canvas){

        //draws the character
        c.drawBitmap(crab.getImage(),crab.getX() - (crab.getImage().getWidth()/2), crab.getY() - (crab.getImage().getHeight()/2), null);
        c.drawBitmap(turtle.getImage(), turtle.getX() - (turtle.getImage().getWidth()/2), turtle.getY() - (turtle.getImage().getHeight()/2), null);

    }
    public void moveCharacter(Character moveMe)
    {
        //calculates the distance between where the character is, and where it is headed.
        float distanceX = moveMe.getDesX() - moveMe.getX(), distanceY = moveMe.getDesY() - moveMe.getY();
        //calculates the polar angle
        float angle = (float) Math.atan2(distanceY, distanceX);

        //continues changing the position as long as the distance between the destination and position is really really small. (CANNOT EQUATE TWO FLOATS)
        if((Math.abs(Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2))) > 0.00000000001) && (Math.abs(distanceX) > 10 || Math.abs(distanceY) > 10))
        {
            moveMe.setX(moveMe.getX() + ((float) Math.cos(angle) * moveMe.getSpeed()));
            moveMe.setY(moveMe.getY() + ((float) Math.sin(angle) * moveMe.getSpeed()));
        }
    }

    public boolean onTouch(View view, MotionEvent me){

        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }



        //not sure if this needs to be used for any character. just a note that "turtle" variable is used here
        switch (me.getAction()){
            case MotionEvent.ACTION_DOWN:
                turtle.move(me.getX(), me.getY());
                break;
            case MotionEvent.ACTION_UP:
                turtle.move(me.getX(), me.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                turtle.move(me.getX(), me.getY());
                break;
        }

        return true;

    }

    public void pause(){
        isItOK = false;
        while(true){
            try{
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }
    public void resume(){
        isItOK = true;
        t = new Thread(this);
        t.start();
    }
}