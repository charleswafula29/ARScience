package com.example.arscience.classes;

public class ModelsClass {

    private int uri;
    private String name,sfb,type,description;

    public ModelsClass(int uri, String name, String sfb, String type, String description) {
        this.uri = uri;
        this.name = name;
        this.sfb = sfb;
        this.type = type;
        this.description = description;
    }

    public int getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getSfb() {
        return sfb;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
