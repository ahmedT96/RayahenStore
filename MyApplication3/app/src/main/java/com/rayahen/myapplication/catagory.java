package com.example.omar.helpinghand;

public class catagory {
    private int id;
    private String name,image;

    public catagory(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
