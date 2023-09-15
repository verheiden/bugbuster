package apps.yuzaco.com.bugbuster;

import static apps.yuzaco.com.bugbuster.DrawTouch.CircleImage;
import static apps.yuzaco.com.bugbuster.DrawTouch.OUTER_WING_SIZE;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_WING_HEIGHT2;
import static apps.yuzaco.com.bugbuster.DrawTouch.TARGET_WING_WIDTH2;
import static apps.yuzaco.com.bugbuster.MozartApp.GetAppContext;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import apps.yuzaco.com.bugbuster.R;

/**
 * Created by jung Verheiden on Feb/17/2017
 */
public class ReadImages  
{
    static ArrayList<DiagItem> DiagArray = null;
    static RainbowColor ColorRed  = null;
    static RainbowColor  ColorOrange;
    static RainbowColor  ColorYellow;
    static RainbowColor ColorGreen ;
    static RainbowColor ColorBlue;
    static RainbowColor ColorIndigo ;
    static RainbowColor ColorViolet;
    static RainbowColor ColorBlack;
    static RainbowColor ColorGold;
    static RainbowColor ColorWhite;
    private static final String TAG = "ReadImages";
    public ReadImages()
    {
    }
    public static void ColorInit()
    {
        if ( ColorRed != null )
            return;
        int colorOrder = 0;
        Context ui = GetAppContext();

        ColorRed = new RainbowColor(ContextCompat.getColor(ui, R.color.red),
            ContextCompat.getColor(ui, R.color.lRed), ContextCompat.getColor(ui, R.color.llRed),colorOrder++);
        ColorOrange = new RainbowColor(ContextCompat.getColor(ui, R.color.orange),
            ContextCompat.getColor(ui, R.color.lOrange),ContextCompat.getColor(ui, R.color.llOrange),colorOrder++);
        ColorYellow = new RainbowColor(ContextCompat.getColor(ui, R.color.yellow),
            ContextCompat.getColor(ui, R.color.lYellow),ContextCompat.getColor(ui, R.color.llYellow),colorOrder++);
        ColorGreen =new RainbowColor(ContextCompat.getColor(ui, R.color.green),
            ContextCompat.getColor(ui, R.color.lGreen), ContextCompat.getColor(ui, R.color.llGreen),colorOrder++);
        ColorBlue = new RainbowColor(ContextCompat.getColor(ui, R.color.blue),
            ContextCompat.getColor(ui, R.color.lBlue), ContextCompat.getColor(ui, R.color.llBlue),colorOrder++);
        ColorIndigo = new RainbowColor(ContextCompat.getColor(ui, R.color.indigo),
            ContextCompat.getColor(ui, R.color.lIndigo), ContextCompat.getColor(ui, R.color.llIndigo),colorOrder++);
        ColorViolet = new RainbowColor(ContextCompat.getColor(ui, R.color.violet),
            ContextCompat.getColor(ui, R.color.lViolet),ContextCompat.getColor(ui, R.color.llViolet), colorOrder++);
        ColorBlack = new RainbowColor(ContextCompat.getColor(ui, R.color.black), ContextCompat.getColor(ui,R.color.lBlack),
            ContextCompat.getColor(ui,R.color.llBlack), colorOrder++);
        ColorGold = new RainbowColor(ContextCompat.getColor(ui, R.color.gold), ContextCompat.getColor(ui,R.color.lGold),
            ContextCompat.getColor(ui,R.color.llGold), colorOrder++);
        ColorWhite = new RainbowColor(ContextCompat.getColor(ui, R.color.ZenWhite), ContextCompat.getColor(ui,R.color.lightCyan),
            ContextCompat.getColor(ui,R.color.lightGolden), colorOrder++);
    }
    public static Bitmap LoadBitmap(int resid) {
        Context context = GetAppContext();
        Resources resources = context.getResources();
        Uri uri = Uri.parse(Screen.ANDROID_RESOURCE + resources.getResourcePackageName(resid) +
            Screen.FORESLASH + resid);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inSampleSize = 1;
        Bitmap temp, newBitmap;
        if (uri == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream is;
        try {
            is = context.getContentResolver().openInputStream(uri);
            temp =  BitmapFactory.decodeStream(is, null, options);
            newBitmap = Bitmap.createScaledBitmap(temp, temp.getWidth(), temp.getHeight(), true);
            temp.recycle();
            return newBitmap;
        } catch (FileNotFoundException e) {
            Log.e(DrawTouch.TAG, "FileNotFoundException for " + uri, e);
            return null;
        }
    }

    private static Bitmap readBitmap(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        Bitmap image =null;
        try {
            int width = in.readInt();
            int height = in.readInt();
            Flowers one = (Flowers) in.readObject();
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inMutable = true;
            opt.inSampleSize = 1;
            opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
            image = BitmapFactory.decodeByteArray(one.imageByteArray, 0, one.imageByteArray.length, opt);
        } catch (Exception e) {
            Log.i(TAG, "IO exception " + e);
        }
        return image;
    }

public static ArrayList<DiagItem> readImagesFromResource() {
        try {
            if ( DiagArray != null )
                return DiagArray;

            ArrayList<DiagItem>
                    diagArray = new ArrayList<DiagItem>();
            Context ui = GetAppContext();

            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j1), R.raw.beethoven1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j2), R.raw.mozart1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j3), R.raw.chopin1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j4), R.raw.bach1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j5), R.raw.mozart0));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j6), R.raw.tchaikovesky1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j7), R.raw.chopin2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j8), R.raw.mozart2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j9), R.raw.mozart3));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j10), R.raw.bach1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j11), R.raw.beethoven1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j12), R.raw.mozart4));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j13), R.raw.beethoven2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j14), R.raw.mozart4));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j15), R.raw.mozart5));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j16), R.raw.beethoven8));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j17), R.raw.chopin2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j18), R.raw.beethoven3));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j19), R.raw.beethoven4));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j20), R.raw.mozart6));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j21), R.raw.mozart14));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j22), R.raw.mozart7));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j23), R.raw.beethoven6));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j24), R.raw.beethoven1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j25), R.raw.mozart8));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j26), R.raw.mozart9));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j27), R.raw.mozart12));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j28), R.raw.beethoven7));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j29), R.raw.mozart10));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j30), R.raw.beethoven10));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j31), R.raw.mozart11));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j32), R.raw.mozart2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j33), R.raw.mozart12));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j34), R.raw.beethoven8));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j35), R.raw.mozart13));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j36), R.raw.beethoven9));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j37), R.raw.mozart13));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j38), R.raw.mozart2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j39), R.raw.beethoven11));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j40), R.raw.mozart13));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j41), R.raw.beethoven1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j42), R.raw.mozart14));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j43), R.raw.mozart5));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j44), R.raw.mozart15));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j45), R.raw.mozart16));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j46), R.raw.chopin2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j47), R.raw.bach1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j48), R.raw.beethoven5));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j49), R.raw.mozart17));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j50), R.raw.beethoven1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j51), R.raw.mozart1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j52), R.raw.mozart10));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j53), R.raw.mozart2));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j54), R.raw.beethoven11));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j55), R.raw.beethoven9));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j56), R.raw.mozart4));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j57), R.raw.mozart17));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j58), R.raw.tchaikovesky1));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j59), R.raw.mozart6));
            diagArray.add(new DiagItem(LoadBitmap(R.drawable.j59), R.raw.mozart14));
            DiagArray = diagArray;
            return (DiagArray);

        } catch (Exception e) {
            Log.i(TAG, "The art pictures  initialization failed " + e);
            return null;
        }
    }

    public static ArrayList<DiagItem> readImagesFromFile() {
        try {
            Context ui = GetAppContext();
            ArrayList<DiagItem> diagArray = new ArrayList<DiagItem>();
            AssetManager assetManager = ui.getAssets();
            InputStream datafile = assetManager.open("flowerwrite.jpg");
            ObjectInputStream inFile = new ObjectInputStream(datafile);
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic2));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.samusic1));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.fleur));
            diagArray.add(new DiagItem(readBitmap(inFile), R.raw.senneville1));
            inFile.close();
            return (diagArray);

        } catch (Exception e) {
            Log.i(TAG, "The first item initialization failed " + e);
            return null;
        }
    }

    public static boolean readWingsFromResource()
    {
        try {

            ButterflyWings.AddWings(new ButterflyWings(null, null, null, null, ColorRed));
            ButterflyWings.AddWings(new ButterflyWings(null, null, null, null, ColorOrange));
            ButterflyWings.AddWings(new ButterflyWings(null, null, null, null, ColorYellow));
            ButterflyWings.AddWings(new ButterflyWings(null, null, null, null, ColorGreen));
            ButterflyWings.AddWings(new ButterflyWings(null, null, null, null, ColorBlue));
            ButterflyWings.AddWings(new ButterflyWings(null, null, null, null, ColorIndigo));
            ButterflyWings.AddWings(new ButterflyWings(null, null, null, null, ColorViolet));


            Bitmap uLeft, uRight, lLeft, lRight;

            uLeft =LoadBitmap(R.drawable.rainbowleft);
            lLeft = LoadBitmap(R.drawable.lowerleft);
            uRight  = LoadBitmap(R.drawable.rainbowright);
            lRight  = LoadBitmap(R.drawable.lowerright);
            int tWidth = DrawTouch.TARGET_WIDTH - OUTER_WING_SIZE;
            int tHeight = DrawTouch.TARGET_HEIGHT-OUTER_WING_SIZE;
            uLeft = CircleImage(tWidth, tHeight, uLeft,0,0);
            uRight = CircleImage(tWidth, tHeight, uRight, 0, 0 );


            tWidth = DrawTouch.TARGET_WING_WIDTH2 - OUTER_WING_SIZE;
            tHeight = DrawTouch.TARGET_WING_HEIGHT2 - OUTER_WING_SIZE;
            lLeft = CircleImage(tWidth, tHeight, lLeft,0,0);
            lRight = CircleImage(tWidth, tHeight, lRight, 0, 0 );
            ButterflyWings.SetRainbowWings(uLeft, uRight, lLeft, lRight, ColorGold);

            ButterflyWings.AddRainbow( ColorRed);
            ButterflyWings.AddRainbow ( ColorOrange);
            ButterflyWings.AddRainbow ( ColorYellow);
            ButterflyWings.AddRainbow( ColorGreen);
            ButterflyWings.AddRainbow( ColorBlue);
            ButterflyWings.AddRainbow( ColorIndigo);
            ButterflyWings.AddRainbow( ColorViolet);

           int wingWidth, wingHeight;
            wingWidth =DrawTouch.WING_WIDTH;
            wingHeight = DrawTouch.WING_HEIGHT;
            uRight =  LoadBitmap(R.drawable.whitell);
            uLeft = lLeft = lRight = LoadBitmap(R.drawable.whitell);
            lRight = CircleImage(wingWidth, wingHeight, lRight, 0, 0);
            wingWidth =DrawTouch.WING_WIDTH2;
            wingHeight = DrawTouch.WING_HEIGHT2;
            uLeft = CircleImage(wingWidth, wingHeight, uLeft, 0, 0);
            uRight = CircleImage(wingWidth, wingHeight, uRight, 0, 0);
            wingWidth = (int) (DrawTouch.RAINBOW_WIDTH*0.7);
            wingHeight = (int) (DrawTouch.RAINBOW_HEIGHT*0.7);
            lLeft = CircleImage(wingWidth, wingHeight, lLeft, 0, 0);
            lRight = CircleImage(wingWidth, wingHeight, lRight, 0, 0);
            ButterflyWings.SetWhiteWings(uLeft, uRight, lLeft, lRight, ColorBlack);


            wingWidth =DrawTouch.TARGET_WIDTH;
            wingHeight = DrawTouch.TARGET_HEIGHT;
            lRight = uRight =  LoadBitmap(R.drawable.evilur);
            uLeft = lLeft  = LoadBitmap(R.drawable.evilul);
            uRight  = CircleImage(wingWidth, wingHeight, uRight, 0, 0);
            uLeft = CircleImage(wingWidth, wingHeight, uLeft, 0, 0);
            lLeft = CircleImage(TARGET_WING_WIDTH2, TARGET_WING_HEIGHT2, lLeft, 0, 0);
            lRight = CircleImage(TARGET_WING_WIDTH2, TARGET_WING_HEIGHT2, lRight, 0, 0);
            ButterflyWings.SetEvilWings(uLeft, uRight, lLeft, lRight, ColorRed);
            return true;
        } catch ( Exception e) {
            Log.i(TAG, "read wing images : " + e);
            return false;
        }
    }
}

