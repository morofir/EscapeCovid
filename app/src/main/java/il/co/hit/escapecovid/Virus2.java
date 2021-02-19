package il.co.hit.escapecovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Virus2
{
    public int speed=15;
    int virus2X=0,virus2Y,width, height;
    Bitmap virus2;

    Virus2(Resources resources)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig=Bitmap.Config.ARGB_8888;
        virus2= BitmapFactory.decodeResource(resources,R.drawable.virus2,options);
        width = virus2.getWidth();
        height = virus2.getHeight();

        virus2 = Bitmap.createScaledBitmap(virus2, width, height, false);


    }
    Bitmap getVirus2()
    {
        return virus2;
    }

}
