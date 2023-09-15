package apps.yuzaco.com.bugbuster;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.Log;

import static apps.yuzaco.com.bugbuster.ButterflyWings.EvilLowerLeft;
import static apps.yuzaco.com.bugbuster.ButterflyWings.EvilLowerRight;
import static apps.yuzaco.com.bugbuster.ButterflyWings.EvilUpperLeft;
import static apps.yuzaco.com.bugbuster.ButterflyWings.EvilUpperRight;
import static apps.yuzaco.com.bugbuster.ButterflyWings.WhiteLowerLeft;
import static apps.yuzaco.com.bugbuster.ButterflyWings.WhiteLowerRight;
import static apps.yuzaco.com.bugbuster.ButterflyWings.WhiteUpperLeft;
import static apps.yuzaco.com.bugbuster.ButterflyWings.WhiteUpperRight;
import static apps.yuzaco.com.bugbuster.DrawTouch.WING_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.WING_HEIGHT2;
import static apps.yuzaco.com.bugbuster.DrawTouch.WING_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.WING_WIDTH2;
import static apps.yuzaco.com.bugbuster.DrawTouch.BODY_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.BODY_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.CircleImage;
import static apps.yuzaco.com.bugbuster.DrawTouch.RAINBOW_BODY_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.RAINBOW_BODY_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.RAINBOW_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.RAINBOW_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.RAINBOW_WING_HEIGHT2;
import static apps.yuzaco.com.bugbuster.DrawTouch.RAINBOW_WING_WIDTH2;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_BODY_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_BODY_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_WING_HEIGHT2;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_WING_WIDTH2;
import static apps.yuzaco.com.bugbuster.DrawTouch.SDisplayHeight;
import static apps.yuzaco.com.bugbuster.DrawTouch.SDisplayWidth;
import static apps.yuzaco.com.bugbuster.DrawTouch.SMALL_WING_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.SMALL_WING_HEIGHT2;
import static apps.yuzaco.com.bugbuster.DrawTouch.SMALL_WING_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.SMALL_WING_WIDTH2;
import static apps.yuzaco.com.bugbuster.DrawTouch.SMALL_BODY_HEIGHT;
import static apps.yuzaco.com.bugbuster.DrawTouch.SMALL_BODY_WIDTH;
import static apps.yuzaco.com.bugbuster.DrawTouch.SXMove;
import static apps.yuzaco.com.bugbuster.DrawTouch.SYMove;

/**
 * Created by jung on 12/19/15.
 */
class Butterfly extends CountDownTimer {

    private DrawTouch view;
    private boolean onLeft = true;

    private boolean  onTop = true;
    private int xLocation, yLocation;
    private int originX, originY;
    private int wingWidth1, wingHeight1;
    private int wingWidth2, wingHeight2;

    private int bodyWidth;
    private int bodyHeight;
    private boolean active = true;
    public DrawTouch.flyMode mode = DrawTouch.flyMode.CACOON;
    public  RainbowColor color = null;
    private DrawTouch.wellMode condition;
    private int modeCount;
   public  boolean good ;

    public final static int NOT_READY_COUNT = 10;
    public final static int FLICKERING_DURATION = 3000;
    public final static int DYING_DURATION = 15;
    public final static int EVIL_DURATION = 35;
    private ButterflyWings wingType = null;
    static public int StartFlickering;
    static public int StartSickCycle ;
    static public int StartDeath ;
    static public int EndLife;
    static private String TAG = "Butterfly";

    public boolean target;

