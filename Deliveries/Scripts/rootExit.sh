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

log "Stop tomee"
su - tomee -c "~tomee/Scripts/myStop.sh"


log "Stop HTTPD"
systemctl stop httpd

log "Stop MariaDB"
systemctl stop mariadb 


log2 "OK"

