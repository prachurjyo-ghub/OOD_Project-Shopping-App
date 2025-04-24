#!/bin/bash

# Clean target directory
rm -rf target

# Create target directory and package structure
mkdir -p target

# Copy any resources
if [ -d "resources" ]; then
  cp -r resources target/
fi

# Compile the project
echo "Compiling Java files..."
javac -d target src/main/java/com/shop/model/*.java src/main/java/com/shop/database/*.java src/main/java/com/shop/ui/*.java src/main/java/com/shop/Main.java

# Check if compilation succeeded
if [ -f target/com/shop/Main.class ]; then
  echo "Compilation successful! Running the application..."
  java -cp target com.shop.Main
else
  echo "Compilation failed. Check error messages above."
fi 