#!/bin/bash

echo "HELLO"

java -Dspring.datasource.password=$DB_PASSWORD -jar wallet-service-*.jar