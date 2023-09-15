package apps.yuzaco.com.bugbuster;

import android.graphics.Bitmap;

/**
 * Created by jung on 11/21/16.
 */

public class instruction_item {
    private Bitmap cacoon;
    private Bitmap butterfly;
    private String description;


    public instruction_item( String name, Bitmap iCacoon, Bitmap iFly)
    {
        cacoon = iCacoon;
        butterfly =iFly;
        description = name;
    }
    public String getTitle() { return description;}
    public Bitmap getCacoon() { return cacoon;}
    public Bitmap getFly() { return butterfly;}
}
