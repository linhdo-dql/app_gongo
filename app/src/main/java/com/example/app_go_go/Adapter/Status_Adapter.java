package com.example.app_go_go.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.app_go_go.Activities.ChildActivities.Profiles;
import com.example.app_go_go.Activities.MainActivity;
import com.example.app_go_go.Objects.Status_Contents;
import com.example.app_go_go.Objects.Status_Image;
import com.example.app_go_go.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Status_Adapter extends RecyclerView.Adapter<Status_Adapter.StatusViewHolder> {
    Context context1;
    ArrayList<Status_Contents> stts;
    Status_Image_Adapter s = new Status_Image_Adapter(context1);
    BottomSheetBehavior bottomSheetBehavior;
    public Status_Adapter(Context context) {
        this.context1 = context;
    }
    public void setDataStt(ArrayList<Status_Contents> status_contents)
    {
        this.stts = status_contents;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stt, parent, false);

        return new StatusViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        holder.tvidStt.setText(stts.get(position).id_stt);
        long z = holder.CalcHour(String.valueOf(stts.get(position).time_stt),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        if(z > 0 && z < 60)
        {
            holder.tvtimeStt.setText("vài giây trước");
        } else if(z>=60 && z<3600)
        {
            holder.tvtimeStt.setText((z/60)+" phút");
        } else if(z>=3600 && z<86399)
        {
            holder.tvtimeStt.setText(z/3600+" giờ");
        } else if(z>=84000)
        {
            holder.tvtimeStt.setText(z/84000+" ngày");
        }
        if(!String.valueOf(stts.get(position).emoji_stt).trim().equals("")) {
            holder.tvEmojiStt.setText("đang cảm thấy " + stts.get(position).emoji_stt);
        }
        else {holder.tvEmojiStt.setVisibility(View.GONE);}

        if(!String.valueOf(stts.get(position).location_stt).trim().equals("")) {
            holder.tvLocationStt.setText("tại " + stts.get(position).location_stt);
        }
        else {holder.tvLocationStt.setVisibility(View.GONE);}

        if(!String.valueOf(stts.get(position).content_stt).trim().equals("")) {
            holder.tvContenStt.setText(stts.get(position).content_stt);
        }
        else {holder.tvContenStt.setVisibility(View.GONE);}

        if(Integer.parseInt(stts.get(position).per_stt)== 0) {
            holder.tvPermission.setImageResource(R.drawable.outline_public_24);
        }else if(Integer.parseInt(stts.get(position).per_stt)== 1)
        {
            holder.tvPermission.setImageResource(R.drawable.outline_people_24);
        }
        if(!String.valueOf(stts.get(position).emoji_stt).trim().equals("")) {
            Glide.with(context1).load(stts.get(position).link_emoji_stt).into(holder.imgiconemoji);
        }
        else {holder.imgiconemoji.setVisibility(View.GONE);}
        holder.getProfile(stts.get(position).acc_stt);
        holder.imgAvatarItemstt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1, Profiles.class);
                Bundle bundle = new Bundle();
                bundle.putString("accName", stts.get(position).acc_stt);
                intent.putExtras(bundle);
                context1.startActivity(intent);
            }
        });
        holder.getImageList(stts.get(position).id_stt);
        s.notifyDataSetChanged();
        holder.imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MainActivity.acc.equals(stts.get(position).acc_stt)) {
                    sendnotification_Like(stts.get(position).acc_stt, stts.get(position).id_stt);
                }
            }
        });
        holder.btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context1, R.style.BottomSheetDialog);
                View view = LayoutInflater.from(context1).inflate(R.layout.dialog_cmt, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                bottomSheetDialog.show();

            }
        });
    }
    public String sendnotification_Like(String acc, String id_stt) {
        RequestQueue r = Volley.newRequestQueue(context1);
        final String[] a = {""};
        String url = "https://mesfa.store/getToken.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++)
                {
                    try {
                        JSONObject  js = response.getJSONObject(i);
                        if(js.getString("acc_token").equals(acc))
                        {
                            sendnotification(js.getString("content_token"),id_stt);
                           // putNotifyOffline(js.getString("content_token"));
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        r.add(jsonArrayRequest);
        return a[0];
    }
   public void sendnotification(String token, String id_stt){
        RequestQueue r = Volley.newRequestQueue(context1);
        String url = "https://fcm.googleapis.com/fcm/send";
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to",token);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title","Yêu thích");
            jsonObject.put("body", MainActivity.acc+" đã thích hình ảnh của bạn");
            mainObj.put("notification",jsonObject);

            JSONObject extradata = new JSONObject();
            extradata.put("type","stt");
            extradata.put("id_stt",id_stt);
            mainObj.put("data",extradata);
            JsonObjectRequest js = new JsonObjectRequest(Request.Method.POST, url, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context1, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type","application/json");
                    header.put("Authorization","key=AAAAE53UG7U:APA91bGU-o7UTBq5AKstesr3zirZhi2_QxH0acwJnKojRc-qV7dPG8zyRE8HGFm1eu_8Ik5C9ndtkqZAgPxW0_A7CE6TDArcXbA-ugwg-zXmeQnjDvRRalF29CD6eQBkpMtgeWW4v313");
                    return header;
                }
            };
            r.add(js);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void putNotifyOffline(String token){
        String url = "https://mesfa.store/sendNotification.php";
        RequestQueue queue = Volley.newRequestQueue(context1);
        StringRequest s = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("to", token);
                return map;
            }
        };
        queue.add(s);
    }


    @Override
    public int getItemCount() {
        if(stts != null)
        {
            return stts.size();
        }
        return 0;
    }


    public class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView tvidStt, tvuNameStt, tvtimeStt, tvContenStt, tvEmojiStt, tvLocationStt;
        RecyclerView rcvImage;
        ImageView imgAvatarItemstt, tvPermission, imgiconemoji, imgHeart;
        Status_Image_Adapter status_image_adapter;
        LinearLayout btnCmt;
        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvidStt = itemView.findViewById(R.id.idSttItem);
            tvuNameStt = itemView.findViewById(R.id.uNameSttItem);
            tvEmojiStt = itemView.findViewById(R.id.tvemojiNameSttItem);
            tvtimeStt = itemView.findViewById(R.id.timeSttItem);
            tvContenStt = itemView.findViewById(R.id.tvContentStt);
            tvLocationStt = itemView.findViewById(R.id.tvlocationSttItem);
            tvPermission = itemView.findViewById(R.id.perSttItem);
            rcvImage = itemView.findViewById(R.id.rcvImgStt);
            imgAvatarItemstt = itemView.findViewById(R.id.imgAvatarSttItem);
            imgiconemoji = itemView.findViewById(R.id.iconemojiSttItem);
            imgHeart = itemView.findViewById(R.id.imgHearth);
            btnCmt = itemView.findViewById(R.id.btnCmtHome);
        }
        public void getProfile(String acc)
        {
            String url = "https://mesfa.store/getProfile.php";
            RequestQueue r = Volley.newRequestQueue(context1);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i = 0 ; i<response.length(); i++)
                    {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            if(object.getString("accName").equals(acc)) {
                                Glide.with(context1).load(object.getString("user_avatar_image")).into(imgAvatarItemstt);
                                tvuNameStt.setText(object.getString("name_user"));
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            r.add(jsonArrayRequest);
        }
        public void getImageList(String idstt) {
            String urigetImage = "https://mesfa.store/SelectSttImage.php";
            RequestQueue requestQueue = Volley.newRequestQueue(context1);
            ArrayList<Status_Image> status_images = new ArrayList<>();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urigetImage, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i = 0 ; i < response.length(); i++)
                            {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    if(object.getString("id_stt").equals(idstt) && object.getString("img_bitmap")!=null) {
                                        status_images.add(new Status_Image(object.getString("id_img"), object.getString("img_bitmap"), object.getString("id_stt")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            status_image_adapter = new Status_Image_Adapter(context1);
                            status_image_adapter.setData(status_images);
                            rcvImage.setAdapter(status_image_adapter);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context1,3,RecyclerView.VERTICAL,false);
                            rcvImage.setLayoutManager(gridLayoutManager);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonArrayRequest);
        }
        public long CalcHour(String a, String b) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date d1 = new Date();

            Date d2 = new Date();

            try {

                try {
                    d1 = format.parse(a);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                d2 = format.parse(b);

            } catch (ParseException e) {

            }

            // Get msec from each, and subtract.

            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000;

            long diffMinutes = diff / (60 * 1000);

            long diffHours = diff / (60 * 60 * 1000);

            long diffDay = diff / (24*60*60*1000);

            return diffSeconds;

        }
    }




}
