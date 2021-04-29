package com.example.app_go_go.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_go_go.Activities.Interface.ItemClickListener;
import com.example.app_go_go.Objects.Emoji;
import com.example.app_go_go.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Emoji_Adapter extends RecyclerView.Adapter<Emoji_Adapter.Emoji_ViewHolder> {
    private Context mcontext;
    private ArrayList<Emoji> e;
    private Dialog dialog;
    private TextView edt, temp;
    private ImageView img;
    public Emoji_Adapter(Context mcontext, Dialog dialog, TextView tv, ImageView img, TextView temp) {
        this.mcontext = mcontext;
        this.dialog = dialog;
        this.edt = tv;
        this.img = img;
        this.temp = temp;
    }

    public void setData(ArrayList<Emoji> emojis)
    {
        this.e = emojis;
    }
    @NonNull
    @Override
    public Emoji_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoji, parent, false);
        return new Emoji_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Emoji_ViewHolder holder, int position) {
        Glide.with(mcontext).load("https://mesfa.store/Image/Icon/"+e.get(position).id+".png").into(holder.imgicone);
        holder.tvtexte.setText(e.get(position).emoji);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                temp.setVisibility(View.VISIBLE);
                edt.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                edt.setText(e.get(position).emoji);
                Glide.with(mcontext).load("https://mesfa.store/Image/Icon/"+e.get(position).id+".png").into(img);
                SharedPreferences s = mcontext.getSharedPreferences("emoji",Context.MODE_PRIVATE);
                s.edit().putString("link_emoji", "https://mesfa.store/Image/Icon/"+e.get(position).id+".png").apply();
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(e.size()!=0)
        {
            return e.size();
        }
        return 0;
    }

    public class Emoji_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgicone;
        TextView tvtexte;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public Emoji_ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgicone = itemView.findViewById(R.id.imgiconEmoji);
            tvtexte = itemView.findViewById(R.id.tvtextEmoji);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}
