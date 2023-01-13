#!/bin/bash

# Pull new changes
#git pull

# Prepare Jar
#mvn clean
#mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Add environment variables
export BOT_NAME=RogovIg_bot
export BOT_TOKEN=5869455089:AAEZk8-JpfACiOaBU1NsC39rnt8CKHtU36Q
export BOT_DB_USERNAME='dev'
export BOT_DB_PASSWORD='dev'

# Start new deployment
docker-compose up --build -d