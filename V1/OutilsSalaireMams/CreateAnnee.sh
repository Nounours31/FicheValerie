#bash

typeset -i test=1

if ((test == 1)); then
	To=/e/WorkSpaces/WS/GitHub/FicheValerie/V1/OutilsSalaireMams/test
	Ref=/e/WorkSpaces/WS/GitHub/FicheValerie/V1/OutilsSalaireMams
	typeset -a Mois=(Janvier Fevrier)
else
	To=/d/mams/BulletinsSalaire
	Ref=/d/PAPA/OutilsSalaireMams
	typeset -a Mois=(Janvier Fevrier Mars Avril Mai Juin Juillet Aout Septembre Octobre Novembre Decembre test)
fi
typeset -a files2Copy=("Ref.xlsm" "runMacro.bat")
XLS='/c/Program Files (x86)/Microsoft Office/root/Office16/EXCEL.EXE'


printf "Annee ? "
read annee

fromRep=$(pwd)
mkdir -p $To/$annee

for ((i = 0; i < ${#Mois[@]}; i++)); do
	mois=$(printf "%02d" "$((i+1))")
	Rep="$To/$annee/${mois}-${annee}-${Mois[$i]}"
	echo "Update of $Rep"
	
	mkdir $Rep
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



