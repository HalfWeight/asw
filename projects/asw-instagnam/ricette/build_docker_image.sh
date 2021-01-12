#!/bin/bash

docker stop asw-vito-ricette
docker rm asw-vito-ricette
gradle build -x test
docker build  -t asw-vitomezzapesa/ricette .
docker run -d -p 9001:8080 --name  asw-vito-ricette --network=asw-instagnam_common-network asw-vitomezzapesa/ricette
