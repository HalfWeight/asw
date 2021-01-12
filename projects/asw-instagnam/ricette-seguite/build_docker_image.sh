#!/bin/bash

docker stop asw-vito-ricette-seguite
docker rm asw-vito-ricette-seguite
gradle build -x test
docker build -t asw-vitomezzapesa/ricette-seguite .
docker run -d -p 9002:8080 --name asw-vito-ricette-seguite --network=asw-instagnam_common-network asw-vitomezzapesa/ricette-seguite
