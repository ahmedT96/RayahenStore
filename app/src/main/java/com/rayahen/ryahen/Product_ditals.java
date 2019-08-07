package com.rayahen.ryahen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xw.repo.BubbleSeekBar;

public class Product_ditals extends AppCompatActivity {
    ImageView imgview,fav;
    TextView namet,prices;
    public Boolean x=true;
    TextView item_name,item_price,item_descreption;
    ImageView item_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageButton btncart;
    String ItemID="";
    FavoritModel favitem;
    String ID, name,price,image,discreption;
    int kind;
    int quantity=100;
    int totalprice=1;
    database localdb;
    BubbleSeekBar qounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_ditals);
        imgview =  findViewById(R.id.item_imag);
        fav =  findViewById(R.id.favorit);
        localdb=new database(this);
        namet =  findViewById(R.id.Item_name1);
        item_descreption =  findViewById(R.id.Item_descreption1);

        prices =  findViewById(R.id.Item_price1);
        qounter=findViewById(R.id.qounter);
        qounter.getConfigBuilder()
                .min(125)
                .max(3000)
                .progress(100)
                .sectionCount(23)
                .build();
        Intent intent=getIntent();
        name=intent.getExtras().getString("name");
        kind=intent.getExtras().getInt("kind");

        final String pric =intent.getExtras().getString("price");
        final double x;
        x=Double.parseDouble(pric)/1000.0;
        price =String.valueOf(x*125);
        totalprice= (int) (x*100);
        image=intent.getExtras().getString("image");
        ID=intent.getExtras().getString("id");
        discreption=intent.getExtras().getString("discreption");
        qounter.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
               // displayCost(progress*((Integer.parseInt(price))/1000));


              displayCost((int) (x*progress));
                quantity=progress;
                totalprice= (int) (progress*x);

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });
        namet.setText(name);
        item_descreption.setText(discreption);
        prices.setText(price+" EGP");
        Glide.with(this)
                .load(Constant.vide_URL+intent.getExtras().getString("image"))
                .into(imgview);
        if (localdb.IsFavorite(ID))
        {
            fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!localdb.IsFavorite(ID))
                {
                    long id= new database(getBaseContext()).addToFav(new FavoritModel(
                            ID,name,
                            image,
                            pric,discreption,kind
                    ));
                    if (id<0)
                    {        Toast.makeText(Product_ditals.this,"this eror",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {        Toast.makeText(Product_ditals.this, R.string.addtoFav ,Toast.LENGTH_LONG).show();
                        fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    }

                }
                else
                {
                    localdb.removeToFav(ID);
                    fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(Product_ditals.this, R.string.remove, Toast.LENGTH_SHORT).show();

                }
            }
        });


        btncart=findViewById(R.id.Addto_carts);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!localdb.IsCart(ID))
                {
                    long id= new database(getBaseContext()).addToCart(new Order(
                            ID,name,
                            String.valueOf(quantity),
                            String.valueOf(totalprice),
                            null,
                            image

                    ));
                    if (id<0)
                    {        Toast.makeText(Product_ditals.this,"this error",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {        Toast.makeText(Product_ditals.this, R.string.addToCart ,Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    localdb.updateToCart(new Order(
                            ID,name,
                            String.valueOf(quantity),
                            String.valueOf(totalprice),
                            null,
                            image

                    ));
                    Toast.makeText(Product_ditals.this, R.string.updaat, Toast.LENGTH_SHORT).show();

                }




            }
        });


    }

    private void displayCost(int quantity) {
        StringBuilder sb = new StringBuilder();
        sb.append(quantity+" EGP");

        //String fmt = NumberFormat.getCurrencyInstance(total);
        prices.setText(sb.toString());
    }
}






