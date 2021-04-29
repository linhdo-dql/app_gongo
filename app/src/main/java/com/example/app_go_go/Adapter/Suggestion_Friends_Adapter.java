package com.example.app_go_go.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.app_go_go.Activities.ChildActivities.Profiles;
import com.example.app_go_go.Activities.Interface.ItemClickListener;
import com.example.app_go_go.Objects.Accounts;
import com.example.app_go_go.Objects.Friends;
import com.example.app_go_go.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Suggestion_Friends_Adapter extends RecyclerView.Adapter<Suggestion_Friends_Adapter.Suggestion_Friends_ViewHolder> {
    Context mcontext;
    ArrayList<Accounts> accounts;

    public Suggestion_Friends_Adapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void setDBsF(ArrayList<Accounts> s) {
        this.accounts = s;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Suggestion_Friends_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsuggestfr, parent, false);
        return new Suggestion_Friends_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Suggestion_Friends_ViewHolder holder, int position) {
        holder.getProfile(accounts.get(position).accName);
    }

    @Override
    public int getItemCount() {
        if(accounts!=null)
        {
            return accounts.size();
        }
        return 0;
    }

    public class Suggestion_Friends_ViewHolder extends RecyclerView.ViewHolder {
        TextView unamefS;
        ImageView imgfS;
        public Suggestion_Friends_ViewHolder(@NonNull View itemView) {
        super(itemView);
        imgfS = itemView.findViewById(R.id.imgsgfr);
        unamefS = itemView.findViewById(R.id.tvusfr);
        }
        public void getProfile(String acc)
        {
            String url = "https://mesfa.store/getProfile.php";
            RequestQueue r = Volley.newRequestQueue(mcontext);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i = 0 ; i<response.length(); i++)
                    {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            if(object.getString("accName").equals(acc)) {
                                Glide.with(mcontext).load(object.getString("user_avatar_image")).into(imgfS);
                                unamefS.setText(object.getString("name_user"));
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

}
