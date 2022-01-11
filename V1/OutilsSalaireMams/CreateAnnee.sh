#bash

typeset -i test=1

if ((test == 1)); then
	To=/e/WorkSpaces/WS/GitHub/FicheValerie/V1/OutilsSalaireMams/test
	Ref=/e/WorkSpaces/WS/GitHub/FicheValerie/V1/OutilsSalaireMams/Ref.xlsm
else
	To=/d/mams/BulletinsSalaire
	Ref=/d/PAPA/OutilsSalaireMams/Ref.xlsm
fi
XLS='/c/Program Files (x86)/Microsoft Office/root/Office16/EXCEL.EXE'

typeset -a Mois=(Janvier Fevrier Mars Avril Mai Juin Juillet Aout Septembre Octobre Novembre Decembre test)

printf "Annee ? "

read annee
echo $annee

mkdir -p $To/$annee
for ((i = 0; i < ${#Mois[@]}; i++)); do
	mois=$(printf "%02d" "$((i+1))")
	Rep="$To/$annee/${mois}-${annee}-${Mois[$i]}"
	
	mkdir $Rep
	cp $Ref "$Rep/_$(basename $Ref)"
done



