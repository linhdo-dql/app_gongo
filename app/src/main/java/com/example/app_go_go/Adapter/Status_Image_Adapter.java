package com.example.app_go_go.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_go_go.Objects.Status_Image;
import com.example.app_go_go.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Status_Image_Adapter extends RecyclerView.Adapter<Status_Image_Adapter.PhotoViewHolder> {
    private Context mcontext;
    private ArrayList<Status_Image> list_photo;
    BitmapVsString bs = new BitmapVsString();
    public Status_Image_Adapter(Context context) {
        this.mcontext = context;
    }
    public void setData(ArrayList<Status_Image> uri)
    {
        this.list_photo = uri;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stt_image, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Status_Image image = list_photo.get(position);
        if(image == null)
        {
            return;
        }
        if (list_photo.get(position).img_bitmap.isEmpty()) {
            holder.img_photo.setImageResource(R.drawable.placeholder);
        }else {
            Glide.with(mcontext).load(image.getImg_bitmap()).into(holder.img_photo);
        }
    }

    @Override
    public int getItemCount() {
        if(list_photo!=null)
        {
            return list_photo.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_photo;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.img_stt);
        }
    }
}
