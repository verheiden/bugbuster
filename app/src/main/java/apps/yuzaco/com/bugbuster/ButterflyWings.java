package apps.yuzaco.com.bugbuster;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import static apps.yuzaco.com.bugbuster.DrawTouch.TAG;

/**
 * Created by jung on 8/31/16.
 */
public class ButterflyWings {
    private Bitmap mUpperLeft = null;
    private Bitmap mUpperRight = null;
    private Bitmap mLowerLeft = null;
    private Bitmap mLowerRight = null;
    private Bitmap monarchImage = null;
    private RainbowColor color = null;
    static ArrayList<ButterflyWings>  Wings = null;
    private static ArrayList<ButterflyWings> monarchs = null;
    private static ArrayList<ButterflyWings> Rainbows = null;
    private static ButterflyWings EvilWings;
    private static ButterflyWings RainbowWings;
    private static ButterflyWings WhiteWings;
    static int Index = 0;
    static int MonarchIndex = 0;
    static int RainbowIndex = 0;

    public static ArrayList<ButterflyWings>  GetWings() {return Wings; }
    public ButterflyWings(Bitmap ul,  Bitmap ur, Bitmap ll, Bitmap lr, RainbowColor flyColor)
    {
        mUpperLeft = ul;
        mUpperRight = ur;
        mLowerLeft = ll;
        mLowerRight = lr;
        color = flyColor;
    }

    public ButterflyWings( RainbowColor flyColor)
    {
        mUpperLeft = null;
        mUpperRight = null;
        mLowerLeft = null;
        mLowerRight = null;
        color = flyColor;
    }

    public static void Init() {
        Index = 0;
        MonarchIndex = 0;
        RainbowIndex = 0;
    }
    public static void AddRainbow(RainbowColor tColor)
    {
        try
        {
            if (Rainbows == null) {
                Rainbows = new ArrayList<ButterflyWings>();
            }
            Rainbows.add(new ButterflyWings(RainbowUpperLeft(),
                RainbowUpperRight(), RainbowLowerLeft(),RainbowLowerRight(), tColor));

        } catch ( Exception e)
        {
            Log.i(TAG, "ButterflyWings::AddMonarchs " + e);
        }
    }
    public static Bitmap EvilUpperLeft()
    {
        return (EvilWings.mUpperLeft);
    }
    public static Bitmap EvilUpperRight()
    {
        return EvilWings.mUpperRight;
    }
    public static Bitmap EvilLowerRight()
    {
        return EvilWings.mLowerRight;
    }
    public static Bitmap EvilLowerLeft()
    {
        return EvilWings.mLowerLeft;
    }


    public static Bitmap RainbowUpperLeft()
    {
        return (RainbowWings.mUpperLeft);
    }
    public static Bitmap RainbowUpperRight()
    {
        return RainbowWings.mUpperRight;
    }
    public static Bitmap RainbowLowerRight()
    {
        return RainbowWings.mLowerRight;
    }
    public static Bitmap RainbowLowerLeft()
    {
        return RainbowWings.mLowerLeft;
    }
    
    public static Bitmap WhiteUpperLeft()
    {
        return (WhiteWings.mUpperLeft);
    }
    public static Bitmap WhiteUpperRight()
    {
        return WhiteWings.mUpperRight;
    }
    public static Bitmap WhiteLowerRight()
    {
        return WhiteWings.mLowerRight;
    }
    public static Bitmap WhiteLowerLeft()
    {
        return WhiteWings.mLowerLeft;
    }

    public static void SetEvilWings(Bitmap uLeft, Bitmap uRight, Bitmap lLeft, Bitmap lRight, RainbowColor color)
    {
        try {
            EvilWings = new ButterflyWings(uLeft, uRight, lLeft, lRight, color);
        } catch (Exception e){
            Log.i(TAG, " SetEvilWings " + e );
        }
    }
    public static void SetWhiteWings(Bitmap uLeft, Bitmap uRight, Bitmap lLeft, Bitmap lRight, RainbowColor color)
    {
        try {
            WhiteWings = new ButterflyWings(uLeft, uRight, lLeft, lRight, color);
        } catch (Exception e){
            Log.i(TAG, " SetWhiteWings " + e );
        }
    }
    public static void SetRainbowWings(Bitmap uLeft, Bitmap uRight, Bitmap lLeft, Bitmap lRight, RainbowColor color)
    {
        try {
            RainbowWings = new ButterflyWings(uLeft, uRight, lLeft, lRight, color);
        } catch (Exception e){
            Log.i(TAG, " SetWhiteWings " + e );
        }
    }
    public static ButterflyWings GetRainbowWings()
    {
        return RainbowWings;
    }
    public static ButterflyWings GetWhiteWings()
    {
        return WhiteWings;
    }
    public static void AddWings(ButterflyWings wings)
    {
        try
        {
            if (Wings == null)
                Wings = new ArrayList<ButterflyWings>();
            Wings.add(wings);
        } catch ( Exception e){
            Log.i(TAG, "ButterflyWings::AddWings " + e);
        }
    }

    public static ButterflyWings GetNextRainbow()
    {
        ButterflyWings tFly = Rainbows.get(RainbowIndex++);
        RainbowIndex = RainbowIndex%Rainbows.size();
        return tFly;
    }
    public static ButterflyWings GetNextMonarch()
    {
        ButterflyWings tFly = monarchs.get(MonarchIndex++);
        MonarchIndex = MonarchIndex%monarchs.size();
        return tFly;
    }
    public static ButterflyWings GetNextWings()
    {
        ButterflyWings tWings = Wings.get(Index++);
        Index = Index% Wings.size();
        return tWings;
    }
    public Bitmap upperLeft(){
        return mUpperLeft;
    }
    public Bitmap upperRight(){
        return mUpperRight;
    }
    public Bitmap lowerLeft(){
        return mLowerLeft;
    }
    public Bitmap lowerRight(){
        return mLowerRight;
    }
    public Bitmap monarchImage() { return monarchImage;}
    public RainbowColor color() {return color;}
}