    public boolean onOff;
    public Butterfly(DrawTouch.flyMode fMode, long millisInFuture, long countDownInterval, int XX, int YY, DrawTouch v) {
        super(millisInFuture, countDownInterval);
        originX = XX;
        originY = YY;
        xLocation = XX;
        yLocation = YY;
        view = v;
        active = true;
        target = false;
        mode = fMode;
        condition = DrawTouch.wellMode.STATIC;
        modeCount = 0;
        good = true;

        try {
            if (fMode == DrawTouch.flyMode.RAINBOW) {
                wingType = ButterflyWings.GetNextRainbow();
                condition = DrawTouch.wellMode.PRE_FIXED;
                color = wingType.color();
                //color = ColorBlack;
                makeNormalSize();
            } else if(fMode == DrawTouch.flyMode.ADULTHOOD){
                wingType = ButterflyWings.GetNextWings();
                color = wingType.color();
                makeNormalSize();
            }else if (mode == DrawTouch.flyMode.IMMORTAL)
            {
                wingType = ButterflyWings.GetNextWings();
                condition = DrawTouch.wellMode.FLICKERING;
                color = ReadImages.ColorBlack;
                //color = wingType.color();
                makeLargeSize();
            }
            else
            {
                wingType = ButterflyWings.GetNextWings();
                makeNormalSize();
                color  = wingType.color();
            }



            if (originX > (SDisplayWidth >> 1))
                onLeft = false;
            else
                onLeft = true;

            if (originY > (SDisplayHeight >> 1))
                onTop = false;
            else
                onTop = true;
        }
        catch ( Exception e){
            Log.i(TAG, " Butterfly() " + e );
        }

    }
    public static void SetFlickeringCount(int gameLevel)
    {
        StartFlickering =  NOT_READY_COUNT ;
        StartSickCycle =  StartFlickering + FLICKERING_DURATION + gameLevel/3;
        StartDeath = StartSickCycle + EVIL_DURATION + gameLevel/2;
        EndLife = StartDeath  + DYING_DURATION ;
    }
    @Override
    public void onFinish() {
        active = false;
        view.invalidate();
    }

    public void kill(){
        active = false;
        view.invalidate();
    }

    public RainbowColor  getWingsColor()
    {
        if ( wingType == null ) {
            wingType = ButterflyWings.GetNextWings();
        }
        return wingType.color();
    }
    public void makeCacoon()
    {
        mode = DrawTouch.flyMode.CACOON;
        good = true;
        makeNormalSize();
        color = getWingsColor();
        modeCount = 0;
    }
    public void makeYouth()
    {
        mode = DrawTouch.flyMode.YOUTH;
    }
    public void makeYouth2()
    {
        mode = DrawTouch.flyMode.YOUTH2;
        modeCount = 0;
    }
    public void makeYouth3()
    {
        mode = DrawTouch.flyMode.YOUTH3;
    }
    public void makeYouth4()
    {
        mode = DrawTouch.flyMode.YOUTH4;
    }
    public void makeAdult()
    {
        mode = DrawTouch.flyMode.ADULTHOOD;
        makeLargeSize();
    }
    public  void makeImmortal()
    {
        mode = DrawTouch.flyMode.IMMORTAL;
        makeNormalSize();
        good = true;
        modeCount = 0;
    }
    public void makeRainbow()
    {
        mode = DrawTouch.flyMode.RAINBOW;
        wingType = ButterflyWings.GetNextRainbow();
        condition = DrawTouch.wellMode.PREVIOUS;
        //color = ColorBlack;
        color = wingType.color();
        makeNormalSize();
        modeCount = 0;
    }
    public  void makeWhite()
    {
        mode = DrawTouch.flyMode.WHITEFLY;
        condition = DrawTouch.wellMode.PREVIOUS;
        good = true;
        modeCount = 0;
        makeNormalSize();
    }

