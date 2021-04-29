package com.example.app_go_go.Activities.Tablayouts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.app_go_go.Activities.ChildActivities.Profiles;
import com.example.app_go_go.Activities.ChildActivities.StatusActivity;
import com.example.app_go_go.Activities.MainActivity;
import com.example.app_go_go.Adapter.Status_Adapter;
import com.example.app_go_go.Adapter.Suggestion_Friends_Adapter;
import com.example.app_go_go.Objects.Accounts;
import com.example.app_go_go.Objects.Status_Contents;
import com.example.app_go_go.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Home extends Fragment{
    View rootView;
    Button btnStt;
    RecyclerView rcv_Stt, rcv_Suggest_fr;
    Status_Adapter status_adapter;
    Suggestion_Friends_Adapter suggestion_friends_adapter;
    ImageView clearSf, avatarHome;
    RelativeLayout rSf;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_home, container, false);
        btnStt = (Button) rootView.findViewById(R.id.btnStt);

        btnStt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),StatusActivity.class));
            }
        });
        rcv_Stt = rootView.findViewById(R.id.rcv_Stt);
        status_adapter = new Status_Adapter(getActivity());
        rcv_Stt.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_Stt.setAdapter(status_adapter);
        getSttDB();



        rSf = rootView.findViewById(R.id.container2sF);
        if(getActivity().getSharedPreferences("emoji", Context.MODE_PRIVATE).getInt("clearsF", 0)==0)
        {
            rcv_Suggest_fr = rootView.findViewById(R.id.rcv_suggestionFr);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            rcv_Suggest_fr.setLayoutManager(layoutManager);
            suggestion_friends_adapter = new Suggestion_Friends_Adapter(getActivity());
            getSugesstFr();
            rcv_Suggest_fr.setAdapter(suggestion_friends_adapter);
        } else {rSf.setVisibility(View.GONE);};

        clearSf = rootView.findViewById(R.id.clearSf);
        clearSf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rSf.setVisibility(View.GONE);
                SharedPreferences s = getActivity().getSharedPreferences("emoji", Context.MODE_PRIVATE);
                s.edit().putInt("clearsF", 1).apply();
            }
        });

        avatarHome = rootView.findViewById(R.id.avatarHome);
        getProfile(MainActivity.acc);
        avatarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Profiles.class));
            }
        });
        return rootView;
    }
    public void getSttDB() {
        String uri = "https://mesfa.store/Status.php";
        ArrayList<Status_Contents> arrStt = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrStt.clear();
                        for(int i=0; i<response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrStt.add(new Status_Contents(object.getString("id_stt"),
                                                               object.getString("acc_stt"),
                                                               object.getString("content_stt"),
                                                               object.getString("time_stt"),
                                                               object.getString("emoji_stt"),
                                                               object.getString("link_emoji_stt"),
                                                               object.getString("location_stt"),
                                                               object.getString("per_stt")));
                            } catch (JSONException e) {
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
    public void getSugesstFr() {
        ArrayList<Accounts> s = new ArrayList<>();
        String url = "https://mesfa.store/Accounts.php";
        RequestQueue r = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++)
                        {

                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(!MainActivity.acc.equals(object.getString("aN"))) {
                                    s.add(new Accounts(object.getString("aN"), object.getString("aP"), object.getString("aM"), Boolean.valueOf(object.getString("sA"))));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        suggestion_friends_adapter.setDBsF(s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        r.add(jsonArrayRequest);
    }
    public void getProfile(String acc)
    {
        String url = "https://mesfa.store/getProfile.php";
        RequestQueue r = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0 ; i<response.length(); i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if(object.getString("accName").equals(acc)) {
                            Glide.with(getActivity()).load(object.getString("user_avatar_image")).into(avatarHome);
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
}

