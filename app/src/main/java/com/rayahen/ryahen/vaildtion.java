package com.rayahen.ryahen;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class vaildtion extends AppCompatActivity {
EditText editText;
Button button;
ProgressDialog progressDialog;
int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaildtion);
        editText=findViewById(R.id.vaildationss);
        progressDialog=new ProgressDialog(this);


        Intent intent=getIntent();
        x=intent.getExtras().getInt("code");

    }
    public void cliv(View view) {
        int y= Integer.parseInt(editText.getText().toString().trim());

        if (x==y)
        {
            Intent intent = new Intent(vaildtion.this, Home.class);
            registerUsser();
        }
        else {
            Toast.makeText(this, "كود غير صحيح", Toast.LENGTH_SHORT).show();
        }
    }
    private void registerUsser() {
        final String Name =sharedprefmanger.getInstance(this).getUsernameConform();
        final String Phone =sharedprefmanger.getInstance(this).getemailConform();
        final String pass =sharedprefmanger.getInstance(this).getpassConform();

            progressDialog.setMessage("Registering ......");
            progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject;

                    jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("error") == false) {
                        sharedprefmanger.getInstance(getApplicationContext())
                                .userIlogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("username"), 0
                                );
                        Intent intent = new Intent(vaildtion.this, Home.class);
                        startActivity(intent);
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();

                }
                // Intent intent= new Intent(signup.this,MainActivity.class);
                //startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(vaildtion.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("username", Name);
                parms.put("email", Phone);
                parms.put("password", pass);
                return parms;

            }
        };
        Requesthandeler.getInstance(this).addToRequestQueue(stringRequest);
    }


}

