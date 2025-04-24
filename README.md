# Shop Management System

A Java Swing application for managing a shop with multiple categories of items, user and admin functionalities.

## Features

- User Registration and Login
- Admin Login (Password: 1122)
- Guest Mode (Browse but cannot purchase)
- Multiple Item Categories:
  - Grocery
  - Clothes
  - Electronics
  - Beauty
  - Stationery
- Shopping Cart & Order Management
- Inventory Management for Admins
- MySQL Database Integration

## Requirements

- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7+
- MySQL Connector/J (JDBC Driver)

## Quick Setup

We've provided shell scripts to make setup and running easier:

1. Set up the database:
```
./ShopApp/setup_db.sh
```

2. Compile the application:
```
./ShopApp/compile.sh
```

3. Run the application:
```
./ShopApp/run.sh
```

## Manual Setup Instructions

### Database Setup

1. Install MySQL if you haven't already
2. Create a new database:
```
mysql -u root -p < ShopApp/src/main/resources/db_schema.sql
```

### Application Setup

1. Download MySQL Connector/J if you don't have it
2. Configure database connection in `DBConnection.java` if needed (default: localhost:3306, user: root, no password)
3. Compile the application:
```
javac -d ShopApp/target -cp mysql-connector-java-8.0.28.jar:ShopApp/target -sourcepath ShopApp/src/main/java ShopApp/src/main/java/com/shop/Main.java
```
4. Run the application:
```
java -cp mysql-connector-java-8.0.28.jar:ShopApp/target com.shop.Main
```

## Usage Guide

### User Functions

1. **Register**: Create a new account with name, phone number, and password
2. **Login**: Log in with phone number and password
3. **Browse Items**: View items by category
4. **Add to Cart**: Select items and specify quantities
5. **Place Order**: Confirm purchase and receive an order ID

### Admin Functions

1. **Admin Login**: Use password "1122" to access admin panel
2. **View Items**: Browse all items by category
3. **Add Items**: Add new items to inventory
4. **Delete Items**: Remove items from inventory

## Project Structure

```
ShopApp/
├── src/
│   ├── main/
│       ├── java/
│       │   └── com/
│       │       └── shop/
│       │           ├── Main.java
│       │           ├── database/
│       │           │   ├── DBConnection.java
│       │           │   ├── ItemDAO.java
│       │           │   ├── OrderDAO.java
│       │           │   └── UserDAO.java
│       │           ├── model/
│       │           │   ├── Item.java
│       │           │   ├── Order.java
│       │           │   └── User.java
│       │           └── ui/
│       │               ├── AdminPanel.java
│       │               ├── CategoryScreen.java
│       │               ├── ItemsScreen.java
│       │               ├── OrderConfirmationScreen.java
│       │               └── WelcomeScreen.java
│       └── resources/
│           └── db_schema.sql
├── compile.sh         # Script to compile the application
├── run.sh             # Script to run the application
├── setup_db.sh        # Script to set up the database
└── README.md
```

## Admin Access

- **Username**: admin
- **Password**: admin 
