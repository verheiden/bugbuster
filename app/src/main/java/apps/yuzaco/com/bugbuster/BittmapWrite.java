package apps.yuzaco.com.bugbuster;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by jung on 7/11/15.
 */
class BitmapWriteTask  extends Thread
{

    private final String TAG="BitmapWrite";
    List<DiagItem> mDiagArray = null;

    public BitmapWriteTask(List<DiagItem> diagArray)
    {
        mDiagArray = diagArray;
    }

   public void run()
    {
        super.run();
        writeImagesToFile();
    }

   private void writeImagesToFile()
    {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File datafile = new File(path, "flowerwrite.jpg");

        try {
            if (!datafile.exists())
                datafile.createNewFile();
            FileOutputStream stream = new FileOutputStream(datafile);
            ObjectOutputStream out = new ObjectOutputStream(stream);
            for(DiagItem item: mDiagArray) {
                writeBitmap(out, item.getImage(), false);
            }
            out.flush();
            out.close();
        }
        catch ( Exception e)
        {
            Log.i(TAG, "Writing out the serialized bitmap failed " + e );
        }
    }

    private void writeBitmap(ObjectOutputStream out, Bitmap image, Boolean png) throws IOException
    {
        int bytes = image.getRowBytes();
        int size =  image.getHeight() * bytes;


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        out.writeInt(image.getWidth());
        out.writeInt(image.getHeight());
        if ( png )
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        else
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Flowers one = new Flowers();
        one.imageByteArray = stream.toByteArray();
        out.writeObject(one);
    }

}
