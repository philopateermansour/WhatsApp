package com.example.whatsapp;

public class ModelUser {
    private String  email;
    private String  name;
    private String  uId;
    private String  image;
    public ModelUser(){}
    public ModelUser(String email, String name, String uId, String image) {
        this.email = email;
        this.name = name;
        this.uId = uId;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getuId() {
        return uId;
    }

    public String getImage() {
        return image;
    }
}
