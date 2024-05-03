package com.example.spotify;


public class user {
    public String id;
    public String name;
    public String lastName;
    public String phone;
    public String age;
    public String email;
    public String role;

    public user() {
        // Constructor vacío requerido por Firebase
    }

    public user(String id, String name, String lastName, String phone, String age, String email, String role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.email = email;
        this.role = role;
    }
}
