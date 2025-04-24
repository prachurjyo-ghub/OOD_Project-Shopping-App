package com.shop.model;

public class User {
    private String name;
    private String phone;
    private String password;
    private boolean isAdmin;
    
    public User(String name, String phone, String password) {
        this(name, phone, password, false);
    }
    
    public User(String name, String phone, String password, boolean isAdmin) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPhoneNumber() {
        return phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
    
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
} 