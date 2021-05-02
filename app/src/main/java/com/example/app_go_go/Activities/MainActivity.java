package com.example.app_go_go.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.mtp.MtpConstants;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.app_go_go.Adapter.BitmapVsString;
import com.example.app_go_go.Adapter.Status_Adapter;
import com.example.app_go_go.Adapter.Tablayout_Primary_Adapter;
import com.example.app_go_go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity{
    public static final String CHANEL_ID = "like";
    androidx.appcompat.widget.Toolbar toolbarapp;
    public static final String CHANEL_NAME = "Yêu thích";
    public static final String CHANEL_DESC = "Yêu thích bài viết!";
    public ActionBar actionbarapp;
    ViewPager viewPager;
    TabLayout tabLayout;
    Status_Adapter status_adapter;
    BitmapVsString bs = new BitmapVsString();
    ImageView useLogoq;
    public static String acc = "";
    int[] tabicon1 = {R.drawable.outline_home_24,
            R.drawable.outline_assignment_24,
            R.drawable.outline_room_24,
            R.drawable.round_notifications_none_24,
            R.drawable.outline_settings_24};
    int[] tabicon2 = {R.drawable.round_home_24,
            R.drawable.round_assignment_24,
            R.drawable.baseline_room_24,
            R.drawable.round_notifications_24,
            R.drawable.round_settings_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setIconA();
        actionbarapp = getSupportActionBar();
        acc = getinfoAcc();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        toolbarapp = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarapp);
        setSupportActionBar(toolbarapp);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new Tablayout_Primary_Adapter(getSupportFragmentManager()));
        viewPager.setBackground(new ColorDrawable(Color.TRANSPARENT));
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        useLogoq = findViewById(R.id.useLogoq);

    }

    public void setIconA() {
        tabLayout.getTabAt(0).setIcon(tabicon1[0]);
        for (int i = 1; i < 5; i++) {
            tabLayout.getTabAt(i).setIcon(tabicon1[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabicon2[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabicon1[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public String getinfoAcc() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null) {
            acc = b.getString("acc");
        }
        return acc;
    }


}