package com.shop.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shop.model.User;

public class UserDAO {
    // In-memory storage for users
    private static final Map<String, User> users = new HashMap<>();
    
    static {
        // Add default admin user
        users.put("admin", new User("Admin", "admin", "1122", true));
        // Add some regular users
        users.put("user1", new User("User One", "user1", "1234", false));
        users.put("user2", new User("User Two", "user2", "1234", false));
        
        System.out.println("Sample users loaded: " + users.size());
    }
    
    public static User login(String phoneNumber, String password) {
        User user = users.get(phoneNumber);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public static boolean register(User user) {
        if (isPhoneNumberRegistered(user.getPhoneNumber())) {
            return false;
        }
        
        users.put(user.getPhoneNumber(), user);
        return true;
    }
    
    public static boolean isPhoneNumberRegistered(String phone) {
        return users.containsKey(phone);
    }
    
    public static User getUser(String phoneNumber) {
        return users.get(phoneNumber);
    }
    
    public static List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
} 