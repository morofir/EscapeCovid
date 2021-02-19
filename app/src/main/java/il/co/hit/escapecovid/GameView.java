package il.co.hit.escapecovid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import static il.co.hit.escapecovid.homeActivity.isMute;


public class GameView extends View implements GestureDetector.OnGestureListener{
    private Paint scorePaint = new Paint();

    //all charecters
    private Bitmap kid[] = new Bitmap[2];
    private Bitmap life[] = new Bitmap[2];
    private Bitmap forceField[] = new Bitmap[2];

     private Bat[] bat;
     private Virus1[] greenVirus;
     private Virus2[] redVirus;
     private Virus3[] purpleVirus;
     private Syringe[] syringe;
     private Mask[] medicalMask;
     private  Coin[] coin;
     private Bullet[] bullet;

//soundbar
    private int sound, sound2, sound3,sound4;
    private SoundPool soundPool, soundPool2, soundPool3,soundPool4;



    private Bitmap imageBackground;
    private Bitmap bulletSmall;
    private int kidSpeed, kidY, canvasWidth, canvasHight;
    int kidX = 10;



    private boolean touch = false;
    private boolean isForcefield = false;
    private boolean hit = false;



    private Paint yellowPaint = new Paint();

    private int  score, kidsLifeCounter;
    private Paint greenPaint = new Paint();

    private int syringeX, syringeY, syringeSpeed = 25;
    private int bulletsNum;

    private GestureDetector gestureDetector;
    int initialX=0;
    int initialY=0;
    private final float slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private int DEFAULT_THRESHOLD = 100;
    private Boolean swipe=false;
    private int hitCount=0;
    private int isFirstTimeHit=0;
    int stage=0;
    private int counter=0;
    private int syringeNum=0;



    public GameView(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
            soundPool2 = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
            soundPool3 = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
            soundPool4 = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();

        }else {
            soundPool2 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            soundPool3 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            soundPool4 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        }
        sound = soundPool.load(context, R.raw.coin, 1);
        sound2 = soundPool2.load(context, R.raw.fail, 1);
        sound3 = soundPool3.load(context, R.raw.gun, 1);
        sound4 = soundPool3.load(context, R.raw.gameover, 1);


