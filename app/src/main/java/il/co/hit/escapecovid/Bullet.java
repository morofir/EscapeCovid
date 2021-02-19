package il.co.hit.escapecovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {
    public int speed=20;
    int x=0,y,width,height;
    Bitmap bullet;

    Bullet(Resources res){

        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inPreferredConfig = Bitmap.Config.ARGB_8888;;
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet,op);

        width = bullet.getWidth();
        height = bullet.getHeight();


        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);
    }

    public Bullet(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }

    Bitmap getBullet()
    {
        return bullet;
    }
}
