package com.rayahen.ryahen;

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

public class SignIn extends AppCompatActivity {
    EditText name,passwprd;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if (sharedprefmanger.getInstance(this).islogin())
        {
            finish();
            startActivity(new Intent(this,Home.class));
            return;
        }
        this.getSupportActionBar().hide();
        name=findViewById(R.id.editpohone1);
        passwprd=findViewById(R.id.editpassword1);
        progress=new ProgressDialog(this);


    }
    public void userlogin() {
        final String username = name.getText().toString();
        final String password = passwprd.getText().toString();
        progress.setMessage("please wait ......");

        if (name.getText().toString().isEmpty() & passwprd.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please chack informtion", Toast.LENGTH_SHORT).show();
        } else {
            progress.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progress.dismiss();
                    try {
                        JSONObject Object;

                        Object = new JSONObject(response);
                        if (!Object.getBoolean("error")) {
                            sharedprefmanger.getInstance(getApplicationContext())
                                    .userIlogin(
                                            Object.getInt("id"),
                                            Object.getString("email"),
                                            Object.getString("username"),
                                            Object.getInt("GroupID")
                                    );
                            if (Object.getInt("GroupID") == 3) {
                                Toast.makeText(SignIn.this, "login sucuccessful", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SignIn.this, Order_history.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "login sucuccessful", Toast.LENGTH_LONG).show();
                                finish();

                                Intent intent = new Intent(SignIn.this, Home.class);
                                startActivity(intent);
                            }


                        } else {
                            Toast.makeText(SignIn.this, Object.getString("message"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Intent intent= new Intent(signup.this,MainActivity.class);
                    //startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    Toast.makeText(SignIn.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parms = new HashMap<>();
                    parms.put("email", username);
                    parms.put("password", password);
                    return parms;
                }


            };
            Requesthandeler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
    public void sinin(View view) {
        userlogin();
        // Intent intent= new Intent(login.this,MainActivity.class);
        //startActivity(intent);
    }

    public void sinup(View view) {
        Intent s= new Intent(SignIn.this,SignUp.class);
        startActivity(s);
    }
}
