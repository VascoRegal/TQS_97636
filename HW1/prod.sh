#!/bin/bash


cd tqscovid && mvn clean package
cd ../
sudo docker-compose down -v
sudo docker-compose build
sudo docker-compose up -d
