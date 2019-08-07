package com.rayahen.ryahen;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class MyLocationListener implements LocationListener {

    public static double latitude;
    public  static double longitude;
    Context context;

    public MyLocationListener( Context context) {

        this.context = context;
    }

    @Override

    public void onLocationChanged(Location loc) {
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        String Text = "My current Latitude = " + latitude  + " Longitude = " + longitude;
      // Toast.makeText( context,Text,Toast.LENGTH_SHORT).show();
        CatagoryID(String.valueOf(sharedprefmanger.getInstance(context).getId()));
            //SendQueryString(); // for send the  Query String of latitude and logintude to the webapp.
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText( context,"Gps Disabled please open GPS",Toast.LENGTH_SHORT ).show();
    }


    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText( context,"Gps Enabled",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    private void CatagoryID(final String id) {

        //progressDialog.setMessage("Waiting ......");
        //progressDialog.show();
        final List<OrderDiv> productModerls=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_Track_div, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                parms.put("lat", String.valueOf(MyLocationListener.latitude));
                parms.put("long", String.valueOf(MyLocationListener.longitude));
                return parms;

            }
        };
        Requesthandeler.getInstance(context).addToRequestQueue(stringRequest);

    }

}
