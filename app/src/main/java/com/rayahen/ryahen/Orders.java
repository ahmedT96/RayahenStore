package com.rayahen.ryahen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orders extends AppCompatActivity {
    List<OrderDiv> orderDivs;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        orderDivs=new ArrayList<>();
        recyclerView=findViewById(R.id.order_History);
        Intent intent=getIntent();
        CatagoryID(String.valueOf(sharedprefmanger.getInstance(this).getId()));
    }
    private void CatagoryID(final String id) {

        //progressDialog.setMessage("Waiting ......");
        //progressDialog.show();
        final List<OrderDiv> productModerls=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_Order, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
                try {
                    JSONObject jsonObject;
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);

                        //adding the product to product list
                        productModerls.add(new OrderDiv(
                                product.getString("order_ID"),
                                product.getString("member_ID"),
                                product.getString("build"),
                                product.getString("Adress"),
                                product.getString("flat"),
                                product.getString("total"),
                                product.getString("notes"),
                                product.getString("lat"),
                                product.getString("lang")
                                ));
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                        OrderAdapter adapter = new OrderAdapter(Orders.this, productModerls);
                        recyclerView.setAdapter(adapter);
                     //  Toast.makeText(Orders.this, productModerls.get(i).getName(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(Orders.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        Requesthandeler.getInstance(Orders.this).addToRequestQueue(stringRequest);

    }

}
