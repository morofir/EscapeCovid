    package il.co.hit.escapecovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {
    private Button btnStartAgain,btnHighScore,btnSubmit;
    private TextView scoreView;
    private String score,string;
    private int intscore;
    private EditText name_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        score = getIntent().getExtras().get("score").toString();
        btnStartAgain = (Button) findViewById(R.id.startOver);
        btnHighScore = (Button) findViewById(R.id.highScore);
        btnSubmit = (Button) findViewById(R.id.submit_btn);
        name_et = (EditText) findViewById(R.id.tvHighScore);


        ImageView kid = findViewById(R.id.kid);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rorate);
        kid.startAnimation(animation);


        scoreView = (TextView) findViewById(R.id.score);

        SharedPreferences pref = getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor = pref.edit();
        intscore = Integer.parseInt(score);
        editor.putInt("LastScore",intscore);
        editor.apply();



        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, highScoreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!name_et.getText().toString().matches(""))
                {
                    Toast.makeText(GameOverActivity.this, getResources().getString(R.string.submit2), Toast.LENGTH_SHORT).show();
                    String name = name_et.getText().toString();
                    editor.putString("LastName",name);
                    editor.apply();

                }else {
                    Toast.makeText(GameOverActivity.this, getResources().getString(R.string.nameNull), Toast.LENGTH_SHORT).show();

                }


            }
        });

        String string = getResources().getString(R.string.pointsgame);
        scoreView.setText(string +score);

    }
}