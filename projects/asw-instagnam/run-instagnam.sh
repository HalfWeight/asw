#!/bin/bash

# Script per avviare l'applicazione Instagnam 

echo Running INSTAGNAM 

# Consul deve essere avviato separatamente 

for projectName in  "connessioni" "ricette" "ricette-seguite" "api-gateway"; do
  cd $projectName
  ./build_docker_image.sh
  cd ..
done