package com.example.app_go_go.Activities.ChildActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.app_go_go.Adapter.Status_Image_Adapter;
import com.example.app_go_go.Objects.Status_Image;
import com.example.app_go_go.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class StatusDetail extends AppCompatActivity {
    ImageView imgAvartar, imgAvatarCmt, imgEmoji, imgPer;
    ImageButton imgbtnSend;
    TextView tvUserMaster, tvTimeStt, tvEmoji, tvContent, tvLocation, tvFrStt;
    RecyclerView rcv_Image;
    Status_Image_Adapter status_image_adapter;
    EditText edtCmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        initView();
        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            getStt(intent.getStringExtra("id_stt"));
            getImageStt(intent.getStringExtra("id_stt"));
        }


    }
    public void initView() {
        imgAvartar = findViewById(R.id.imgAvatarSttDetail);
        imgAvatarCmt = findViewById(R.id.avatarHomeCmt);
        imgEmoji = findViewById(R.id.iconemojiSttDetail);
        imgPer = findViewById(R.id.perSttDetail);
        imgbtnSend = findViewById(R.id.btnsendCmtSttDetail);
        tvUserMaster = findViewById(R.id.uNameSttDetail);
        tvTimeStt = findViewById(R.id.timeSttDetail);
        tvEmoji = findViewById(R.id.tvemojiNameSttDetail);
        tvContent = findViewById(R.id.tvContentSttDetail);
        tvLocation = findViewById(R.id.tvlocationSttDetail);
        tvFrStt = findViewById(R.id.tvfrSttDetail);
        rcv_Image = findViewById(R.id.rcvImgSttDeatil);
        edtCmt = findViewById(R.id.edtCmtDetail);
    }
    public void getStt(String id_stt)
    {
        String url = "https://mesfa.store/Status.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest js = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            for(int i = 0; i< response.length(); i++)
            {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    if(jsonObject.getString("id_stt").equals(id_stt))
                    {
                        tvEmoji.setText("đang cảm thấy "+jsonObject.getString("emoji_stt"));
                        tvContent.setText(jsonObject.getString("content_stt"));
                        tvLocation.setText(jsonObject.getString("location_stt"));
                        tvFrStt.setText("chưa làm đc");
                        Glide.with(StatusDetail.this).load(jsonObject.getString("link_emoji_stt")).into(imgEmoji);
                        getProfile(jsonObject.getString("acc_stt"));
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
        queue.add(js);
    }
    public void getProfile(String acc)
    {
        String url = "https://mesfa.store/getProfile.php";
        RequestQueue r = Volley.newRequestQueue(StatusDetail.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0 ; i<response.length(); i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if(object.getString("accName").equals(acc)) {
                            Glide.with(StatusDetail.this).load(object.getString("user_avatar_image")).into(imgAvartar);
                            tvUserMaster.setText(object.getString("name_user"));
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
    public void getImageStt(String id_stt)
    {
        String urigetImage = "https://mesfa.store/SelectSttImage.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ArrayList<Status_Image> status_images = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urigetImage, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(object.getString("id_stt").equals(id_stt) && object.getString("img_bitmap")!=null) {
                                    status_images.add(new Status_Image(object.getString("id_img"), object.getString("img_bitmap"), object.getString("id_stt")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        status_image_adapter = new Status_Image_Adapter(StatusDetail.this);
                        status_image_adapter.setData(status_images);
                        rcv_Image.setAdapter(status_image_adapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(StatusDetail.this,4,RecyclerView.VERTICAL,false);
                        rcv_Image.setLayoutManager(gridLayoutManager);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}