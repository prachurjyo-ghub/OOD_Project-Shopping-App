package com.shop.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.shop.database.UserDAO;
import com.shop.model.User;

public class RegistrationScreen extends JFrame {
    private static final Color BACKGROUND_START = new Color(145, 145, 121);
    private static final Color BACKGROUND_END = new Color(165, 165, 141);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);
    private static final int FIELD_HEIGHT = 50;
    
    private JTextField nameField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    
    public RegistrationScreen() {
        setTitle("Create Account");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
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
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        
        // Full Name
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(LABEL_FONT);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        nameField = createStyledTextField();
        nameField.setMaximumSize(new Dimension(300, FIELD_HEIGHT));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Phone Number
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(LABEL_FONT);
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(phoneLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        phoneField = createStyledTextField();
        phoneField.setMaximumSize(new Dimension(300, FIELD_HEIGHT));
        phoneField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(phoneField);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(LABEL_FONT);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        passwordField = createStyledPasswordField();
        passwordField.setMaximumSize(new Dimension(300, FIELD_HEIGHT));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(30));
        
        // Register Button
        JButton registerButton = createStyledButton("REGISTER");
        registerButton.addActionListener(e -> register());
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(registerButton);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Back Button
        JButton backButton = createStyledButton("BACK");
        backButton.addActionListener(e -> goBack());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(backButton);
        
        add(mainPanel);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(INPUT_FONT);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(new RoundedCornerBorder());
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(INPUT_FONT);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(new RoundedCornerBorder());
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                
                // Draw rounded rectangle background
                RoundRectangle2D.Float shape = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, 15, 15);
                g2.setColor(getBackground());
                g2.fill(shape);
                
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(BUTTON_FONT);
        button.setForeground(BACKGROUND_START);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setMaximumSize(new Dimension(300, FIELD_HEIGHT));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void register() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            showError("All fields are required");
            return;
        }
        
        if (!phone.matches("\\d{11}")) {
            showError("Phone number must be 11 digits");
            return;
        }
        
        User newUser = new User(name, phone, password);
        if (UserDAO.register(newUser)) {
            JOptionPane.showMessageDialog(this, 
                "Registration successful!\nPlease login with your credentials.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            goBack();
        } else {
            showError("Phone number already registered");
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void goBack() {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.setVisible(true);
        this.dispose();
    }
    
    // RoundedCornerBorder class
    private static class RoundedCornerBorder extends javax.swing.border.AbstractBorder {
        private static final int RADIUS = 15;
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, width - 1, height - 1, RADIUS, RADIUS);
            g2.dispose();
        }
        
        public Shape getBorderShape(int x, int y, int w, int h) {
            return new RoundRectangle2D.Float(x, y, w, h, RADIUS, RADIUS);
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(RADIUS / 2, RADIUS / 2, RADIUS / 2, RADIUS / 2);
        }
    }
}