        kid[0] = BitmapFactory.decodeResource(getResources(), R.drawable.kid1);
        kid[1] = BitmapFactory.decodeResource(getResources(), R.drawable.kid2);
        imageBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(60);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);


        forceField[0] = BitmapFactory.decodeResource(getResources(), R.drawable.forcefield1);
        forceField[1] = BitmapFactory.decodeResource(getResources(), R.drawable.forcefield2);


        kidY = 550;
        score = 0;
        kidsLifeCounter = 3;
        bulletsNum = 0; //start with no bullets
        bulletSmall = BitmapFactory.decodeResource(getResources(), R.drawable.bullet1);


        //enemy-Bat
        bat = new Bat[1];
        Bat bat1 = new Bat(getResources());
        bat[0] = bat1;

        //enemy-Virus1
        greenVirus = new Virus1[1];
        Virus1 virus1 = new Virus1(getResources());
        greenVirus[0] = virus1;

        //enemy-Virus2
        redVirus = new Virus2[1];
        Virus2 virus2 = new Virus2(getResources());
        redVirus[0] = virus2;

        //enemy-Virus3
        purpleVirus = new Virus3[1];
        Virus3 virus3 = new Virus3(getResources());
        purpleVirus[0] = virus3;


        //Syringe
        syringe = new Syringe[1];
        Syringe syringe1 = new Syringe(getResources());
        syringe[0] =syringe1;

        //Mask
        medicalMask = new Mask[1];
        Mask mask = new Mask(getResources());
        medicalMask[0] = mask;

        //coin
        coin = new Coin[1];
        Coin coin1 = new Coin(getResources());
        coin[0] = coin1;

        //bullets
        bullet = new Bullet[1];
        Bullet bullet1 = new Bullet(getResources());
        bullet[0] = bullet1;



    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Rect dest = new Rect(0, 0, getWidth(), getHeight());
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(imageBackground, null, dest, paint);
        canvasWidth = canvas.getWidth();
        canvasHight = canvas.getHeight();
        int minKidY = kid[0].getHeight();
        int maxKidY = canvasHight - kid[0].getHeight() * 3;

        if (kidY < minKidY)
            kidY = minKidY;

        if (kidY > maxKidY)
            kidY = maxKidY;
        else
            ifTouchedGround();

        kidSpeed = kidSpeed + 2;

        kidY = kidY + kidSpeed;


        ifTouchedGround();

        if (touch) {
            if (!isForcefield)
                canvas.drawBitmap(kid[1], kidX, kidY, null);
            else
                canvas.drawBitmap(forceField[1], kidX, kidY, paint); // draw kid with force field

            touch = false;
        } else {
            if (!isForcefield)
                canvas.drawBitmap(kid[0], kidX, kidY, null);
            else
                canvas.drawBitmap(forceField[0], kidX, kidY, paint); // draw kid with force field

        }


        //virus1
        for (Virus1 virus1 : greenVirus)
        {
            //For an increasing level of difficulty
            isVirus1LvlUp(virus1);



            virus1.virus1X = virus1.virus1X - virus1.speed;
            if (hitBallCheck(virus1.virus1X, virus1.virus1Y))
                if (!isForcefield) {
                    kidsLifeCounter--;
                    if (!isMute)
                        soundPool2.play(sound2, 1, 1, 0, 0, 1);

                    virus1.virus1X = -100;
                    if (kidsLifeCounter == 1) {
                        Toast.makeText(getContext(), getResources().getString(R.string.toastlastchance), Toast.LENGTH_SHORT).show();

                    }
                    dieAction();
                } else {
                    virus1.virus1X = -100;
                    Toast.makeText(getContext(), getResources().getString(R.string.shild), Toast.LENGTH_SHORT).show();
                }
            if (virus1.virus1X < 0) {
                virus1.virus1X = canvasWidth + 21;
                virus1.virus1Y = (int) Math.floor(Math.random() * (maxKidY - minKidY)) + minKidY;
            }
        }
        for (Virus1 virus1 : greenVirus)
            canvas.drawBitmap(virus1.getVirus1(), virus1.virus1X, virus1.virus1Y, paint);

        //virus2
        for (Virus2 virus2 : redVirus)
        {
            isVirus2LvlUp(virus2);


                virus2.virus2X = virus2.virus2X - virus2.speed;
            if (hitBallCheck(virus2.virus2X, virus2.virus2Y))
                if (!isForcefield) {
                    kidsLifeCounter--;
                    if (!isMute)
                        soundPool2.play(sound2, 1, 1, 0, 0, 1);
                    virus2.virus2X = -100;
                    if (kidsLifeCounter == 1) {
                        Toast.makeText(getContext(), getResources().getString(R.string.toastlastchance), Toast.LENGTH_SHORT).show();

                    }
                    dieAction();
                } else {
                    virus2.virus2X = -100;
                    Toast.makeText(getContext(), getResources().getString(R.string.shild), Toast.LENGTH_SHORT).show();
                }
            if (virus2.virus2X < 0) {
                virus2.virus2X = canvasWidth + 21;
                virus2.virus2Y = (int) Math.floor(Math.random() * (maxKidY - minKidY)) + minKidY;
            }
        }
        for (Virus2 virus2 : redVirus)
            canvas.drawBitmap(virus2.getVirus2(), virus2.virus2X, virus2.virus2Y, paint);

        //virus3
        if(score>=70)
        {
            for (Virus3 virus3 : purpleVirus)
            {
                isVirus3LvlUp(virus3);
                virus3.virus3X = virus3.virus3X - virus3.speed;
                if (hitBallCheck(virus3.virus3X, virus3.virus3Y))
                    if (!isForcefield) {
                        if (!isMute)
                            soundPool2.play(sound2, 1, 1, 0, 0, 1);
                        kidsLifeCounter--;
                        virus3.virus3X = -100;
                        if (kidsLifeCounter == 1) {
                            Toast.makeText(getContext(), getResources().getString(R.string.toastlastchance), Toast.LENGTH_SHORT).show();
                        }
                        dieAction();
                    } else {
                        virus3.virus3X = -100;
                        Toast.makeText(getContext(), getResources().getString(R.string.shild), Toast.LENGTH_SHORT).show();
                    }
                if (virus3.virus3X < 0) {
                    virus3.virus3X = canvasWidth + 21;
                    virus3.virus3Y = (int) Math.floor(Math.random() * (maxKidY - minKidY)) + minKidY;
                }
            }
            for (Virus3 virus3 : purpleVirus)
                canvas.drawBitmap(virus3.getVirus3(), virus3.virus3X, virus3.virus3Y, paint);
        }

        ///Bat
        if (score > 130)
        {

            for (Bat bat1 : bat) {
                isBatLvlUp(bat1);

                bat1.batX = bat1.batX - bat1.speed;
                if (hitBallCheck(bat1.batX, bat1.batY))
                    if (!isForcefield) {
                        if (!isMute)
                            soundPool2.play(sound2, 1, 1, 0, 0, 1);
                        kidsLifeCounter--;

                        bat1.batX = -100;
                        if (kidsLifeCounter == 1) {

                            Toast.makeText(getContext(), getResources().getString(R.string.toastlastchance), Toast.LENGTH_SHORT).show();

                        }
                        dieAction();
                    } else {
                        bat1.batX = -100;
                        Toast.makeText(getContext(), getResources().getString(R.string.toastlastchance), Toast.LENGTH_SHORT).show();

                    }
                if (bat1.batX < 0) {
                    bat1.batX = canvasWidth + 21;
                    bat1.batY = (int) Math.floor(Math.random() * (maxKidY - minKidY)) + minKidY;
                }
            }
            for (Bat bat1 : bat)
                canvas.drawBitmap(bat1.getBat(), bat1.batX, bat1.batY, paint);
        }


        //syringe  GIVES U force field! + 5 syringes gives u life up
        for (Syringe syringe1 : syringe) {
            syringe1.syringeX = syringe1.syringeX - syringe1.speed;
            isSyringeLvlUp(syringe1);

            if (hitBallCheck(syringe1.syringeX, syringe1.syringeY))
            {
                if (!isMute)
                    soundPool.play(sound, 1, 1, 0, 0, 1);
                syringeNum++;
                score+=5;
                syringe1.syringeX = -100;

                //life++ after 5 syringes
                if(syringeNum == 5 && kidsLifeCounter<3)
                {
                    kidsLifeCounter++;
                    Toast.makeText(getContext(), getResources().getString(R.string.lifeUp), Toast.LENGTH_SHORT).show();
                    syringeNum=0;
                }

                //force field gives protection for 3 seconds
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        isForcefield = true;
                    }

                    @Override
                    public void onFinish() {
                        isForcefield = false;
                    }
                }.start();
            }

            if (syringe1.syringeX < 0) {
                syringe1.syringeX = canvasWidth + 21;
                syringe1.syringeY = (int) Math.floor(Math.random() * (maxKidY - minKidY)) + minKidY;
            }
        }
        for (Syringe syringe1 : syringe)
            canvas.drawBitmap(syringe1.getSyringe(), syringe1.syringeX, syringe1.syringeY, paint);


        //Mask
        //mask scores 15 pts and 1 bullet!
        for (Mask mask : medicalMask) {
            mask.maskX = mask.maskX - mask.speed;
            if (hitBallCheck(mask.maskX, mask.maskY)) {
                if (!isMute)
                    soundPool.play(sound, 1, 1, 0, 0, 1);
                score = score + 15;
                mask.maskX = -100;
                if (bulletsNum != 5)
                    bulletsNum++;
            }

            if (mask.maskX < 0) {
                mask.maskX = canvasWidth + 21;
                mask.maskY = (int) Math.floor(Math.random() * (maxKidY - minKidY)) + minKidY;
            }
        }
        for (Mask mask : medicalMask)
            canvas.drawBitmap(mask.getMask(), mask.maskX, mask.maskY, paint);

        // gold coin score+=20
        for (Coin coin1 : coin) {
            isCoinLvlUp(coin1);

            coin1.x = coin1.x - coin1.speed;

            if (hitBallCheck(coin1.x, coin1.y)) {
                if (!isMute)
                    soundPool.play(sound, 1, 1, 0, 0, 1);
                score = score + 20;
                coin1.x = -100;
            }

            if (coin1.x < 0) {
                coin1.x = canvasWidth + 21;
                coin1.y = (int) Math.floor(Math.random() * (maxKidY - minKidY)) + minKidY;
            }
        }
        for (Coin coin1 : coin)
            canvas.drawBitmap(coin1.getCoins(), coin1.x, coin1.y, paint);

        //bullets

        for (Bullet bullet1 : bullet) {
            hit = false;
            bullet1.x = (bullet1.x + 15);  //moving bullet


            if (isBullethitVirus1(bullet1.x+15, bullet1.y,greenVirus[0]))
            {
                greenVirus[0].virus1X = -100;
            }
            if (isBullethitVirus2(bullet1.x+15, bullet1.y,redVirus[0]))
            {
                redVirus[0].virus2X = -100;
            }
            if (isBullethitVirus3(bullet1.x+15, bullet1.y,purpleVirus[0]))
            {
                purpleVirus[0].virus3X = -100;
            }
            if (isBullethitBat(bullet1.x+15, bullet1.y,bat[0]))
            {
                bat[0].batX = -100;
            }


            if (bullet1.x > canvasWidth && swipe) {
                bullet1.x = kidX %1200;
                bullet1.y = kidY;
            }
        }
        if(hitCount>0)
        {
            score=hitCount*5;
            isFirstTimeHit = 0;
        }


        for (Bullet bullet1 : bullet)
            if(bulletsNum>0 && swipe) //swipe from left to right
                canvas.drawBitmap(bullet1.getBullet(), bullet1.x, bullet1.y, paint);


        //Strings
        String string = getResources().getString(R.string.scoreTxt);
        canvas.drawText(string + "" + score, 20, 60, scorePaint);

        String string2 = getResources().getString(R.string.numOfBullets);
        canvas.drawText(string2 + "" +bulletsNum, 20, 120, scorePaint);


        //player can have only 5 bullets
        for(int j=0;j<5;j++){
            int xx= (int) (595 + bulletSmall.getWidth() * 1.3 * j);
            int yy=140;
            if(j < bulletsNum)
                canvas.drawBitmap(bulletSmall,xx,yy,null);
        }

        //for 3 lives in game
        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < kidsLifeCounter) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
        ifTouchedGround();



        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                ifTouchedGround();

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    touch = true;
                    kidSpeed = -22;
                    initialX = (int) event.getX();
                    initialY = (int) event.getY();
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    int currentX = (int) event.getX();
                    int currentY = (int) event.getY();
                    int offsetX = currentX - initialX;
                    int offsetY = currentY - initialY;
                    if (Math.abs(offsetX) > slop) {
                        if (offsetX > DEFAULT_THRESHOLD) {
                            counter=1;

                            if (bulletsNum > 0) {
                                new CountDownTimer(2200, 2199) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        swipe = true;
                                        if (!isMute)
                                            soundPool3.play(sound3, 1, 1, 0, 0, 1);


                                    }

                                    @Override
                                    public void onFinish() {
                                        if(counter==1)
                                            bulletsNum--;
                                        counter--;

                                        swipe=false;
                                        if(bulletsNum<0)
                                            bulletsNum=0;

                                    }
                                }.start();


                            }

                            // TODO :: Do Right to Left action!
                        } else if (offsetX < -DEFAULT_THRESHOLD) {
//                            Toast.makeText(getContext(), "shoot", Toast.LENGTH_SHORT).show();

                            // TODO :: Do Left to Right action!
                        }
                    }
                    if (Math.abs(offsetY) > slop) {
                        if (offsetY > DEFAULT_THRESHOLD) {
//                            Toast.makeText(getContext(), "Bottom to Top", Toast.LENGTH_SHORT).show();

                            // TODO :: Do Bottom to Top action!
                        } else if (offsetY < -DEFAULT_THRESHOLD) {
                            // TODO :: Do Top to Bottom action!
//                            Toast.makeText(getContext(), "top to bottom", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    // Do nothing!
                }

                     return true;
            }
        });

    }

    private void isBatLvlUp(Bat bat) {
        //For an increasing level of difficulty
        if(score>=150&& score<180)
            bat.speed=24;
        if(score>=180&& score<210)
            bat.speed=28;
        if(score>=210&& score<250)
            bat.speed=32;
        if(score>=250)
            bat.speed=38;
    }


    //the coordinates are the bullets
    //y is from 1200 to 0
    private boolean isBullethitVirus1(int x, int y, Virus1 v1) {
        return (swipe&&(y-88<v1.virus1Y && v1.virus1Y<y+88 && x-50<v1.virus1X&& v1.virus1X<x+50));

    }
    private boolean isBullethitVirus2(int x, int y,Virus2 v2) {
        return (swipe&&(y-88<v2.virus2Y && v2.virus2Y<y+88 && x-100<v2.virus2X&& v2.virus2X<x+100));
    }

    private boolean isBullethitVirus3(int x, int y, Virus3 v3) {
        return (swipe && (y - 98 < v3.virus3Y && v3.virus3Y < y + 98 && x - 100 < v3.virus3X && v3.virus3X < x + 100));
    }
        private boolean isBullethitBat(int x, int y, Bat bat) {
            return (swipe && (y - 50 < bat.batY && bat.batY < y + 110 && x - 100 < bat.batX && bat.batX < x + 100));

        }


        private void isCoinLvlUp(Coin coin1)
        {
            if(score>=80&& score<150)
                coin1.speed=16;
            if(score>=150&& score<200)
                coin1.speed=19;
            if(score>=200&& score<260)
                coin1.speed=23;
            if(score>=260&& score<320)
                coin1.speed=27;
            if(score>=320&& score<400)
                coin1.speed=32;
            if(score>=400&& score<470)
                coin1.speed=35;
            if(score>=470)
                coin1.speed=40;
        }
    private void isVirus1LvlUp(Virus1 virus)
    {
        if(score>=80&& score<150)
            virus.speed=16;
        if(score>=150&& score<200)
            virus.speed=19;
        if(score>=200&& score<260)
            virus.speed=23;
        if(score>=260&& score<320)
            virus.speed=27;
        if(score>=320&& score<400)
            virus.speed=32;
        if(score>=400&& score<470)
            virus.speed=35;
        if(score>=470)
            virus.speed=40;
    }

    private void isVirus2LvlUp(Virus2 virus)
    {
        if(score>=80&& score<150)
            virus.speed=18;
        if(score>=150&& score<200)
            virus.speed=21;
        if(score>=200&& score<260)
            virus.speed=25;
        if(score>=260&& score<320)
            virus.speed=27;
        if(score>=320&& score<400)
            virus.speed=32;
        if(score>=400&& score<470)
            virus.speed=35;
        if(score>=470)
            virus.speed=40;
    }

    private void isVirus3LvlUp(Virus3 virus)
    {
        if(score>=80&& score<150)
            virus.speed=20;
        if(score>=150&& score<200)
            virus.speed=23;
        if(score>=200&& score<260)
            virus.speed=26;
        if(score>=260&& score<320)
            virus.speed=29;
        if(score>=320&& score<400)
            virus.speed=32;
        if(score>=400&& score<470)
            virus.speed=36;
        if(score>=470)
            virus.speed=40;
    }

    private void isSyringeLvlUp(Syringe syr)
    {
        if(score>=80&& score<150)
            syr.speed=20;
        if(score>=150&& score<200)
            syr.speed=23;
        if(score>=200&& score<260)
            syr.speed=26;
        if(score>=260&& score<320)
            syr.speed=29;
        if(score>=320&& score<400)
            syr.speed=32;
        if(score>=400&& score<470)
            syr.speed=36;
        if(score>=470)
            syr.speed=40;
    }




    //        if kid touch the ground live-1
    private void ifTouchedGround() {
        if (kidY > canvasHight - kid[0].getHeight() * 3 || kidY > canvasHight - kid[1].getHeight() * 3)  //bottom left corner is the ground(floor)
        {
            new CountDownTimer(1500, 1500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (!isForcefield) {
                        kidsLifeCounter--; //once
                        Toast.makeText(getContext(), getResources().getString(R.string.floorIsLava), Toast.LENGTH_SHORT).show();
                        dieAction();
                        kidY = kid[0].getHeight();
                        isForcefield= true; //force field for remaining time
                    }

                }
                @Override
                public void onFinish() {
                    isForcefield = false;

                }
            }.start();
        }
    }


    public boolean hitBallCheck(int x, int y) { //one of the characters get hit by an object
        return ((kidX < x && x < (kid[0].getWidth() + kidX) && kidY < y && y < (kidY + kid[0].getHeight()) || (kidX < x && x < (kid[1].getWidth() + kidX) && kidY < y && y < (kidY + kid[1].getHeight()))));
    }

    public void dieAction() {
        if (kidsLifeCounter == 0)
        {
            if (!isMute)
                soundPool4.play(sound4, 1, 1, 0, 0, 1);
            Intent gameOver = new Intent(getContext(), GameOverActivity.class);
            gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            gameOver.putExtra("score", score);

            getContext().startActivity(gameOver);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
