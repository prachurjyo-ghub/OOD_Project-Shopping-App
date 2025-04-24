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
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.shop.database.ItemDAO;
import com.shop.model.Item;
import com.shop.model.User;

public class ItemsScreen extends JFrame {
    private User user;
    private String category;
    public static Map<Item, Integer> cart = new HashMap<>();
    private JButton viewCartButton;
    private JButton backButton;
    private JPanel itemsPanel;
    private JScrollPane scrollPane;
    
    private static final Color BACKGROUND_START = new Color(145, 145, 121);  // Slightly darker version
    private static final Color BACKGROUND_END = new Color(165, 165, 141);    // Requested color
    private static final Color CARD_BACKGROUND = new Color(183, 183, 164);   // Light gray-beige
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font CARD_TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 250;
    
    public ItemsScreen(User user, String category) {
        this.user = user;
        this.category = category;
        setTitle("Shop Items - " + category);
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
        
        // Create a wrapper panel for all content
        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.setBackground(CARD_BACKGROUND);
        
        // Categories dropdown panel
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropdownPanel.setBackground(CARD_BACKGROUND);
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        String[] categories = {"Grocery", "Clothes", "Electronics", "Beauty", "Stationery"};
        JComboBox<String> categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setSelectedItem(category);
        categoryDropdown.setPreferredSize(new Dimension(200, 40));
        categoryDropdown.setFont(new Font("Arial", Font.BOLD, 16));
        categoryDropdown.setBackground(Color.WHITE);
        categoryDropdown.setForeground(Color.BLACK);
        
        // Style the dropdown
        categoryDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    setBackground(new Color(184, 134, 11));  // Golden brown for selected
                    setForeground(Color.WHITE);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        });
        
        // Add action listener to dropdown
        categoryDropdown.addActionListener(e -> {
            String selectedCategory = (String) categoryDropdown.getSelectedItem();
            if (!selectedCategory.equals(category)) {
                openItemsScreen(selectedCategory);
            }
        });
        
        dropdownPanel.add(categoryDropdown);

        // Create a large spacer panel (100 pixels height)
        JPanel spacerPanel = new JPanel();
        spacerPanel.setBackground(CARD_BACKGROUND);
        spacerPanel.setPreferredSize(new Dimension(1, 100));
        
        // Items panel with grid layout
        itemsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        itemsPanel.setBackground(CARD_BACKGROUND);
        
        // Scroll pane for items
        scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BACKGROUND);
        
        // Bottom panel for navigation
        JPanel bottomPanel = new JPanel(new BorderLayout(20, 0));
        bottomPanel.setBackground(CARD_BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Back button (left side)
        backButton = new JButton("← Back");
        styleButton(backButton);
        
        // Cart button (right side)
        viewCartButton = new JButton("🛒 View Cart");
        styleButton(viewCartButton);
        
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(viewCartButton, BorderLayout.EAST);
        
        // Add all components to the content wrapper
        contentWrapper.add(dropdownPanel);
        contentWrapper.add(spacerPanel);  // Add large space after dropdown
        contentWrapper.add(scrollPane);
        
        // Add components to main panel
        mainPanel.add(contentWrapper, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Action listeners
        viewCartButton.addActionListener(e -> viewCart());
        backButton.addActionListener(e -> goBack());
    }
    
    private JPanel createItemCard(Item item) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Item name
        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Price
        JLabel priceLabel = new JLabel(String.format("৳%.0f", item.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Available quantity
        JLabel quantityLabel = new JLabel("Available: " + item.getQuantity());
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        quantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Quantity spinner
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, item.getQuantity(), 1));
        quantitySpinner.setMaximumSize(new Dimension(80, 25));
        quantitySpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buy button
        JButton buyButton = new JButton("BUY");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setMaximumSize(new Dimension(120, 35));
        buyButton.setBackground(new Color(125, 133, 151));  // New gray-blue color
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.setBorderPainted(false);
        buyButton.setOpaque(true);
        buyButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add hover effect to buy button
        buyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buyButton.setBackground(new Color(105, 113, 131));  // Slightly darker on hover
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buyButton.setBackground(new Color(125, 133, 151));
            }
        });
        
        buyButton.addActionListener(e -> {
            int quantity = (int) quantitySpinner.getValue();
            cart.merge(item, quantity, Integer::sum);
            showCustomDialog("Success", quantity + " " + item.getName() + "(s) added to cart", "OK");
        });
        
        // Add components to card with spacing
        card.add(Box.createVerticalStrut(10));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(quantityLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(quantitySpinner);
        card.add(Box.createVerticalStrut(15));
        card.add(buyButton);
        card.add(Box.createVerticalStrut(10));
        
        return card;
    }
    
    private void styleButton(JButton button) {
        // Change from rose brown to light blue to match other screens
        Color NAV_BUTTON_COLOR = new Color(135, 206, 235); // Light blue for nav buttons
        Color NAV_BUTTON_HOVER = NAV_BUTTON_COLOR.darker(); // Darker blue for hover
        
        button.setBackground(NAV_BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40)); // Set fixed size to match other screens
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(NAV_BUTTON_HOVER);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(NAV_BUTTON_COLOR);
            }
        });
    }
    
    private void loadItems() {
        try {
            List<Item> items = ItemDAO.getItemsByCategory(category);
            itemsPanel.removeAll();
        
            if (items != null) {
        for (Item item : items) {
                    JPanel itemCard = createItemCard(item);
                    itemsPanel.add(itemCard);
                }
            }
            
            itemsPanel.revalidate();
            itemsPanel.repaint();
        } catch (Exception e) {
            System.err.println("Error loading items: " + e.getMessage());
            e.printStackTrace();
            showCustomDialog("Error", "Error loading items. Please try again.", "OK");
        }
    }
    
    private void openItemsScreen(String category) {
        ItemsScreen itemsScreen = new ItemsScreen(user, category);
        itemsScreen.setVisible(true);
        this.dispose();
    }
    
    private void viewCart() {
        if (cart.isEmpty()) {
            // Create custom dialog
            JDialog dialog = new JDialog(this, "Empty Cart", true);
            dialog.setLayout(new BorderLayout(20, 20));
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(this);
            
            // Message panel
            JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            messagePanel.setBackground(Color.WHITE);
            JLabel messageLabel = new JLabel("Your cart is empty");
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            messagePanel.add(messageLabel);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(Color.WHITE);
            JButton okButton = new JButton("OK");
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
            return;
        }
        
        OrderConfirmationScreen confirmationScreen = new OrderConfirmationScreen(user, cart);
        confirmationScreen.setVisible(true);
        this.dispose();
    }
    
    private void goBack() {
        CategoryScreen categoryScreen = new CategoryScreen(user);
        categoryScreen.setVisible(true);
        this.dispose();
    }

    // Add method to clear cart when needed (e.g., after successful order)
    public static void clearCart() {
        cart.clear();
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
} 