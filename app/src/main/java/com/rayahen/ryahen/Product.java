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

public class Product extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ProductModerl> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView= findViewById(R.id.product);
        productList = new ArrayList<>();
        Intent intent=getIntent();
        CatagoryID(intent.getExtras().getString("id"));


    }
    private void CatagoryID(final String id) {

        //progressDialog.setMessage("Waiting ......");
        //progressDialog.show();
        final List<ProductModerl> productModerls=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_PRO, new Response.Listener<String>() {
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
                        productModerls.add(new ProductModerl(
                                product.getString("inv_pro_id"),
                                product.getString("inv_pro_name"),
                                product.getString("inv_pro_desc"),
                                product.getString("price"),
                                product.getString("inv_pro_img"),
                                product.getInt("kind")

                        ));
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        ProductsAdapter adapter = new ProductsAdapter(Product.this, productModerls);
                        recyclerView.setAdapter(adapter);                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        Requesthandeler.getInstance(Product.this).addToRequestQueue(stringRequest);

    }

}

