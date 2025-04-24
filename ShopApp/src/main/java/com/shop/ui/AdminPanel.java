package com.shop.ui;

import com.shop.database.ItemDAO;
import com.shop.model.Item;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminPanel extends JFrame {
    
    private JComboBox<String> categoryComboBox;
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private List<Item> items;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JButton refreshButton;
    private JButton logoutButton;
    private static int nextItemId = 26; // Since we have 25 sample items
    private static final Color SKY_BLUE = new Color(135, 206, 235);
    
    public AdminPanel() {
        setTitle("Admin Panel - Shop Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        setVisible(true);
    }
    
    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Admin Control Panel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Category selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.setBackground(Color.WHITE);
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel categoryLabel = new JLabel("Select Category: ");
        categoryComboBox = new JComboBox<>(new String[] {
            "Grocery", "Clothes", "Electronics", "Beauty", "Stationery"
        });
        
        refreshButton = new JButton("Refresh");
        styleButton(refreshButton);
        
        selectionPanel.add(categoryLabel);
        selectionPanel.add(categoryComboBox);
        selectionPanel.add(refreshButton);
        
        // Table
        String[] columns = {"ID", "Name", "Price", "Quantity", "Category"};
        tableModel = new DefaultTableModel(columns, 0);
        
        itemsTable = new JTable(tableModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Action buttons panel (left side)
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionButtonsPanel.setBackground(Color.WHITE);
        
        addItemButton = new JButton("Add New Item");
        deleteItemButton = new JButton("Delete Selected Item");
        
        styleButton(addItemButton);
        styleButton(deleteItemButton);
        
        actionButtonsPanel.add(addItemButton);
        actionButtonsPanel.add(deleteItemButton);
        
        // Logout button panel (right side)
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        logoutPanel.setBackground(Color.WHITE);
        
        logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutPanel.add(logoutButton);
        
        // Add to bottom panel
        bottomPanel.add(actionButtonsPanel, BorderLayout.WEST);
        bottomPanel.add(logoutPanel, BorderLayout.EAST);
        
        // Add all components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(selectionPanel, BorderLayout.PAGE_START);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Load initial data
        loadItems();
        
        // Action Listeners
        categoryComboBox.addActionListener(e -> loadItems());
        refreshButton.addActionListener(e -> loadItems());
        addItemButton.addActionListener(e -> showAddItemDialog());
        deleteItemButton.addActionListener(e -> deleteSelectedItem());
        logoutButton.addActionListener(e -> logout());
    }
    
    private void styleButton(JButton button) {
        button.setBackground(SKY_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SKY_BLUE.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SKY_BLUE);
            }
        });
    }
    
    private void loadItems() {
        try {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Get selected category
        String category = (String) categoryComboBox.getSelectedItem();
        
        // Load items from database
        items = ItemDAO.getItemsByCategory(category);
        
            if (items != null) {
        for (Item item : items) {
            Object[] row = {
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getQuantity(),
                item.getCategory()
            };
            tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading items: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading items", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showAddItemDialog() {
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JComboBox<String> categoryField = new JComboBox<>(new String[] {
            "Grocery", "Clothes", "Electronics", "Beauty", "Stationery"
        });
        
        // Set default category to current selected category
        categoryField.setSelectedItem(categoryComboBox.getSelectedItem());
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Item", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String priceText = priceField.getText();
            String quantityText = quantityField.getText();
            String category = (String) categoryField.getSelectedItem();
            
            if (name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                double price = Double.parseDouble(priceText);
                int quantity = Integer.parseInt(quantityText);
                
                if (price <= 0 || quantity < 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than 0 and quantity must be 0 or greater", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Item item = new Item(nextItemId++, name, price, quantity, category);
                boolean success = ItemDAO.addItem(item);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Item added successfully", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // If added to the currently displayed category, refresh the list
                    if (category.equals(categoryComboBox.getSelectedItem())) {
                        loadItems();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add item", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and quantity", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteSelectedItem() {
        int selectedRow = itemsTable.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete", 
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int itemId = (int) tableModel.getValueAt(selectedRow, 0);
        String itemName = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete the item: " + itemName + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = ItemDAO.deleteItem(itemId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Item deleted successfully", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadItems();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete item", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.setVisible(true);
        this.dispose();
        }
    }
} 