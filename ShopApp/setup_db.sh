#!/bin/bash

# Default MySQL credentials
DB_USER="root"
DB_PASS=""

# Get MySQL credentials from user
read -p "Enter MySQL username (default: root): " input_user
read -s -p "Enter MySQL password (leave empty if none): " input_pass
echo ""

# Use provided credentials or defaults
USER=${input_user:-$DB_USER}
PASS=${input_pass:-$DB_PASS}

# Password option for mysql command
if [ -z "$PASS" ]; then
    PASS_OPT=""
else
    PASS_OPT="-p$PASS"
fi

# Run the SQL script
echo "Setting up the database..."
mysql -u $USER $PASS_OPT < src/main/resources/db_schema.sql

if [ $? -eq 0 ]; then
    echo "Database setup complete."
else
    echo "Error setting up database. Please check your MySQL credentials and try again."
    exit 1
fi 