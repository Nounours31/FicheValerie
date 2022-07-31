#!bash

function usage() {
  echo "gerarateur.sh -f <from> -t <to> -a <annee>"
  echo "./generateur.sh -f Juin -t decembre -a 2022"
}


typeset -l FROM=Janvier
typeset -l TO=Decembre

typeset -i iAnnee=$(date +"%Y")
typeset -i iErrNoArgs=1
typeset -i iErr=0

while [ "$1" != "" ]; do
    iErrNoArgs=0
	case "$1" in
	   "-f") FROM=$2; shift 1;;
	   "-t") TO=$2; shift 1;;
	   "-a") iAnnee=$2; shift 1;;
	   *) iErr=1
	esac
	shift 1
done

if (( (iErr > 0) || (iErrNoArgs > 0) )); then
	usage
	exit 1
fi

/d/mams/BulletinsSalaire/OutilsSalaireMams/FicheValerie/V1/OutilsSalaireMams/_CreateAnnee.sh $FROM $TO $iAnnee