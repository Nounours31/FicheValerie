#!/bin/bash

function log () {
	typeset toPrint="$1"
	printf "==================================================================\n"
	printf "== %s\n" "${toPrint}"
	printf "==================================================================\n"
}

function log2 () {
	typeset toPrint="$1"
	printf "==> %s\n" "${toPrint}"
}

log "Refresh GIT"
su - rel -c "cd ~rel/github/FicheValerie; git checkout DevHTTPS; git pull"
log2 "OK Git"


log "Untar new War and UI data"
cd ~rel/github/FicheValerie/Deliveries
[ -d ~rel/github/FicheValerie/Deliveries/Deliveries ] && {
    rm -rf ~rel/github/FicheValerie/Deliveries/Deliveries
    ls -al ~rel/github/FicheValerie/Deliveries 
    log2 "   OK clean previous Tar Extract"
}
tar xvf Deliveries.tar
log2 "OK Detar"

log "Update script under tomee"
[ -d ~tomee/Scripts ] && {
    rm -rf  ~tomee/Scripts
    log2 "  OK Clean tomee scripts"
}
mkdir -p ~tomee/Scripts
ls -al ~tomee/Scripts

cp ~rel/github/FicheValerie/Deliveries/Scripts/* ~tomee/Scripts
chown -R tomee:sct ~tomee/Scripts
ls -alR ~tomee/Scripts
log2 "OK cp scripts"

log "Update tomee"
log2 "Stop tomee and sleep"
su - tomee -c "~tomee/Scripts/myStop.sh"
sleep 60
 
log2 "Clean tomee"
rm -rf ~tomee/apache-tomee-plus-8.0.6/webapps/ficheValerie-2021-07-0*
ls -al ~tomee/apache-tomee-plus-8.0.6/webapps
log2 "   OK clean tomee"

log2 "update tomee"
cp ~rel/github/FicheValerie/Deliveries/Deliveries/war/* ~tomee/apache-tomee-plus-8.0.6/webapps
chown -R tomee:sct ~tomee/apache-tomee-plus-8.0.6/webapps

log2 "Start tomee and sleep"
su - tomee -c "~tomee/Scripts/myStart.sh"
sleep 60
ls -al ~tomee/apache-tomee-plus-8.0.6/webapps
log2 "OK End tomee"


log "Update HTTPD"
systemctl stop httpd
rm -rf /var/www/html/FicheValerie/ui/v1.0.0
mkdir -p  /var/www/html/FicheValerie/ui/v1.0.0
ls -al /var/www/html/FicheValerie/ui/v1.0.0
log2 "    OK Clean httpd"

cp -p -r  ~rel/github/FicheValerie/Deliveries/Deliveries/ui/* /var/www/html/FicheValerie/ui/v1.0.0
chown -R apache:apache /var/www/html/FicheValerie/ui/v1.0.0
systemctl start httpd
log2 "OK HTTPD"

log "MariaDB"
systemctl restart mariadb 
log2 "OK Maria DB"

