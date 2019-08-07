package com.rayahen.ryahen;

public class CategoryModel {
    private String id;
    private String title;

    private String image;

    public CategoryModel(String id, String title) {
        this.id = id;
        this.title = title;
       // this.image = image;
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
}
