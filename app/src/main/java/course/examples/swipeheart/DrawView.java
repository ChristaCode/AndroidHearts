package course.examples.swipeheart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawView extends View {
   public static ArrayList<ColorBall> colorballs = new ArrayList<ColorBall>(); // array that holds the balls
   private int balID = 0; // variable to know what ball is being dragged
    
    public DrawView(Context context) {
        super(context);
        setFocusable(true); //necessary for getting the touch events
        
        // setting the start point for the balls
        Point point1 = new Point();
        point1.x = 50;
        point1.y = 20;
        Point point2 = new Point();
        point2.x = 100;
        point2.y = 20;
        Point point3 = new Point();
        point3.x = 150;
        point3.y = 20;
        
                       
        // declare each ball with the ColorBall class
        colorballs.add(new ColorBall(context,R.drawable.heart, point1));
        colorballs.add(new ColorBall(context,R.drawable.heart, point2));
        colorballs.add(new ColorBall(context,R.drawable.heart, point3));
        System.out.print("HELLO1 ");

     /*   while(colorballs.size() > 0){
            System.out.print("HELLO2 "
            );

            for (ColorBall b: colorballs){
             //   b.updateBall();
            }
            SystemClock.sleep(1000);
        }
*/
    }
    
    // the method that draws the balls
    @Override protected void onDraw(Canvas canvas) {
        //canvas.drawColor(0xFFCCCCCC);     //if you want another background color       
        
    	//draw the balls on the canvas
    	for (ColorBall ball : colorballs) {
            canvas.drawBitmap(ball.getBitmap(),null,new Rect(ball.getX(), ball.getY(),ball.getX() + 100, ball.getY() + 100),null);
          }

    }
    
    // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (eventaction) {

            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
                balID = 0;
                for (ColorBall ball : colorballs) {
                    // check if inside the bounds of the ball (circle)
                    // get the center for the ball
                    int centerX = ball.getX() + 50;
                    int centerY = ball.getY() + 50;

                    // calculate the radius from the touch to the center of the ball
                    double radCircle = Math.sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y) * (centerY - Y)));

                    // if the radius is smaller then 23 (radius of a ball is 22), then it must be on the ball
                    if (radCircle < 50) {
                        balID = ball.getID();
                        break;
                    }

                    // check all the bounds of the ball (square)
                    //if (X > ball.getX() && X < ball.getX()+50 && Y > ball.getY() && Y < ball.getY()+50){
                    //	balID = ball.getID();
                    //	break;
                    //}
                }

                break;


            case MotionEvent.ACTION_MOVE:   // touch drag with the ball
                // move the balls the same as the finger
                if (balID > 0) {
                    colorballs.get(balID - 1).setX(X - 50);
                    colorballs.get(balID - 1).setY(Y - 50);
                }

                break;

            case MotionEvent.ACTION_UP:
                // touch drop - just do things here after dropping

                break;

        }

        // redraw the canvas
        invalidate(); 
        return true; 
	
    }
}
