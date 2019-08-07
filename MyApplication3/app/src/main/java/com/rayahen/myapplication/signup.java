package com.example.omar.helpinghand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class signup extends AppCompatActivity {
EditText name,phone,passwprd;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.getSupportActionBar().hide();
        name=findViewById(R.id.phone);
        phone=findViewById(R.id.editpohone);
        passwprd=findViewById(R.id.editpassword);
       progressDialog=new ProgressDialog(this);
       if (sharedprefmanger.getInstance(this).islogin())
       {
           finish();
           startActivity(new Intent(this,MainActivity.class));
           return;
       }




    }
    public void done(View view) {
        registerUsser();

    }

    private void registerUsser() {
        final String Name =name.getText().toString().trim();
        final String Phone =phone.getText().toString().trim();
        final String pass =passwprd.getText().toString().trim();
        progressDialog.setMessage("Registering ......");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject;

                    jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("error")==false){
                        sharedprefmanger.getInstance(getApplicationContext())
                                .userIlogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("username")
                                );
                        Intent intent= new Intent(signup.this,MainActivity.class);
                        startActivity(intent);}
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(signup.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parms=new HashMap<>();
                parms.put("username",Name);
                parms.put("email",Phone);
                parms.put("password",pass);
                return parms;

            }
        };
        Requesthandeler.getInstance(this).addToRequestQueue(stringRequest);


    }
}
