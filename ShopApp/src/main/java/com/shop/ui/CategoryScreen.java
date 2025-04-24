package com.shop.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.shop.model.User;

public class CategoryScreen extends JFrame {
    private static final Color BACKGROUND_START = new Color(145, 145, 121);
    private static final Color BACKGROUND_END = new Color(165, 165, 141);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    private User user;
    
    public CategoryScreen(User user) {
        this.user = user;
        setTitle("Shop Categories");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
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

        // Categories panel
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
        categoriesPanel.setOpaque(false);
        
        // Add category buttons - all with the same color
        Color categoryButtonColor = new Color(169, 143, 132);  // Light brown/rose color for all buttons
        addCategoryButton(categoriesPanel, "Grocery", categoryButtonColor);
        addCategoryButton(categoriesPanel, "Clothes", categoryButtonColor);
        addCategoryButton(categoriesPanel, "Electronics", categoryButtonColor);
        addCategoryButton(categoriesPanel, "Beauty", categoryButtonColor);
        addCategoryButton(categoriesPanel, "Stationery", categoryButtonColor);

        // Bottom panel for logout button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(135, 206, 235)); // Light blue to match back buttons
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(150, 40)); // Size to match other buttons
        
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(135, 206, 235).darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(135, 206, 235));
            }
        });
        
        logoutButton.addActionListener(e -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
            dispose();
        });
        
        bottomPanel.add(logoutButton);
        
        // Add panels to main panel
        mainPanel.add(categoriesPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        setContentPane(mainPanel);
    }

    private void addCategoryButton(JPanel panel, String category, Color buttonColor) {
        JButton button = new JButton(category);
        button.setFont(BUTTON_FONT);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(new Dimension(300, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor);
            }
        });
        
        button.addActionListener(e -> {
        ItemsScreen itemsScreen = new ItemsScreen(user, category);
        itemsScreen.setVisible(true);
            dispose();
        });
        
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);
    }
} 