package com.example.kristinmurray.androidgame2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {


    private GameThread gameThread;

    private ChibiCharacter[] heartArr = new ChibiCharacter[1];

    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
        for(ChibiCharacter c : heartArr) {
            c.update();
        }
    }

    public ChibiCharacter currentlyHeld = null;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x=  (int)event.getX();
            int y = (int)event.getY();

            int movingVectorX =x-  this.heartArr.getX() ;
            int movingVectorY =y-  this.heartArr.getY() ;

            this.heartArr.setMovingVector(movingVectorX,movingVectorY);
            return true;
        }
        */
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            ChibiCharacter grabHeart = null;
            Log.v("heart","down");
            for(ChibiCharacter c : heartArr){
                if(inBounds(c, event)){
                    grabHeart = c;
                }
            }
            if(grabHeart != null) {
                currentlyHeld = grabHeart;
                grabHeart.setX((int) event.getX() - 16);
                grabHeart.setY((int) event.getY() - 16);
                grabHeart.setMovingVector(0,0);
            }
        }else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                currentlyHeld = null;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                Log.v("heart", "moving");
                if (currentlyHeld != null) {
                    currentlyHeld.setX((int) event.getX() - 16);
                    currentlyHeld.setY((int) event.getY() - 16);
                }
            }
        }
        return true;
    }

    public boolean inBounds(ChibiCharacter c, MotionEvent event){
        int X = (int)event.getX();
        int Y = (int)event.getY();
        return c.getX() > X-50 && c.getX() < X+50 && c.getY() > Y-50 && c.getY() < Y+50;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        for(ChibiCharacter c : heartArr) {
            c.draw(canvas);
        }
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        this.heartArr[0] = new ChibiCharacter(this,chibiBitmap1,100,50);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}