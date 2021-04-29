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

import com.example.app_go_go.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Photo_Adapter extends RecyclerView.Adapter<Photo_Adapter.PhotoViewHolder> {
    private Context context;
    private List<Uri> list_photo;
    public static ArrayList<String> bits = new ArrayList<>();
    BitmapVsString bs = new BitmapVsString();
    public Photo_Adapter(Context context) {
        this.context = context;
    }
    public void setData(List<Uri> uri)
    {
        this.list_photo = uri;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Uri uri = list_photo.get(position);
        if(uri==null)
        {
            return;
        }
        else {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
                if(bitmap!=null)
                {
                    holder.img_photo.setImageBitmap(bitmap);
                    bits.add(bs.convertBitmapToString(bitmap));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            img_photo = itemView.findViewById(R.id.item_photo);
        }
    }
}
