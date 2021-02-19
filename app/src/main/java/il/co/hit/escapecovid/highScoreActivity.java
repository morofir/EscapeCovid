package il.co.hit.escapecovid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static il.co.hit.escapecovid.homeActivity.isMute;
import static il.co.hit.escapecovid.homeActivity.mediaPlayer;

public class highScoreActivity extends AppCompatActivity {

    private TextView tv_score,tv_names;
    int lastScore;
    int best1,best2,best3;
    String name1;
    String name2;
    String name3;
    String listline1;
    String listline2;
    String listline3;
    String lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
        isMute=true;



        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(highScoreActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });

        //load old prefs

//        this.getSharedPreferences("PREFS", 0).edit().clear().commit(); to clear SF
        SharedPreferences pref = getSharedPreferences("PREFS",0);
        lastScore =pref.getInt("LastScore",0);
        lastName = pref.getString("LastName", "Moshe");

        name1 = pref.getString("name1","Mor");
        name2 = pref.getString("name2","Danny");
        name3 = pref.getString("name3","Oshri");

        best1 =pref.getInt("best1",0);
        best2 =pref.getInt("best2",0);
        best3 =pref.getInt("best3",0);
        ListView listView=(ListView)findViewById(R.id.highscore_listview);

        //Replace spots if there is high score

        if(lastScore>best1)
        {
            int temp = best1;
            int temp2 = best2;
            String tempString = name1;
            String tempString2 = name2;
            //switch
            name1 = lastName;
            best1 = lastScore;
            best2=temp;
            name2 = tempString;
            best3=temp2;
            name3 = tempString2;
        }
        else if(lastScore>best2)
        {
            int temp = best2;
            String tempString = name2;
            best2 = lastScore;
            name2 = lastName;
            best3 = temp;
            name3 = tempString;
        }
        else if(lastScore>best3)
        {
            best3 = lastScore;
            name3 = lastName;
        }

        if(lastScore!=0 &&lastScore<best3 && lastScore<best2 && lastScore<best1) //no high score
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Sorry, no high score.");
            builder1.setCancelable(true);

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("best1",best1);
        editor.putInt("best2",best2);
        editor.putInt("best3",best3);
        editor.putString("name1",name1);
        editor.putString("name2",name3);
        editor.putString("name3",name3);
        editor.apply();

            listline1=name1+"            "+best1;
            listline2=name2+"            "+best2;
            listline3=name3+"            "+best3;

        String[] names={listline1,listline2,listline3};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.medalcell,R.id.textlist,names);
        listView.setAdapter(adapter);


        ImageView gold = findViewById(R.id.gold);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        gold.startAnimation(animation);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),homeActivity.class);
        startActivity(intent);
        finish();
    }


}