package com.shop.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.shop.model.User;
import com.shop.database.UserDAO;

public class WelcomeScreen extends JFrame {
    private static final Color BACKGROUND_START = new Color(145, 145, 121);  // Slightly darker version
    private static final Color BACKGROUND_END = new Color(165, 165, 141);    // Requested color
    private static final Color BUTTON_COLOR = new Color(255, 255, 255);      // White
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 36);
    private static final Font SUBTITLE_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 600;
    
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JTextField phoneField;  // Added as class field
    
    public WelcomeScreen() {
        setTitle("Welcome to Shop App");
        setSize(400, 600);  // Updated size to be consistent
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);  // Prevent resizing for consistent look
        
        initComponents();
        showWelcomeContent();
        setVisible(true);
    }
    
    private void initComponents() {
        // Main panel with gradient background
        mainPanel = new JPanel(new BorderLayout()) {
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Content panel for center alignment
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }
    
    private void showWelcomeContent() {
        contentPanel.removeAll();
        
        // App icon/logo (🛍️ shopping bag emoji as placeholder)
        JLabel iconLabel = new JLabel("🛍️");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 72));
        iconLabel.setForeground(TEXT_COLOR);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Title
        JLabel titleLabel = new JLabel("ShopApp");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Your one-stop shop for everything");
        subtitleLabel.setFont(SUBTITLE_FONT);
        subtitleLabel.setForeground(TEXT_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Login button
        JButton loginButton = createStyledButton("LOGIN");
        loginButton.addActionListener(e -> showLoginOptions());
        
        // Sign up button
        JButton signUpButton = createStyledButton("SIGN UP");
        signUpButton.addActionListener(e -> showRegisterContent());
        
        // Add components with spacing
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(60));
        contentPanel.add(loginButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(signUpButton);
        contentPanel.add(Box.createVerticalGlue());
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showLoginOptions() {
        contentPanel.removeAll();
        
        // Title
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Choose your option");
        subtitleLabel.setFont(SUBTITLE_FONT);
        subtitleLabel.setForeground(TEXT_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons
        JButton userLoginButton = createStyledButton("USER LOGIN");
        JButton adminLoginButton = createStyledButton("ADMIN LOGIN");
        JButton guestButton = createStyledButton("CONTINUE AS GUEST");
        JButton backButton = createStyledButton("BACK");
        
        // Action listeners
        userLoginButton.addActionListener(e -> showLoginContent(false));
        adminLoginButton.addActionListener(e -> showLoginContent(true));
        guestButton.addActionListener(e -> openCategoryScreen(null));
        backButton.addActionListener(e -> showWelcomeContent());
        
        // Add components with spacing
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(userLoginButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(adminLoginButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(guestButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(backButton);
        contentPanel.add(Box.createVerticalGlue());
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showLoginContent(boolean isAdmin) {
        contentPanel.removeAll();
        
        // Title
        JLabel titleLabel = new JLabel(isAdmin ? "Admin Login" : "Welcome Back");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Center panel for input fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setMaximumSize(new Dimension(300, 250));

        if (!isAdmin) {
            // Phone Number Label
            JLabel phoneLabel = new JLabel("Phone Number");
            phoneLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            phoneLabel.setForeground(TEXT_COLOR);
            phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Phone field with custom styling
            phoneField = new JTextField() {
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
            phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
            phoneField.setMaximumSize(new Dimension(300, 50));
            phoneField.setAlignmentX(Component.CENTER_ALIGNMENT);
            phoneField.setHorizontalAlignment(JTextField.CENTER);
            phoneField.setBorder(new RoundedCornerBorder());
            phoneField.setBackground(Color.WHITE);
            
            centerPanel.add(phoneLabel);
            centerPanel.add(Box.createVerticalStrut(10));
            centerPanel.add(phoneField);
            centerPanel.add(Box.createVerticalStrut(20));
        }

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Password field with custom styling
        JPasswordField passwordField = new JPasswordField() {
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
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(300, 50));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setBorder(new RoundedCornerBorder());
        passwordField.setBackground(Color.WHITE);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Login button
        JButton loginButton = createStyledButton("LOGIN");
        loginButton.setMaximumSize(new Dimension(300, 50));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Back button
        JButton backButton = createStyledButton("BACK");
        backButton.setMaximumSize(new Dimension(300, 50));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to panels
        centerPanel.add(passwordLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(passwordField);
        
        buttonsPanel.add(Box.createVerticalStrut(30));
        buttonsPanel.add(loginButton);
        buttonsPanel.add(Box.createVerticalStrut(15));
        buttonsPanel.add(backButton);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(centerPanel);
        contentPanel.add(buttonsPanel);
        contentPanel.add(Box.createVerticalGlue());

        // Add action listeners
        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword()).trim();

            if (isAdmin) {
                // Admin Login Logic
                if (password.equals("admin")) {
                    User adminUser = new User("Admin", "admin", password, true);
                    openAdminPanel(adminUser);
                } else {
                    showCustomDialog("Error", "Invalid admin password", "OK");
                }
            } else {
                // User Login Logic
                String phone = phoneField.getText().trim();
            if (phone.isEmpty() || password.isEmpty()) {
                    showCustomDialog("Error", "Please fill in all fields", "OK");
                return;
            }
            
            User user = UserDAO.login(phone, password);
            if (user != null) {
                openCategoryScreen(user);
            } else {
                    showCustomDialog("Error", "Invalid phone number or password", "OK");
                }
            }
        });
        
        backButton.addActionListener(e -> showLoginOptions());
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showRegisterContent() {
        contentPanel.removeAll();
        
        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Input fields panel
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setOpaque(false);
        inputsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputsPanel.setMaximumSize(new Dimension(250, 200));
        
        // Fields
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setMaximumSize(new Dimension(250, 35));
        
        JTextField phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.setMaximumSize(new Dimension(250, 35));
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(250, 35));
        
        // Labels
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setFont(SUBTITLE_FONT);
        
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setForeground(TEXT_COLOR);
        phoneLabel.setFont(SUBTITLE_FONT);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setFont(SUBTITLE_FONT);
        
        // Register button
        JButton registerButton = createStyledButton("REGISTER");
        JButton backButton = createStyledButton("BACK");
        
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());
            
            if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                showCustomDialog("Error", "Please fill in all fields", "OK");
                return;
            }
            
            if (UserDAO.isPhoneNumberRegistered(phone)) {
                showCustomDialog("Error", "Phone number already registered", "OK");
                return;
            }
            
            User newUser = new User(name, phone, password, false);
            if (UserDAO.register(newUser)) {
                showCustomDialog("Success", "Registration successful! Please login.", "OK");
                showLoginOptions();
            } else {
                showCustomDialog("Error", "Registration failed. Please try again.", "OK");
            }
        });
        
        backButton.addActionListener(e -> showWelcomeContent());
        
        // Add components
        inputsPanel.add(nameLabel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(nameField);
        inputsPanel.add(Box.createVerticalStrut(15));
        inputsPanel.add(phoneLabel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(phoneField);
        inputsPanel.add(Box.createVerticalStrut(15));
        inputsPanel.add(passwordLabel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(passwordField);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(inputsPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(registerButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(backButton);
        contentPanel.add(Box.createVerticalGlue());
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(BUTTON_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(BUTTON_COLOR.brighter());
            } else {
                    g2d.setColor(BUTTON_COLOR);
                }
                
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 25, 25);
                g2d.fill(roundedRectangle);
                
                g2d.setColor(BACKGROUND_START);
                g2d.setFont(BUTTON_FONT);
                FontMetrics metrics = g2d.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setFont(BUTTON_FONT);
        button.setForeground(BACKGROUND_START);
        button.setPreferredSize(new Dimension(200, 45));
        button.setMaximumSize(new Dimension(200, 45));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void openCategoryScreen(User user) {
        CategoryScreen categoryScreen = new CategoryScreen(user);
        categoryScreen.setVisible(true);
        this.dispose();
    }
    
    private void openAdminPanel(User admin) {
        AdminScreen adminScreen = new AdminScreen(admin);
        adminScreen.setVisible(true);
        this.dispose();
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
    
    // Add custom rounded border class
    private static class RoundedCornerBorder extends javax.swing.border.AbstractBorder {
        private static final int RADIUS = 15;
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.draw(getBorderShape(x, y, width - 1, height - 1));
            g2.dispose();
        }
        
        public Shape getBorderShape(int x, int y, int w, int h) {
            return new RoundRectangle2D.Float(x, y, w, h, RADIUS, RADIUS);
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(RADIUS/2, RADIUS/2, RADIUS/2, RADIUS/2);
        }
    }
} 