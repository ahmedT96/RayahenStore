package com.example.omar.helpinghand;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class Requesthandeler{
    private static Requesthandeler instance;
    private RequestQueue requestQueue;
    private static Context context1;

    public Requesthandeler(Context context) {
        this.context1=context;
        this.requestQueue =getRequestQueue(); ;
    }
    public static synchronized Requesthandeler getInstance(Context context)
    {
        if (instance==null){
           instance=new Requesthandeler(context);
        }
        return instance;
    }
    public RequestQueue getRequestQueue(){
        if (requestQueue == null) {
            requestQueue= Volley.newRequestQueue(context1.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
