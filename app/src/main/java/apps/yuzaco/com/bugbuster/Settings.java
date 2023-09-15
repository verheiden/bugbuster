package apps.yuzaco.com.bugbuster;

import android.content.Context;

/**
 * Created by jung on 9/24/15.
 */
public class Settings {
    boolean mMusic = true;
    Context mainP;
    public Settings(Context ui)
    {
          mainP = ui;
    }

    public void putBoolean(String name, boolean flag)
    {
        mMusic = flag;
    }

    public boolean getBoolean(String name)
    {
        return mMusic;
    }
}
