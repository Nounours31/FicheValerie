#bash

echo "Step in: $*"

typeset -l from=$1
typeset -l to=$2
typeset -i iAnnee=$3

typeset -i test=0

if ((test == 1)); then
	To=/e/WorkSpaces/WS/GitHub/FicheValerie/V1/OutilsSalaireMams/test
	Ref=/e/WorkSpaces/WS/GitHub/FicheValerie/V1/OutilsSalaireMams
	typeset -a Mois=(Janvier Fevrier)
else
	To=/d/mams/BulletinsSalaire
	Ref=/d/mams/BulletinsSalaire/OutilsSalaireMams/FicheValerie/V1/OutilsSalaireMams
	typeset -a Mois=(Janvier Fevrier Mars Avril Mai Juin Juillet Aout Septembre Octobre Novembre Decembre)
fi
typeset -a files2Copy=("Ref.xlsm" "runMacro.bat")


function testMois() {
	local cMois=$1
	local iMois=13
	local iIndice=0
	
	for ((iIndice = 0; iIndice < ${#Mois[@]}; iIndice++ )); do
		typeset -l lMois=${Mois[$iIndice]}
		if [ "${lMois}" = "$cMois" ]; then
			iMois=$iIndice
			break
		fi
	done
	echo $iMois
	return 0
}
typeset iFrom=$(testMois $from)
typeset iTo=$(testMois $to)
if (( ((iFrom < 0) || (iFrom > 11)) || ((iTo < 0) || (iTo > 11)) )); then
	echo "mois entre Janvier et Decembre: iFrom $iFrom - iTo $iTo"
	exit 3
fi 
printf "Mise a jour des mois de: %s (%d) a %s (%d) - %d \n" $from $iFrom $to $iTo $iAnnee
annee=$iAnnee


fromRep=$(pwd)
mkdir -p $To/$annee

for ((i = iFrom; i <= iTo; i++)); do
	mois=$(printf "%02d" "$((i+1))")
	Rep="$To/$annee/${mois}-${annee}-${Mois[$i]}"
	echo "Update of $Rep"
	
	mkdir -p $Rep
	for ((j = 0; j < ${#files2Copy[@]}; j++)); do
		cp $Ref/${files2Copy[$j]} "$Rep"
	done

	cd $Rep
	./runMacro.bat
	for ((j = 0; j < ${#files2Copy[@]}; j++)); do
		rm "${files2Copy[$j]}"
	done

	cd $fromRep
done



