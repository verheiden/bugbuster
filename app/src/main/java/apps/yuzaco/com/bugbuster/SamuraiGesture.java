package apps.yuzaco.com.bugbuster;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by jung on 9/23/15.
 */
public class SamuraiGesture  extends GestureDetector.SimpleOnGestureListener {
    DrawTouch view;
    Context uiContext;
    static final String TAG = "Moma";

    public SamuraiGesture(Context ui, DrawTouch v) {
        view = v;
        uiContext = ui;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e )
    {
       // Log.i(TAG, "oneTapUp x : " + (int)e.getX() + "y:" +  (int)e.getY() );
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e1) {
        Log.i(TAG, "oneTapUpConfirmed x : " + (int)e1.getX() + "y:" +  (int)e1.getY() );
        try {
            view.handleOneTap((int)e1.getX(), (int)e1.getY());
            return true;
        } catch (Exception ex) {
            Log.i(TAG, "onTap Exception " + ex);
        }
        return true;
    }

   @Override
   public boolean onDoubleTap(MotionEvent e)
   {
       Log.i(TAG, "oneDoubleTap x : " + (int)e.getX() + "y:" +  (int)e.getY() );
       /*  jhv debug
      try
      {
          view.handleTwoTap((int)e.getX(), (int)e.getY());
      }
      catch (Exception error)
      {
          Log.i(TAG, " onDoubleTap " +e );
      }*/
      return true;
   }
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

}