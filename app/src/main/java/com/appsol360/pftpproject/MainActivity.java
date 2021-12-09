package com.appsol360.pftpproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation top_anim, bottom_anim;
    ImageView image;
    TextView name, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //load animations
        top_anim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        //hooks
        image = findViewById(R.id.logo);
        name = findViewById(R.id.name);
        slogan = findViewById(R.id.slogan);
        //setAnimations
        image.setAnimation(top_anim);
        name.setAnimation(bottom_anim);
        slogan.setAnimation(bottom_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(name, "logo_text");

                // Apply activity transition
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent,options.toBundle());
                finish();


            }
        }, 3000);


    }
}