package com.rayahen.ryahen;

public class FavoritModel{
    private String id;
    private String title,descrepton;
    int kind;

    private String image,price;

    public FavoritModel(String id, String title, String image, String price,String descrepton,int kind) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.descrepton = descrepton;
        this.kind=kind;
    }

    public String getDescrepton() {
        return descrepton;
    }

    public void setDescrepton(String descrepton) {
        this.descrepton = descrepton;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
