#!/bin/bash

SERVER="kikisito"
DEPLOY_PATH="/srv/iweb/api"
JAR_NAME="paqueteria-0.0.1-SNAPSHOT.jar"
APP_NAME="api-app"

echo "Compilando el proyecto..."
./mvnw clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "Error: Falló la compilación del proyecto"
    exit 1
fi

echo "Desplegando el proyecto..."
scp "target/${JAR_NAME}" "${SERVER}:${DEPLOY_PATH}/${APP_NAME}.jar"
if [ $? -ne 0 ]; then
    echo "Error: No se ha podido copiar el archivo al servidor"
    exit 1
fi

echo "Reiniciando el contenedor..."
ssh $SERVER "cd ${DEPLOY_PATH} && docker compose up -d --build"
