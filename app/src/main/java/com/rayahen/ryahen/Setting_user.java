package com.rayahen.ryahen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Setting_user extends AppCompatActivity {
    EditText name,phone,passwprd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user);
        name=findViewById(R.id.phoneEdit);
        phone=findViewById(R.id.editpohoneEdit);
        passwprd=findViewById(R.id.editpasswordEdit);
        name.setText(sharedprefmanger.getInstance(this).getUsername(), TextView.BufferType.EDITABLE);
        phone.setText(sharedprefmanger.getInstance(this).getEmail(), TextView.BufferType.EDITABLE);

        if (!sharedprefmanger.getInstance(this).islogin())
        {
            finish();
            startActivity(new Intent(this,SignIn.class));
            return;
        }
    }

    public void done(View view) {
        registerUsser();
        finish();


    }
    private void registerUsser() {
        final String Name =name.getText().toString().trim();
        final String Phone =phone.getText().toString().trim();
        final String pass =passwprd.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.URL_UBDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject;

                    jsonObject = new JSONObject(response);
                  //  if (jsonObject.getBoolean("error")==false){
                        sharedprefmanger.getInstance(getApplicationContext())
                                .userIlogin(
                                        jsonObject.getInt("userID"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("username"),0
                              );
                        Intent intent= new Intent(Setting_user.this,Home.class);
                        startActivity(intent);
                   // }
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Setting_user.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parms=new HashMap<>();
                parms.put("id", String.valueOf(sharedprefmanger.getInstance(Setting_user.this).getId()));
                parms.put("name",Name);
                parms.put("pass",pass);
                parms.put("email",Phone);
                return parms;

            }
        };
        Requesthandeler.getInstance(this).addToRequestQueue(stringRequest);


    }

}
