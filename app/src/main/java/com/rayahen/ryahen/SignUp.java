package com.rayahen.ryahen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Random;

public class SignUp extends AppCompatActivity {
    EditText name,phone,passwprd;
    ProgressDialog progressDialog;
    List<String> kays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getSupportActionBar().hide();
        name=findViewById(R.id.phone);
        phone=findViewById(R.id.editpohone);
        kays=new ArrayList<>();

        passwprd=findViewById(R.id.editpassword);
        progressDialog=new ProgressDialog(this);
        if (sharedprefmanger.getInstance(this).islogin())
        {
            finish();
            startActivity(new Intent(this,Home.class));
            return;
        }


    }
    public void done(View view) {
        registerUsser();
    }

    private void registerUsser() {
        int max = 9999;
        int min = 1111;
        int diff = max - min;
        Random rn = new Random();
          int i = rn.nextInt(diff + 1);
        i += min;

    final String Name =name.getText().toString().trim();
        final String Phone =phone.getText().toString().trim();
        final String pass =passwprd.getText().toString().trim();
        sharedprefmanger.getInstance(getApplicationContext()).userIconform(Name,Phone,pass);

        if(name.getText().toString().isEmpty()&phone.getText().toString().isEmpty()& passwprd.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please chack informtion", Toast.LENGTH_SHORT).show();        }
        else {


            progressDialog.setMessage("Registering ......");
            progressDialog.show();
            String msg="Code'"+String.valueOf(i)+"'";
            final int finalI = i;
            StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://smsmisr.com/api/webapi/?username=0PUUYAXC&password=0PUUYA&language=1&sender=RAYAHEN&mobile=2"+Phone+",&message="+msg, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject;

                        jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("code") == 1901) {
                            Intent intent = new Intent(SignUp.this, vaildtion.class);
                            intent.putExtra("code", finalI);
                            startActivity(intent);
                            finish();
                        }
                        else  if (jsonObject.getInt("code") == 1905) {
                            Toast.makeText(getApplicationContext(), "رقم غير صحيح ", Toast.LENGTH_LONG).show();

                        }

                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parms = new HashMap<>();

                    return parms;

                }
            };
            Requesthandeler.getInstance(this).addToRequestQueue(stringRequest);

        }
    }
}
