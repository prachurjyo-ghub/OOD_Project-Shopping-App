package com.shop.database;

import com.shop.model.Item;
import java.util.*;

public class ItemDAO {
    // In-memory storage for items
    private static final Map<String, List<Item>> itemsByCategory = new HashMap<>();
    private static int nextItemId = 1;

    static {
        // Initialize with sample items for each category
        List<Item> groceryItems = new ArrayList<>();
        groceryItems.add(new Item(nextItemId++, "Rice", 120.0, 50, "Grocery"));
        groceryItems.add(new Item(nextItemId++, "Bread", 35.0, 20, "Grocery"));
        groceryItems.add(new Item(nextItemId++, "Milk", 45.0, 30, "Grocery"));
        groceryItems.add(new Item(nextItemId++, "Eggs", 90.0, 40, "Grocery"));
        
        List<Item> clothesItems = new ArrayList<>();
        clothesItems.add(new Item(nextItemId++, "T-Shirt", 450.0, 30, "Clothes"));
        clothesItems.add(new Item(nextItemId++, "Jeans", 850.0, 15, "Clothes"));
        clothesItems.add(new Item(nextItemId++, "Dress", 1200.0, 10, "Clothes"));
        
        List<Item> electronicsItems = new ArrayList<>();
        electronicsItems.add(new Item(nextItemId++, "Headphones", 1200.0, 10, "Electronics"));
        electronicsItems.add(new Item(nextItemId++, "Power Bank", 750.0, 20, "Electronics"));
        electronicsItems.add(new Item(nextItemId++, "Speaker", 2500.0, 5, "Electronics"));
        
        List<Item> beautyItems = new ArrayList<>();
        beautyItems.add(new Item(nextItemId++, "Face Wash", 180.0, 25, "Beauty"));
        beautyItems.add(new Item(nextItemId++, "Lipstick", 250.0, 15, "Beauty"));
        beautyItems.add(new Item(nextItemId++, "Shampoo", 320.0, 20, "Beauty"));
        
        List<Item> stationeryItems = new ArrayList<>();
        stationeryItems.add(new Item(nextItemId++, "Notebook", 60.0, 100, "Stationery"));
        stationeryItems.add(new Item(nextItemId++, "Pen Set", 90.0, 50, "Stationery"));
        stationeryItems.add(new Item(nextItemId++, "Pencil Box", 120.0, 30, "Stationery"));
        
        // Add to the map
        itemsByCategory.put("Grocery", groceryItems);
        itemsByCategory.put("Clothes", clothesItems);
        itemsByCategory.put("Electronics", electronicsItems);
        itemsByCategory.put("Beauty", beautyItems);
        itemsByCategory.put("Stationery", stationeryItems);
        
        System.out.println("Sample items loaded: " + (groceryItems.size() + clothesItems.size() + 
                          electronicsItems.size() + beautyItems.size() + stationeryItems.size()));
    }

    public static boolean addItem(Item item) {
        try {
            System.out.println("Adding item: " + item.getName() + " in category: " + item.getCategory());
            item.setId(nextItemId++);
            List<Item> categoryItems = itemsByCategory.get(item.getCategory());
            System.out.println("Category items found: " + (categoryItems != null));
            if (categoryItems != null) {
                categoryItems.add(item);
                System.out.println("Item added successfully. Total items in category: " + categoryItems.size());
                return true;
            }
            System.out.println("Failed to add item: category not found");
            return false;
        } catch (Exception e) {
            System.out.println("Error adding item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<Item> getItemsByCategory(String category) {
        System.out.println("Getting items for category: " + category);
        List<Item> items = itemsByCategory.getOrDefault(category, new ArrayList<>());
        System.out.println("Found " + items.size() + " items in category: " + category);
        for(Item item : items) {
            System.out.println("  - " + item.getName() + " (Price: " + item.getPrice() + ", Qty: " + item.getQuantity() + ")");
        }
        return new ArrayList<>(items);
    }

    public static boolean updateItemQuantity(int itemId, int newQuantity) {
        try {
            for (List<Item> items : itemsByCategory.values()) {
                for (Item item : items) {
                    if (item.getId() == itemId) {
                        item.setQuantity(newQuantity);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteItem(int itemId) {
        try {
            for (List<Item> items : itemsByCategory.values()) {
                for (Item item : items) {
                    if (item.getId() == itemId) {
                        items.remove(item);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
} 