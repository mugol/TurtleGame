package coolstudios.turtlegame;

import android.content.Context;
import android.graphics.Bitmap;
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
    Bitmap ball;
    Canvas c;
    float x, y, desX, desY;
    final float speed = 10;

    public OurView(Context context){
        super(context);
        holder = getHolder();
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.turtle);
        x = 0;
        y = 0;
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
        float distanceX = desX - x, distanceY = desY - y;
        float angle = (float) Math.atan2(distanceY, distanceX);
        if(Math.abs(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)) > 0.0000001)
        {
            x = x + ((float)Math.cos(angle) * speed);
            y = y + ((float)Math.sin(angle) * speed);
        }
        c.drawBitmap(ball, x - (ball.getWidth()/2), y - (ball.getHeight()/2), null);

    }

    public boolean onTouch(View view, MotionEvent me){

        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }



        switch (me.getAction()){
            case MotionEvent.ACTION_DOWN:
                desX = me.getX();
                desY = me.getY();
                break;
            case MotionEvent.ACTION_UP:
                desX = me.getX();
                desY = me.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                desX = me.getX();
                desY = me.getY();
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