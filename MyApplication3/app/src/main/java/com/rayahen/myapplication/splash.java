package com.example.omar.helpinghand;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {
    private static int splash_time_out=4000;

    Animation anmi;
 //   TextView load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       getSupportActionBar().hide();
       // load= (TextView) findViewById(R.id.load);
       ImageView logo= (ImageView) findViewById(R.id.imgee);

        anmi= AnimationUtils.loadAnimation(this,R.anim.load);
        // load.setAnimation(anmi);
       logo.setAnimation(anmi);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent=new Intent(splash.this,login.class);
                //homeactivity name of second layout.java
                startActivity(homeintent);
                finish();
            }
        },splash_time_out);
    }
}
