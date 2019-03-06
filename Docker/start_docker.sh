#!/bin/bash

#Create Image
./create_image.sh

#Start Docker-compose
docker-compose down
docker-compose up 
