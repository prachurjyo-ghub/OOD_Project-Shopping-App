-- Create database
CREATE DATABASE IF NOT EXISTS shopdb;
USE shopdb;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create items table
CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    category VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(20) NOT NULL UNIQUE,
    user_id VARCHAR(20) NOT NULL,
    order_date TIMESTAMP NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create order_items table
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(20) NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);

-- Insert admin user
INSERT INTO users (name, phone_number, password, is_admin)
VALUES ('Admin', 'admin', '1122', TRUE);

-- Insert sample items
INSERT INTO items (name, price, quantity, category) VALUES
('Rice', 2.99, 100, 'Grocery'),
('Milk', 1.99, 50, 'Grocery'),
('Bread', 1.49, 30, 'Grocery'),
('Eggs', 2.49, 40, 'Grocery'),
('Cereal', 3.99, 25, 'Grocery');

INSERT INTO items (name, price, quantity, category) VALUES
('T-Shirt', 15.99, 20, 'Clothes'),
('Jeans', 29.99, 15, 'Clothes'),
('Dress', 39.99, 10, 'Clothes'),
('Socks', 5.99, 30, 'Clothes'),
('Hat', 12.99, 15, 'Clothes');

INSERT INTO items (name, price, quantity, category) VALUES
('Smartphone', 499.99, 10, 'Electronics'),
('Headphones', 59.99, 20, 'Electronics'),
('Laptop', 899.99, 5, 'Electronics'),
('Mouse', 19.99, 15, 'Electronics'),
('Keyboard', 49.99, 10, 'Electronics');

INSERT INTO items (name, price, quantity, category) VALUES
('Shampoo', 4.99, 25, 'Beauty'),
('Soap', 2.99, 40, 'Beauty'),
('Lotion', 7.99, 20, 'Beauty'),
('Makeup Kit', 24.99, 10, 'Beauty'),
('Hair Gel', 6.99, 15, 'Beauty');

INSERT INTO items (name, price, quantity, category) VALUES
('Notebook', 3.99, 30, 'Stationery'),
('Pen Set', 5.99, 25, 'Stationery'),
('Pencil Box', 7.99, 20, 'Stationery'),
('Eraser', 1.99, 50, 'Stationery'),
('Ruler', 2.49, 40, 'Stationery'); 