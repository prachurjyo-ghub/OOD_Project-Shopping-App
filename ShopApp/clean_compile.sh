#!/bin/bash

# Clean the target directory
rm -rf target

# Create target directory
mkdir -p target

# Compile all Java files again
javac -d target src/main/java/com/shop/model/*.java
javac -d target -cp target src/main/java/com/shop/database/*.java
javac -d target -cp target src/main/java/com/shop/ui/*.java
javac -d target -cp target src/main/java/com/shop/*.java

echo "Compilation complete. Running the application..."

# Run the application
java -cp target com.shop.Main 