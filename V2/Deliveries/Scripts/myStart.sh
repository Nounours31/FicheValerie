#!/bin/bash


export JAVA_HOME=/home/tomee/jdk1.8.0_301


typeset -i pid=$(ps -ef | grep -i java | grep -i "apache-tomee" | awk '{print $2}')
if ((pid > 0)); then
    printf "Tomme existe deja: %d le detruire\n" ${pid}
else
    /home/tomee/apache-tomee-plus-8.0.6/bin/startup.sh
fi