    public void makeNormalSize()
    {
        wingWidth1 = WING_WIDTH;
        wingHeight1 = WING_HEIGHT;
        wingWidth2 = WING_WIDTH2;
        wingHeight2 = WING_HEIGHT2;
        bodyHeight = BODY_HEIGHT;
        bodyWidth = BODY_WIDTH;
    }
    public void makeSmallSize()
    {
        wingWidth1 = SMALL_WING_WIDTH;
        wingHeight1 = SMALL_WING_HEIGHT;
        wingWidth2 = SMALL_WING_WIDTH2;
        wingHeight2 = SMALL_WING_HEIGHT2;
        bodyHeight = SMALL_BODY_HEIGHT;
        bodyWidth = SMALL_BODY_WIDTH;
    }
    public void makeLargeSize()
    {
        wingWidth1 = RAINBOW_WIDTH;
        wingHeight1 = RAINBOW_HEIGHT;
        wingWidth2 = RAINBOW_WING_WIDTH2;
        wingHeight2 = RAINBOW_WING_HEIGHT2;
        bodyHeight = RAINBOW_BODY_HEIGHT;
        bodyWidth = RAINBOW_BODY_WIDTH;
    }
    public void makeTargetSize()
    {
        wingWidth1 = TARGET_WIDTH;
        wingHeight1 = TARGET_HEIGHT;
        wingWidth2 = TARGET_WING_WIDTH2;
        wingHeight2 = TARGET_WING_HEIGHT2;
        bodyHeight = TARGET_BODY_HEIGHT;
        bodyWidth = TARGET_BODY_WIDTH;
    }
    public void makeEvil()
    {
        good = false;
        makeNormalSize();
        mode = DrawTouch.flyMode.ADULTHOOD;
        modeCount = 0;
    }
    public void makeMissed()
    {
        condition  = DrawTouch.wellMode.MISSED;
        modeCount = 0;
    }
    @Override
    public void onTick(long millisLeft) {
        try {
            modeCount++;
            if ((modeCount < DrawTouch.SHOW_WIN_POINT) && (condition == DrawTouch.wellMode.PREVIOUS)) {
                view.invalidate();
                return;
            }
            if ((modeCount == DrawTouch.SHOW_WIN_POINT)) {
                if (condition == DrawTouch.wellMode.PRE_FIXED) {
                    condition = DrawTouch.wellMode.FIXED;
                } else if (condition == DrawTouch.wellMode.PREVIOUS) {
                    if ((mode == DrawTouch.flyMode.IMMORTAL) || (mode == DrawTouch.flyMode.RAINBOW))
                        condition = DrawTouch.wellMode.FIXED;
                }
                view.invalidate();
                return;
            }
            if (condition == DrawTouch.wellMode.FIXED) {
                view.invalidate();
                return;
            }

            if (onLeft) {
                if (target)
                    xLocation += Math.min(SXMove * 0.7, DrawTouch.MAX_X_MOVE);
                else
                    xLocation += Math.min(SXMove, DrawTouch.MAX_X_MOVE);
            } else {
                if (target)
                    xLocation -= Math.min(SXMove *0.7, DrawTouch.MAX_X_MOVE);
                else
                    xLocation -= Math.min(SXMove, DrawTouch.MAX_X_MOVE);
            }
            if (onTop) {
                if (target)
                    yLocation += Math.min(SYMove * 1.5, DrawTouch.MAX_Y_MOVE);
                else
                    yLocation += Math.min(SYMove, DrawTouch.MAX_Y_MOVE);
            } else {
                if (target)
                    yLocation -= Math.min(SYMove * 1.5, DrawTouch.MAX_Y_MOVE);
                else
                    yLocation -= Math.min(SYMove, DrawTouch.MAX_Y_MOVE);

            }
            if (xLocation  < wingWidth1) {
                xLocation = SDisplayWidth  - xLocation  - 2*wingWidth1;
                onLeft = false;
            } else if (xLocation >= (SDisplayWidth - wingWidth1)) {
                xLocation = ( xLocation + wingWidth1 )% SDisplayWidth + 2*wingWidth1;
                onLeft = true;
            }
            if ((yLocation ) < wingHeight1) {
                  yLocation = SDisplayHeight -  ( wingHeight1 -  yLocation ) - 2*wingHeight1 ;
                  onTop = false;
            } else if (yLocation > (SDisplayHeight - wingHeight1)) {
                    yLocation = ( yLocation + wingHeight1)%SDisplayHeight+ 2*wingHeight1;
                    onTop = true;
            }
            view.invalidate();
        }
        catch ( Exception e){
            Log.i(TAG, "onTick " + e );
        }
    }


