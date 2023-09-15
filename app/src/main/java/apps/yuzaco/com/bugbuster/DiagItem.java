package apps.yuzaco.com.bugbuster;

import android.graphics.Bitmap;

/**
 * Created by jung on 8/8/14.
 */
public class DiagItem {
    private Bitmap image;
    private int music;
    public DiagItem( Bitmap picture, int sonata)
    {
        image =  picture;
        music = sonata;
    }

    public int getMusic() { return music;}
    public Bitmap  getImage() {
        return image;
    }
}