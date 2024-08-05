#!/bin/bash

TOMCAT_HOME=/home/augusto/apache-tomcat-10.1.19
PROJECT_HOME=/home/augusto/Documentos/Ingenieria\ Sistemas/IPC2/parking-system

# Stop Tomcat server
$TOMCAT_HOME/bin/shutdown.sh
sleep 5

# Compile the Maven project
mvn clean install

# Deploy the generated WAR file in the Tomcat webapps folder
TOMCAT_WEBAPPS_DIR=$TOMCAT_HOME/webapps
CONTEXT_CONFIG_DIR=$TOMCAT_HOME/conf/Catalina/localhost

# Remove the existing WAR file and exploded directory
rm -rf $TOMCAT_WEBAPPS_DIR/application-0.0.1.jar
rm -rf $TOMCAT_WEBAPPS_DIR/domain-0.0.1.jar
rm -rf $TOMCAT_WEBAPPS_DIR/infrastructure-0.0.1.war
rm -rf $TOMCAT_WEBAPPS_DIR/infrastructure-0.0.1

# Copy the new WAR file to the Tomcat webapps folder
cp /home/augusto/Documentos/Ingenieria\ Sistemas/IPC2/parking-system/application/target/application-0.0.1.jar $TOMCAT_WEBAPPS_DIR/
cp /home/augusto/Documentos/Ingenieria\ Sistemas/IPC2/parking-system/domain/target/domain-0.0.1.jar $TOMCAT_WEBAPPS_DIR/
cp /home/augusto/Documentos/Ingenieria\ Sistemas/IPC2/parking-system/infrastructure/target/infrastructure-0.0.1.war $TOMCAT_WEBAPPS_DIR/

# Create the context configuration file for the new deployment
cat > $CONTEXT_CONFIG_DIR/parking-system.xml <<EOL
<?xml version="1.0" encoding="UTF-8"?>
<Context path="/parking-system" docBase="$PROJECT_HOME/infrastructure/target/infrastructure-0.0.1"/>
EOL

# Start Tomcat
$TOMCAT_HOME/bin/startup.sh