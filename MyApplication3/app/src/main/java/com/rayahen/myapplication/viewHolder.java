package com.example.omar.helpinghand;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class viewHolder extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public Integer [] image={R.drawable.whatss,R.drawable.s2,R.drawable.s3};
    public String[] slide={"Shoping","All taza","zorona H"};
    public viewHolder(Context context) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.layout_custom,null);
        ImageView imageView=view.findViewById(R.id.img_view);
        TextView textView=view.findViewById(R.id.textview5);


        textView.setText(slide[position]);
        imageView.setImageResource(image [position]);


//container.addView(view);
        ViewPager vp= (ViewPager) container;
        vp.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp= (ViewPager) container;
        View view= (View) object;
        vp.removeView(view);

        //container.removeView((RelativeLayout)object);

    }
}


