#!/bin/bash

install-task-manager() {
  mvn clean package
  mvn install:install-file -Dfile=./task-manager/target/task-manager-0.1.0.jar -DgroupId=org.kybinfrastructure -DartifactId=task-manager -Dversion=0.1.0 -Dpackaging=jar
}

if false; then
  echo
elif [ "$1" = "install-task-manager" ]; then
  install-task-manager ${@:2}
else
  echo "Usage: bash make.sh [OPTIONS]"
  echo
  echo -e "\033[1;4;32m""Options:""\033[0;34m"
  compgen -A function
  echo
  echo -e "\033[0m"
fi
