package com.example.arscience.classes;

public class Repository {
    private int image;
    private String name,type,description,model_name;

    public Repository(int image, String name, String type, String description, String model_name) {
        this.image = image;
        this.name = name;
        this.type = type;
        this.description = description;
        this.model_name = model_name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getModel_name() {
        return model_name;
    }
}
