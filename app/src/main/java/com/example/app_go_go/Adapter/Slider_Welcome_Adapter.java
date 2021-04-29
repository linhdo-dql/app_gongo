package com.example.app_go_go.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.app_go_go.R;

public class Slider_Welcome_Adapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public Slider_Welcome_Adapter(Context context) {
        this.context = context;
    }

    public int[] imageSlide = {
            R.drawable.w1,
            R.drawable.w2,
            R.drawable.w3
    };

    public String[] titleSlide = {
            "Chia sẻ chuyến đi",
            "Lên lịch trình",
            "Chat, Hội nhóm"
    };

    public String[] contentSlide = {
            "Chia sẻ chuyến đi của bạn đến những người có cùng sở thích!",
            "Lên lịch trình cho chuyến đi của bạn, bạn bè và nhóm.",
            "Chat với các thành viên trong nhóm cùng đam mê."
    };

    @Override
    public int getCount() {
        return contentSlide.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.slide_welcome, container, false);
        ImageView imgWel = v.findViewById(R.id.imgwel);
        TextView txtTitleWel = v.findViewById(R.id.ttwel);
        TextView txtContentWel = v.findViewById(R.id.contwel);
        imgWel.setImageResource(imageSlide[position]);
        txtTitleWel.setText(titleSlide[position]);
        txtContentWel.setText(contentSlide[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
