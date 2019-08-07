package com.example.omar.helpinghand;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class myAdabters extends RecyclerView.Adapter<myAdabters.MyviewHolder>{
    private Context mCtx;

    private List<lists> productList;

    public myAdabters(Context mCtx, List<lists> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_item_cat, null);
        return new MyviewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {



        holder.textViewTitle.setText(productList.get(position).getName());
        holder.textViewPrice.setText(productList.get(position).getName());
        holder.imageView.setImageResource(productList.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    class MyviewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        CardView cardView;



        public MyviewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_cat);
            textViewPrice = itemView.findViewById(R.id.text_cat);
            cardView = itemView.findViewById(R.id.cards_cat);
            imageView = itemView.findViewById(R.id.img_cat);
        }

    }
}


