import $ from 'jquery'
import cWS from '../WS/cWS';
import { iPersonne } from '../WS/iWSMessages';

export default class cDialogTools {
    constructor () {

    }

    public static d () : void {} 

    public static addMoisInSelect(jqIdSelect : string) : void {
        let d: Date = new Date();
        let iIndiceMoisCourant: number = d.getMonth();
        let monthNames: Array<string> = ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"];
        for (let i: number = 0; i < 12; i++) {
            let m: string = monthNames[i];
            if (i == iIndiceMoisCourant)
                $(jqIdSelect).append($('<option>', { value: i, text: m }).attr('selected', 'selected'));
            else
                $(jqIdSelect).append($('<option>', { value: i, text: m }));
        }
    }

    static addAnneeInSelect(jqIdSelectAnnee: string) {
        let d: Date = new Date();
        let iIndiceAnneeCourant: number = d.getFullYear();
        for (let i: number = 2020; i < 2030; i++) {
            if (i == iIndiceAnneeCourant)
                $(jqIdSelectAnnee).append($('<option>', { value: i, text: i }).attr('selected', 'selected'));
            else
                $(jqIdSelectAnnee).append($('<option>', { value: i, text: i }));
        }

    }

    static addPersonneInSelect(jqIdSelectPersonne: string) {
        let ws: cWS = new cWS();
        let allPersonnes: iPersonne[] = ws.getAllPersonnes();
        allPersonnes.forEach(unePersonne => {
            $(jqIdSelectPersonne).append($('<option>', { value: unePersonne.id, text: (unePersonne.genre + "  " + unePersonne.nom) }));
        });
    }
}