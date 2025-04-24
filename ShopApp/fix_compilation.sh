#!/bin/bash

# Ensure target directory exists and is clean
rm -rf target
mkdir -p target

# First create the necessary package structure
mkdir -p target/com/shop/model
mkdir -p target/com/shop/database
mkdir -p target/com/shop/ui

# Compile files in the correct order
echo "Compiling model classes..."
javac -d target src/main/java/com/shop/model/*.java

echo "Compiling database classes..."
javac -d target -cp target src/main/java/com/shop/database/*.java

echo "Compiling UI classes..."
javac -d target -cp target src/main/java/com/shop/ui/*.java

echo "Compiling Main class..."
javac -d target -cp target src/main/java/com/shop/Main.java

echo "Compilation complete."

# Check if Main.class was created
if [ -f target/com/shop/Main.class ]; then
    echo "Successfully compiled. Running application..."
    java -cp target com.shop.Main
else
    echo "Compilation failed. Main class was not created."
fi 