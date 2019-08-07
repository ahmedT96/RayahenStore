package com.example.omar.helpinghand;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class mainFregment extends Fragment {
    RecyclerView recyclermune,recyclermune2,recyclermune3,recyclermune4;
    RecyclerView.LayoutManager layoutManager;
    List<lists>items;
    ProgressDialog progressDialog;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    HashMap<String,String> imagelist;
    SliderLayout mslider;

    List<Product> productList;
    List<banner> pannerss;

    List<catagory> productList2;

    //the search
    List<String> suggestlist=new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fregment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ViewPager viewPager=view.findViewById(R.id.pager);
        //  viewHolder viewHolder=new viewHolder(getContext());
        // viewPager.setAdapter(viewHolder);
        // loadRecyclerview();
        items = new ArrayList<>();
        items.add(new lists("المهندسين", "xcc", R.drawable.fr1));
        items.add(new lists("الجيزه", "xcc", R.drawable.fr2));
        items.add(new lists("اكتوبر", "xcc", R.drawable.fr3));
        items.add(new lists("الشيخ زايد", "xcc", R.drawable.fr4));
        items.add(new lists("5li", "xcc", R.drawable.fggg));

        materialSearchBar = view.findViewById(R.id.searchBars);
        materialSearchBar.setHint("Enter you product");
        loadSuggest();
        recyclermune = (RecyclerView) view.findViewById(R.id.moss);
        recyclermune2 = (RecyclerView) view.findViewById(R.id.moss2);
        recyclermune3 = (RecyclerView) view.findViewById(R.id.moss3);
        recyclermune4 = (RecyclerView) view.findViewById(R.id.moss4);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        productList = new ArrayList<>();
        productList2 = new ArrayList<>();

        recyclermune.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclermune3.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclermune4.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        recyclermune2.setLayoutManager(linearLayoutManager2);
        loadProducts();
        loadcatagory();
        //stupSlider();
        //setslider();
        //mslider=view.findViewById(R.id.slider);
        //pannerss = new ArrayList<>();
        /*
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);


        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

    }


    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }
    private void stupSlider() {
        imagelist=new HashMap<>();
for (String key:imagelist.keySet()) {
    String[] keysplit = key.split("-");
    String namesplit = keysplit[0];
    String idOfsplit = keysplit[1];


    TextSliderView textSliderView = new TextSliderView(getContext());
    textSliderView.description(namesplit).image(imagelist.get(key)).setScaleType(BaseSliderView.ScaleType.Fit)
            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                }
            });


        textSliderView.bundle(new Bundle());
        textSliderView.getBundle().putString("",null);
         mslider.addSlider(textSliderView);

//imagelist.put(viewHolder.slide,viewHolder.image);
 mslider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
           mslider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mslider.setCustomAnimation(new DescriptionAnimation());
          mslider.setDuration(4000);
          mslider.startAutoCycle();
}
    }
*/
    }
    private void loadSuggest() {

    }
    private void loadbanner() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_banner,
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
                                pannerss.add(new banner(
                                        product.getString("id"),
                                        product.getString("name"),
                                        product.getString("image")
                                ));

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
        Volley.newRequestQueue(getContext()).add(stringRequest);
}

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_show,
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
                                productList.add(new Product(
                                        product.getString("id"),
                                        product.getString("title"),
                                        product.getString("image"),
                                        product.getString("price")

                                ));

                            }

                            //creating adapter object and setting it to recyclerview
                         ProductsAdapter adapter = new ProductsAdapter(getActivity(), productList);

                            myAdabters adabters1=new myAdabters(getActivity(),items);
                            recyclermune.setAdapter(adapter);
                            recyclermune2.setAdapter(adabters1);
                            recyclermune4.setAdapter(adapter);
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
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    private void loadcatagory() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_confiq,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array1 = new JSONArray(response);

                                //traversing through all the object

                                for (int i = 0; i < array1.length(); i++) {
                                    JSONObject catagory1 = array1.getJSONObject(i);

                                    productList2.add(new catagory(
                                            catagory1.getInt("id"),
                                            catagory1.getString("name"),
                                            catagory1.getString("image")
                                    ));
                            }
                            categoryAdapter adapter3 = new categoryAdapter(getActivity(), productList2);


                            recyclermune3.setAdapter(adapter3);

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
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }



    private void setslider() {

    }


}





