#!/bin/bash

# Create directory for output
mkdir -p target

# Compile all Java files directly without using any database
javac -d target src/main/java/com/shop/model/*.java
javac -d target -cp target src/main/java/com/shop/database/*.java
javac -d target -cp target src/main/java/com/shop/ui/*.java
javac -d target -cp target src/main/java/com/shop/*.java

# Run the application
java -cp target com.shop.Main 

# Create directory for output
mkdir -p target

# Compile all Java files directly without using any database
javac -d target src/main/java/com/shop/model/*.java
javac -d target -cp target src/main/java/com/shop/database/*.java
javac -d target -cp target src/main/java/com/shop/ui/*.java
javac -d target -cp target src/main/java/com/shop/*.java

# Run the application
java -cp target com.shop.Main 