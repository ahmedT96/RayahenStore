package com.rayahen.ryahen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtfullname;
ImageView imageView ,imageBranch,imageNavg;
    Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        signin=findViewById(R.id.btnsign);
        signup=findViewById(R.id.btnsignUp);
        if (sharedprefmanger.getInstance(this).islogin())
        {
            if(sharedprefmanger.getInstance(this).getGroupId()==3)
            {
                Intent intent= new Intent(Home.this,Order_history.class);
                startActivity(intent);
                finish();
            }

            signin.setText(sharedprefmanger.getInstance(this).getUsername());
            signup.setText(sharedprefmanger.getInstance(this).getEmail());
            signin.setEnabled(false);
            signup.setEnabled(false);
        }


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Home.this,SignIn.class);
                startActivity(intent);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Home.this,SignUp.class);
                startActivity(intent);

            }
        });

imageView=findViewById(R.id.imageview1);
imageBranch=findViewById(R.id.branch2);

      loadimagebroduct();
      loadimagebranch();

        imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Home.this,Catagory.class);
        startActivity(intent);
    }
});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        txtfullname=header.findViewById(R.id.usernametext);
        txtfullname.setText(sharedprefmanger.getInstance(this).getUsername());
        imageNavg=header.findViewById(R.id.imgNav);
        loadimagenavg();
    }

    public void branchView(View view) {
        Intent intent=new Intent(Home.this,Branches.class);
        startActivity(intent);
    }

    public void facebock(View view) {
        Intent wepsite = new Intent(Intent.ACTION_VIEW);
        wepsite.setData(Uri.parse("https://www.facebook.com/Rayahen-Roastery-محمصة-رياحين-677281375776747/"));
        if (wepsite.resolveActivity(getPackageManager()) != null) {
            startActivity(wepsite);


        }
    }
    public void calling(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0128 080 0058"));
        if (intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        }
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
        if (sharedprefmanger.getInstance(this).islogin()) {
            getMenuInflater().inflate(R.menu.home, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            sharedprefmanger.getInstance(this).islogout();
            finish();
            startActivity(new Intent(this,SignIn.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home) {
            // Handle the camera action
        } else if (id == R.id.nav_Cart) {
            if (new database(this).IsCartEmpty())
            {
                Toast.makeText(this, R.string.CartEmpty, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, Cart.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_Favorit) {
            if (new database(this).IsFavEmpty())
            {
                Toast.makeText(this, R.string.favoritEmpty, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, Favorit.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_Order) {
            Intent intent=new Intent(this,Orders.class);
            startActivity(intent);

        } else if (id == R.id.nav_Setting) {
            Intent intent=new Intent(this,Setting_user.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
public void   loadimagebroduct()
{
    StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_product_image,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        
                        JSONObject Object;
                         Object = new JSONObject(response);
                        Glide.with(getApplicationContext())
                                .load(Constant.video_URL+Object.getString("img"))
                                .into(imageView);
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
    Volley.newRequestQueue(Home.this).add(stringRequest);


}
    public void   loadimagebranch()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_branche_image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject Object;
                            Object = new JSONObject(response);
                            Glide.with(getApplicationContext())
                                    .load(Constant.video_URL+Object.getString("img"))
                                    .into(imageBranch);
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
        Volley.newRequestQueue(Home.this).add(stringRequest);


    }
    public void   loadimagenavg()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_bar_image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject Object;
                            Object = new JSONObject(response);
                            Glide.with(getApplicationContext())
                                    .load(Constant.video_URL+Object.getString("img"))
                                    .into(imageNavg);
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
        Volley.newRequestQueue(Home.this).add(stringRequest);


    }


    public void whatsapp(View view) {
        Uri uri=Uri.parse("smsto:"+"201280800058");
        Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
        intent.setPackage("com.whatsapp");
        startActivity(intent);
        //
    }

    public void instegram(View view) {
        Uri uri = Uri.parse("https://www.instagram.com/ryhan_roastery/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/ryhan_roastery")));
        }
    }
}
