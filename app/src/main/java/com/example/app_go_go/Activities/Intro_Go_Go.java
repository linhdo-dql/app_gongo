package com.example.app_go_go.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_go_go.R;

public class Intro_Go_Go extends AppCompatActivity {
    public static final int REQUEST_CODE_PERMISSION = 1;
    ImageView i1,i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro__go__go);
        initView();
        Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        i1.setAnimation(anim1);
        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        i2.setAnimation(anim2);
        CountDownTimer c = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(Intro_Go_Go.this, Welcome_GoGo.class));
            }
        }.start();
    }

    public void initView() {
        i1 = (ImageView) findViewById(R.id.img1);
        i2 = (ImageView) findViewById(R.id.img2);
    }


}