package com.rayahen.ryahen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class Catagory extends AppCompatActivity {
    RecyclerView recyclerView;
    List<CategoryModel> categoryModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);
//        this.getActionBar().setTitle("Catagory");
        recyclerView=findViewById(R.id.category);
        categoryModels = new ArrayList<>();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        loadProducts();

    }
    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_show,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                categoryModels.add(new CategoryModel(
                                        product.getString("catID"),
                                        product.getString("catName")
                                      //  product.getString("category_pic")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            CategoryAdapter adapter = new CategoryAdapter(Catagory.this, categoryModels);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(Catagory.this).add(stringRequest);
    }

}
