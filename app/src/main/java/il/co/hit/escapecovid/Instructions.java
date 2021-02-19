package il.co.hit.escapecovid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static il.co.hit.escapecovid.homeActivity.isMute;

public class Instructions  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
        isMute=true;

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),homeActivity.class);
        startActivity(intent);
        finish();
    }
}
