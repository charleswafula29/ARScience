package com.example.arscience.classes;

public class Studentmodelslist {

    private int uri;
    private String name,sfb,type;

    public Studentmodelslist(int uri, String name, String sfb, String type) {
        this.uri = uri;
        this.name = name;
        this.sfb = sfb;
        this.type = type;
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
}
