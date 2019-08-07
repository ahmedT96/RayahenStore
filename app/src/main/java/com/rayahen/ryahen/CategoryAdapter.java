package com.rayahen.ryahen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatagoryViewHolder> {


    private Context mCtx;
    private List<CategoryModel> categoryModels;

    public CategoryAdapter(Context mCtx, List<CategoryModel> categoryModels) {
        this.mCtx = mCtx;
        this.categoryModels = categoryModels;
    }

    @Override
    public CatagoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_item_cat, null);
        return new CatagoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatagoryViewHolder holder, int position) {
        final CategoryModel categoryModel = categoryModels.get(position);

        //loading the image
      //  Glide.with(mCtx)
        //        .load(Constant.IMAG_URL + categoryModel.getImage())
          //      .into(holder.imageView);

        holder.textViewTitle.setText(categoryModel.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mCtx,Product.class);
               intent.putExtra("id",categoryModel.getId());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }


    class CatagoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
      //  ImageView imageView;
        CardView cardView;


        public CatagoryViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_cat);
            cardView = itemView.findViewById(R.id.cards_cat);
            //imageView = itemView.findViewById(R.id.img_cat);
        }

    }


}



