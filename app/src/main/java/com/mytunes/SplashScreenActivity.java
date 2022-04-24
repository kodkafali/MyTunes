package com.mytunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView txtContent = findViewById(R.id.txtContent);
        Animation sideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide);
        txtContent.startAnimation(sideAnimation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
            finish();
        }, 2000);
    }
}