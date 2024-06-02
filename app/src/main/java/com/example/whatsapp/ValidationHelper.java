package com.example.whatsapp;

public class ValidationHelper {
    private String email;
    private String password;
    private String name;
    private String message;

    public ValidationHelper(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ValidationHelper(String name) {
        this.name = name;
    }

    public boolean emailValidate(){
        if (email.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public boolean passwordValidate(){
        if (password.isEmpty() || password.length() < 6){
            return false;
        }else {
            return true;
        }
    }

    public boolean nameValidate(){
        if(name.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean messageValidate(){
        if(message.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

}
