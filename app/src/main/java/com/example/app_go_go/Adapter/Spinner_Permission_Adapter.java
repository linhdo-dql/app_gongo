package com.example.app_go_go.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app_go_go.R;

import java.util.ArrayList;
import java.util.List;

public class Spinner_Permission_Adapter extends BaseAdapter {
    private String[] text;
    private int[] icon;

    public Spinner_Permission_Adapter(String[] text, int[] icon, Activity context, int layoutResource) {
        this.text = text;
        this.icon = icon;
        this.context = context;
        this.layoutResource = layoutResource;
        this.layoutInflater = context.getLayoutInflater();
    }

    Context context;
    LayoutInflater layoutInflater;
    int layoutResource;

    @Override
    public int getCount() {
        return icon.length;
    }

    @Override
    public Object getItem(int position) {
        return text[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View v = this.layoutInflater.inflate(this.layoutResource,null, true);
        ImageView imgicon = v.findViewById(R.id.iconp1);
        imgicon.setImageResource(icon[position]);
        TextView tvtext = v.findViewById(R.id.textp2);
        tvtext.setText(text[position]);
        return v;
    }
}
