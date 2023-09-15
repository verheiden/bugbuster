package apps.yuzaco.com.bugbuster;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jung on 8/15/16.

 */
public class ImmortalNotes {
    static ImmortalNotes piano = null;
    List<Integer> list;
    int index = 0;
    public ImmortalNotes(){
    }
   public static ImmortalNotes instance()
   {
       if ( piano == null )
       {
           piano = new ImmortalNotes();
       }
       return piano;
   }    
       
    void  init(Context context, SoundPool soundPool)
    {
        list = new ArrayList<Integer>();
        index = 0;
        try {
 /*           list.add( soundPool.load(context, R.raw.m1, 1 ));
            list.add( soundPool.load(context, R.raw.m2, 1 ));
            list.add( soundPool.load(context, R.raw.m3, 1 ));
            list.add( soundPool.load(context, R.raw.m4, 1 ));
            list.add( soundPool.load(context, R.raw.m5, 1 ));
            list.add( soundPool.load(context, R.raw.m6, 1 ));
            list.add( soundPool.load(context, R.raw.m7, 1 ));
            list.add( soundPool.load(context, R.raw.m9, 1 ));
            list.add( soundPool.load(context, R.raw.m10, 1 ));
            list.add( soundPool.load(context, R.raw.m13, 1 ));
            list.add( soundPool.load(context, R.raw.m14, 1 ));
            list.add( soundPool.load(context, R.raw.m15, 1 ));
            list.add( soundPool.load(context, R.raw.m16, 1 ));
            list.add( soundPool.load(context, R.raw.m17, 1 ));
            list.add( soundPool.load(context, R.raw.m18, 1 ));
            list.add( soundPool.load(context, R.raw.m19, 1 ));
            list.add( soundPool.load(context, R.raw.m20, 1 ));
            list.add( soundPool.load(context, R.raw.m21, 1 ));
            list.add( soundPool.load(context, R.raw.m22, 1 ));
            list.add( soundPool.load(context, R.raw.m23, 1 ));
            list.add( soundPool.load(context, R.raw.m24, 1 ));
            list.add( soundPool.load(context, R.raw.m25, 1 ));
            list.add( soundPool.load(context, R.raw.m26, 1 ));
            list.add( soundPool.load(context, R.raw.m27, 1 ));
            list.add( soundPool.load(context, R.raw.m28, 1 ));
            list.add( soundPool.load(context, R.raw.m29, 1 ));
            list.add( soundPool.load(context, R.raw.m30, 1 ));
            list.add( soundPool.load(context, R.raw.m31, 1 ));
            list.add( soundPool.load(context, R.raw.m32, 1 ));
            list.add( soundPool.load(context, R.raw.m33, 1 ));
            list.add( soundPool.load(context, R.raw.m34, 1 ));
            list.add( soundPool.load(context, R.raw.m35, 1 ));
            list.add( soundPool.load(context, R.raw.m36, 1 ));
            list.add( soundPool.load(context, R.raw.m37, 1 ));
            list.add( soundPool.load(context, R.raw.m38, 1 ));
            list.add( soundPool.load(context, R.raw.m39, 1 ));
            list.add( soundPool.load(context, R.raw.m40, 1 ));
            list.add( soundPool.load(context, R.raw.m40, 1 ));
            list.add( soundPool.load(context, R.raw.m41, 1 ));
            list.add( soundPool.load(context, R.raw.m42, 1 ));
            list.add( soundPool.load(context, R.raw.m43, 1 ));
            list.add( soundPool.load(context, R.raw.m44, 1 ));
            list.add( soundPool.load(context, R.raw.m45, 1 ));
            list.add( soundPool.load(context, R.raw.m46, 1 ));
            list.add( soundPool.load(context, R.raw.m47, 1 ));
            list.add( soundPool.load(context, R.raw.m48, 1 ));
            list.add( soundPool.load(context, R.raw.m49, 1 ));
            list.add( soundPool.load(context, R.raw.m50, 1 ));
            list.add( soundPool.load(context, R.raw.m51, 1 ));
            list.add( soundPool.load(context, R.raw.m52, 1 ));
            list.add( soundPool.load(context, R.raw.m55, 1 ));
            list.add( soundPool.load(context, R.raw.m56, 1 ));
            list.add( soundPool.load(context, R.raw.m57, 1 ));
            list.add( soundPool.load(context, R.raw.m58, 1 ));
            list.add( soundPool.load(context, R.raw.m59, 1 ));
            list.add( soundPool.load(context, R.raw.m60, 1 ));
            list.add( soundPool.load(context, R.raw.m61, 1 ));
            list.add( soundPool.load(context, R.raw.m62, 1 ));
            list.add( soundPool.load(context, R.raw.m63, 1 ));
    */
        }
        catch (Exception e)
        {
            Log.i(DrawTouch.TAG, "Immortal.init " + e );
        }
    }
    public int getNextTune()
    {
        int  tNote = -1;
        try
        {
            tNote = list.get(index);
            index = ( index + 1)%(list.size());
        }
        catch ( Exception e)
        {
            Log.i(DrawTouch.TAG, "getColor : " + e);
        }
        return tNote;
    }    
        
}
