package il.co.hit.escapecovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Virus1
{
    public int speed=15;
    int virus1X=0,virus1Y,width, height;
    Bitmap virus1;

    Virus1(Resources resources)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig=Bitmap.Config.ARGB_8888;
        virus1= BitmapFactory.decodeResource(resources,R.drawable.virus1,options);
        width = virus1.getWidth();
        height = virus1.getHeight();

        virus1 = Bitmap.createScaledBitmap(virus1, width, height, false);


    }
    Bitmap getVirus1()
    {
        return virus1;
    }

}
