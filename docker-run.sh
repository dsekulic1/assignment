#!/bin/sh
docker build -t my-app:latest . &&
docker run --name myapp -d -p 8081:8081 my-app:latest