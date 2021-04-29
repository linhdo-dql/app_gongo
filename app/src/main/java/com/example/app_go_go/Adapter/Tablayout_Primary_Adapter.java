package com.example.app_go_go.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.app_go_go.Activities.Tablayouts.Gps;
import com.example.app_go_go.Activities.Tablayouts.Home;
import com.example.app_go_go.Activities.Tablayouts.Notify;
import com.example.app_go_go.Activities.Tablayouts.Setting;
import com.example.app_go_go.Activities.Tablayouts.Trip;

public class Tablayout_Primary_Adapter extends FragmentStatePagerAdapter {
    private String[] listTab = {"home", "trip", "gps", "notify","setting"};
    private Home h;
    private Trip t;
    private Gps g;
    private Notify n;
    private Setting s;
    public Tablayout_Primary_Adapter(@NonNull FragmentManager fm) {
        super(fm);
        h = new Home();
        t = new Trip();
        g = new Gps();
        n = new Notify();
        s = new Setting();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return h;
        }
        else if (position ==1)
        {
            return t;
        }
        else if(position == 2)
        {
            return g;
        }
        else if(position == 3)
        {
            return n;
        }
        else if(position == 4)
        {
            return s;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }
}


