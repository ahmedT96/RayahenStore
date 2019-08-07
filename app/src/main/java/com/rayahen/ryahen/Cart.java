package com.rayahen.ryahen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    TextView txtTotal,total_dilivary;
    Button btnplace,btnadress;
    List<Order> carts = new ArrayList<>();
    CartAdepter adepter;
    String location;
    static String branch,build,flat,mal;
    Common common;
    List<String> citys;
    String address_cost=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (!sharedprefmanger.getInstance(this).islogin())
        {
            Intent intent=new Intent(Cart.this,SignIn.class);
            startActivity(intent);
            finish();
        }
            recyclerView = findViewById(R.id.listCart);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            txtTotal = findViewById(R.id.total_cart);
             total_dilivary = findViewById(R.id.total_dilivary);

        btnadress = findViewById(R.id.btn_address);
        total_dilivary.setText(String.valueOf( Constant.cost_adress)+" Egp");
            btnplace = findViewById(R.id.chackout);
        btnplace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Constant.f==true) {
                        RequestOrder request1 = new RequestOrder(sharedprefmanger.getInstance(Cart.this).getId(), sharedprefmanger.getInstance(Cart.this).getEmail(), sharedprefmanger.getInstance(Cart.this).getUsername(),
                                branch, txtTotal.getText().toString(),build,flat,mal, carts);
                        insertOrder(request1);
                        //requests.child(String.valueOf(System.currentTimeMillis())).setValue(request1);
                        new database(getApplicationContext()).cleenCart();
                        Toast.makeText(Cart.this, R.string.doneOrder, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Cart.this, Home.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(Cart.this, "Please select your adress", Toast.LENGTH_SHORT).show();                    }
                }
            });
            loadListItem();
            btnadress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert();


                }
            });

        }
    public void insertOrder(final RequestOrder request) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.postCart, new Response.Listener<String>() {
            // String newDataArray=gson.toJson(carts);

            @Override
            public void onResponse(String response) {

                final String result=response.toString();
                Toast.makeText(Cart.this, R.string.track, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cart.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parms = new HashMap<>();
                parms.put("id", String.valueOf(request.getId()));
                parms.put("city", request.getAddress());
                parms.put("total", request.getTotal());
                parms.put("build", request.getBuliding());
                parms.put("flat", request.getFlat());
                parms.put("notes", request.getCommant());
                parms.put("lat1", Constant.lat);
                parms.put("lang1", Constant.log);
                final Gson gson=new Gson();
                final String newDataArray=gson.toJson(request.getOrder());
               parms.put("carts", newDataArray);


                return parms;
            }

        };
        Requesthandeler.getInstance(this).addToRequestQueue(stringRequest);
    }

        private void showAlert() {
            final AlertDialog.Builder alertdialg=new AlertDialog.Builder(this);
            View view=getLayoutInflater().inflate(R.layout.dialog,null);

            final Spinner city=view.findViewById(R.id.city);
            final EditText editText=view.findViewById(R.id.bulid);
            final EditText room=view.findViewById(R.id.room);
            final EditText nots=view.findViewById(R.id.Communt);
            citys=new ArrayList<>();
            List<String>c=new ArrayList<>();
            c.add("Select Branch");
            c.add("October-Branch");
            c.add("Zaid-Branch");
            c.add("Sharm El-Shaikh");
            c.add("altajamue alkhamis-Branch");
            c.add("Suez-Branch");
            loadcbranch();
            ArrayAdapter<String>adapter=new ArrayAdapter<>(Cart.this,android.R.layout.simple_spinner_item,
                    c);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
city.setAdapter(adapter);

            Button dons=view.findViewById(R.id.dons);

            alertdialg.setView(view);
            AlertDialog bulid=alertdialg.create();
            bulid.show();

 dons.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(!editText.getText().toString().isEmpty()&!room.getText().toString().isEmpty()&!city.getSelectedItem().toString().equalsIgnoreCase("Select Branch"))
        {
                branch=city.getSelectedItem().toString();
                build=editText.getText().toString();
                flat=room.getText().toString();
                mal=nots.getText().toString();
                Intent intent=new Intent(Cart.this,MapsActivity.class);
                if (city.getSelectedItem().toString().equalsIgnoreCase("Zaid-Branch"))
                {
                    intent.putExtra("lat",30.0420781);
                    intent.putExtra("lang",30.9449751);
                }
            if (city.getSelectedItem().toString().equalsIgnoreCase("Sharm El-Shaikh"))
            {
                intent.putExtra("lat",30.0420781);
                intent.putExtra("lang",30.9449751);
            }
            if (city.getSelectedItem().toString().equalsIgnoreCase("altajamue alkhamis-Branch"))
            {
                intent.putExtra("lat",30.0420781);
                intent.putExtra("lang",30.9449751);
            }
            if (city.getSelectedItem().toString().equalsIgnoreCase("Suez-Branch"))
            {
                intent.putExtra("lat",29.9620576);
                intent.putExtra("lang",32.5374316);
            }
            if (city.getSelectedItem().toString().equalsIgnoreCase("October-Branch"))
            {
                intent.putExtra("lat",29.97416);
                intent.putExtra("lang",30.94467);
            }
                startActivity(intent);

                finish();


        }
        else {
            Toast.makeText(Cart.this, R.string.compleatedata, Toast.LENGTH_LONG).show();

        }
    }
});

        }

        private void loadListItem() {

            carts = new database(this).getCart();
            adepter = new CartAdepter(carts, this);
            recyclerView.setAdapter(adepter);
            layoutManager=new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            adepter.notifyDataSetChanged();
            int total = 0;
            for (Order order : carts)
                total += (Double.parseDouble(order.getPrice()));
            //   Locale locale = new Locale("en", "US");
            StringBuilder sb = new StringBuilder();
            total= (int) (total+Constant.cost_adress);
            sb.append(total+" EGP");

            //String fmt = NumberFormat.getCurrencyInstance(total);
            txtTotal.setText(sb.toString());

        }
    private void loadcbranch() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_brancjSelect,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            citys.add("Select Branches");
                            //traversing through all the object
                            for (int i = 1; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                citys.add(
                                        product.getString("name")

                                );
                            }


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
        Volley.newRequestQueue(Cart.this).add(stringRequest);
    }

    }




