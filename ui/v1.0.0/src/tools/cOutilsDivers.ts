import { iPersonne } from "../WS/iWSMessages";

export default class cOutilsDivers {

    constructor() {
    }

    public static MoisFromNomToInt(mois : string): number {
        const _mois: string[] = [
            "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"
        ];

        let i: number = 0;
        for (i = 0; i < 12; i++) {
            if (mois == _mois[i])
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
}