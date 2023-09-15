package apps.yuzaco.com.bugbuster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static apps.yuzaco.com.bugbuster.MozartApp.SavePreferences;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by jung on 12/19/15.
 */
public class SamuraiTouch  extends DrawTouch implements View.OnTouchListener
{
    public SamuraiTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            mContext = (AppCompatActivity) context;
            paint = new Paint();
            setOnTouchListener(this);
            mEnableButton = false;
        } catch (Exception e) {
            Log.i(TAG, "DrawTocu : " + e);
        }
    }
    void enableButton()
    {
        mEnableButton = true;
    }

    @Override
    protected void initCacoonNumbers()
    {
        DiagItem item;
        try {
            super.initCacoonNumbers();
            item = (DiagItem) Screen.SDiagInfoArray.get(mArtIndex);
            mBackGroundImage = new BitmapDrawable(getResources(), item.getImage());
            mBackgroundArt = Bitmap.createScaledBitmap(item.getImage(), SDisplayWidth, SDisplayHeight, true);
            setBackground(mBackGroundImage);
            if ( musicP != null )
            {
                musicP.stop();
            }
            musicP = new MusicPlayer(gameActivity, item.getMusic(), musicOn, MUSIC_VOLUME_LEVEL);
            musicP.start();
            mArtIndex = incrementArtIndex(mArtIndex);
            SavePreferences("ArtIndex", mArtIndex);
        }
        catch ( Exception e)
        {
            Log.i(TAG, "cleanUpRainbowSet : " + e);
        }
    }
    @Override
    public void cleanGame()
    {
        try {
            if (gameTimer != null) {
                gameTimer.stop();
                gameTimer = null;
            }
            if ( musicP != null)
                musicP.halt();
            mNumMissed = 0;
            mTotalRainbows = 0;
            deleteAllFlys();
        } catch (Exception e) {
            Log.i(TAG, "cleanGame : " + e);
        }
    }

    public void offStartMsg()
    {
        mDrawStartMsg = false;
        ProgressTimer mTimer = new ProgressTimer(500, 250, this);
        mTimer.start();
    }
    public void enableStartMsg()
    {
        mDrawStartMsg = true;
    }

    class ProgressTimer extends CountDownTimer
    {
        private SamuraiTouch view;

        long totalTime;

        public ProgressTimer(long millisInFuture, long countDownInterval,  SamuraiTouch v ) {
            super(millisInFuture, countDownInterval);
            totalTime = millisInFuture;
            view = v;
        }

        @Override
        public void onFinish()
        {
            super.cancel();
            view.enableStartMsg();
            view.invalidate();

        }

        @Override
        public void onTick(long millieLeft)
        {

        }

    }
}