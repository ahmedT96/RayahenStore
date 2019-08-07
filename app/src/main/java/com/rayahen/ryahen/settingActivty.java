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

public class settingActivty extends AppCompatActivity {
    ImageView imgview,fav;
    TextView namet,prices;
    public Boolean x=true;
    TextView item_name,item_price,item_descreption;
    ImageView item_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageButton btncart;
    String ItemID="";
  //  Ditals item;
    //fav favitem;
    String ID, name,price,image,discreption;
    int kind;
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
        discreption=intent.getExtras().getString("discreption");
        kind=intent.getExtras().getInt("kind");



        namet.setText(name);
        prices.setText(price);
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
                    price,discreption,kind
            ));
            if (id<0)
            {        Toast.makeText(settingActivty.this,"this eror", Toast.LENGTH_SHORT).show();
            }
            else
            {        Toast.makeText(settingActivty.this,R.string.addtoFav , Toast.LENGTH_LONG).show();
                fav.setImageResource(R.drawable.ic_favorite_black_24dp);

            }

    }
    else
        {
            localdb.removeToFav(ID);
            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            Toast.makeText(settingActivty.this, R.string.remove, Toast.LENGTH_SHORT).show();

        }
    }
});


        btncart=findViewById(R.id.Addto_carts);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!localdb.IsCart(ID)) {
                    long id = new database(getBaseContext()).addToCart(new Order(
                            ID, name,
                            String.valueOf(quantity),
                            String.valueOf(totalprice),
                            null,
                            image

                    ));
                    if (id < 0) {
                        Toast.makeText(settingActivty.this, "this eror", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(settingActivty.this, R.string.addToCart, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(settingActivty.this, R.string.updaat, Toast.LENGTH_SHORT).show();

                }                // Toast.makeText(Item_inormation.this,"Add to cart" +item.getName()+item.getPrice()+item.getDiscount(),Toast.LENGTH_LONG).show();

            }
        });

        //item_id


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






