#!/bin/bash

docker stop asw-vito-connessioni
docker rm asw-vito-connessioni
gradle build -x test
docker build  -t asw-vitomezzapesa/connessioni .
docker run -d -p 9000:8080 --name  asw-vito-connessioni --network=asw-instagnam_common-network asw-vitomezzapesa/connessioni
