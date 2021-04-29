package com.example.app_go_go.Activities.ChildActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.app_go_go.Activities.SignUp;
import com.example.app_go_go.Adapter.BitmapVsString;
import com.example.app_go_go.Adapter.Emoji_Adapter;
import com.example.app_go_go.Adapter.Photo_Adapter;

import com.example.app_go_go.Adapter.Spinner_Permission_Adapter;
import com.example.app_go_go.Adapter.Status_Adapter;
import com.example.app_go_go.Objects.Emoji;
import com.example.app_go_go.R;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class StatusActivity extends AppCompatActivity {
    ImageButton btnpickimg, btnback1, btncheckin, btnemoji, backtoSttA;
    RecyclerView rcvImg;
    Spinner spinnerP;
    Photo_Adapter photo_adapter;
    List<Uri> uris = new ArrayList<>();
    TextView tvUserNameStt;
    Spinner_Permission_Adapter spinner_permission_adapter;
    EditText edtContentStt;
    TextView tvLocationStt,  tvEmojiStt, tvtemp;
    ImageView imgt, icon2, avatarstt;
    String TAG = "s";
    RecyclerView listView;
    Emoji_Adapter emoji_adapter;
    private String[] text = new String[]{"Công khai", "Bạn bè"};
    private int[] icon = new int[]{R.drawable.outline_public_24, R.drawable.outline_people_24};
    BitmapVsString bs = new BitmapVsString();
    String per_stt = "";
    String link_emoji_stt = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
        getProfile(MainActivity.acc);
        photo_adapter = new Photo_Adapter(this);
        GridLayoutManager gridLayout = new GridLayoutManager(this,4);
        rcvImg.setLayoutManager(gridLayout);
        rcvImg.setAdapter(photo_adapter);
        spinner_permission_adapter = new Spinner_Permission_Adapter(text,icon,this, R.layout.item_spinner_per);
        spinnerP.setAdapter(spinner_permission_adapter);



    }
    public void initView() {
        btnpickimg = findViewById(R.id.btnPickImage);
        rcvImg = findViewById(R.id.rcv_photoUpStt);
        tvUserNameStt = findViewById(R.id.uNameStt);
        edtContentStt = findViewById(R.id.edtContentStt);
        tvLocationStt = findViewById(R.id.tvlocationSttItem);
        tvEmojiStt = findViewById(R.id.tvemoji);
        tvtemp = findViewById(R.id.tvtemp);
        avatarstt = findViewById(R.id.avatarStt);
        imgt = findViewById(R.id.imgemoji);
        spinnerP = findViewById(R.id.spinerPerStt);
        btnback1 = findViewById(R.id.backtoMain);
        btncheckin = findViewById(R.id.btnCheckin);
        icon2 = findViewById(R.id.icons2);
        btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GetLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatusActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up_activity);
            }
        });
        btnemoji = findViewById(R.id.imgbtnEmoji);
        btnemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboardFrom(StatusActivity.this,btnemoji);
                SharedPreferences s = StatusActivity.this.getSharedPreferences("emoji",Context.MODE_PRIVATE);
                s.edit().clear();
                Dialog dia = new Dialog(StatusActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                dia.setContentView(R.layout.bottom_sheet_emoji);
                listView = dia.findViewById(R.id.listEmoji);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(StatusActivity.this, 3);
                listView.setLayoutManager(gridLayoutManager);
                emoji_adapter = new Emoji_Adapter(StatusActivity.this, dia, tvEmojiStt, imgt, tvtemp);
                emoji_adapter.setData(getEmoji());
                listView.setAdapter(emoji_adapter);
                dia.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                backtoSttA = dia.findViewById(R.id.backtoSttA);
                backtoSttA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dia.dismiss();
                    }
                });
                dia.show();

            }
        });
    }

    public void pickImage(View v) {
        requestPermission();
    }
    public void requestPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
    public void selectImage() {
        TedBottomPicker.with(StatusActivity.this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Xong")
                .setEmptySelectionText("Trống")
                .setSelectedUriList(uris)
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        // here is selected image uri list
                        if(uriList != null && !uriList.isEmpty())
                        {
                            uris = uriList;
                            photo_adapter.setData(uriList);
                        }
                    }
                });
    }
    public void upStt(View v)
    {
        String id_stt = "stt"+MainActivity.acc+new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Calendar.getInstance().getTime());
        String acc_stt = MainActivity.acc;
        if(spinnerP.getSelectedItem().equals("Công khai"))
        {
            per_stt = "0";
        }
        else
        {
            per_stt = "1";
        }
        String content_stt = edtContentStt.getText().toString();
        String time_stt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
        String location_stt = tvLocationStt.getText().toString();
        String emoji_stt = tvEmojiStt.getText().toString();
        SharedPreferences s = StatusActivity.this.getSharedPreferences("emoji",Context.MODE_PRIVATE);
        if(s.getAll()!=null) {
          link_emoji_stt = s.getString("link_emoji", "");
        }
        String url = "https://mesfa.store/InsertStatus.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(StatusActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    upImageStt(id_stt);
                    startActivity(new Intent(StatusActivity.this, MainActivity.class));
                }
                else
                {
                    Toast.makeText(StatusActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StatusActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                Log.e("err",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_stt",id_stt);
                param.put("acc_stt",acc_stt);
                param.put("content_stt",content_stt);
                param.put("time_stt",time_stt);
                param.put("emoji_stt",emoji_stt);
                param.put("link_emoji_stt",link_emoji_stt);
                param.put("location_stt",location_stt);
                param.put("per_stt",per_stt);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void upImageStt(String id)
    {
        for( int i = 0; i < uris.size(); i++) {
            String url = "http://mesfa.store/InsertImgStt.php";
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            int  z = i;
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("name",id+z);
                    param.put("img_bitmap","https://mesfa.store/Image/Stt/"+id+z+".jpg");
                    try {
                        param.put("image", bs.convertBitmapToString(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),uris.get(z))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    param.put("id_stt",id);
                    param.put("path","Stt");
                    return param;
                }
            };
            requestQueue.add(request);
        }
    }
    public void GetLocation() throws IOException {
        try {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(StatusActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, 200);

                return;
            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d(TAG, "onLocationChanged: " + location.getLongitude() + " , " + location.getLatitude());

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                        Log.d(TAG, "onStatusChanged: " + s);

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(bestProvider);

                if (location == null) {
                    Toast.makeText(getApplicationContext(), "Không có tín hiệu GPS!",
                            Toast.LENGTH_LONG).show();
                }
                if (location != null) {
                    Log.e("location", "location--" + location);
                    Log.e("latitude at beginning",
                            "@@@@@@@@@@@@@@@" + location.getLatitude());
                    // onLocationChanged(location);
                }
                Log.e("s", location.getLatitude() + " " + location.getLongitude());

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                if(address!=null)
                {
                    tvLocationStt.setVisibility(View.VISIBLE);
                    icon2.setVisibility(View.VISIBLE);
                    tvLocationStt.setText(address);
                }
            }
        }catch (Exception e)
        {
            Log.e("Tag", e.getMessage());
        }
    }
    public ArrayList<Emoji> getEmoji() {
        ArrayList<Emoji> emojis = new ArrayList<>();
        String[] s = new String[]{"hạnh phúc","đáng yêu","hùng vĩ", "điên", "sung sướng", "khờ khạo", "tuyệt vời", "thư giãn", "tệ", "mệt mỏi", "chu đáo", "hoài niệm", "ốm yếu", "kiệt sức", "tự tin", "vui vẻ", "buồn ngủ", "đói", "đau đớn", "thất vọng", "lạ", "phong cách", "hối tiếc", "lo lắng", "tồi tệ",  "sáng tạo", "phấn khích", "bối rối", "trống vắng", "mỉa mai", "mạnh mẽ", "đặc biệt", "kích động", "kinh ngạc", "mới mẻ", "nản", "xinh đẹp", "tội lỗi", "tự do", "hổ thẹn", "gay go", "khủng khiếp", "có phúc", "buồn", "yêu", "cảm kích", "hân hoan", "cô đơn", "OK", "giận dữ", "hài lòng", "xúc động", "bực mình", "may mắn", "buồn tẻ", "khí thế", "ồn ào", "dễ thương", "vui nhộn", "ổn", "cáu", "băn khoăn", "cạn lời", "hài hước", "gian dối", "bức xúc", "hay ho", "nóng", "buồn ngủ", "say", "buồn nôn", "no", "bất ngờ", "ngạc nhiên", "nhẹ nhàng", "thèm", "kinh tởm", "bị lừa rồi", "ngớ ngẩn", "tồi tệ", "ấn tượng", "an ủi", "lịch sự", "xấu hổ", "lạ", "khinh bỉ", "có lỗi"};
        for(int i = 1; i<=87; i++)
        {
            emojis.add(new Emoji(i, s[i-1]));
        }
        Collections.sort(emojis, new Comparator<Emoji>() {
            @Override
            public int compare(Emoji o1, Emoji o2) {
                return o1.getEmoji().compareTo(o2.getEmoji());
            }
        });
        return emojis;
    };
    public void getProfile(String acc)
    {
        String url = "https://mesfa.store/getProfile.php";
        RequestQueue r = Volley.newRequestQueue(StatusActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0 ; i<response.length(); i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if(object.getString("accName").equals(acc)) {
                            Glide.with(StatusActivity.this).load(object.getString("user_avatar_image")).into(avatarstt);
                            tvUserNameStt.setText(object.getString("name_user"));
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
    
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}