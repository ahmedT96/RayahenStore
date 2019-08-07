package com.example.omar.helpinghand;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class settingActivty extends AppCompatActivity {
    ImageView imgview,fav;
    TextView namet,prices;
    public Boolean x=true;
    TextView item_name,item_price,item_descreption;
    ImageView item_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView btncart;
    String ItemID="";
    Ditals item;
    fav favitem;
    String ID, name,price,image;
    int quantity=1,totalprice;
    database localdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activty);
        imgview =  findViewById(R.id.item_imag);
        fav =  findViewById(R.id.favorit);
localdb=new database(this);
        namet =  findViewById(R.id.Item_name1);
        prices =  findViewById(R.id.Item_price1);
        Intent intent=getIntent();
      name=intent.getExtras().getString("name");
      price =intent.getExtras().getString("price");
       image=intent.getExtras().getString("image");
        ID=intent.getExtras().getString("id");

        namet.setText(name);
        prices.setText(price);
        Glide.with(this)
                .load(Constants.IMAG_URL+intent.getExtras().getString("image"))
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
            long id= new database(getBaseContext()).addToFav(new fav(
                    ID,name,
                    image,
                    price
            ));
            if (id<0)
            {        Toast.makeText(settingActivty.this,"this eror",Toast.LENGTH_SHORT).show();
            }
            else
            {        Toast.makeText(settingActivty.this,"Add To Favorit" ,Toast.LENGTH_LONG).show();
                fav.setImageResource(R.drawable.ic_favorite_black_24dp);

            }

    }
    else
        {
            localdb.removeToFav(ID);
            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            Toast.makeText(settingActivty.this, "Remove To Favorit", Toast.LENGTH_SHORT).show();

        }
    }
});


        btncart=findViewById(R.id.Addto_carts);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id= new database(getBaseContext()).addToCart(new Order(
                        ID,name,
                        String.valueOf(quantity),
                        String.valueOf(totalprice),
                        null,
                        image

                ));
                if (id<0)
                {        Toast.makeText(settingActivty.this,"this eror",Toast.LENGTH_SHORT).show();
                }
                else
                {        Toast.makeText(settingActivty.this,"Add to cart" ,Toast.LENGTH_LONG).show();
                }


                // Toast.makeText(Item_inormation.this,"Add to cart" +item.getName()+item.getPrice()+item.getDiscount(),Toast.LENGTH_LONG).show();

            }
        });

        //item_id


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK)
        {imgview.setImageBitmap((Bitmap) data.getExtras().get("data"));
        }
    }



    public void increment(View view) {
        quantity=quantity+1;
        displayQuntity(quantity);
        totalprice=quantity*(Integer.parseInt(price));
        displayCost(totalprice);


    }



    public void decremnt(View view) {
        if (quantity>1){
            quantity=quantity-1;
            totalprice=quantity*(Integer.parseInt(price));
            displayCost(totalprice);
            displayQuntity(quantity);
        }
    }
    private void displayQuntity(int quantity) {
        TextView textViewQuntity=findViewById(R.id.txtQuntity);
        textViewQuntity.setText(String.valueOf(quantity));

    }

    private void displayCost(int quantity) {
        StringBuilder sb = new StringBuilder();
        sb.append(quantity+" EGP");

        //String fmt = NumberFormat.getCurrencyInstance(total);
        prices.setText(sb.toString());
    }
}






