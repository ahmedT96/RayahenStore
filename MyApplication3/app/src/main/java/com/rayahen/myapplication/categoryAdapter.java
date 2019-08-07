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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.omar.helpinghand.Product;
import com.example.omar.helpinghand.R;

import java.util.List;


public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryViewHolder> {

    public ItemClick itemClick;

    private Context mCtx;
    private List<catagory> productList;

    public categoryAdapter(Context mCtx, List<catagory> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public categoryAdapter.categoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_item_cat, null);
        return new categoryAdapter.categoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(categoryAdapter.categoryViewHolder holder, final int position) {
        final catagory catagory = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(catagory.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(catagory.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intentlist=new Intent (mCtx,sub.class);
                Intentlist.putExtra("GategroyID",catagory.getId());
                Toast.makeText(mCtx,""+position,Toast.LENGTH_LONG).show();
                mCtx.startActivity(Intentlist);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class categoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        CardView cardView;


        public categoryViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_cat);
            cardView = itemView.findViewById(R.id.cards_cat);
            imageView = itemView.findViewById(R.id.img_cat);
        }

    }
}

