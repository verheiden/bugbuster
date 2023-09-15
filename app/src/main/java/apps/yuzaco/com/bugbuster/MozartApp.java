package apps.yuzaco.com.bugbuster;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;


/**
 * Created by jung on 11/10/16.
 */

public class MozartApp extends Application
{
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MozartApp.context = getApplicationContext();
        //makeActionOverflowMenuShown();
    }
    public static void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(MozartApp.context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d(Screen.TAG, e.getLocalizedMessage());
        }
    }
    static public int GetSavedPreferences(String key)
    {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context);
        return (sPref.getInt(key, 0));
    }

    static public void SavePreferences(String key, int value)
    {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    static public Context GetAppContext() {return context;}
}
