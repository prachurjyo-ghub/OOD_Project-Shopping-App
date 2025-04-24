#!/bin/bash

# Check if the target directory exists and has the Main class
if [ ! -f target/com/shop/Main.class ]; then
  echo "Application not compiled! Running compilation first..."
  ./compile_app.sh
else
  echo "Starting ShopApp application..."
  # Run the application with verbose output
  java -cp target com.shop.Main
fi

echo "Application closed." 