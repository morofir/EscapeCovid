package il.co.hit.escapecovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Mask
{
    public int speed=20;
    int maskX=0,maskY,width,height;
    Bitmap mask;

    Mask(Resources resources)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig=Bitmap.Config.ARGB_8888;
        mask= BitmapFactory.decodeResource(resources,R.drawable.mask,options);
        width = mask.getWidth();
        height = mask.getHeight();

        mask = Bitmap.createScaledBitmap(mask, width, height, false);


    }
    Bitmap getMask()
    {
        return mask;
    }
}
