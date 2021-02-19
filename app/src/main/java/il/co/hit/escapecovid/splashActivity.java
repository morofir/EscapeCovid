package il.co.hit.escapecovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //open the app with splash activity for 2.5 seconds
        Thread thread=new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(2500);
                }
                catch (Exception exc)
                {
                    exc.printStackTrace();
                }
                finally
                {
                    Intent mainIntent=new Intent(splashActivity.this,homeActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}