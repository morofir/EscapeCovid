package il.co.hit.escapecovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Bat {
    public int speed =18;
    int batX = 0, batY, width, height;
    Bitmap bat;

    Bat(Resources resources) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bat = BitmapFactory.decodeResource(resources, R.drawable.bat, options);
        width = bat.getWidth();
        height = bat.getHeight();

        bat = Bitmap.createScaledBitmap(bat, width, height, false);
    }

    Bitmap getBat()
    {
            return bat;
    }

}

