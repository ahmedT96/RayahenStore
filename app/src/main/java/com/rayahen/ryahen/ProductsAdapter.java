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

import com.bumptech.glide.Glide;


import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>  {


    private Context mCtx;
    private List<ProductModerl> productList;

    public ProductsAdapter(Context mCtx, List<ProductModerl> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final ProductModerl product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(Constant.vide_URL+product.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(product.getName());
        holder.textViewPrice.setText(product.getPrice()+" EGP");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getKind()==0) {
                    Intent intent = new Intent(mCtx, Product_ditals.class);
                    intent.putExtra("id", product.getId());
                    intent.putExtra("name", product.getName());
                    intent.putExtra("price", product.getPrice());
                    intent.putExtra("image", product.getImage());
                    intent.putExtra("discreption", product.getDescrepton());
                    intent.putExtra("kind", product.getKind());
                    mCtx.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(mCtx, settingActivty.class);
                    intent.putExtra("id", product.getId());
                    intent.putExtra("name", product.getName());
                    intent.putExtra("price", product.getPrice());
                    intent.putExtra("image", product.getImage());
                    intent.putExtra("discreption", product.getDescrepton());
                    intent.putExtra("kind", product.getKind());

                    mCtx.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        CardView cardView;



        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_product);
            textViewPrice = itemView.findViewById(R.id.text_price);
            cardView = itemView.findViewById(R.id.cards);
            imageView = itemView.findViewById(R.id.img_product);
        }

    }
}
