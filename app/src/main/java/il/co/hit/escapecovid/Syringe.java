package il.co.hit.escapecovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Syringe
{
    public int speed=17;
    int syringeX=0,syringeY,width,height;
    Bitmap syringe;

    Syringe(Resources resources)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig=Bitmap.Config.ARGB_8888;
        syringe= BitmapFactory.decodeResource(resources,R.drawable.vacc,options);
        width = syringe.getWidth();
        height = syringe.getHeight();

        syringe = Bitmap.createScaledBitmap(syringe, width, height, false);


    }
    Bitmap getSyringe()
    {
        return syringe;
    }
}
