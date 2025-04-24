package com.shop.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.shop.model.Item;
import com.shop.model.Order;

public class OrderDAO {
    private static final List<Order> orders = new ArrayList<>();
    private static int nextOrderId = 1;

    public static int createOrder(String userId, Map<Item, Integer> items) {
        try {
            int orderId = nextOrderId++;
            Order newOrder = new Order(orderId, userId, items);
            orders.add(newOrder);
            return orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static List<Order> getUserOrders(String userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    public static Order getOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public static boolean updateOrderStatus(int orderId, String status) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                order.setStatus(status);
                return true;
            }
        }
        return false;
    }

    public static List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
} 