package apps.yuzaco.com.bugbuster;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import apps.yuzaco.com.bugbuster.R;

public class DiagAdapter extends ArrayAdapter<DiagItem>
{
    private Context mContext;
    private int mSelectedItem = -1;
    private static final String TAG = "ZenFlowers";

    public DiagAdapter(Screen c, int resourceId, List<DiagItem> values)
    {
        super(c, resourceId, values);
        mContext  = c;
    }

    /* private view holder class */
    private class ViewHolder {
        TextView haiku;
        ImageView definition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try {
            ViewHolder holder = null;
            DiagItem item = (DiagItem) getItem(position);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.infoitem, null);
                holder.definition = (ImageView) convertView.findViewById(R.id.definition);
                holder.haiku = (TextView) convertView.findViewById(R.id.haiku);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.definition.setImageBitmap(item.getImage());
            return convertView;
        }
        catch (OutOfMemoryError e)
        {
            Log.i(TAG, "running out of memory, now what to do" + e);
        }
        catch  (Exception     e)
        {
            Log.i(TAG, "DiagAdapter exception " + e );
        }
        return null;
    }
    public void setSelectedItem(int index)
    {
        mSelectedItem = index;
    }
    public int getSelectedItem()
    {
        return mSelectedItem;
    }
}