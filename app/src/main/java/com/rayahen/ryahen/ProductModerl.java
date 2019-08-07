package com.rayahen.ryahen;

public class ProductModerl{
    private String id;
    private String name,descrepton;
    int kind;
    private String image,price;

    public ProductModerl(String id, String name, String descrepton, String price, String image,int kind) {
        this.id = id;
        this.name = name;
        this.descrepton = descrepton;
        this.image = image;
        this.price = price;
        this.kind=kind;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrepton() {
        return descrepton;
    }

    public void setDescrepton(String descrepton) {
        this.descrepton = descrepton;
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
