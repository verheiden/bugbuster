package apps.yuzaco.com.bugbuster;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by jung on 2/2/15.
 */
public class ImageAdapter extends BaseAdapter
{
    private Context mContext;

    // references to our images

    public  ImageAdapter()
    {

    }

    public int getCount()
    {
        return  Screen.GetThumbCount();
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        try {

            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(MozartApp.GetAppContext());
                imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
                imageView.setPadding(2, 2, 2, 2);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(Screen.GetThumbImage(position));
        } catch ( Exception e) {
            Log.i(Screen.TAG, "ImageAdapter:getView " + e );
        }
        return imageView;
    }

}
