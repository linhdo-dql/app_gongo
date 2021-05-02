package com.example.app_go_go.Activities.ChildActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.app_go_go.Activities.MainActivity;
import com.example.app_go_go.Adapter.BitmapVsString;
import com.example.app_go_go.Adapter.Status_Adapter;
import com.example.app_go_go.Objects.Profile;
import com.example.app_go_go.Objects.Status_Contents;
import com.example.app_go_go.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class Profiles extends AppCompatActivity {
    LinearLayout btneditPrf;
    RelativeLayout layout_dia_edit_prf, containerbtn, containerbtn1;
    Button btnaddprf;
    BottomSheetBehavior bottomSheetBehavior;
    ImageView img_avatar, img_cover;
    DatePickerDialog date_pik;
    BitmapVsString bs = new BitmapVsString();
    String imgA="", imgC="";
    Status_Adapter status_adapter;
    RecyclerView rcv_stt_prf;
    String accPrf = "";
    EditText edtuser_name, edtuser_job, edtuser_school, edtuser_address, edtuser_country, edtuser_birth, edtuser_relation;
    TextView tvUser_name, tvuser_job, tvuser_school, tvuser_address, tvuser_country, tvuser_birth, tvuser_relation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b==null)
        {
            accPrf = MainActivity.acc;
        }else
        {
            accPrf = b.getString("accName");
            if(!accPrf.equals(MainActivity.acc))
            {
                btneditPrf.setVisibility(View.INVISIBLE);
                containerbtn.setVisibility(View.INVISIBLE);
                containerbtn1.setVisibility(View.VISIBLE);
            }
        }
        getProfile(accPrf);
    }
    public void initView()
    {
        btneditPrf = findViewById(R.id.btnEditprf);
        layout_dia_edit_prf = findViewById(R.id.layoutdialog);
        containerbtn = findViewById(R.id.containerbtn);
        containerbtn1 = findViewById(R.id.containerbtn1);
        bottomSheetBehavior = BottomSheetBehavior.from(layout_dia_edit_prf);
        btnaddprf = findViewById(R.id.btnAddPrf);
        img_avatar = findViewById(R.id.img_avatar);
        registerForContextMenu(img_avatar);
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Profiles.this, img_avatar);
                if(!accPrf.equals(MainActivity.acc))
                {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_img1, popupMenu.getMenu());
                }else {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_img, popupMenu.getMenu());
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.menu_avatar_view:
                                return true;
                            case R.id.menu_avatar_change:
                                TedBottomPicker.with(Profiles.this)
                                        .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                            @Override
                                            public void onImageSelected(Uri uri) {
                                                // here is selected image uri
                                                Bitmap bitmap = null;
                                                try {
                                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                img_avatar.setImageBitmap(bitmap);
                                                imgA = bs.convertBitmapToString(bitmap);
                                                updateImage("https://mesfa.store/updateAvatar.php","image_a", imgA, "name_a", "avatar"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()), "user_avatar_image", "https://mesfa.store/Image/"+accPrf+"/Avatar/avatar"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+".jpg" );
                                            }
                                        });
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        img_cover = findViewById(R.id.img_cover);
        img_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Profiles.this, img_cover);
                if(!accPrf.equals(MainActivity.acc))
                {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_img1, popupMenu.getMenu());
                }else {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_img, popupMenu.getMenu());
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.menu_avatar_view:
                                return true;
                            case R.id.menu_avatar_change:
                                TedBottomPicker.with(Profiles.this)
                                        .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                            @Override
                                            public void onImageSelected(Uri uri) {
                                                // here is selected image uri
                                                Bitmap bitmap = null;
                                                try {
                                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                img_cover.setImageBitmap(bitmap);
                                                imgC = bs.convertBitmapToString(bitmap);
                                                updateImage("https://mesfa.store/updateCover.php", "image_c", imgC, "name_c", "cover"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()),"user_cover_image","https://mesfa.store/Image/"+accPrf+"/Cover/cover"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+".jpg");
                                            }
                                        });
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        edtuser_name = findViewById(R.id.edtUnamePrf);
        edtuser_job = findViewById(R.id.edtJobPrf);
        edtuser_school = findViewById(R.id.edtSchoolPrf);
        edtuser_address = findViewById(R.id.edtAddressPrf);
        edtuser_country = findViewById(R.id.edtCountryPrf);
        edtuser_birth = findViewById(R.id.edtBirthPrf);
        edtuser_relation = findViewById(R.id.edtRelationPrf);
        tvUser_name = findViewById(R.id.tvUsernamePrf);
        tvuser_job = findViewById(R.id.tvJobPrf);
        tvuser_school = findViewById(R.id.tvSchoolPrf);
        tvuser_address = findViewById(R.id.tvAddressPrf);
        tvuser_country = findViewById(R.id.tvCountryPrf);
        tvuser_birth = findViewById(R.id.tvBirthPrf);
        tvuser_relation = findViewById(R.id.tvRelationPrf);
        edtuser_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                date_pik = new DatePickerDialog(Profiles.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if(dayOfMonth<9 && monthOfYear < 9)
                                {
                                    edtuser_birth.setText("0"+dayOfMonth+"-"+"0"+(monthOfYear+1)+"-"+year);
                                }
                                else if(dayOfMonth<9)
                                {
                                    edtuser_birth.setText("0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                                else if(monthOfYear<9)
                                {
                                    edtuser_birth.setText(dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year);
                                }
                                else
                                {
                                    edtuser_birth.setText(dayOfMonth + "-" +(monthOfYear + 1) + "-" + year);
                                }
                            }
                        }, year, month, day);
                date_pik.show();
            }
        });
        try {
            rcv_stt_prf = findViewById(R.id.rcv_sttPrf);
            //status_adapter = new Status_Adapter(Profiles.this);
            rcv_stt_prf.setLayoutManager(new LinearLayoutManager(this));
            getSttDB();
            rcv_stt_prf.setAdapter(status_adapter);
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void editPrf(View view)
    {
        if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bs.hideKeyboardFrom(Profiles.this, layout_dia_edit_prf);
        }else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        edtuser_name.setText(tvUser_name.getText().toString());
        edtuser_job.setText(tvuser_job.getText().toString());
        edtuser_school.setText(tvuser_school.getText().toString());
        edtuser_address.setText(tvuser_address.getText().toString());
        edtuser_country.setText(tvuser_country.getText().toString());
        edtuser_birth.setText(tvuser_birth.getText().toString());
        edtuser_relation.setText(tvuser_relation.getText().toString());
    }
    public void addPrftoSever(View v) {
        String url = "https://mesfa.store/InsertProfile.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")) {
                    getProfile(accPrf);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Toast.makeText(Profiles.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profiles.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                //'$name_user','$user_avatar_image','$user_cover_image','$user_job','$user_school','$user_address','$user_country','$user_birth','$user_relation','$accName'
                param.put("name_user", edtuser_name.getText().toString());
                param.put("user_job", edtuser_job.getText().toString());
                param.put("user_school", edtuser_school.getText().toString());
                param.put("user_address", edtuser_address.getText().toString());
                param.put("user_country", edtuser_country.getText().toString());
                param.put("user_birth", edtuser_birth.getText().toString());
                param.put("user_relation", edtuser_relation.getText().toString());
                param.put("accName", MainActivity.acc);
                return param;
            }
        };
        queue.add(sr);
    }
    public void getProfile(String acc)
    {
        String url = "https://mesfa.store/getProfile.php";
        RequestQueue r = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0 ; i<response.length(); i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if(object.getString("accName").equals(acc)) {
                            //prfs.add(new Profile(object.getString("name_user"), object.getString("user_image_avatar"), object.getString("user_image_cover"), object.getString("user_job"), object.getString("user_school"), object.getString("user_address"), object.getString("user_country"), object.getString("user_birth"), object.getString("user_relation"), object.getString("accName")));
                            tvUser_name.setText(object.getString("name_user"));
                            if (object.getString("user_avatar_image") != "") {
                                Glide.with(Profiles.this).load(object.getString("user_avatar_image")).into(img_avatar);
                            }
                            if (object.getString("user_cover_image") != "") {
                                Glide.with(Profiles.this).load(object.getString("user_cover_image")).into(img_cover);
                            }
                            tvuser_job.setText(object.getString("user_job"));
                            tvuser_school.setText(object.getString("user_school"));
                            tvuser_address.setText(object.getString("user_address"));
                            tvuser_country.setText(object.getString("user_country"));
                            tvuser_birth.setText(object.getString("user_birth"));
                            tvuser_relation.setText(object.getString("user_relation"));
                            //Save in Client
                            SharedPreferences shared = getSharedPreferences("profile",MODE_PRIVATE);
                            shared.edit().putString("accName",object.getString("accName")).apply();
                            shared.edit().putString("name_user",object.getString("name_user")).apply();
                            shared.edit().putString("user_avatar_image",object.getString("user_avatar_image")).apply();
                            shared.edit().putString("user_cover_image",object.getString("user_cover_image")).apply();
                            shared.edit().putString("user_job",object.getString("user_job")).apply();
                            shared.edit().putString("user_school",object.getString("user_school")).apply();
                            shared.edit().putString("user_address",object.getString("user_address")).apply();
                            shared.edit().putString("user_country",object.getString("user_country")).apply();
                            shared.edit().putString("user_birth",object.getString("user_birth")).apply();
                            shared.edit().putString("user_relation",object.getString("user_relation")).apply();
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
                Toast.makeText(Profiles.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        r.add(jsonArrayRequest);
    }
    public void updateImage(String url, String image_sql, String image_client, String name_image_sql, String name_image_client, String user_image_mysql, String user_image_client) {
        RequestQueue r = Volley.newRequestQueue(this);
        StringRequest s = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success"))
                {
                    Toast.makeText(Profiles.this, "Đổi ảnh thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> p = new HashMap<>();
                p.put(image_sql, image_client);
                p.put(name_image_sql, name_image_client);
                p.put(user_image_mysql, user_image_client);
                p.put("accName", accPrf);
                return p;
            }
        };
        r.add(s);
    }
    public void getSttDB() {
        String uri = "https://mesfa.store/Status.php";
        ArrayList<Status_Contents> arrStt = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(Profiles.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0; i<response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getString("acc_stt").equals(accPrf)) {
                                    arrStt.add(new Status_Contents(object.getString("id_stt"),
                                            object.getString("acc_stt"),
                                            object.getString("content_stt"),
                                            object.getString("time_stt"),
                                            object.getString("emoji_stt"),
                                            object.getString("link_emoji_stt"),
                                            object.getString("location_stt"),
                                            object.getString("per_stt")));
                                }
                                } catch(JSONException e){
                                    e.printStackTrace();
                                }
                        }
                        Collections.sort(arrStt, new Comparator<Status_Contents>() {
                            @Override
                            public int compare(Status_Contents o1, Status_Contents o2) {
                                if(CalcHour(o1.time_stt, o2.time_stt)<0) {
                                    return -1;
                                }
                                else if (CalcHour(o1.time_stt, o2.time_stt)>0){
                                    return 1;
                                }
                                else {
                                    return 0;
                                }
                            }
                        });
                        status_adapter.setDataStt(arrStt);
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