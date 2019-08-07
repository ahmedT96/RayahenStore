package com.example.omar.helpinghand;

public class Product {
    private String id;
    private String title;

    private String image,price;

    public Product(String id, String title, String image, String price) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public String getId() {return id;
    }

    public String getTitle() {
        return title;
    }


    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }
}

