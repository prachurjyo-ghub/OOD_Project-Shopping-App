package com.shop.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import com.shop.model.Item;
import com.shop.model.User;
import com.shop.database.ItemDAO;

public class AdminScreen extends JFrame {
    private static final Color BACKGROUND_START = new Color(145, 145, 121);
    private static final Color BACKGROUND_END = new Color(165, 165, 141);
    private static final Color BUTTON_COLOR = new Color(151, 157, 172);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Color INPUT_BG = new Color(183, 183, 164);
    private User admin;
    private String selectedCategory = "Grocery"; // Default category
    
    public AdminScreen(User admin) {
        this.admin = admin;
        setTitle("Admin Panel - Add Items");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
        setVisible(true);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_START, 0, getHeight(), BACKGROUND_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Add New Item");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Input fields panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);
        
        // Name field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(BUTTON_FONT);
        JTextField nameField = createStyledTextField();
        
        // Price field
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(BUTTON_FONT);
        JTextField priceField = createStyledTextField();
        
        // Quantity field
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setFont(BUTTON_FONT);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        styleSpinner(quantitySpinner);
        
        // Category buttons
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(BUTTON_FONT);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel categoryPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        categoryPanel.setOpaque(false);
        String[] categories = {"Grocery", "Clothes", "Electronics", "Beauty", "Stationery"};
        
        ButtonGroup categoryGroup = new ButtonGroup();
        for (String category : categories) {
            JToggleButton categoryBtn = createStyledToggleButton(category);
            if (category.equals(selectedCategory)) {
                categoryBtn.setSelected(true);
            }
            final String catName = category;
            categoryBtn.addActionListener(e -> {
                selectedCategory = catName;
                System.out.println("Selected category: " + selectedCategory);
            });
            categoryGroup.add(categoryBtn);
            categoryPanel.add(categoryBtn);
        }
        
        // Add Item button
        JButton addButton = createStyledButton("Add Item");
        addButton.setMaximumSize(new Dimension(200, 40));
        
        // Logout button
        JButton logoutBtn = createStyledButton("Logout");
        logoutBtn.setMaximumSize(new Dimension(200, 40));
        
        // Add components with proper spacing
        inputPanel.add(nameLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(nameField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(priceLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(priceField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(quantityLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(quantitySpinner);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(categoryLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(categoryPanel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        inputPanel.add(addButton);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(logoutBtn);
        
        mainPanel.add(inputPanel);
        add(mainPanel);
        
        // Add action listeners
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = (Integer) quantitySpinner.getValue();
                
                if (name.isEmpty()) {
                    showErrorDialog("Please enter item name!");
                    return;
                }
                
                Item newItem = new Item(name, price, quantity, selectedCategory);
                if (ItemDAO.addItem(newItem)) {
                    showSuccessDialog("Item added successfully!");
                    // Clear fields
                    nameField.setText("");
                    priceField.setText("");
                    quantitySpinner.setValue(1);
                } else {
                    showErrorDialog("Failed to add item!");
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("Please enter a valid price!");
            }
        });
        
        logoutBtn.addActionListener(e -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
            this.dispose();
        });
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(INPUT_BG);
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setBorder(new RoundedCornerBorder());
        field.setMaximumSize(new Dimension(300, 40));
        field.setPreferredSize(new Dimension(300, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setMaximumSize(new Dimension(300, 40));
        spinner.setPreferredSize(new Dimension(300, 40));
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setFont(new Font("Arial", Font.PLAIN, 14));
        }
        spinner.setBorder(new RoundedCornerBorder());
    }

    private JToggleButton createStyledToggleButton(String text) {
        JToggleButton button = new JToggleButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.isSelected()) {
                    button.setBackground(BUTTON_COLOR.darker());
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.isSelected()) {
                    button.setBackground(BUTTON_COLOR);
                }
            }
        });
        
        return button;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class RoundedCornerBorder extends AbstractBorder {
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
            return new RoundRectangle2D.Double(x, y, w, h, RADIUS, RADIUS);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
    }
}