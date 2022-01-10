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

log "Start tomee"
su - tomee -c "~tomee/Scripts/myStart.sh"


log "Start HTTPD"
systemctl start httpd

log "Start MariaDB"
systemctl start mariadb 


log2 "OK"

