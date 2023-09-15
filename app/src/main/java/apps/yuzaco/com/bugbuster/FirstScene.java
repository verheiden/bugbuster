package apps.yuzaco.com.bugbuster;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import static apps.yuzaco.com.bugbuster.DrawTouch.SetDisplaySize;

import apps.yuzaco.com.bugbuster.R;


/**
 * Created by jung on 11/9/16.
 */

public class FirstScene extends Screen {
    Settings mPreferences;
    private static final String TAG = "First";

    ProgressBar mProgressBar = null;
    Button mPlayButton = null;
    DisplaySize mDisplay;
    List<DiagItem> mDiagInfoArray = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        DiagItem startArt;
        try {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            WindowManager wm = (WindowManager) this.getSystemService(WINDOW_SERVICE);
            ReadImages.ColorInit();
            SetDisplaySize();
            ReadImages.readWingsFromResource();
            mDiagInfoArray = ReadImages.readImagesFromResource();
            SetInfoTable(mDiagInfoArray);
            startArt = GetCurrentArt();
            showFirstScene(startArt, true);
            mFirstView.enableButton();
        } catch (Exception e) {
            Log.i(TAG, "OnCreate : Exception Occurred " + e);

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
         super.onPause();
    }
    public void showFirstScene(DiagItem startArt, boolean startMsg)
    {
        try {
            setContentView(R.layout.startimage);
            SetDisplaySize();
            if (mFirstView == null) {
                mFirstView = (SamuraiTouch) findViewById(R.id.firstImage);
                gameView =  mFirstView;
            }
            mFirstView.setGameActivity(this);
            mFirstView.startingScene(startArt, startMsg );


        } catch (Exception e) {
            Log.i(TAG, " while overlayinbg into screen size image" + e);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (mFirstView != null)
            mFirstView.haltGame();
        super.onBackPressed();
    }

    class BitmapTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected void onPreExecute()
        {
            if ( mProgressBar != null )
            {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            if ( mPlayButton != null )
                mPlayButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try
            {
                //To read images from the jpeg drawables,  uncomment
                // readImagesFromResouce function.
                //mDiagInfoArray = ReadImages.readImagesFromResource();

               mDiagInfoArray = ReadImages.readImagesFromFile();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result)
        {
            if ( mDiagInfoArray != null ) {

                SetInfoTable(mDiagInfoArray);
                if (mProgressBar != null) {

                    mProgressBar.setVisibility(View.GONE);

                }
                if (mFirstView != null) {
                    mFirstView.enableButton();
                }
                /* uncomment only when to generate the background art data file */
                BitmapWriteTask writeTask = new BitmapWriteTask(mDiagInfoArray);
                writeTask.run();
            }
        }
    }
}
