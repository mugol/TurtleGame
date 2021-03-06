package coolstudios.turtlegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class OurView extends SurfaceView implements Runnable, View.OnTouchListener {

    Thread t = null;
    SurfaceHolder holder;
    boolean isItOK = false;
    Canvas c;
    Character crab;
    Character turtle;
    Character bird;
    Character bomb;
    float temp;
    long follow, countdown, explode, points;
    Paint ptext;


    public OurView(Context context){
        super(context);
        holder = getHolder();


        //first spawn for the characters
        turtle = new Character(300, 500, 300, 500, 10, 50, BitmapFactory.decodeResource(getResources(), R.drawable.turtle));
        crab = new Character(0, 150, 5000, 150, 7, 40, BitmapFactory.decodeResource(getResources(), R.drawable.crab));
        bird = new Character(700, 1000, turtle.getX(), turtle.getY(), 2, 40, BitmapFactory.decodeResource(getResources(), R.drawable.bird));
        bomb = new Character(500, 700, 500, 700, 0, 0, BitmapFactory.decodeResource(getResources(), R.drawable.bomb));
        countdown = System.currentTimeMillis();
        follow = System.currentTimeMillis();
        points = System.currentTimeMillis();
        ptext = new Paint();
        ptext.setColor(Color.BLACK);
        ptext.setTextSize(42);

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
            //birds needs turtle's location constantly
            bird.move(turtle.getX(),turtle.getY());
            moveCharacter(bird);


            //checkCollision returns true if hit, and isItOK needs to be false to stop
            if((checkCollision(turtle, crab) || checkCollision(turtle, bird)) || (checkCollision(turtle,bomb)) )
            {
                isItOK = false;
            }

            //Respawns the crab on the other side of the map -----------------------------
            if((crab.getX() > c.getWidth() || crab.getX() > c.getHeight()) || ((crab.getX() < 0 || crab.getY() < 0)))
            {
                crab.setX(1);
                temp = (float) (Math.random() * (c.getHeight() - 100));
                crab.setY(temp);
                crab.move(5000, temp);
            }
            //--------------------------------------------------------------------------

            //handles bird spawning ---------------------------------
            if(System.currentTimeMillis() - follow > 10000) {
                bird.setX(0);
                bird.setY((float) (Math.random() * (c.getHeight() - 100)));
                follow = System.currentTimeMillis();
            }
            //--------------------------------------------------

            //handles bomb stuff --------------------------------------
            if (bomb.isAlive() && System.currentTimeMillis() - countdown > 3000) {
                explode = System.currentTimeMillis();
                bomb.setSize(100);
                bomb.setAlive(false);
                bomb.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.explode));
            }

            if(!bomb.isAlive() && System.currentTimeMillis() - explode > 1000)//resets bomb
            {
                bomb.setAlive(true);
                bomb.setSize(0);
                bomb.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.bomb));
                bomb.setX((float)(Math.random() * (c.getWidth()-bomb.getImage().getWidth())));
                bomb.setY((float)(Math.random() * (c.getHeight()-bomb.getImage().getHeight())));

                countdown = System.currentTimeMillis();
                explode = 0;
            }
            //------------------------------------------------------------

            draw(c);
            holder.unlockCanvasAndPost(c);
        }
    }

    public void draw(Canvas canvas){

        //draws the character
        c.drawBitmap(crab.getImage(), crab.getX() - (crab.getImage().getWidth()/2), crab.getY() - (crab.getImage().getHeight()/2), null);
        c.drawBitmap(turtle.getImage(), turtle.getX() - (turtle.getImage().getWidth()/2), turtle.getY() - (turtle.getImage().getHeight()/2), null);
        c.drawBitmap(bird.getImage(), bird.getX() - (bird.getImage().getWidth()/2), bird.getY() - (bird.getImage().getHeight()/2), null);
        c.drawBitmap(bomb.getImage(), bomb.getX() - (bomb.getImage().getWidth()/2), bomb.getY() - (bomb.getImage().getHeight()/2), null);

        //draws text for points (just a timer)
        c.drawText("POINTS: " + (System.currentTimeMillis() - points), 250, 1075, ptext);

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

    //hit detection
    public boolean checkCollision(Character firstChar, Character secondChar)
    {
        if(Math.sqrt(Math.pow(firstChar.getX() - secondChar.getX(),2) + Math.pow(firstChar.getY() - secondChar.getY(),2)) < firstChar.getSize() + secondChar.getSize())
        {
            return true;
        }
        else
        {
            return false;
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