package com.example.omar.helpinghand;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


class favViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {


    TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
    ImageView imageView;
    CardView cardView;
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View view) {

    }


    public favViewHolder(View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_product);
        textViewPrice = itemView.findViewById(R.id.text_price);
        cardView = itemView.findViewById(R.id.cards);
        imageView = itemView.findViewById(R.id.img_product);
    }
}

public class favoritAdapter extends RecyclerView.Adapter<favViewHolder>{
    private List<fav> listData=new ArrayList<>();
    private Context context;

    public favoritAdapter(List<fav> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public favViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item, null);
        return new favViewHolder(view);
    }

    @Override
    public void onBindViewHolder(favViewHolder holder, final int position) {

        Locale locale=new Locale("en","US");
        //NumberFormat fmt =NumberFormat.getCurrencyInstance(locale);
//     int price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.textViewPrice.setText(listData.get(position).getPrice());
        holder.textViewTitle.setText(listData.get(position).getTitle());
        Glide.with(context)
                .load(listData.get(position).getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,settingActivty.class);

                intent.putExtra("id",listData.get(position).getId());
                intent.putExtra("name",listData.get(position).getTitle());
                intent.putExtra("price",listData.get(position).getPrice());
                intent.putExtra("image",listData.get(position).getImage());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
