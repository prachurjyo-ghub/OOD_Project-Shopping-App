#!/bin/bash

# Create directories
mkdir -p target
mkdir -p lib

# Download SQLite JDBC driver if not present
if [ ! -f "lib/sqlite-jdbc.jar" ]; then
    echo "Downloading SQLite JDBC driver..."
    curl -L "https://github.com/xerial/sqlite-jdbc/releases/download/3.44.1.0/sqlite-jdbc-3.44.1.0.jar" -o "lib/sqlite-jdbc.jar"
fi

# Compile all Java files
javac -d target -cp "lib/*:src/main/java" $(find src/main/java -name "*.java")

echo "Compilation complete. Run the application with:"
echo "java -cp \"target:lib/*\" com.shop.Main" 