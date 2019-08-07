package com.rayahen.ryahen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.MediaController;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bransh_Ditals extends AppCompatActivity {
    TabHost tabHost;
    VideoView videoView1, videoView2, videoView3;
    String videoUri;
    private RecyclerView mVideosListView;
    private List<Video> mVideosList = new ArrayList<>();
    private VideoAdapter mVideoAdapter;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView recyclerView;
    List<BranchModel> branches;
    RecyclerView.LayoutManager layoutManager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bransh__ditals);

        tabHost = findViewById(R.id.tabss);
        tabHost.setup();
        TabHost.TabSpec imag = tabHost.newTabSpec("image");
        imag.setIndicator("Video");
        imag.setContent(R.id.tab1);
        tabHost.addTab(imag);

        TabHost.TabSpec video = tabHost.newTabSpec("video");
        video.setIndicator("Image");
        video.setContent(R.id.tab2);
        tabHost.addTab(video);


        mVideosListView = findViewById(R.id.List_video);


        /***populate video list to adapter**/
        layoutManager = new LinearLayoutManager(this);
        mVideosListView.setLayoutManager(layoutManager);

        recyclerView = findViewById(R.id.List_image);
        branches = new ArrayList<>();
        layoutManager1 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager1);
        Intent intent = getIntent();

        CatagoryID(intent.getExtras().getString("id"));
        Photo(intent.getExtras().getString("id"));

    }

    private void CatagoryID(final String id) {

        //progressDialog.setMessage("Waiting ......");
        //progressDialog.show();
        final List<Video> productModerls = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_branches_video, new Response.Listener<String>() {
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
                        productModerls.add(new Video(
                                product.getString("vedio")

                        ));
                        mVideosListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        VideoAdapter adapter = new VideoAdapter(bransh_Ditals.this, productModerls);
                        mVideosListView.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(bransh_Ditals.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        Requesthandeler.getInstance(bransh_Ditals.this).addToRequestQueue(stringRequest);

    }
    private void Photo(final String id) {

        //progressDialog.setMessage("Waiting ......");
        //progressDialog.show();
        final List<BrancImageModel> productModerls = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_branches_image, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
                try {
                    JSONObject jsonObject;
                    JSONArray array1 = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array1.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array1.getJSONObject(i);

                        //adding the product to product list
                        productModerls.add(new BrancImageModel(
                                product.getString("img")

                        ));
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        BranchAdapter_image adapter = new BranchAdapter_image(bransh_Ditals.this, productModerls);
                        recyclerView.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(bransh_Ditals.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        Requesthandeler.getInstance(bransh_Ditals.this).addToRequestQueue(stringRequest);

    }

}
