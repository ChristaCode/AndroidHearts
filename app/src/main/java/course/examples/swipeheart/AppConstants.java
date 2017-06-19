package course.examples.swipeheart;

/**
 * Created by Christa on 6/18/2017.
 */

import android.content.Context;
        import android.util.DisplayMetrics;
        import android.view.Display;
        import android.view.WindowManager;

public class AppConstants
{
    static GameEngine _engine;

    public static int SCREEN_WIDTH,
            SCREEN_HEIGHT;

    /**
     * Initiates the applciation constants
     * */
    public static void Initialization(Context context)
    {

        SetScreenSize(context);
        _engine = new GameEngine(context);
        _engine.start();
    }


    /**
     * Sets screen size constants accordingly to device screen size
     * */
    private static void SetScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        AppConstants.SCREEN_WIDTH = width;
        AppConstants.SCREEN_HEIGHT = height;
    }

    /**
     * @return GameEngine instance
     * */
    public static GameEngine GetEngine()
    {
        return _engine;
    }


    /**
     * Stops the given thread
     * @param thread
     * 			thread to stop
     * */
    public static void StopThread(Thread thread)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

}