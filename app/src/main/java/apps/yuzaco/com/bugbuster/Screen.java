package apps.yuzaco.com.bugbuster;

import static apps.yuzaco.com.bugbuster.DrawTouch.SetDisplaySize;
import static apps.yuzaco.com.bugbuster.MozartApp.GetAppContext;
import static apps.yuzaco.com.bugbuster.MozartApp.GetSavedPreferences;
import static apps.yuzaco.com.bugbuster.MozartApp.SavePreferences;

//import android.app.WallpaperManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import apps.yuzaco.com.bugbuster.R;


public class Screen extends AppCompatActivity implements OnItemClickListener {
    /**
     * Called when the activity is first created.
     */
    public static final String TAG = "Moma";
    private ListView mListView;
    private DiagAdapter mAdapter = null;
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FORESLASH = "/";
    static final int THUMB_HEIGHT = 75;
    static final int THUMB_WIDTH = 75;
    private boolean gameHalt = false;
    protected DrawTouch gameView = null;
    protected static List<DiagItem> SDiagInfoArray = null;
    SamuraiTouch mFirstView = null;
    //static boolean  mSnapShut = false;
    void showGrid()
    {
        int textColor;
        try {
            setContentView(R.layout.gridalbum);
            GridView gridview = (GridView) findViewById(R.id.flowergrid);
            gridview.setAdapter(new ImageAdapter());
            textColor = ContextCompat.getColor(this, R.color.color_white);
            androidx.appcompat.app.ActionBar  actionBar = getSupportActionBar();
            CharSequence myTitleText = "" + getString(R.string.gridHeader);
            Spannable text = new SpannableString(myTitleText);
            text.setSpan(new ForegroundColorSpan(textColor), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            actionBar.setTitle(text);
            if ( mAdapter == null )
                mAdapter = new DiagAdapter(this, R.layout.infoitem, SDiagInfoArray);

            gridview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        CharSequence myTitleText = getString(R.string.header);
                        int textColor = ContextCompat.getColor(GetAppContext(), R.color.color_white);
                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(GetAppContext(), R.color.brown3)));
                        Spannable text = new SpannableString(myTitleText);
                        text.setSpan(new ForegroundColorSpan(textColor), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        actionBar.setTitle(text);
                        setContentView(R.layout.main);

                        mListView = (ListView) findViewById(R.id.list);
                        mListView.setAdapter(mAdapter);
                        mListView.setOnItemClickListener(new OnItemClickListener(){
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                view.setSelected(true);
                                if ( gameView != null )
                                    gameView.haltGame();
                                SavePreferences("ArtIndex", position);
                                firstScene();
                            } catch (Exception e) {
                                Log.i(TAG, "Not getting the definition resource." + e);
                            }
                        } }
                        );

