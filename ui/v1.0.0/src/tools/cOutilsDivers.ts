import { iPersonne } from "../WS/iWSMessages";

export default class cOutilsDivers {
    static _mois: string[] = [
        "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"
    ];
    
    static _days : string[] =  [
        'Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'
    ];

    constructor() {
    }

    public static MoisFromNomToInt(mois : string): number {
        let i: number = 0;
        for (i = 0; i < 12; i++) {
            if (mois == cOutilsDivers._mois[i])
                return (i+1)
        }
        return -1;
    }

    public static personne2String(p : iPersonne) : string {
        let s : string  = "";
        s += (p.genre + " ");
        s += p.nom;
        s += (" ["+p.id+"]");
        return s;
    }

    static periode2String(mois: number, annee: number): string {
        let retour: string = "";
        retour = cOutilsDivers._mois[mois];
        retour += " " ;
        retour += annee;
        return retour;
    }

    static SemaineFromIntToNom(jour: number): string {
        return cOutilsDivers._days[jour];
    }
}