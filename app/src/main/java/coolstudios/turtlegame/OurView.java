package coolstudios.turtlegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class OurView extends SurfaceView implements Runnable, View.OnTouchListener {

    Thread t = null;
    SurfaceHolder holder;
    boolean isItOK = false;
    Canvas c;
    Character turtle;

    public OurView(Context context){
        super(context);
        holder = getHolder();
        turtle = new Character(0, 0, BitmapFactory.decodeResource(getResources(), R.drawable.turtle));
        setOnTouchListener(this);
    }

    public void run(){
        while (isItOK){
            if(!holder.getSurface().isValid()){
                continue;
            }

            c = holder.lockCanvas();
            c.drawARGB(255, 150, 150, 10);
            draw(c);
            holder.unlockCanvasAndPost(c);
        }
    }

    public void draw(Canvas canvas){

        //this lets the turtle move to the point correctly, but, he has a seizure or something when he reaches the destination. (doesn't crash or anything tho)
        //also, this draw function needs to be usable with any kind of character, not just turtle, TO DO FOR LATER

        //calculates the distance between where the character is, and where it is headed.
        float distanceX = turtle.getDesX() - turtle.getX(), distanceY = turtle.getDesY() - turtle.getY();
        //calculates the polar angle
        float angle = (float) Math.atan2(distanceY, distanceX);

        //continues changing the position as long as the distance between the destination and position is really really small. (CANNOT EQUATE TWO FLOATS)
        if(Math.abs(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) > 0.00000000001)
        {
            turtle.setX(turtle.getX() + ((float)Math.cos(angle) * turtle.getSpeed()));
            turtle.setY(turtle.getY() + ((float)Math.sin(angle) * turtle.getSpeed()));
        }
        //draws the character
        c.drawBitmap(turtle.getImage(), turtle.getX() - (turtle.getImage().getWidth()/2), turtle.getY() - (turtle.getImage().getHeight()/2), null);

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