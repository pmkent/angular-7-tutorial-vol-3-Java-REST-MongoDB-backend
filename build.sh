#! /bin/bash

echo "Building user app"

mvn clean package -DskipTests=true

echo "Ending user app build..."
