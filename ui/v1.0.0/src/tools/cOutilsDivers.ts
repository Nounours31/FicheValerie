import { iActivite, iPersonne } from "../WS/iWSMessages";

export default class cOutilsDivers {
    static _mois: string[] = [
        "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"
    ];
    
    static _days : string[] =  [
        'Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'
    ];

    constructor() {
    }

    public static MoisFromNomToInt(mois: string): number {
        let i: number = 0;
        for (i = 0; i < 12; i++) {
            if (mois == cOutilsDivers._mois[i])
                return (i + 1)
        }
        return -1;
    }

    public static MoisFromIntToNom(mois: number): String {
        return cOutilsDivers._mois[mois];
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
        retour += " ";
        retour += annee;
        return retour;
    }

    static heureFloat2HeureString(aCOnvertir: number): string {
        let retour: string = "";
        
        let heure: number = Math.floor (aCOnvertir);
        let minute : number = Math.floor (((aCOnvertir - heure) * 60.0) + 0.99999999999 );

        let sHeure: string = heure.toFixed(0);
        let sMinute: string = minute.toFixed(0);

        if (sHeure.length == 0) sHeure = "00";
        if (sHeure.length == 1) sHeure = "0" + sHeure;
        if (sHeure.length > 2) sHeure = "##";

        if (sMinute.length == 0) sMinute = "00";
        if (sMinute.length == 1) sMinute = "0" + sMinute;
        if (sMinute.length > 2) sMinute = "##";
        retour = sHeure + ":" + sMinute;
        return retour;
    }

    static heureString2HeureFloat(aCOnvertir: string): number {
        let info: string[] = aCOnvertir.split(":");
        
        let heure: number = Number.parseInt(info[0]);
        let minute : number = Number.parseInt(info[1]);

        return ((heure * 1.0) + (minute * 1.0) / 60.0);
    }

    static SemaineFromIntToNom(jour: number): string {
        return cOutilsDivers._days[jour];
    }

    static stringJourSemainetoInt(jour: string): number {
        for (let i : number = 0; i < cOutilsDivers._days.length; i++)
            if (cOutilsDivers._days[i].toLowerCase() == jour.toLowerCase())
                return i;
        return -1;
    }

    public static replaceAll(s: string, a: string, b: string) {
        let s2: string = s.replace(a, b);
        while (s2 != s) {
            s = s2;
            s2 = s.replace(a, b);
        }
        return s2;
    }

    static ActiviteeToString(x: iActivite) {
        let retour: string = "";
        retour += "[id:" + x.id + "]";
        retour += "[activitee:" + x.activite + "]";
        retour += "[debut:" + x.gmtepoch_debut + "]";
        retour += "[fin:" + x.gmtepoch_fin + "]";
        retour += "[bulletin:" + x.idBulletinSalaire + "]";
        return retour;
    }

    
}