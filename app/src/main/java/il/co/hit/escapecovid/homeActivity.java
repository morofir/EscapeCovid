package il.co.hit.escapecovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class homeActivity extends AppCompatActivity
{
    public static boolean isMute;
    boolean exit=true;
    public static MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.backgroundsound);
        if(isMute==false) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button startButton = (Button) findViewById(R.id.play);
        Button highScorebtn = (Button) findViewById(R.id.highScores);
        ImageView soundBtn = (ImageView) findViewById(R.id.volumeBtn);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
//        virus.startAnimation(animation);
        soundBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isMute = !isMute;
                if (isMute) {
                    soundBtn.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    mediaPlayer.pause();
                } else {
                    soundBtn.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }


            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        highScorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, highScoreActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button exitbtn = (Button) findViewById(R.id.exitbuttin);
        exitbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(exit) {
                    Toast.makeText(homeActivity.this, getResources().getString(R.string.tos_txt1), Toast.LENGTH_LONG).show();
                    exit=false;
                }
                else {
                    finish();
                    System.exit(0);
                }

            }
        });
        Button instBtn = (Button) findViewById(R.id.inst);
        instBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, Instructions.class);
                isMute = true;
                startActivity(intent);
                finish();
            }
        });

    }

}