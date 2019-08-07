package com.example.omar.helpinghand;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class sharedprefmanger {
    private static sharedprefmanger instance;
    private static Context context1;
    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USERNAME="username";
    private static final String KEY_USER_EMAIL="email";
    private static final String KEY_USER_ID="userid";


    public sharedprefmanger(Context context) {
        this.context1=context;
 ;
    }
    public static synchronized sharedprefmanger getInstance(Context context)
    {
        if (instance==null){
            instance=new sharedprefmanger(context);
        }
        return instance;
    }
    public  boolean userIlogin(int id ,String email,String username)
    {
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);
        editor.apply();
        return true;

    }
    public  boolean islogin()
    {
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME,null)!=null)
        {
            return true;
        }
        return false;
    }
    public  boolean islogout()
    {
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public  String getUsername(){
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);
    }
    public  String getEmail(){
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);

    }



}
