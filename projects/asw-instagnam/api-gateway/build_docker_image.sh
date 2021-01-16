#!/bin/bash

docker stop asw-vito-api-gateway
docker rm asw-vito-api-gateway
gradle build -x test
docker build  -t asw-vitomezzapesa/api-gateway .
docker run -d -p 8080:8080 --name asw-vito-api-gateway --network=asw-instagnam_common-network asw-vitomezzapesa/api-gateway