                        mListView.setSelector(R.drawable.bg_key);
                        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                        mListView.setSelectionFromTop(position, 0);
                    } catch (Exception e) {
                        Log.i(TAG, "Crash in the onGridItem" + e);
                    }
                }
            });
            gridview.setSelector(R.drawable.bg_key);
            gridview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        } catch (Exception e) {
            Log.i(TAG, "setup : " + e);
        }
    }


    static public void  SetInfoTable(List<DiagItem> array)
    {
        SDiagInfoArray = array;
    }

    static public List<DiagItem> GetInfoTable()
    { return SDiagInfoArray;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DiagItem paintSelected;

        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_overflow:
                return true;

            case R.id.musicOn:
                if (item.isChecked()) {
                    item.setChecked(false);
                    changeMusicSetting(false);
                    SavePreferences("MusicOn", 1 );
                } else {
                    item.setChecked(true);
                    changeMusicSetting(true);
                    SavePreferences("MusicOn", 0);
                }
                if ((gameView != null) && gameHalt) {
                    gameView.restart();
                    gameHalt = false;
                }
                return true;

            case R.id.soundOn :
                if (item.isChecked()) {
                    item.setChecked(false);
                    changeSoundSetting(false);
                    SavePreferences("SoundOn", 1);
                } else {
                    item.setChecked(true);
                    changeSoundSetting(true);
                    SavePreferences("SoundOn",0);
                }
                if ((gameView != null) && gameHalt) {
                    gameView.restart();
                    gameHalt = false;
                }
                return true;

            case R.id.resetOption:
                SavePreferences("Level", 1);
                if ((gameView != null) ) {
                    gameView.cleanGame();
                }
                firstScene();
                return true;

            default:
                if ((gameView != null) && gameHalt) {
                    gameHalt = false;
                    gameView.restart();
                }
                return super.onOptionsItemSelected(item);
        }
    }

    private void  shareBitmap()
    {
        try
        {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            Bitmap image = gameView.snapShot();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), image, "Title", null);
            Uri imageUri = Uri.parse(path);
            image.compress(Bitmap.CompressFormat.JPEG,  100, outStream);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Select Image Share"));
        }
        catch( Exception e){
            Log.i(TAG, "saveBitmap Exception" + e );
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            view.setSelected(true);
            if ( gameView != null )
                gameView.haltGame();
            SavePreferences("ArtIndex", position);
            firstScene();
        } catch (Exception e) {
            Log.i(TAG, "Not getting the definition resource." + e);
        }
    }

    void changeSoundSetting(boolean onFlag)
    {
        if ( gameView != null ){
            gameView.sound(onFlag);
        }
    }
    void changeMusicSetting(boolean onFlag)
    {
        if ( gameView != null )
        {
            gameView.music(onFlag);
        }
    }
    @Override
    public void onBackPressed(){

        if (gameView != null)
            gameView.haltGame();
        SDiagInfoArray = null;
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        SetDisplaySize();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        try {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.actions, menu);

            if ( GetSavedPreferences("MusicOn") == 0 )
            {
                menu.findItem(R.id.musicOn).setChecked(true);
            } else {
                menu.findItem(R.id.musicOn).setChecked(false);
            }
            if (GetSavedPreferences("SoundOn") == 0 ) {
                menu.findItem(R.id.soundOn).setChecked(true);
            } else {
                menu.findItem(R.id.soundOn).setChecked(false);
            }
        } catch (Exception e) {
            Log.i(TAG, "Creating Options " + e);
        }
        return true;
    }

    static public Bitmap GetThumbImage(int index)
    {
        return( Bitmap.createScaledBitmap(SDiagInfoArray.get(index).getImage(),
            THUMB_WIDTH, THUMB_HEIGHT, true));
    }
    static public int GetThumbCount(){
        return( SDiagInfoArray.size());
    }

    static DiagItem GetCurrentArt()
    {
        int artIndex = GetSavedPreferences("ArtIndex");
        return( SDiagInfoArray.get(artIndex));
    }


    @Override
    protected void onSaveInstanceState(Bundle out)
    {
        try {
            super.onSaveInstanceState(out);

        } catch ( Exception e){
            Log.i(TAG, "onSaveInstanceState " + e );
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_ACTION_BAR);
            androidx.appcompat.app.ActionBar ab = getSupportActionBar();
            ab.setDisplayShowHomeEnabled(true);
           ab.setIcon(R.drawable.ic_launcher);


        } catch (Exception e) {
            Log.i(TAG, "OnCreate : Exception Occurred " + e);

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (gameView != null) {
            gameView.restart();
        }
        else
        {
            try {
                int gameIndex = GetSavedPreferences("ArtIndex");
                setContentView(R.layout.photoimage);

                gameView = (DrawTouch) findViewById(R.id.myImage);
                gameView.prepareNewGame(gameIndex, this);
                gameView.startGame();
            } catch (Exception e) {
                Log.i(TAG, " while overlayinbg into screen size image" + e);
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (gameView != null)
            gameView.pause();
    }

    /**
     * Method called on UI thread with loaded bitmap.

    private void mySetWallpaper(final Bitmap image)
    {

        if (image == null)
            return;
        mSnapShut = true;
        Thread r = new Thread() {
            private Bitmap art = image;

            @Override
            public void run()
            {
                try {
                    WallpaperManager wpm = WallpaperManager.getInstance(getApplicationContext());
                    wpm.clear();
                    wpm.setBitmap(art);
                    mSnapShut = true;
                } catch (Exception e) {
                    Log.i(TAG, " while overlayinbg into screen size wall paper exception" + e);
                }
            }
        };
        r.start();
    }
*/
    public DiagItem startNewArt(int artIndex)
    {
        try {
            return((DiagItem) SDiagInfoArray.get(artIndex));
        } catch (Exception e) {
            Log.i(TAG, " while overlayinbg into screen size image" + e);
            return null;
        }
    }

    public int  incrementArtIndex(int artIndex)
    {
        try {
            artIndex = ++artIndex % SDiagInfoArray.size();
             return(artIndex);
        } catch (Exception e) {
            Log.i(TAG, " while overlayinbg into screen size image" + e);
            return 0;
        }
    }

    public void firstScene()
    {
        Intent intent = new Intent(GetAppContext(), FirstScene.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
