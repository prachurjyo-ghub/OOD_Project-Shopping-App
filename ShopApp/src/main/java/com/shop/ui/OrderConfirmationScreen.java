package com.shop.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.shop.database.ItemDAO;
import com.shop.database.OrderDAO;
import com.shop.model.Item;
import com.shop.model.User;

public class OrderConfirmationScreen extends JFrame {
    private User user;
    private Map<Item, Integer> cart;
    private JLabel totalLabel;
    private double totalAmount;
    private String category;
    
    private static final Color BACKGROUND_START = new Color(145, 145, 121);  // Slightly darker version
    private static final Color BACKGROUND_END = new Color(165, 165, 141);    // Requested color
    private static final Color CARD_BACKGROUND = new Color(183, 183, 164);   // Light gray-beige
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 16);
    
    private JPanel itemsPanel;
    
    public OrderConfirmationScreen(User user, Map<Item, Integer> cart) {
        this.user = user;
        this.cart = cart;
        this.totalAmount = 0;
        if (!cart.isEmpty()) {
            this.category = cart.keySet().iterator().next().getCategory();
        }
        
        setTitle("Order Confirmation");
        setSize(400, 600);  // Updated size to be consistent
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);  // Prevent resizing for consistent look
        
        initComponents();
        loadItems();
        setVisible(true);
    }
    
    private void initComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_START, 0, getHeight(), BACKGROUND_END);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Cart icon and total at top
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(CARD_BACKGROUND);
        
        // Cart icon
        JLabel cartIcon = new JLabel("🛒");
        cartIcon.setFont(new Font("Arial", Font.PLAIN, 48));
        cartIcon.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Total amount
        totalLabel = new JLabel(String.format("Total: ৳%.0f", totalAmount));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        
        topPanel.add(cartIcon, BorderLayout.CENTER);
        topPanel.add(totalLabel, BorderLayout.SOUTH);
        
        // Items panel with vertical layout
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(CARD_BACKGROUND);
        
        // Wrap items panel in another panel for proper scrolling
        JPanel itemsWrapper = new JPanel(new BorderLayout());
        itemsWrapper.setBackground(CARD_BACKGROUND);
        itemsWrapper.add(itemsPanel, BorderLayout.NORTH);
        
        // Scroll pane for items
        JScrollPane scrollPane = new JScrollPane(itemsWrapper);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BACKGROUND);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Bottom panel with confirm button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(CARD_BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton confirmButton = new JButton("Confirm Order");
        styleButton(confirmButton, true);
        
        JButton backButton = new JButton("← Back");
        styleButton(backButton, false);
        
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(confirmButton);
        
        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Action listeners
        confirmButton.addActionListener(e -> confirmOrder());
        backButton.addActionListener(e -> goBack());
    }
    
    private JPanel createItemCard(Item item, int quantity) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        // Left side - Item info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_BACKGROUND);
        
        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel priceLabel = new JLabel(String.format("৳%.0f", item.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(100, 100, 100));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);
        
        // Right side - Quantity controls
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsPanel.setBackground(CARD_BACKGROUND);
        
        JButton minusButton = new JButton("-");
        styleQuantityButton(minusButton);
        
        JLabel quantityLabel = new JLabel(String.valueOf(quantity));
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        quantityLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        
        JButton plusButton = new JButton("+");
        styleQuantityButton(plusButton);
        
        controlsPanel.add(minusButton);
        controlsPanel.add(quantityLabel);
        controlsPanel.add(plusButton);
        
        // Add components to card
        card.add(infoPanel, BorderLayout.WEST);
        card.add(controlsPanel, BorderLayout.EAST);
        
        // Action listeners for quantity buttons
        minusButton.addActionListener(e -> {
            int currentQty = Integer.parseInt(quantityLabel.getText());
            if (currentQty > 1) {
                cart.put(item, currentQty - 1);
                quantityLabel.setText(String.valueOf(currentQty - 1));
                updateTotal();
            }
        });
        
        plusButton.addActionListener(e -> {
            int currentQty = Integer.parseInt(quantityLabel.getText());
            if (currentQty < item.getQuantity()) {
                cart.put(item, currentQty + 1);
                quantityLabel.setText(String.valueOf(currentQty + 1));
                updateTotal();
            }
        });
        
        return card;
    }
    
    private void styleButton(JButton button, boolean isPrimary) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        if (isPrimary) {
            button.setBackground(new Color(255, 165, 0));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(135, 206, 235));
            button.setForeground(Color.WHITE);
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(150, 40));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isPrimary) {
                    button.setBackground(new Color(255, 165, 0).darker());
                } else {
                    button.setBackground(new Color(135, 206, 235).darker());
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (isPrimary) {
                    button.setBackground(new Color(255, 165, 0));
                } else {
                    button.setBackground(new Color(135, 206, 235));
                }
            }
        });
    }
    
    private void styleQuantityButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(CARD_BACKGROUND);
        button.setForeground(new Color(255, 165, 0));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0)));
        button.setPreferredSize(new Dimension(30, 30));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 165, 0));
                button.setForeground(Color.WHITE);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(CARD_BACKGROUND);
                button.setForeground(new Color(255, 165, 0));
            }
        });
    }
    
    private void loadItems() {
        itemsPanel.removeAll();
        totalAmount = 0;
        
        for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            
            JPanel itemCard = createItemCard(item, quantity);
            itemsPanel.add(itemCard);
            itemsPanel.add(Box.createVerticalStrut(1)); // Small gap between cards
            
            totalAmount += item.getPrice() * quantity;
        }
        
        totalLabel.setText(String.format("Total: ৳%.0f", totalAmount));
        
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
    
    private void updateTotal() {
        totalAmount = 0;
        for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
            totalAmount += entry.getKey().getPrice() * entry.getValue();
        }
        totalLabel.setText(String.format("Total: ৳%.0f", totalAmount));
    }
    
    private void showCustomDialog(String title, String message, String buttonText) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout(20, 20));
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        
        // Message panel
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setBackground(Color.WHITE);
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messagePanel.add(messageLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton okButton = new JButton(buttonText);
        okButton.setPreferredSize(new Dimension(100, 35));
        okButton.setBackground(new Color(151, 157, 172));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);
        
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showLoginDialog() {
        JDialog dialog = new JDialog(this, "Login Required", true);
        dialog.setLayout(new BorderLayout(20, 20));
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        
        // Message panel
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setBackground(Color.WHITE);
        JLabel messageLabel = new JLabel("You need to login to place an order.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messagePanel.add(messageLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.setBackground(new Color(151, 157, 172));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setBackground(new Color(151, 157, 172));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        loginButton.addActionListener(e -> {
            dialog.dispose();
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
            this.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void confirmOrder() {
        if (user == null) {
            showLoginDialog();
            return;
        }
        
        int orderId = OrderDAO.createOrder(user.getPhoneNumber(), cart);
        
        if (orderId > 0) {
            // Update item quantities
            for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                ItemDAO.updateItemQuantity(item.getId(), item.getQuantity() - quantity);
            }
            
            showCustomDialog("Success", "Order placed successfully!\nOrder ID: " + orderId, "OK");
            
            // Clear the cart after successful order
            ItemsScreen.clearCart();
            
            // Return to category screen
            CategoryScreen categoryScreen = new CategoryScreen(user);
            categoryScreen.setVisible(true);
            this.dispose();
        } else {
            showCustomDialog("Error", "Failed to place order. Please try again.", "OK");
        }
    }
    
    private void goBack() {
        ItemsScreen itemsScreen = new ItemsScreen(user, category);
        itemsScreen.setVisible(true);
            this.dispose();
    }
} 