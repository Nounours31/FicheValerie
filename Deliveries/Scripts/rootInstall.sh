#!/bin/bash

cd ~ref/GitHub/FicheValerie/Deliveries
tar xvf Deliveries.tar

cp ~ref/GitHub/FicheValerie/Deliveries/Deliveries/Scripts ~tomee/Scripts
chown -R tomee:sct ~tomee/Scripts

su - tomee -c "~tomee/Scripts/myStop.sh"

rm -rf ~tomee/apaches/webapps.fiche*
cp  ~ref/GitHub/FicheValerie/Deliveries/Deliveries/*.war ~tomee/apaches/webapps
chown -R tomee:sct ~tomee/apaches/webapps/fiche*
su - tomee -c "~tomee/Scripts/myStart.sh"

systemctl stop httpd
rm -rf /var/www.html/fichevalerie/ui/v1.0.0/*
cp  ~ref/GitHub/FicheValerie/Deliveries/Deliveries/ui/* /var/www.html/fichevalerie/ui/v1.0.0
chown -R tomee:sct /var/www.html/fichevalerie/ui/v1.0.0/*
systemctl start httpd

