#!/bin/bash


export JAVA_HOME=/home/tomee/jdk1.8.0_301


typeset pid=$(ps -ef | grep -i java | grep -i "apache-tomee" | awk '{print $2}')
if [ "${pid}" = "" ]; then
    printf "Tomme n'existe pas, ...\n" 
else
    /home/tomee/apache-tomee-plus-8.0.6/bin/shutdown.sh
fi

