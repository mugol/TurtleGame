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
    ArrayList<Character> dudeList;
    Character turtle;
    int crabCount = 0;

    public OurView(Context context){
        super(context);
        holder = getHolder();
        turtle = new Character(0, 0, 0, 0, BitmapFactory.decodeResource(getResources(), R.drawable.turtle));
        dudeList = new ArrayList<Character>();
        dudeList.add(new Character(50, 50, 1000, 0, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
        setOnTouchListener(this);
    }

    public void run(){
        while (isItOK){
            if(!holder.getSurface().isValid()){
                continue;
            }

            c = holder.lockCanvas();
            if(System.currentTimeMillis() > 10000 && crabCount == 0)
            {
                dudeList.get(1).setX(0);
                dudeList.get(1).setY((float)(Math.random() * (c.getHeight() - 100)));
                crabCount++;
            }
            c.drawARGB(255, 150, 150, 10);
            moveCharacter(turtle);
            for(Character ch : dudeList)
            {
                moveCharacter(ch);
                if(Math.sqrt(Math.pow(turtle.getX() - ch.getX(), 2) + Math.pow(turtle.getX() - ch.getX(), 2)) < turtle.getSize() + ch.getSize())
                {
                    isItOK = false;
                }
                if(ch.getX() > c.getWidth() || ch.getX() > c.getHeight())
                {
                    ch.setX(0);
                    ch.setY((float) (Math.random() * (c.getHeight() - 100)));
                }
            }
            draw(c);
            holder.unlockCanvasAndPost(c);
        }
    }

    public void draw(Canvas canvas){

        //draws the character
        for(Character ch : dudeList) {
            c.drawBitmap(ch.getImage(),ch.getX() - (ch.getImage().getWidth()/2), ch.getY() - (ch.getImage().getHeight()/2), null);
        }
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