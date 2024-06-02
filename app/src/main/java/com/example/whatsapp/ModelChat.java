package com.example.whatsapp;

public class ModelChat {
    private String name;
    private int img;

    public ModelChat(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}
