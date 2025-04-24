#!/bin/bash

# Colors for better visibility
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Starting ShopApp Clean Rebuild ===${NC}"

# Clean everything
echo -e "${YELLOW}Cleaning previous build...${NC}"
rm -rf target
mkdir -p target

# Compile model classes first
echo -e "${YELLOW}Compiling model classes...${NC}"
javac -d target src/main/java/com/shop/model/*.java

if [ $? -ne 0 ]; then
    echo -e "${RED}Model compilation failed!${NC}"
    exit 1
fi

# Compile database classes
echo -e "${YELLOW}Compiling database classes...${NC}"
javac -d target -cp target src/main/java/com/shop/database/*.java

if [ $? -ne 0 ]; then
    echo -e "${RED}Database compilation failed!${NC}"
    exit 1
fi

# Compile UI classes
echo -e "${YELLOW}Compiling UI classes...${NC}"
javac -d target -cp target src/main/java/com/shop/ui/*.java

if [ $? -ne 0 ]; then
    echo -e "${RED}UI compilation failed!${NC}"
    exit 1
fi

# Compile Main class
echo -e "${YELLOW}Compiling Main class...${NC}"
javac -d target -cp target src/main/java/com/shop/Main.java

if [ $? -ne 0 ]; then
    echo -e "${RED}Main class compilation failed!${NC}"
    exit 1
fi

echo -e "${GREEN}Compilation successful!${NC}"

# Run the application
echo -e "${BLUE}Starting ShopApp...${NC}"
java -cp target com.shop.Main

echo -e "${BLUE}Application closed.${NC}" 