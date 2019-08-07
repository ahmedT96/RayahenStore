package com.example.omar.helpinghand;

public class Ditals
{
    private String Name ,Image,Description,Price,Discount,Menu_ID;
    public Ditals() {
    }

    public Ditals(String name, String image, String description, String price, String discount, String menu_ID) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Discount = discount;
        Menu_ID = menu_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenu_ID() {
        return Menu_ID;
    }

    public void setMenu_ID(String menu_ID) {
        Menu_ID = menu_ID;
    }
}
