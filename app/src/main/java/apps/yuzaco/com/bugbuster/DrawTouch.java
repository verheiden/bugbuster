package apps.yuzaco.com.bugbuster;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.os.Build;
import android.view.WindowManager;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;
import static apps.yuzaco.com.bugbuster.MozartApp.GetAppContext;
import static apps.yuzaco.com.bugbuster.MozartApp.GetSavedPreferences;
import static apps.yuzaco.com.bugbuster.MozartApp.SavePreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import apps.yuzaco.com.bugbuster.R;


/**
 * Created by jung on 7/27/15.
 */
public class DrawTouch extends View implements View.OnTouchListener {

    public enum flyMode {CACOON, BORN, YOUTH, YOUTH2, YOUTH3, YOUTH4, ADULTHOOD, IMMORTAL, MONARCH, RAINBOW, WHITEFLY }

    private boolean mInGame = false;
    public enum wellMode { STATIC, FLICKERING,  PREVIOUS, PRE_FIXED, FIXED, MISSED}

    Canvas canvas = null;
    Paint paint;
    public static  int SDisplayWidth;
    public static  int SDisplayHeight;
    public static  int BasicXMove;
    public static int BasicYMove;

    public static  int SXMove;
    public static int SYMove;
    protected Screen gameActivity = null;
    public  static int WING_WIDTH ;
    public  static int WING_HEIGHT ;
    public  static int WING_WIDTH2;
    public  static int WING_HEIGHT2;
    public  static int SMALL_WING_WIDTH ;
    public  static int SMALL_WING_HEIGHT;
    public  static int SMALL_WING_WIDTH2;
    public  static int SMALL_WING_HEIGHT2 ;
    
    public static int SMALL_BODY_WIDTH ;
    public static int SMALL_BODY_HEIGHT ;
    public static int BODY_HEIGHT;
    public static int BODY_WIDTH = 22;
    public  static int RAINBOW_WIDTH ;
    public  static int RAINBOW_HEIGHT ;
    public  static int RAINBOW_WING_WIDTH2 ;
    public  static int RAINBOW_WING_HEIGHT2 ;

    public static int RAINBOW_BODY_WIDTH ;
    public static int RAINBOW_BODY_HEIGHT;

    public static int TARGET_WIDTH;
    public  static int TARGET_HEIGHT ;
    public  static int TARGET_WING_WIDTH2 ;
    public  static int TARGET_WING_HEIGHT2 ;

    public static int TARGET_BODY_WIDTH ;
    public static int TARGET_BODY_HEIGHT;
    public static  int RAINBOW_RANGE  = 360;
    public final static int LIFE_TIME = 100000000;

    public final static int CYCLE_OUT_DURATION = -50;
    public final static int SHOW_WIN_POINT = 3;
    public final static int TICK_INTERVAL = 100;
    public static int GAME_RANGE = 50;
    public final static int TOTAL_MOVES = 20;
    public static   int MAX_GAME_RANGE = 80;

    public static int MSG_X ;
    public static int MSG_Y;

    public static int CYCLE_X;
    public static int CYCLE_Y;
    public static int CYCLE_TEXT_X;
    public static int CYCLE_TEXT_Y;
    public static int CYCLE_O_RIGHT;
    public static int CYCLE_O_BOTTOM;
    public final static int OUTER_WING_SIZE = 5;
    public final static int GAME_DURATION = 45000;
    public final static int GAME_INTERVAL = 500 ;
    public final static int MAX_GAME_TIME = 300000;
    public final static int LEVEL_INCREASE_TIME =  10000;
    public final static int GAME_END_DURATION = 200;
    public  static int IMMORTAL_X_BASE=600;
    public  static int IMMORTAL_Y_BASE ;

    public  final static int MAX_CYCLE_TIMEOUT  = 3;

    public static int mMaxCycleTimeOut;
    public  static int RAINBOW_X_BASE;
    public  static int RAINBOW_Y_BASE ;
    public final static int MAX_CACOON = 7;

    public final static int MAX_Y_MOVE = 120;

    public final static int MAX_X_MOVE = 100;
    public final static int FULL_RAINBOW_COLORS = 0x7f;
    public final static int RAINBOW_COLOR_NUMBER = 7;
    public final static int STARTING_IMMORTALS = 7;
    public final static int MAX_TOTAL_MISS = 9 ;
    public final static int MIN_CYCLE_TIME = 7;
    public final static int MAX_CYCLE_TIME = 17;
    public static final String TAG = "Moma";
    protected RainbowColor mRainbowColor = null;
    protected static final float    MUSIC_VOLUME_LEVEL = (float) 0.6;
    protected static final float    SOUND_VOLUME_LEVEL = (float) 0.2;
    protected int mNumMoves ;
    protected int mMaxMiss;

    protected int mNumCycleTimeOut ;
    protected int mStartMsgToggle = 0;
    protected List<Butterfly> mActiveFlys = null;
    protected List<Butterfly> mRainbowFlys = null;

    protected MusicPlayer musicP;
    protected boolean musicOn = true;
    protected boolean soundOn = true;
    protected Bitmap mBackgroundArt;

    protected Bitmap mBurst;
    protected Bitmap mSmallBurst;
    protected BitmapDrawable mBackGroundImage = null;
    protected int mNextTargetColor = 0;

    protected Butterfly mFly = null;
    protected int mWingColor;
    protected int mBodyColor;
    protected int mWing2Color;
    protected int mTargetColor;
    protected int mTargetRainbows;
    protected Bitmap mFace ;
    protected Bitmap mGoodFace;
    protected GameTimer gameTimer = null;
    protected int mSecondLeft;

    protected int mCycleLeft;

    protected boolean titleColorToggle = true;
    protected boolean mGameHalt = false;
    protected int mGameEnd;
    protected int mTotalRainbows = 0;

    protected int mBackGroundColor = 0;
    protected Bitmap mEvilFace = null;
    protected int mLevel = 1;
    protected int mArtIndex = 0;
    protected boolean mDrawStartMsg = false;
    protected boolean mEnableButton = true;
    protected PlayButton mPlayButton = null;
    protected AppCompatActivity mContext = null;

    protected DiagItem mStartArt= null;
    protected int mNumMissed;
    protected int mIndex = 0;


    public DrawTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            mContext = (AppCompatActivity) context;
            paint = new Paint();
            setWillNotDraw(false);
            setOnTouchListener(this);
            mActiveFlys = new LinkedList<>();
            mRainbowFlys = new LinkedList<>();
            mArtIndex = GetSavedPreferences("ArtIndex");
            mLevel = GetSavedPreferences("Level");
            mGameHalt = false;
            if ( GetSavedPreferences("MusicOn") == 0 )
                musicOn = true;
            else
                musicOn = false;
            if ( GetSavedPreferences("SoundOn") == 0)
                soundOn = true;
            else
                soundOn = false;

