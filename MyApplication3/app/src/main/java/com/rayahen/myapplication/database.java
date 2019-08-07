package com.example.omar.helpinghand;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class database  {
    DB db;
    List<Order>result;
    List<fav>res;


    public database(Context context) {

        db=new DB(context);
    }

    public List<Order>getCart()
    {
        SQLiteDatabase data=db.getWritableDatabase();
        //SQLiteQueryBuilder qb =new SQLiteQueryBuilder();

        String[] sqlSelect={"productId","productName","quantity","price","discount","image"};
        //String sqlTable="orders_info";

        //qb.setTables("orders_info");

        Cursor c =data.query("orders_info",sqlSelect,null,null,null,null,null);
        result=new ArrayList<>();
        while (c.moveToNext())
        {
            String uid=c.getString(0);
            String nam=c.getString(1);
            String quan=c.getString(2);
            String pric=c.getString(3);
            String disc=c.getString(4);
            String imag=c.getString(5);



            result.add(new Order(uid,
                    nam,
                    quan,
                    pric,
                    disc,imag

            ));

        }
        return result;
    }




    public long addToCart(Order order)
    {
        SQLiteDatabase data=db.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("productId",order.getProductId());
        contentValues.put("productName",order.getProductName());
        contentValues.put("quantity",order.getQuantity());
        contentValues.put("price",order.getPrice());
        contentValues.put("discount",order.getDiscount());
        contentValues.put("image",order.getImage());

        long id=data.insert("orders_info",null,contentValues);
        return id;
    }
    public void cleenCart()
    {
        SQLiteDatabase data=db.getWritableDatabase();
        String query=String.format("DELETE FROM orders_info");
        data.execSQL(query);
    }
    public long addToFav(fav favid)
    {
        SQLiteDatabase data=db.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("productId",favid.getId());
        contentValues.put("productName",favid.getTitle());
        contentValues.put("discount",favid.getImage());
        contentValues.put("price",favid.getPrice());

        long id=data.insert("favorit",null,contentValues);
        return id;
    }

    public void removeToCart(String cartId)
    {
        SQLiteDatabase data=db.getWritableDatabase();
        String query=String.format("DELETE FROM orders_info WHERE productId = "+cartId);
        data.execSQL(query);
    }
    public void removeToFav(String favid)
    {
        SQLiteDatabase data=db.getWritableDatabase();
        String query=String.format("DELETE FROM favorit WHERE productId = "+favid);
        data.execSQL(query);
    }
    public boolean IsFavorite(String favid)
    {
        SQLiteDatabase data=db.getWritableDatabase();

        String query=String.format("SELECT * FROM favorit WHERE productId = '"+favid+"'");

        Cursor c =data.rawQuery(query,null);
        if (c.getCount()<=0)
        {
            c.close();
            return false;
        }
        c.close();
        return true;
    }
    public List<fav>getFavorit()
    {
        SQLiteDatabase data=db.getWritableDatabase();
        //SQLiteQueryBuilder qb =new SQLiteQueryBuilder();

        String[] sqlSelect={"productId","productName","discount","price"};
        //String sqlTable="orders_info";

        //qb.setTables("orders_info");

        Cursor c =data.query("favorit",sqlSelect,null,null,null,null,null);
        res=new ArrayList<>();
        while (c.moveToNext())
        {
            String uid=c.getString(0);
            String nam=c.getString(1);
            String disc=c.getString(2);
            String pric=c.getString(3);


            res.add(new fav(uid,
                    nam,
                    disc,
                    pric
            ));

        }
        return res;
    }
}
class DB  extends SQLiteOpenHelper{

    public static final String database_name="Omar";
    public static final String table_name="orders_info";
    public static final String table_fav="favorit";
    public static final String uid="productId";
    public static final String uidfav="productId";

    public static final String names="productName";
    public static final String user="quantity";
    public static final String pass="price";
    public static final String pa="discount";
    public static final String image="image";

    public static final String clen="DELETE FROM "+table_name;
    private static final String DROP="DROP TABLE IF EXISTS "+table_name;
    private static final String DROP2="DROP TABLE IF EXISTS "+uidfav;

    private static final String CREATE_TABLE="CREATE TABLE "+table_name +" ( "+uid +" VARCHAR(255)  , "+names+" VARCHAR(255) ,"+user+" VARCHAR(255),"+pass+" VARCHAR(255),"+pa+" VARCHAR(255),"+image+" VARCHAR(255) );" ;
    private static final String CREATE_TABLE2="CREATE TABLE "+table_fav +" ( "+uidfav +" VARCHAR(255)  , "+names+" VARCHAR(255) ,"+pa+" VARCHAR(255) ,"+pass+" VARCHAR(255));" ;

    Context context;


    public DB(Context context) {
        super(context,database_name, null,1);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE2);

        Toast.makeText(context,"on created ",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(DROP);
        sqLiteDatabase.execSQL(DROP2);

        onCreate(sqLiteDatabase);
        Toast.makeText(context,"on onUpgrade ",Toast.LENGTH_SHORT).show();


    }
}
