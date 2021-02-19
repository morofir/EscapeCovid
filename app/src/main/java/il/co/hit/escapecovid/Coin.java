package il.co.hit.escapecovid;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Coin {
    public int speed=13;
    int x=0,y,width,height;
    Bitmap coin1;
    Coin(Resources resources){
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inPreferredConfig = Bitmap.Config.ARGB_8888;;
        coin1= BitmapFactory.decodeResource(resources,R.drawable.coin,op);

        width=coin1.getWidth();
        height=coin1.getHeight();

        coin1=Bitmap.createScaledBitmap(coin1,width,height,false);

    }
    Bitmap getCoins()
    {
        return coin1;
    }



}