            if ( mLevel <= 0 ) {
                mLevel = 1;
                SavePreferences("Level", 1);
            }
            mGoodFace = ReadImages.LoadBitmap(R.drawable.face1);
            mEvilFace = ReadImages.LoadBitmap(R.drawable.evilface);
            mBurst = ReadImages.LoadBitmap(R.drawable.burst);
            mSmallBurst = ReadImages.LoadBitmap(R.drawable.smallburst);
            mRainbowColor = new RainbowColor(ContextCompat.getColor(mContext, R.color.black), ContextCompat.getColor(mContext, R.color.silver),
                ContextCompat.getColor(mContext, R.color.disney_gray));

        } catch (Exception e) {
            Log.i(TAG, "DrawTouch : " + e);
        }
    }

    protected void  initCacoonNumbers()
    {
        try {

            int numCacoons = (Math.min((mLevel - mTotalRainbows), MAX_CACOON / 7)) * 7;
            int cacoonCreated = 0;
            Butterfly bFly;
            int jump = 65;
            int yJump = 77;
            double k  = Math.random();
            mCycleLeft = calculateCycleTime();
            mNextTargetColor = 0;
            boolean firstTarget = true;
            while (++cacoonCreated <= numCacoons) {
                k = Math.random()*100;
                bFly = createCacoon(k *jump, k * yJump, flyMode.ADULTHOOD);
                ;
                bFly.good = false;
                if (firstTarget && (bFly.colorOrder() == mNextTargetColor)) {
                    bFly.makeTargetSize();
                    bFly.setModeCount(0);
                    bFly.target = true;
                    bFly.setCondition(wellMode.FLICKERING);
                    firstTarget = false;
                }
                jump += 65;
                yJump += 77;
            }
        }
        catch(Exception e) {
            Log.i(TAG, "initCacoonNumbers : " + e);
        }
    }
    protected int calculateCycleTime()
    {
        return ( Math.max( MIN_CYCLE_TIME,  MAX_CYCLE_TIME - mLevel/5  )*1000 ) ;
    }
    public boolean onTouch(View v, MotionEvent event) {
        if ((mGameEnd > 1) || (mPlayButton != null)) {
            buttonOnTouch(v, event);
        } else if (mGameHalt) {
            restart();
            mGameHalt = false;
            return true;
        }
        gameTouch(v, event);
        return true;
    }

    protected Bitmap getBackgroundArt()
    {
        return mBackgroundArt;
    }
    private void gameTouch(View v, MotionEvent event)
    {
        {
            final int action = event.getActionMasked();

            if ((action == MotionEvent.ACTION_DOWN) ||
                ( action == MotionEvent.ACTION_POINTER_DOWN )) {
                int fingerCount = event.getPointerCount();
                for (int i = 0; i < fingerCount; i++) {
                    handleOneTap((int) event.getX(i), (int) event.getY(i));
                }
                invalidate();
            }
            else if ( action == MotionEvent.ACTION_UP )
            {
                invalidate();
            }
        }
    }

    private void buttonOnTouch(View v, MotionEvent event)
    {
        int action = event.getAction();

        if ( (action == MotionEvent.ACTION_UP) || mPlayButton == null ){
            invalidate();
        } else if (action == MotionEvent.ACTION_DOWN) {
            try {
                if (mPlayButton.pushed((int) event.getX(), (int) event.getY())) {
                    deleteAllFlys();
                    musicP.stop();
                    mGameEnd = 0;
                    prepareNewGame(mArtIndex,  gameActivity);
                    startGame();
                }
            } catch (Exception e) {
                Log.i(TAG, "buttonOnTouch " + e);
            }
        }
    }
    public static  void SetDisplaySize()
    {
        try {

            DisplayMetrics metrics;
            WindowManager wm = (WindowManager) GetAppContext().getSystemService(WINDOW_SERVICE);

            Display display = wm.getDefaultDisplay();
            if ( Build.VERSION.SDK_INT >= 15) {
                metrics = new DisplayMetrics();
                display.getRealMetrics(metrics);
                SDisplayWidth = metrics.widthPixels;
                // get the height without the softkey areas.
                wm.getDefaultDisplay().getMetrics(metrics);
                SDisplayHeight = metrics.heightPixels;
            } else {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                SDisplayWidth = (Integer) mGetRawW.invoke(display);
                SDisplayHeight = (Integer) mGetRawH.invoke(display);
            }
            WING_WIDTH = (int) ( SDisplayWidth/9 );
            WING_HEIGHT = (int)  ( SDisplayHeight/28 );
            WING_WIDTH2 = (int) (WING_WIDTH*0.7);
            WING_HEIGHT2 = ( int ) (WING_HEIGHT*0.7);
            BODY_HEIGHT = (int) ( WING_HEIGHT *1.3);
            BODY_WIDTH =(int) ( WING_WIDTH/8);
            SMALL_WING_WIDTH = (int) (WING_WIDTH*0.8);
            SMALL_WING_HEIGHT = (int) (WING_HEIGHT*0.8);
            SMALL_WING_WIDTH2 = (int)( SMALL_WING_WIDTH*0.7);
            SMALL_WING_HEIGHT2 = (int)(SMALL_WING_HEIGHT*0.7);
            SMALL_BODY_HEIGHT = (int) ( BODY_HEIGHT*0.8);
            SMALL_BODY_WIDTH = (int) ( WING_WIDTH/8);

            RAINBOW_WIDTH = (int)( SDisplayWidth/8);
            RAINBOW_HEIGHT = SDisplayHeight/23;
            RAINBOW_WING_WIDTH2 = (int)( RAINBOW_WIDTH*0.7);
            RAINBOW_WING_HEIGHT2 = (int)(RAINBOW_HEIGHT*0.9);
            RAINBOW_BODY_HEIGHT = (int) ( RAINBOW_HEIGHT);
            RAINBOW_BODY_WIDTH = (int) ( RAINBOW_WIDTH/8);

            TARGET_WIDTH = (int) ( SDisplayWidth/7.6 );
            TARGET_HEIGHT = SDisplayHeight/23;
            TARGET_WING_WIDTH2 = (int)( TARGET_WIDTH*0.7);
            TARGET_WING_HEIGHT2 = (int)(TARGET_HEIGHT*0.9);
            TARGET_BODY_HEIGHT = (int) ( TARGET_HEIGHT);
            TARGET_BODY_WIDTH = (int) ( TARGET_WIDTH/8 );
            BasicXMove = (int) SDisplayWidth/58;
            BasicYMove = ( int ) SDisplayHeight/72;
            RAINBOW_RANGE = SDisplayWidth - 100;
            GAME_RANGE = SDisplayWidth/15;
            MAX_GAME_RANGE = SDisplayWidth/10;
            RAINBOW_X_BASE = SDisplayWidth/20;
            RAINBOW_Y_BASE = SDisplayHeight/8;
            IMMORTAL_X_BASE = 50;
            IMMORTAL_Y_BASE =(int)  ( SDisplayHeight*0.6 );

            MSG_X = SDisplayWidth/11;
            CYCLE_X = SDisplayWidth /12 ;
            CYCLE_O_RIGHT = CYCLE_X + (int)  ( TARGET_WIDTH *2.4);
            CYCLE_O_BOTTOM = CYCLE_Y + ( int )  ( TARGET_HEIGHT * 0.85);
            CYCLE_TEXT_X = CYCLE_X + (int) ( TARGET_WIDTH *0.9 );
            CYCLE_TEXT_Y = CYCLE_Y + (int) ( TARGET_HEIGHT * 0.7);
            MSG_Y = SDisplayHeight/12;
            CYCLE_Y = SDisplayHeight/45;
        } catch (Exception e) {
            Log.i(TAG, "getDisplaySize : " + e);
        }
    }
    private int calculateGameTime(int level )
    {
        return( Math.min((GAME_DURATION + (mLevel - 1 )*LEVEL_INCREASE_TIME), calculateCycleTime()*mLevel ));
    }

    public void prepareNewGame(int artNumber, Screen gamer) {
        DiagItem item;
        try {
            gameActivity = gamer;
            mDrawStartMsg =false;
            mArtIndex = artNumber;
            SavePreferences("ArtIndex", mArtIndex);
            mLevel = GetSavedPreferences("Level");
            SXMove =   Math.min( BasicXMove + mLevel, MAX_X_MOVE);
            SYMove =  Math.min( BasicYMove + (int) (mLevel), MAX_Y_MOVE);
            if  ( GetSavedPreferences("MusicOn") == 0 ) {
               musicOn = true;
            }else {
                musicOn = false;
            }
            if ( GetSavedPreferences("SoundOn") == 0 ){
                soundOn = true;
            }
            else
            {
                soundOn = false;
            }
            if ( gameTimer != null ) {
                gameTimer.stop();
                gameTimer = null;
            }
            item = (DiagItem) Screen.SDiagInfoArray.get(mArtIndex);
            mMaxCycleTimeOut = Math.min( MAX_CYCLE_TIMEOUT, ( mLevel/10 + 1));
            musicP = new MusicPlayer(gameActivity, item.getMusic(), musicOn, MUSIC_VOLUME_LEVEL);
            mTargetRainbows = mLevel;
            mMaxMiss = calculateMaxMiss(mLevel);
            mBackGroundImage = new BitmapDrawable(getResources(), item.getImage());
            mBackgroundArt = Bitmap.createScaledBitmap(item.getImage(), SDisplayWidth, SDisplayHeight, true);
            setBackground(mBackGroundImage);
            setWillNotDraw(false);
            mNumMoves = 0;
            mNumMissed = 0;
            invalidate();
        } catch (Exception e) {
            Log.i(TAG, "prepareNewGame :  " + e);
        }
    }
    public int calculateMaxMiss(int level){
        return ( Math.min( MAX_TOTAL_MISS,(int) (  mLevel *0.35 ) + 1)) ;
    }
    public void startGame()
    {
        try {
            mSecondLeft =(int) (  calculateGameTime(mLevel)/1000 );
            mGameHalt = false;
            mGameEnd = 0;
            mInGame = true;
            mTotalRainbows = 0;
            mPlayButton = null;
            mNumMissed = 0;
            mNumCycleTimeOut = 0;
            if (gameTimer != null)
                gameTimer.stop();
            gameTimer = new GameTimer(mSecondLeft*1000, GAME_INTERVAL, this);
            Butterfly.SetFlickeringCount(mLevel);
            ButterflyWings.Init();
            gameTimer.start();
            setBackground(mBackGroundImage);
            resetRainbowSet();
            musicP.start();
            invalidate();
        } catch (Exception e) {
            Log.i(TAG, " startGame " + e);
        }
    }

    public void touchButterfly(int x, int y) {

        ListIterator<Butterfly> listIterator = mActiveFlys.listIterator();
        try {
            while (listIterator.hasNext()) {

                Butterfly baloon = listIterator.next();

               if (baloon.mode() != flyMode.ADULTHOOD )
                  continue;
                    int xLeftRange;
                    int xRightRange;
                    int gameRange = Math.max(GAME_RANGE, MAX_GAME_RANGE - mLevel );
                    xLeftRange = baloon.xLocation()  - gameRange;
                    xRightRange = baloon.xLocation()  + gameRange;

                    gameRange = Math.min(GAME_RANGE, MAX_GAME_RANGE - mLevel );
                    int yTopRange = baloon.yLocation()  + gameRange;
                    int yBottomRange = baloon.yLocation() - gameRange;

                        if ((x >= xLeftRange) && (x <= xRightRange)) {
                            if ((y <= yTopRange) && (y >= yBottomRange)) {
                                if ( ((baloon.colorOrder() == mNextTargetColor) && (baloon.good == false )) ){
                                        playSound(R.raw.gotit);
                                        setBackgroundColor(baloon.color().getlLight());
                                        baloon.good = true;
                                        baloon.setCondition(wellMode.PREVIOUS);
                                        baloon.setModeCount(0);
                                        baloon.setTarget(false);
                                        moveTargetColor(baloon);
                                }
                                else {
                                    playSound(R.raw.slashing);
                                    setBackgroundColor(Color.BLACK);
                                    if (baloon.good() == false) {
                                        baloon.makeMissed();
                                        mNumMissed++;
                                        updateTime(mSecondLeft);
                                        baloon.setCondition(wellMode.MISSED);
                                        baloon.setModeCount(0);
                                    }
                                }
                                return;
                            }
                        }
                    }
        } catch (Exception e) {
            Log.i(TAG, "touch Butter fly Exception  " + e);

        }
    }

    public boolean makeNextStage(Butterfly baloon)
    {
        boolean returnValue = true;
        switch (baloon.mode()) {
            case CACOON:
                baloon.makeImmortal();
                break;

            case YOUTH:
                baloon.makeYouth2();
                break;
            case YOUTH2:
                baloon.makeYouth3();
                break;
            case YOUTH3:
                baloon.makeAdult();
                break;
            case ADULTHOOD:
                baloon.makeImmortal();
                break;

            case IMMORTAL:
                baloon.makeRainbow();
                break;

            default:
                returnValue = false;
        }

        return returnValue;
    }

    public void cleanGame()
    {
        try {
            if (gameTimer != null) {
                gameTimer.stop();
                gameTimer = null;
            }
            if (musicP  != null )
                musicP.halt();
            deleteAllFlys();
        } catch (Exception e) {
            Log.i(TAG, "cleanGame : " + e);
        }
    }
    public boolean gameMode()
    {
        return mInGame;
    }
    public void endGame()
    {
        if (gameTimer == null)
            return;
        mInGame = false;
        mGameEnd = GAME_END_DURATION;
        musicP.stop();
        if ( mTotalRainbows >= mTargetRainbows )
        {
            mLevel++;
            setBackgroundColor(ContextCompat.getColor(GetAppContext(),R.color.mattisse_blue1));
            playSound(R.raw.bigwin1);
        }
        else {
            setBackgroundColor(ContextCompat.getColor(GetAppContext(),R.color.ZenBlack));
        }

        SavePreferences("Level", mLevel);
        mArtIndex = incrementArtIndex(mArtIndex);
        SavePreferences("ArtIndex", mArtIndex);


        try {
            if (gameTimer != null) {
                gameTimer.stop();
                gameTimer = null;
            }
            playSound(R.raw.finale);

        } catch (Exception e) {
            Log.i(TAG, "endGame : " + e);
        }
        invalidate();
    }

    public void setGameActivity(FirstScene gamer)
    {
        gameActivity = gamer;
    }

    public int  incrementArtIndex(int artIndex)
    {
        try {
            artIndex++;
            artIndex = artIndex % Screen.SDiagInfoArray.size();
            return(artIndex);
        } catch (Exception e) {
            Log.i(TAG, " while overlayinbg into screen size image" + e);
            return 0;
        }
    }
    public Bitmap snapShot() {
       Bitmap image = null;
        try {
            this.setDrawingCacheEnabled(true);
            image = Bitmap.createBitmap(getDrawingCache());
            setDrawingCacheEnabled(false);
        } catch (Exception e) {
            Log.i(TAG, " snapShot dies " + e);
        }
        return (image);
    }
    public void moveTargetColor(Butterfly fly)
    {
        if ( (fly.colorOrder() ==  ( RAINBOW_COLOR_NUMBER - 1) )) {
            mActiveFlys.remove(fly);
            fly.makeRainbow();
            mRainbowFlys.add(fly);
            setBackgroundColor(fly.color().getlLight());
            playSound(R.raw.bigwin1);
            mTotalRainbows++;
            updateTime(mSecondLeft);
            if ( mRainbowFlys.size() >= 8 ){
                Butterfly tFly = mRainbowFlys.get(0);
                mRainbowFlys.remove(0);
                tFly.kill();
            }
        }

        ListIterator<Butterfly> li = mActiveFlys.listIterator();
        mNextTargetColor = ( mNextTargetColor + 1 )%RAINBOW_COLOR_NUMBER;
        while (li.hasNext()) {
            mFly = li.next();
            if ((mFly.colorOrder() == mNextTargetColor)&&(!mFly.good)) {
                mFly.setModeCount(0);
                mFly.makeTargetSize();
                mFly.setTarget(true);
                mFly.setCondition(wellMode.FLICKERING);
                break;
            }
        }
        invalidate();
    }

    protected void resetRainbowSet()
    {
        try {
            if ( mActiveFlys != null  && mActiveFlys.size() > 0 ) {
                ListIterator<Butterfly> listIterator = mActiveFlys.listIterator();
                while (listIterator.hasNext()) {
                    Butterfly timer = listIterator.next();
                    timer.kill();
                    listIterator.remove();
                }
            }
        }
        catch(Exception e){
            Log.i(TAG, "resetRainbowSet" + e );
        }
    }
    protected void drawCycleEnd()
    {

        CharSequence sMsg = "**" + getResources().getString(R.string.cycle)  +   " " + getResources().getString(R.string.timeOut) + "!!";

        drawMsg(sMsg, CYCLE_X, CYCLE_Y + RAINBOW_HEIGHT,
                ContextCompat.getColor(GetAppContext(), R.color.ZenWhite ), ContextCompat.getColor(GetAppContext(), R.color.almond), SDisplayWidth /13);
        return;
    }
    @Override
    public void onDraw(Canvas myCanvas) {

        try {
        canvas = myCanvas;
        if (mDrawStartMsg) {
            drawStartMsg();
        }

        if ( mCycleLeft < 0 ) {
            if ( mCycleLeft == CYCLE_OUT_DURATION ){
                playSound(R.raw.slashing);
                setBackgroundColor(Color.RED);
            }
            mCycleLeft++;
            if ( mCycleLeft == 0 ){
                resetRainbowSet();
            }
            drawCycleEnd();
            return;
        }
        mNumMoves = (++mNumMoves )%(( SDisplayWidth/3));

        if ( mGameEnd == 0 ) {
            mIndex = mActiveFlys.size();
            ListIterator<Butterfly> li = mActiveFlys.listIterator(mIndex);
            while (li.hasPrevious()) {
                mIndex--;
                mFly = li.previous();
                if ((mFly.condition() == wellMode.PRE_FIXED) || (mFly.condition() == wellMode.FIXED)) {
                    setLocation();
                }
                drawSpot();
            }

            li = mRainbowFlys.listIterator();
            while (li.hasNext()) {
                mFly = li.next();
                if ((mFly.condition() == wellMode.PRE_FIXED) || (mFly.condition() == wellMode.FIXED)) {
                    setLocation();
                }
                drawSpot();
            }
        }
        else if (mGameEnd > 1)
        {
                mGameEnd--;
                drawGameFinalScore();
                if (mGameEnd == 1)
                {
                    Intent intent = new Intent(mContext, FirstScene.class);
                    mContext.startActivity(intent);
                    mContext.finish();
                }
                return;
            }
            if (( mTotalRainbows >= mTargetRainbows ) || ( mNumMissed >= mMaxMiss ) || mNumCycleTimeOut >= mMaxCycleTimeOut) {
                endGame();
            }

        } catch (Exception e) {
            Log.i(TAG, "onDraw Exception" + e);
        }
    }

    protected void drawStartMsg()
    {
        try {
            CharSequence ssMsg;
            int textColor, shadowColor;
            int textSize = SDisplayWidth/14;
            int yLocation = SDisplayHeight /16;
            Resources resources = getResources();
            mStartMsgToggle++;
            mStartMsgToggle %= 40;
            if(mStartMsgToggle < 20  ){
                    shadowColor = ContextCompat.getColor(GetAppContext(),R.color.black);
                    textColor = ContextCompat.getColor(GetAppContext(),R.color.almond);
                }
            else{
                shadowColor = ContextCompat.getColor(GetAppContext(),R.color.black);
                textColor = ContextCompat.getColor(GetAppContext(),R.color.morning_white);
            }

            ssMsg = resources.getString(R.string.level) + " :  " + mLevel;
            drawMsg(ssMsg,(int)(SDisplayWidth *0.15), yLocation, textColor, shadowColor, textSize);

            yLocation += textSize + 20;
            ssMsg = resources.getString(R.string.goal)  +" : "+ mTargetRainbows;
            drawMsg(ssMsg,(int)(SDisplayWidth *0.15), yLocation, textColor, shadowColor, textSize);


            yLocation += textSize + 20;
            ssMsg = resources.getString(R.string.totalMiss)  + " :  "+ mMaxMiss ;
            drawMsg(ssMsg,(int)(SDisplayWidth *0.15), yLocation, textColor, shadowColor, textSize);

            yLocation += textSize + 20;
            int seconds = (int) (calculateGameTime(mLevel) / 1000);
            ssMsg =  resources.getString(R.string.game) +" " + resources.getString(R.string.time) + " : " +  seconds/60 + "." + seconds%60;
            drawMsg(ssMsg,(int)(SDisplayWidth *0.15), yLocation, textColor, shadowColor, textSize);

            yLocation += textSize + 20;
            seconds = (int) (calculateCycleTime() / 1000);
            ssMsg = resources.getString(R.string.cycle) + " " +  resources.getString(R.string.time) + " : " + seconds;
            drawMsg(ssMsg,(int)(SDisplayWidth *0.15), yLocation, textColor, shadowColor, textSize);
            int playButtonX = (int) ( (float) SDisplayWidth/4);
            int playButtonY = yLocation + (textSize + 30);

            if ( mEnableButton ) {
                if (mPlayButton == null)
                {
                    Bitmap bImage = ReadImages.LoadBitmap(R.drawable.button);
                    mPlayButton = new PlayButton(bImage, playButtonX, playButtonY);
                }
                textSize = (int) (mPlayButton.getHeight()*0.5);
                canvas.drawBitmap(mPlayButton.getImage(), playButtonX, playButtonY, null);
                ssMsg = resources.getString(R.string.play);
                drawMsg(ssMsg, playButtonX + (int) (mPlayButton.getWidth()/4), playButtonY + (int) (mPlayButton.getHeight()*0.7),
                    textColor, shadowColor, textSize);
            }

        } catch (Exception e) {
            Log.i(TAG, "drawStartMsg Exception" + e);
        }
    }


    public void startingScene(DiagItem item, boolean startMsg ) {
        try {
            if ( GetSavedPreferences("MusicOn")== 0 )
                musicOn = true;
            else
                musicOn = false;

            musicP = new MusicPlayer(mContext, item.getMusic(), musicOn, MUSIC_VOLUME_LEVEL);

            mLevel = GetSavedPreferences("Level");

            mBackGroundImage = new BitmapDrawable(getResources(), item.getImage());
            mBackgroundArt = Bitmap.createScaledBitmap(item.getImage(), SDisplayWidth, SDisplayHeight, true);
            setBackground(mBackGroundImage);
            //setBackgroundColor(ContextCompat.getColor(GetAppContext(),R.color.tan));
            mTargetRainbows = Math.max( 1, mLevel);
            mMaxMiss = calculateMaxMiss(mLevel);
            mNumMissed = 0;
            SXMove = BasicXMove;
            SYMove = BasicYMove;
            int jump =10;
            int yJump =21;
            double  k;
            for (int i = 1; i <= STARTING_IMMORTALS; i++) {
                k = 1+ ( Math.random()*1000)%100;
                Butterfly fly = createCacoon(k*jump , k*yJump, flyMode.IMMORTAL);
                jump += 87;
                yJump += 77;
            }

            musicP.start();
            mDrawStartMsg = startMsg;
            mStartArt = item;
            mGameHalt = false;
            invalidate();
        } catch (Exception e) {
            Log.i(TAG, "setCursorSize " + e);
        }
    }
    public void resumeFirstScene( ) {
        try {
            if ( musicP != null ) {
                musicP.start();
            }
            if ( mActiveFlys != null ) {
                ListIterator<Butterfly> listIterator = mActiveFlys.listIterator();
                while (listIterator.hasNext()) {
                    Butterfly timer = listIterator.next();
                    timer.start();
                }
            }
            if ( gameTimer !=null ){
                gameTimer.start();
            }
        } catch (Exception e) {
            Log.i(TAG, "resumeFirstScene " + e);
        }
    }
    public void haltFirstScene( ) {
        try {
            if ( musicP != null ) {
                musicP.pause();
            }
            if ( gameTimer != null ) {
                gameTimer.cancel();
                gameTimer = null;
            }
            if ( mActiveFlys != null ) {
                ListIterator<Butterfly> listIterator = mActiveFlys.listIterator();
                while (listIterator.hasNext()) {
                    Butterfly timer = listIterator.next();
                    timer.cancel();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "haltFirstScene " + e);
        }
    }
    private void drawGameFinalScore()
    {
        CharSequence ssMsg;

        int textColor, shadowColor;
        int textSize = SDisplayWidth/15;

        Resources resources = getResources();
        shadowColor = ContextCompat.getColor(GetAppContext(),R.color.color_white);
        if (mTotalRainbows >= mTargetRainbows) {
            ssMsg = resources.getString(R.string.winner) + " : " + mTotalRainbows;
            textColor = ContextCompat.getColor(GetAppContext(),R.color.black);
        } else {

            textColor = ContextCompat.getColor(GetAppContext(),R.color.fred1);
            if ( mNumMissed >= mMaxMiss ){
                ssMsg = resources.getString(R.string.lost) + " :  " + mMaxMiss + " " + resources.getString(R.string.totalMiss);
            }
            else if ( mNumCycleTimeOut>= mMaxCycleTimeOut )
            {
                ssMsg = resources.getString(R.string.lost)  + " :  " + mMaxCycleTimeOut + " " + resources.getString(R.string.cycle)+ " " + resources.getString(R.string.timeOut);
            }
            else {
                ssMsg = resources.getString(R.string.lost)  + " :  " + resources.getString(R.string.game)+ " " + resources.getString(R.string.timeOut);
            }
        }

        drawMsg(ssMsg, SDisplayWidth /12, SDisplayHeight / 8, textColor, shadowColor, textSize);
        ssMsg = resources.getString(R.string.nextLevel) + " :  " + mLevel;
        drawMsg(ssMsg, SDisplayWidth /12, SDisplayHeight / 5, textColor, shadowColor, textSize);

        ssMsg = resources.getString(R.string.score) + " : " + mTotalRainbows;
        drawMsg(ssMsg, SDisplayWidth /12, SDisplayHeight / 4 + 20, textColor, shadowColor, textSize);

        invalidate();
    }

    void playNotes(int soundId){
        musicP.playNotes(1000);
    }

    void playSound(int soundId)
    {
        if (soundOn)
            MusicPlayer.playOnce(soundId, SOUND_VOLUME_LEVEL);
    }

    void playCameraSound()
    {
        if ( soundOn )
            playSound(R.raw.camera);
    }

    public void deleteAllFlys() {
        if ( mActiveFlys != null ) {
            ListIterator<Butterfly> listIterator = mActiveFlys.listIterator();
            while (listIterator.hasNext()) {
                Butterfly timer = listIterator.next();
                timer.kill();
                listIterator.remove();
            }
        }
        if ( mRainbowFlys != null ) {
            ListIterator<Butterfly> listIterator = mRainbowFlys.listIterator();
            while (listIterator.hasNext()) {
                Butterfly timer = listIterator.next();
                timer.kill();
                listIterator.remove();
            }
        }
    }

    public void pause()
    {
        if ( mInGame ){
            haltGame();
        }
        else {
            haltFirstScene();
        }
    }
    public void haltGame() {
        try {
            mGameHalt = true;
            musicP.pause();
            if (mActiveFlys != null) {
                ListIterator<Butterfly> listIterator = mActiveFlys.listIterator();
                while (listIterator.hasNext()) {
                    Butterfly timer = listIterator.next();
                    timer.cancel();
                }
            }
            if (mRainbowFlys != null) {
                ListIterator<Butterfly> listIterator = mRainbowFlys.listIterator();
                while (listIterator.hasNext()) {
                    Butterfly timer = listIterator.next();
                    timer.cancel();
                }
            }
            if (gameTimer != null) {
                gameTimer.cancel();
                gameTimer = null;
            }
        } catch ( Exception e){
            Log.i(TAG, " haltGame: " + e );
        }
    }

    public void restart()
    {
        if ( mInGame == false )
        {
            resumeFirstScene();
            return;
        }

        if (mGameHalt == true) {
            mGameHalt = false;
            if (musicP != null)
                musicP.start();
            if (mActiveFlys != null) {
                ListIterator<Butterfly> listIterator = mActiveFlys.listIterator();
                while (listIterator.hasNext()) {
                    Butterfly timer = listIterator.next();
                    timer.start();
                }
            }
            if (mRainbowFlys != null) {
                ListIterator<Butterfly> listIterator = mRainbowFlys.listIterator();
                while (listIterator.hasNext()) {
                    Butterfly timer = listIterator.next();
                    timer.start();
                }
            }
            if (gameTimer != null)
                gameTimer.start();
            else {
                gameTimer = new GameTimer(( mSecondLeft  + 2 )  * 1000, GAME_INTERVAL, this);
                gameTimer.start();
            }
        }
        invalidate();
    }

    protected Butterfly createCacoon(double  xCount, double yCount, flyMode fMode)
    {
        Butterfly newFly = null;
        try {
            int x = (int) (xCount  % ( SDisplayWidth - WING_WIDTH));
            int y = (int) (yCount % ( SDisplayHeight- WING_HEIGHT));
            if (x < WING_WIDTH)
                x += WING_WIDTH;
            if (y < WING_HEIGHT)
                y += WING_HEIGHT;
            newFly = new Butterfly(fMode, LIFE_TIME * TICK_INTERVAL, TICK_INTERVAL, x, y, this);
            newFly.start();
            mActiveFlys.add(newFly);
            invalidate();
        } catch (Exception e) {
            Log.i(TAG, " createCacoon" + e);
        }
        return newFly;
    }

    void handleOneTap(int x, int y)
    {
        if ((mGameEnd != 0) || (mGameHalt == true))
            return;
        try {
            touchButterfly(x, y);
            invalidate();
        } catch (Exception e) {
            Log.i(TAG, "handleOneTap " + e);
        }
    }


    protected void drawScore(int point, int x, int y, int textColor, int shadowColor, int size)
    {
        try {
            CharSequence sMsg;
            if ( point >= 0 )
                sMsg = "" + point;
            else
                sMsg = "-" + point;

            paint.setFakeBoldText(false);
            paint.setStyle(Paint.Style.STROKE);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            paint.setTextSize(size);
            paint.setStrokeMiter(40);
            paint.setColor(shadowColor);
            paint.setStrokeWidth(6);
            canvas.drawText(sMsg, 0, sMsg.length(), x, y, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(textColor);
            canvas.drawText(sMsg, 0, sMsg.length(), x, y, paint);
        } catch (Exception e) {
            Log.i(TAG, "drawSCore" + e);
        }
    }

    public  void drawMsg(CharSequence sMsg, int x, int y, int textColor, int shadowColor, int textSize)
    {
        try {
            paint.setFakeBoldText(false);
            paint.setStyle(Paint.Style.STROKE);
            paint.setTextSize(textSize);
            paint.setStrokeMiter(60);
            paint.setColor(shadowColor);
            paint.setStrokeWidth(textSize/7);
            canvas.drawText(sMsg, 0, sMsg.length(), x, y, paint);

            paint.setStrokeWidth(textSize /10);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(textColor);
            canvas.drawText(sMsg, 0, sMsg.length(), x, y, paint);
        } catch (Exception e) {
            Log.i(TAG, "drawText" + e);
        }
    }
    void setLocation()
    {
        int xBase = 0, yBase = 0;
        int colorOrder = 0 ; int height = 0, width=0;
        int move = (mNumMoves * 3 )%RAINBOW_RANGE;

        if ( mFly.condition() == wellMode.FIXED)
            colorOrder = mFly.colorOrder() + 1;
        else if (mFly.condition() == wellMode.PRE_FIXED )
            colorOrder = 0;
        else
            return;

         if (( mFly.mode() == flyMode.RAINBOW) && (mFly.condition() != wellMode.PRE_FIXED))
        {
            xBase = RAINBOW_X_BASE;
            yBase = RAINBOW_Y_BASE;
        } else {
             xBase = IMMORTAL_X_BASE;
             yBase = IMMORTAL_Y_BASE;
        }
        width = (int) ( mFly.firstWingWidth()*1.7);
        height = mFly.firstWingHeight();
        mFly.setXLocation(xBase + colorOrder * width  - move);
        mFly.setYLocation(yBase - colorOrder * height + move);
    }


    protected void drawSpot() {
        try {
            setColors();

            switch (mFly.mode())
            {
                case CACOON:
                    drawCacoon();
                    break;

                case BORN:
                    drawBorn();
                    break;

                default:
                    drawButterfly();
            }
            drawSpecialEffect();

        } catch (Exception e) {
            Log.i(TAG, "drawSpot" + e);
        }
    }

    protected void setColors()
    {
        try
        {
            Bitmap face;
            RainbowColor tColor ;
            if ( mFly.good || mFly.target )
                mFace = mGoodFace;
            else
                mFace = mEvilFace;
            tColor = mFly.color();
            if (mFly.mode == flyMode.ADULTHOOD )
            {
                if (((mNextTargetColor == mFly.colorOrder()) && mIndex < RAINBOW_COLOR_NUMBER)){
                    tColor = mFly.color();
                }
                else {
                    tColor = ReadImages.ColorBlack;
                }
            }
            int toggle = mFly.modeCount()%3;
            RainbowColor ttColor = RainbowColor.getColor(mNextTargetColor);

            if (toggle == 0) {
                mWingColor =  tColor.getRealColor();
                mBodyColor = tColor.getRealColor();
                mWing2Color = tColor.getShadowColor();
                mTargetColor = ttColor.getRealColor();
                mBackGroundColor = tColor.getlLight();
            }
            else if ( toggle == 1 ) {
                mWingColor = tColor.getShadowColor();
                mBodyColor = tColor.getShadowColor();
                mWing2Color = tColor.getRealColor();
                mTargetColor = ttColor.getShadowColor();
                mBackGroundColor = tColor.getRealColor();
            }
            else if ( toggle == 2 ) {
                    mWingColor = tColor.getRealColor();
                    mBodyColor = tColor.getlLight();
                    mWing2Color = tColor.getShadowColor();
                   mTargetColor = ttColor.getlLight();
                    mBackGroundColor = tColor.getlLight();
            }

        } catch (Exception e)
        {
            Log.i(TAG, "DrawTouch DrawWing Exception  " + e);
        }
    }

    void drawCacoon()
    {
        try {
            int  wingWidth, wingHeight;
            wingWidth =(int) ( mFly.firstWingWidth()*0.7);
            wingHeight = (int ) ( mFly.firstWingWidth()*0.48);
            if ( mFly.colorOrder() == mNextTargetColor )
            {
                wingWidth = (int) (wingWidth*1.2);
                wingHeight = (int) (wingHeight * 1.2);
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(mWingColor);
            canvas.drawOval(mFly.xLocation()-wingWidth, mFly.yLocation()-wingHeight,
                mFly.xLocation() + wingWidth , mFly.yLocation() + wingHeight, paint);

            wingWidth *= 0.8;
            wingHeight *= 0.8;
            int x1, x2, y1, y2;
            x1 = mFly.xLocation() - wingWidth;
            x2 = mFly.xLocation() + wingWidth;
            y1 = mFly.yLocation() - wingHeight;
            y2 = mFly.yLocation() + wingHeight;
            paint.setColor(mWing2Color);
            canvas.drawOval(x1, y1, x2, y2, paint);
            if ( mFly.colorOrder() == mNextTargetColor )
            {
                drawTarget(x1,y1, x2, y2);
            }

        } catch (Exception e) {
            Log.i(TAG, "DrawCacoon  " + e);
        }
    }

    void drawTarget(int x1, int y1, int x2, int y2)
    {
        try {
            paint.setStrokeWidth(10);
            paint.setColor(mTargetColor);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawOval(x1, y1, x2, y2, paint);
            //paint.setColor(ContextCompat.getColor(GetAppContext(), R.color.indigo));
            paint.setStrokeWidth(10);
            int width = (x2 - x1) /5;
            int height = (y2 - y1) /5;
            canvas.drawLine(x1 + width, y1 + height, x2 - width, y2 - height, paint);
            canvas.drawLine(x2 - width, y1 + height, x1 + width, y2 - height, paint);
        } catch ( Exception e) {
            Log.i(TAG, "DrawTarget : " + e );
        }
    }

    void drawBorn()
    {
        try {
            drawBody();
            drawFace();
            if (mFly.modeCount() <= SHOW_WIN_POINT) {
                int xBegin, yBegin, wingWidth, wingHeight;
                int far = (int)( mFly.modeCount()*0.4);
                wingWidth = (int) (mFly.firstWingWidth());
                wingHeight = (int) (mFly.firstWingHeight());
                final RectF oval = new RectF();
                oval.set( mFly.xLocation() - wingWidth/2-far, mFly.yLocation() - wingHeight/2,
                    mFly.xLocation() +wingWidth/2 + far,
                    mFly.yLocation() + wingHeight/2);
                paint.setColor(mWingColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawArc(oval, 90, 180, true, paint);
                canvas.drawArc(oval, 270, 180, true, paint);
            }
        } catch (Exception e) {
            Log.i(TAG, "DrawBorn " + e);
        }
    }

    void drawButterfly()
    {
        try {
            switch (mFly.mode()) {
                case MONARCH:
                    drawHalo();
                    drawFirstWing( true);
                    drawSecondWing( true);
                    break;

                case RAINBOW:
                case WHITEFLY:
                case IMMORTAL:
                   drawHalo();
                case ADULTHOOD:
                    drawFirstWing( true);
                    drawSecondWing( true);
                    break;
                case YOUTH3:
                    drawFirstWing( true);
                    drawSecondWing(false);
                    break;
                case YOUTH2:
                    drawFirstWing( true);
                    break;
                case YOUTH:
                    drawFirstWing( false);
                    break;
                default :
                    return;
            }

            drawBody();
            drawFace();
        } catch (Exception e) {
            Log.i(TAG, "draw Butterfly" + e);
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void drawMonarchWings()
    {

        try {
               canvas.drawBitmap(mFly.getMonarchImage(), mFly.xLocation()-mFly.firstWingWidth(),
                mFly.yLocation()-(int) ( mFly.firstWingHeight()) , paint);
            }
            catch (Exception e) {
                Log.i(TAG, "Exception " + e);
            }
    }

    private void drawHalo()
    {
        int y1, y2 ;
        paint.setColor(mWingColor);
        int faceY = mFly.yLocation() - (int) (mFly.bodyHeight()*0.4) - mFace.getHeight();
        try {
                y1 = faceY + (int) (mFace.getHeight()*0.27);
                y2 = y1 + (int) ( mFace.getHeight()*0.6);
                canvas.drawOval(mFly.xLocation() - mFace.getWidth()/2 - OUTER_WING_SIZE , y1,
                mFly.xLocation() + mFace.getWidth()/2 + OUTER_WING_SIZE, y2, paint);
        }
        catch ( Exception e)
        {
            Log.i(TAG, "drawHalo : " +e );
        }
    }

    void drawFirstWing(boolean right)
    {
        int wingWidth, wingHeight;
        int distance = (int) (mFly.bodyWidth()*0.13);
        Bitmap leftWing = null , rightWing = null;
        int wingAngle = computeWingAngle();
        int wingY1, wingY2;
        int wingX1, wingX2;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mWingColor);
        try{
            wingWidth =(int) ( mFly.firstWingWidth());
            wingHeight = (int) ( mFly.firstWingHeight());
            wingY1 = mFly.yLocation() - (int) ( mFly.bodyHeight()/2);
            wingY2 = wingY1 + wingHeight;
            wingX1 = mFly.xLocation() - wingWidth - mFly.bodyWidth() - OUTER_WING_SIZE;
            wingX2 = wingX1 + wingWidth -mFly.bodyWidth();

            drawWingAngle(wingAngle, mFly.xLocation() - wingWidth - mFly.bodyWidth() /2 -OUTER_WING_SIZE,
                    wingY1 , mFly.xLocation() - mFly.bodyWidth() /2 , wingY2);
            //drawWingAngle(wingAngle, wingX1, wingY1 , wingX2 , wingY2);

            if (right) {
                wingX1 = mFly.xLocation() + mFly.bodyWidth()/2 ;
                wingX2 = wingX1 + wingWidth + OUTER_WING_SIZE;
                //drawWingAngle(-wingAngle, wingX1 , wingY1, wingX2, wingY2);
                drawWingAngle(-wingAngle, mFly.xLocation() + mFly.bodyWidth()/2 - OUTER_WING_SIZE,
                        wingY1, mFly.xLocation() +  wingWidth+ OUTER_WING_SIZE + mFly.bodyWidth() /2, wingY2);
            }
            //if ((mFly.mode == flyMode.ADULTHOOD &&  (mNextTargetColor == mFly.colorOrder()) && mIndex < RAINBOW_COLOR_NUMBER) )
            if ( mFly.condition() == wellMode.FLICKERING )
            {
                if ( mFly.target ) {
                    if (mFly.onOff  ) {
                        return;
                    }
                }
                else {
                    if ( mFly.modeCount()%4 == 0 )
                        return;
                }
            }

            if ( (mFly.mode() == flyMode.RAINBOW ) ||  (mFly.good() == false  )  ||( mFly.mode() == flyMode.WHITEFLY))
            {

                leftWing = mFly.getUpperLeft();
                wingX1 = mFly.xLocation() - wingWidth - mFly.bodyWidth()/2;
                drawImageAngle(wingAngle, leftWing, wingX1, wingY1);
                if (right) {
                    wingX1 = mFly.xLocation() + mFly.bodyWidth()/2 ;
                    rightWing = mFly.getUpperRight();
                    drawImageAngle(-wingAngle, rightWing, wingX1, wingY1);
                }
            }
            else {

              drawPictureWing(mBackgroundArt, wingAngle, wingWidth, wingHeight, mBackgroundArt, wingY1);
            }
        } catch (Exception e) {
            Log.i(TAG, "drawFirstWing " + e);
        }
     }
     static Bitmap CircleImage(int width, int height, Bitmap art, int artX, int artY)
     {
         Bitmap butterFlyM = Bitmap.createBitmap(art, artX, artY, width, height, null, false);
         Bitmap tArt = Butterfly.getCircleBitmap(butterFlyM);
         butterFlyM.recycle();
         return tArt;
     }

     void drawCircleImage( int wingAngle, int width, int height, Bitmap art, int artX, int artY, int x, int y)
     {
         try {
             Bitmap butterFlyM = Bitmap.createBitmap(art, artX, artY, width, height, null, false);
             Bitmap tArt = Butterfly.getCircleBitmap(butterFlyM);
             butterFlyM.recycle();
             drawImageAngle(wingAngle, tArt, x, y);
             tArt.recycle();
         } catch (Exception e)
         {
             Log.i(TAG, " drawCircleImage " + e );
         }
     }
     void drawPictureWing( Bitmap left, int wingAngle, int wingWidth, int wingHeight, Bitmap right, int wingY1)
     {
         int xBegin, yBegin, wingX;

         xBegin = mFly.originX() - wingWidth;
         if (xBegin < 0) {
             xBegin = OUTER_WING_SIZE;
         }
         if (xBegin > (SDisplayWidth - wingWidth  )) {
             xBegin = SDisplayWidth - wingWidth ;
         }
         yBegin = mFly.originY() - wingHeight;
         if (yBegin < 0) {
             yBegin = OUTER_WING_SIZE;
         }
         if (yBegin > (SDisplayHeight - wingHeight )) {
             yBegin = SDisplayHeight - wingHeight;
         }

         wingX = mFly.xLocation() - wingWidth - mFly.bodyWidth()/3;
         drawCircleImage(wingAngle, wingWidth-OUTER_WING_SIZE, wingHeight-OUTER_WING_SIZE, left, xBegin, yBegin, wingX, wingY1);

         if (right != null ) {
             wingX = mFly.xLocation() + mFly.bodyWidth()/3;
             drawCircleImage(-wingAngle, wingWidth-OUTER_WING_SIZE, wingHeight-OUTER_WING_SIZE, right, xBegin, yBegin, wingX, wingY1);
         }
     }

    void drawSecondWing(boolean right)
    {
        try {
            int wingWidth, wingHeight;
            int wingAngle = computeWingAngle();
            Bitmap leftWing = null, rightWing = null;
            wingWidth =(int) ( mFly.secondWingWidth());
            wingHeight = (int) ( mFly.secondWingHeight());
            int wingY1 = mFly.yLocation() + OUTER_WING_SIZE;
            int wingY2 = wingY1 + wingHeight;
            int distance = (int) (mFly.bodyWidth()*0.13);
            paint.setColor(mWingColor);
            drawWingAngle(wingAngle, mFly.xLocation() - wingWidth - distance - OUTER_WING_SIZE, wingY1, mFly.xLocation() -distance, wingY2+OUTER_WING_SIZE);
            if (right)
                    drawWingAngle(-wingAngle, mFly.xLocation() + distance, wingY1, mFly.xLocation() + wingWidth + OUTER_WING_SIZE + distance, wingY2+OUTER_WING_SIZE);
            if ( mFly.condition() == wellMode.FLICKERING  )
            {
                if ( mFly.target ) {
                    if ( mFly.onOff ) {
                        mFly.onOff = false;
                        return;
                    }
                    else {
                        mFly.onOff = true;
                    }
                }
                else {
                    if ( mFly.modeCount()%4 == 0 )
                        return;
                }
            }

           if ( (mFly.mode() == flyMode.RAINBOW ) ||  (mFly.good() == false  )  ||( mFly.mode() == flyMode.WHITEFLY))
            {
                leftWing = mFly.getLowerLeft();
                int wingX = mFly.xLocation() - wingWidth - distance;
                drawImageAngle(wingAngle, leftWing, wingX, wingY1);

                if (right)
                {
                    rightWing = mFly.getLowerRight();
                    wingX = mFly.xLocation() + distance;
                    drawImageAngle(-wingAngle, rightWing, wingX, wingY1);
                }
            } else {
                drawPictureWing(mBackgroundArt,wingAngle, wingWidth, wingHeight, mBackgroundArt, wingY1);
            }
        } catch ( Exception e){
            Log.i("TAG", " drawSecondWing " + e );
        }
    }

    int computeWingAngle()
    {
        int wingAngle, count, cycle;
        int ratio;
        if (mFly.condition() == wellMode.FIXED) {
            cycle = 5 * TOTAL_MOVES;
            count = mNumMoves % cycle;
            ratio = 1;
        } else {
            cycle = TOTAL_MOVES;
            count = mFly.modeCount() % cycle;
            ratio = 5;
        }

        if (count < (int) (cycle / 2)) {
            wingAngle =  42 - ratio * count;
        } else {
            wingAngle = 48 - ratio * (cycle - count);
        }
        return wingAngle;
    }

    @TargetApi(23)
    private void drawWingAngle( int angle, int x1, int y1, int x2, int y2){

        try {
            canvas.save();
            canvas.rotate(angle, mFly.xLocation(), mFly.yLocation());
            canvas.drawOval(x1, y1, x2, y2, paint);
            canvas.restore();
        } catch (Exception e)
        {
            Log.i(TAG, " drawWingAngle : " + e );
        }
    }

    void drawImageAngle( int angle, Bitmap wing, int x, int y){

        canvas.save();
        canvas.rotate(angle, mFly.xLocation(), mFly.yLocation());
        canvas.drawBitmap(wing,x, y, null);
        canvas.restore();
    }
    @TargetApi(23)
    private void drawBody()
    {
        try
        {
            int x1, x2, y1, y2;

            paint.setStyle(Paint.Style.FILL);
            y1 = mFly.yLocation() - (int) ( mFly.bodyHeight()*0.55);
            y2 = y1 +(int) (mFly.bodyHeight());
            x1 = mFly.xLocation() -  (int) (mFly.bodyWidth());
            x2 = mFly.xLocation() +  (int) (mFly.bodyWidth());
            if ( mFly.good() || mFly.colorOrder() == mNextTargetColor )
                paint.setColor(mBodyColor);
            else
                paint.setColor(ContextCompat.getColor(GetAppContext(),R.color.black));
            canvas.drawOval(x1, y1, x2, y2,  paint);

        } catch (Exception e) {
            Log.i(TAG, "drawBody " + e);
        }
    }

    private void drawFace()
    {
        int faceY = mFly.yLocation() - (int) (mFly.bodyHeight()*0.4) - mFace.getHeight();
        try {
            canvas.drawBitmap(mFace, mFly.xLocation()-mFace.getWidth() / 2, faceY, paint);
        } catch (Exception e) {
            Log.i(TAG, "drawFace : " + e);
        }
    }

    public void sound(boolean onFlag)
    {
        soundOn = onFlag;
        if ( onFlag == true )
            SavePreferences("SoundOn", 0);
        else
            SavePreferences("SoundOn", 1);
    }

    public void music(boolean onFlag)
    {
        if (onFlag) {
            if ( musicOn )
                return;
            else {
                musicOn = true;
                musicP.On();
                musicP.start();
                SavePreferences("MusicOn", 0);
            }
        }
        else {
            if ( musicOn  )
                musicP.stop();
            musicOn = false;
            musicP.Off();
            SavePreferences("MusicOn", 1);
        }

    }


    public void gameTimerInterval(long millisLeft)
    {

        try {
            mSecondLeft = (int) millisLeft / 1000;
            updateTime(mSecondLeft);
            if ( mActiveFlys.size() == 0 ) {
                initCacoonNumbers();
                invalidate();
            }
            else if ( mCycleLeft >= GAME_INTERVAL ) {
                mCycleLeft -= GAME_INTERVAL;
                if ( mCycleLeft == 0 && (mActiveFlys.size() >0) ){
                    playSound(R.raw.gong);
                    mNumCycleTimeOut++;
                    mCycleLeft = CYCLE_OUT_DURATION;
                }
            }
            invalidate();
        } catch (Exception e) {
            Log.i(TAG, "gameTimerInterval : " + e);
        }
    }
    public void drawSpecialEffect()
    {
        CharSequence sMsg;
        int mCount = mFly.modeCount();
        flyMode mode = mFly.mode;

        try {
            if ( mCycleLeft > 0 ) {
                int back;
                int front;
                int nNum = mCycleLeft/1000;
                if (nNum%2 == 0 ){
                     back  = ContextCompat.getColor(GetAppContext(), R.color.blue );
                     front = ContextCompat.getColor(GetAppContext(), R.color.red_reflexion);
                }else {
                    front  = ContextCompat.getColor(GetAppContext(), R.color.blue);
                    back = ContextCompat.getColor(GetAppContext(), R.color.red_reflexion);
                }
                paint.setColor(back);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawOval( CYCLE_X, CYCLE_Y, CYCLE_O_RIGHT, CYCLE_O_BOTTOM , paint);
                sMsg = "" + nNum ;
                drawMsg(sMsg, CYCLE_TEXT_X   , CYCLE_TEXT_Y  ,
                        ContextCompat.getColor(GetAppContext(), R.color.morning_white), back, SDisplayWidth / 13);
            }

            if ( (mFly.condition() == wellMode.MISSED)) {
                if (mFly.modeCount() < ( SHOW_WIN_POINT + 5 )) {
                    canvas.drawBitmap(mBurst, mFly.xLocation() - mBurst.getWidth() / 2, mFly.yLocation() - (int) (mBurst.getHeight() * 0.8), paint);
                    drawScore(mNumMissed, mFly.xLocation() - 50, mFly.yLocation() + 20,
                            ContextCompat.getColor(GetAppContext(), R.color.ZenWhite),
                            ContextCompat.getColor(GetAppContext(), R.color.ZenBlue), SDisplayWidth / 8);
                    sMsg = getResources().getString(R.string.need) + "!";
                    drawMsg(sMsg, CYCLE_X + 2*RAINBOW_WIDTH, CYCLE_Y +  (int) (TARGET_HEIGHT*0.8),
                            ContextCompat.getColor(GetAppContext(), R.color.ZenWhite ), ContextCompat.getColor(GetAppContext(), R.color.ZenBlue), SDisplayWidth /13);
                    return;
                }
                else if (  mFly.modeCount() ==  SHOW_WIN_POINT +5  ) {
                    setBackground(mBackGroundImage);
                    mFly.setCondition(wellMode.FLICKERING);
                }
                return;
            }
            if ( (mFly.condition() == wellMode.PREVIOUS )){
                if  (mFly.mode == flyMode.RAINBOW) {
                    if (mFly.modeCount() < SHOW_WIN_POINT) {
                        drawScore(mTotalRainbows, mFly.xLocation() - 50, mFly.yLocation() + 20,
                                ContextCompat.getColor(GetAppContext(), R.color.ZenWhite),
                                ContextCompat.getColor(GetAppContext(), R.color.ZenBlue), SDisplayWidth / 8);
                        return;
                    } else if (mFly.modeCount() == SHOW_WIN_POINT) {
                        setBackground(mBackGroundImage);
                        mFly.setCondition(wellMode.FIXED);
                    }
                    return;
                }
                else if ( mFly.mode == flyMode.ADULTHOOD && mFly.good  ){
                    if (mFly.modeCount() == SHOW_WIN_POINT) {
                        setBackground(mBackGroundImage);
                        mActiveFlys.remove(mFly);
                        mFly.kill();
                        return;
                    }
                }

            }
           if ((mFly.target)) {
                if (mFly.modeCount() ==  SHOW_WIN_POINT  ) {
                        setBackground(mBackGroundImage);
                    }
                    if ( mFly.modeCount() < (SHOW_WIN_POINT + 20 )) {
                        sMsg =  getResources().getString(R.string.slice) + " " + mFly.colorName() + " " +
                                getResources().getString(R.string.evil);

                        drawMsg(sMsg, MSG_X + TARGET_WIDTH/5, MSG_Y + (int) (TARGET_HEIGHT*0.7),
                                //ContextCompat.getColor(GetAppContext(), R.color.ZenBlue ),ContextCompat.getColor(GetAppContext(), R.color.red_reflexion), SDisplayWidth / 13);
                                mTargetColor,ContextCompat.getColor(GetAppContext(), R.color.ZenBlack), SDisplayWidth / 13);
                    }

                }
        } catch (Exception e) {
            Log.i(TAG, "drawSpecialEffect : " + e);
        }
    }

    private void updateTime(int seconds)
    {
        try
        {
            androidx.appcompat.app.ActionBar ab = mContext.getSupportActionBar();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int barColor;
            View v =inflater.inflate(R.layout.puzzle_action_bar, null);
            int timeTextColor;
            ab.setDisplayOptions(ab.DISPLAY_SHOW_CUSTOM);
            ab.setCustomView(v);
            if (titleColorToggle) {
                timeTextColor = ContextCompat.getColor(mContext,R.color.ZenBlack);
                titleColorToggle = false;
                barColor = ContextCompat.getColor(mContext, R.color.ZenOrange);
            } else
            {
                timeTextColor = ContextCompat.getColor(mContext,R.color.ZenOrange);
                titleColorToggle = true;
                barColor = ContextCompat.getColor(mContext, R.color.VanGoghBlue);
            }
            ab.setBackgroundDrawable(new ColorDrawable(barColor));
            CharSequence tText;
            Spannable time;
            TextView timeV;

            tText = "L:" + mLevel ;
            time = new SpannableString(tText);
            time.setSpan(new ForegroundColorSpan(timeTextColor), 0,tText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            timeV = ab.getCustomView().findViewById(R.id.level);
            timeV.setText(time);

            tText = "" + seconds/60 + "." + seconds%60;

            time = new SpannableString(tText);
            time.setSpan(new ForegroundColorSpan(timeTextColor), 0, tText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            timeV = ab.getCustomView().findViewById(R.id.time);
            timeV.setText(time);

            tText = "" +  mTotalRainbows ;
            time = new SpannableString(tText);
            time.setSpan(new ForegroundColorSpan(timeTextColor), 0,tText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            timeV = ab.getCustomView().findViewById(R.id.rainbows);
            timeV.setText(time);
        }
        catch ( Exception e)
        {
            Log.i(TAG, "diaplyGameTime : " + e);
        }
    }
}
