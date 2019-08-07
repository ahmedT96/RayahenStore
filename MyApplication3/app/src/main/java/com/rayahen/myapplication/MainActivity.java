package com.rayahen.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment=null;
    Button editset;
    TextView txtfullname;
    RecyclerView recyclermune,recyclermune2,recyclermune3,recyclermune4;
    RecyclerView.LayoutManager layoutManager;
    List<com.example.omar.helpinghand.lists> items;
    ProgressDialog progressDialog;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    HashMap<String,String> imagelist;
    SliderLayout mslider;

    List<com.example.omar.helpinghand.Product> productList;
    List<com.example.omar.helpinghand.banner> pannerss;

    List<com.example.omar.helpinghand.catagory> productList2;

    //the search
    List<String> suggestlist=new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!com.example.omar.helpinghand.sharedprefmanger.getInstance(this).islogin())
        {
            finish();
            startActivity(new Intent(this, com.example.omar.helpinghand.login.class));
            return;
        }

        fragment=new com.example.omar.helpinghand.mainFregment();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.area1,fragment);
        ft.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        txtfullname=header.findViewById(R.id.usernametext);
        txtfullname.setText(com.example.omar.helpinghand.sharedprefmanger.getInstance(this).getUsername());

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
/*
        editset= (Button) findViewById(R.id.editset);
        editset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,editSetting.class);
                startActivity(intent);
            }
        });
*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.action_cart)
        {

        }
        else if (item.getItemId()==R.id.logout)
        {
            com.example.omar.helpinghand.sharedprefmanger.getInstance(this).islogout();
            finish();
            startActivity(new Intent(this, com.example.omar.helpinghand.login.class));

        }



        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment=null;

        int id = item.getItemId();
        if (id == R.id.nav_menu) {
            fragment=new com.example.omar.helpinghand.mainFregment();
        }
        else if (id == R.id.nav_cart) {
            fragment=new com.example.omar.helpinghand.cart();
        }
        else if (id == R.id.nav_order) {
            //fragment=new setting();
        }

        else if (id == R.id.nav_fav) {
            fragment=new com.example.omar.helpinghand.favorit();
        }
        if(fragment!=null)
        {
            android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.area1,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ononon(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:010000000000"));
        if (intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        }
    }

    public void locationf(View view) {
        Intent location = new Intent(Intent.ACTION_VIEW);
        location.setData(Uri.parse("geo:29.9550311,30.9196702,16z"));
        startActivity(location);
    }

    public void all(View view) {
        fragment=new com.example.omar.helpinghand.setting();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.area1,fragment);
        ft.commit();


    }

    public void adress(View view) {
        Intent intent =new Intent(MainActivity.this, com.example.omar.helpinghand.MapsActivity.class);
        startActivity(intent);
    }

    public void onclickRemove(View view) {

    }
}
