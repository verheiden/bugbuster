package apps.yuzaco.com.bugbuster;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by jung on 10/15/15.
 */
class GameTimer extends CountDownTimer
{
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    DrawTouch gameEngine;
    int count;
    long timeLeft;

    public GameTimer(long millisInFuture, long countDownInterval, DrawTouch game) {
        super(millisInFuture, countDownInterval);
        gameEngine = game;
    }


    @Override
    public void  onTick(long  millisLeft){
        timeLeft = millisLeft;
        try {
            gameEngine.gameTimerInterval(millisLeft);
        }
        catch ( Exception e)
        {
            Log.i( DrawTouch.TAG, "GameTimer:onTick : " + e );
        }
    }
    public void stop()
    {
        gameEngine = null;
        cancel();
    }

    @Override
    public void onFinish()
    {
        try {
            if ( gameEngine != null )
                gameEngine.endGame();
        }
        catch (Exception e)
        {
            Log.i(DrawTouch.TAG, "GameTimer.onFinish : " + e ) ;
        }
    }

}
