package com.example.arscience.classes;

public class SingleModel {

    private int Image;
    private String name;

    public SingleModel(int image, String name) {
        Image = image;
        this.name = name;
    }

    public int getImage() {
        return Image;
    }

    public String getName() {
        return name;
    }
}
