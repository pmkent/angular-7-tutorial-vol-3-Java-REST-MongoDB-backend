#! /bin/bash

echo "Starting Build Shade"

mvn clean package -Pmaven-shade

echo "Ending Build Shade..."
