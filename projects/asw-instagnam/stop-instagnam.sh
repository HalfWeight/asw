#!/bin/bash

echo Halting all running java processes  

docker stop asw-vito-api-gateway
docker stop asw-vito-connessioni
docker stop asw-vito-ricette
docker stop asw-vito-ricette-seguite
