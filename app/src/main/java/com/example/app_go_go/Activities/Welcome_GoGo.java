package com.example.app_go_go.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app_go_go.Adapter.Slider_Welcome_Adapter;
import com.example.app_go_go.R;

import me.relex.circleindicator.CircleIndicator;

public class Welcome_GoGo extends AppCompatActivity {
    ViewPager vpager;
    CircleIndicator dotlayout;
    TextView[] dot;
    Button btnsignin;

    Slider_Welcome_Adapter slider_welcome_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__go_go);
        initView();

    }
    public void initView() {
        vpager = (ViewPager) findViewById(R.id.vpager);
        dotlayout = (CircleIndicator) findViewById(R.id.dotlayout);
        btnsignin = (Button) findViewById(R.id.btnSignInWel);
        slider_welcome_adapter = new Slider_Welcome_Adapter(this);
        vpager.setAdapter(slider_welcome_adapter);
        dotlayout.setViewPager(vpager);
    }
    public void nextActiviyLogin(View v)
    {
        startActivity(new Intent(Welcome_GoGo.this, SignIn.class));
    }

}