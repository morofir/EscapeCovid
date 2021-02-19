package il.co.hit.escapecovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Virus3
{
    public int speed=15;
    int virus3X=0,virus3Y,width, height;
    Bitmap virus3;

    Virus3(Resources resources)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig=Bitmap.Config.ARGB_8888;
        virus3= BitmapFactory.decodeResource(resources,R.drawable.virus3,options);
        width = virus3.getWidth();
        height = virus3.getHeight();
        virus3 = Bitmap.createScaledBitmap(virus3, width, height, false);
    }
    Bitmap getVirus3()
    {
        return virus3;
    }
}
