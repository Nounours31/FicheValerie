#!/bin/bash
URI=http://localhost:8090/ficheValerie-2021-07-01/v1.0.0

typeset -i cmd=0 
typeset opt1="$1" 
typeset -l opt1

if [ "$opt1" = "debug" ]; then
	cmd=1
fi
if [ "$opt1" = "env" ]; then
	cmd=2
fi

if ((cmd == 1)); then
	curl -X GET -H "Accept: application/json" -H "Content-Type: application/json" ${URI}/debug/debug -i
fi

if ((cmd == 2)); then
	echo "{
		'retour': 'iEnvInfo',
		'infos': {},
	}" > /tmp/toto.json

	echo "{
		'retour': 'iEnvInfo',
		'infos': {
				'storePath': "E:/WS/GitHubPerso/FicheValerie/store",
				'CSG': 0.082,
				'TauxImposition' : 0.099 
		}
	}" > /tmp/toto.json

	echo "{
		'retour': 'iEnvInfo',
		'infos': {
				'CSG': 0.082,
				'TauxImposition' : 0.099 
		}
	}" > /tmp/toto.json


	curl -X POST --data @/tmp/toto.json -H "Accept: application/json" -H "Content-Type: application/json" ${URI}/sql -i
fi
