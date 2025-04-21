#!/bin/bash

# Check if user provided the number of clients
if [ -z "$1" ]; then
    echo "Please provide the number of clients to run!"
fi

NUM_CLIENTS=$1

# Run server
mvn -f Project3Server/Project3Server/pom.xml clean compile exec:java &

# Wait for server to init
sleep 3

# Start the specified number of clients
for ((i = 1; i <= NUM_CLIENTS; i++))
do
  echo "Starting client $i"
  mvn -f Project3Client/Project3Client/pom.xml clean compile exec:java &
done

# Wait for all background processes to finish
wait