    public int xLocation()
    {
        return xLocation;
    }
    public int bodyWidth()
    {
        return bodyWidth;
    }
    public DrawTouch.wellMode condition()
    {
        return condition;
    }
    public void setCondition(DrawTouch.wellMode newMode) { condition = newMode;}
    public DrawTouch.flyMode mode()
    {
        return mode;
    }
    public int yLocation()
    {
        return yLocation;
    }
    public void setXLocation(int x) { xLocation = x;}
    public void setYLocation(int y) { yLocation = y;}
    public void setTarget(boolean m) { target = m; }
    public boolean active()
    {
        return active;
    }
    public RainbowColor color()
    {
        return color;
    }
    public String colorName() { return wingType.color().colorName();}
    public int originX()
    {
        return originX;
    }
    public int originY()
    {
        return originY;
    }
    public int modeCount()
    {
        return modeCount;
    }
    public int bodyHeight()
    {
        return bodyHeight;
    }
    public int colorOrder() {
        if ( wingType != null )
            return wingType.color().getOrder();
        else
            return color.getOrder();
    }
    public int firstWingWidth() { return wingWidth1;}
    public int firstWingHeight() { return wingHeight1;}
    public int secondWingWidth() { return wingWidth2; }
    public int secondWingHeight() { return wingHeight2; }

    public Bitmap getUpperRight()
    {
        if (good == false )
        {
            return(EvilUpperRight());
        }
        else if ( mode == DrawTouch.flyMode.WHITEFLY )
        {
            return(WhiteUpperRight());
        }else if ( mode == DrawTouch.flyMode.RAINBOW )
        {
            return wingType.upperRight();
        }
        return( getWing(true, false));
    }

    public Bitmap getUpperLeft() {

        if (good == false )
        {
            return(EvilUpperLeft());
        }
        else if ( mode == DrawTouch.flyMode.WHITEFLY )
        {
            return(WhiteUpperLeft());
        }else if ( mode == DrawTouch.flyMode.RAINBOW )
        {
            return wingType.upperLeft();
        }

        return( getWing(true, true));
    }
    public Bitmap getLowerRight()
    {
        if ( mode   ==  DrawTouch.flyMode.RAINBOW ){
            return (wingType.lowerRight());
        }
        else if ( mode == DrawTouch.flyMode.WHITEFLY )
        {
            return(WhiteLowerRight());
        }
        else if ( good == false  )
        {
            return(EvilLowerRight());
        }
        return getWing(false, false);

    }
    public Bitmap getLowerLeft()
    {
        if ( mode   ==  DrawTouch.flyMode.RAINBOW )
        {
            return (wingType.lowerLeft());
        }
        else if ( mode == DrawTouch.flyMode.WHITEFLY )
        {
            return(WhiteLowerLeft());
        }
        else if (good == false )
        {
            return(EvilLowerLeft());
        }
        return getWing(false, true);
    }
    private Bitmap getWing( boolean upper, boolean left)
    {
        int xBegin, yBegin;
        int cWidth, cHeight;
        if ( upper ) {
            cWidth = wingWidth1;
            cHeight = wingHeight1;
            yBegin = originY - cHeight;
        }
        else
        {
            cWidth = (int)( wingWidth2);
            cHeight = (int) ( wingWidth2*0.28 );
            yBegin = originY;
        }
        if ( left )
            xBegin = originX - cWidth;
        else
            xBegin = originX;
        return(CircleImage(cWidth, cHeight,view.getBackgroundArt(), xBegin, yBegin ));
    }
    public Bitmap getMonarchImage()
    { return wingType.monarchImage();}

    public void setModeCount(int number){
        modeCount = number;
    }


    public boolean good() {  return  good;}

    static public Bitmap getCircleBitmap(Bitmap bitmap)
    {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        try {
            final Canvas canvas = new Canvas(output);
            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawOval(rectF, paint);

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect, rect, paint);

                bitmap.recycle();
             }
             catch (Exception e) {
                    Log.i(TAG, "getCircleBitmap " + e);
            }
            return output;
        }
    }