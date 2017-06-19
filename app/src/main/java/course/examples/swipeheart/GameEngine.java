package course.examples.swipeheart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Responsible for screen painting.
 * */
public class GameEngine extends Thread
{
    SurfaceHolder _surfaceHolder;
    Paint _backgroundPaint;

    long _sleepTime;

    //Delay amount between screen refreshes
    final long  DELAY = 1000;

    boolean  _isOnRun;

    public GameEngine(Context context)
    {
     //   _surfaceHolder = surfaceHolder;

        //black painter below to clear the screen before the game is rendered
        _backgroundPaint = new Paint();
        _backgroundPaint.setARGB(255, 0, 0, 0);
        _isOnRun = true;
    }

    /**
     * This is the main nucleus of our program.
     * From here will be called all the method that are associated with the display in GameEngine object
     * */
    @Override
    public void run()
    {
        //Looping until the boolean is false
        while (_isOnRun)
        {
            //Updates the game objects buisiness logic
           // AppConstants.GetEngine().Update();

            //locking the canvas
            //Canvas canvas = _surfaceHolder.lockCanvas(null);
/*
            if (canvas != null)
            {
                //Clears the screen with black paint and draws object on the canvas
                synchronized (_surfaceHolder)
                {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), _backgroundPaint);
                    AppConstants.GetEngine().Draw(canvas);
                }

                //unlocking the Canvas
                _surfaceHolder.unlockCanvasAndPost(canvas);
            }
*/
            for (ColorBall ball : DrawView.colorballs) {
                ball.updateBall();
            }
            //delay time
            try
            {
                Thread.sleep(DELAY);
            }
            catch (InterruptedException ex)
            {
                //TODO: Log
            }
        }
    }

    /**
     * @return whether the thread is running
     * */
    public boolean IsRunning()
    {
        return _isOnRun;
    }
    /**
     * Sets the thread state, false = stoped, true = running
     **/
    public void SetIsRunning(boolean state)
    {
        _isOnRun = state;
    }
}