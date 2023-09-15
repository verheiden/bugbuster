package apps.yuzaco.com.bugbuster;

import android.graphics.Bitmap;

/**
 * Created by jung on 12/18/15.
 */
public class PlayButton {
    private Bitmap image;
    private Boolean pushed = false;
    private int x;
    private int y;

    public PlayButton(Bitmap bitmap, int XX, int YY)
    {
        image = bitmap;
        x = XX;
        y = YY;
    }
    public Bitmap getImage()
    {
        return image;
    }

    public int getWidth() { return image.getWidth();}
    public int getHeight() {return image.getHeight();}
    public boolean pushed(int XX, int YY)
    {
        if ( (( XX >= x) && ( XX <= (x + image.getWidth()))) && ((YY >= y) && (YY <= ( y+image.getHeight()))))
        {
            return(true);
        }
        else
           return(false);
    }
}
