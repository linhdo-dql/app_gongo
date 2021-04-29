package com.example.app_go_go.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_go_go.Objects.Accounts;
import com.example.app_go_go.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    ImageButton imgbtnback;
    Button btnSignIn;
    EditText edtAccName, edtAccMail, edtAccPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }
    public void initView() {
        imgbtnback = (ImageButton) findViewById(R.id.imgbtnback);
        btnSignIn = (Button) findViewById(R.id.btnSignUp);
        edtAccName = (EditText) findViewById(R.id.edtAccName);
        edtAccMail = (EditText) findViewById(R.id.edtAccMail);
        edtAccPass = (EditText) findViewById(R.id.edtAccPass);
    }
    public void returntoSignIn(View v){
        startActivity(new Intent(SignUp.this, SignIn.class));
    }
    public void SignUp(View v)
    {
        String url = "https://mesfa.store/Signup.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    sendinfotoLogin();
                }
                else 
                {
                    Toast.makeText(SignUp.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUp.this, "Lỗi", Toast.LENGTH_SHORT).show();
                Log.e("err",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("accName",edtAccName.getText().toString());
                param.put("accPass",edtAccPass.getText().toString());
                param.put("accMail",edtAccMail.getText().toString());
                param.put("accActive","0");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void sendinfotoLogin() {
        Intent intent = new Intent(SignUp.this, SignIn.class);
        Bundle b = new Bundle();
        b.putString("nameAcc", edtAccName.getText().toString());
        b.putString("passAcc", edtAccPass.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
    }
}