package com.example.app_go_go.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.app_go_go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity  {
    EditText edtNameAccLog, edtPassAccLog;
    Button btnSignIn;
    TextView tv;
    int noneTk = 0, noneMk= 0;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        getinfo();
        //mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        tv.setText(token);
                    }
                });
    }



    public void getinfo() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null) {
            edtNameAccLog.setText(b.getString("nameAcc"));
            edtPassAccLog.setText(b.getString("passAcc"));
        }
    }

    public void sendinfoToMain() {
        Intent intent2 = new Intent(SignIn.this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("acc",edtNameAccLog.getText().toString());
        intent2.putExtras(b);
        startActivity(intent2);
    }
    public void initView() {
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        edtNameAccLog = (EditText) findViewById(R.id.edtnameAccLog);
        edtPassAccLog = (EditText) findViewById(R.id.edtpassAccLog);
        tv = findViewById(R.id.token);
    }
    public void SignIn(View v)
    {
        String url = "https://mesfa.store/Accounts.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if(jsonObject.getString("aN").equals(edtNameAccLog.getText().toString()))
                        {
                            if(jsonObject.getString("aP").equals(edtPassAccLog.getText().toString()))
                            {
                                sendinfoToMain();
                                createUserAuth();
                                break;
                            }
                            else {
                                Toast.makeText(SignIn.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                        else {
                            if (i == response.length() - 1) {
                                Toast.makeText(SignIn.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
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
        requestQueue.add(jsonArrayRequest);

    }
    public void createUserAuth() {
       RequestQueue q = Volley.newRequestQueue(this);
        StringRequest s = new StringRequest(Request.Method.POST, "https://mesfa.store/putToken.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignIn.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pr = new HashMap<>();
                pr.put("acc_token", edtNameAccLog.getText().toString());
                pr.put("content_token", tv.getText().toString());
                return pr;
            }
        };
        q.add(s);
    }

}