import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';

import $ from 'jquery'
import cWS from '../WS/cWS';
import { iPdf, iPersonne } from '../WS/iWSMessages';

export default class cDialogTools {
    constructor () {

    }


    public static addMoisInSelect(jqIdSelect: string, searchMode: boolean = false) : void {
        let d: Date = new Date();
        let iIndiceMoisCourant: number = d.getMonth();
        let monthNames: Array<string> = ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"];
        for (let i: number = 0; i < 12; i++) {
            let m: string = monthNames[i];
            if ((i == iIndiceMoisCourant) && (!searchMode))
                $(jqIdSelect).append($('<option>', { value: i, text: m }).attr('selected', 'selected'));
            else
                $(jqIdSelect).append($('<option>', { value: i, text: m }));
        }
        if (searchMode) {
            $(jqIdSelect).append($('<option>', { value: "-", text: "-" }).attr('selected', 'selected'));
        }
    }

    static addAnneeInSelect(jqIdSelectAnnee: string, searchMode : boolean = false) {
        let d: Date = new Date();
        let iIndiceAnneeCourant: number = d.getFullYear();
        for (let i: number = 2020; i < 2030; i++) {
            if ((i == iIndiceAnneeCourant) && (!searchMode))
                $(jqIdSelectAnnee).append($('<option>', { value: i, text: i }).attr('selected', 'selected'));
            else
                $(jqIdSelectAnnee).append($('<option>', { value: i, text: i }));
        }
        if (searchMode) {
            $(jqIdSelectAnnee).append($('<option>', { value: "-", text: "-" }).attr('selected', 'selected'));
        }
    }

    static addPersonneInSelect(jqIdSelectPersonne: string) {
        let ws: cWS = new cWS();
        let allPersonnes: iPersonne[] = ws.getAllPersonnes();
        allPersonnes.forEach(unePersonne => {
            $(jqIdSelectPersonne).append($('<option>', { value: unePersonne.id, text: (unePersonne.genre + "  " + unePersonne.nom) }));
        });
    }

    static addActiviteInSelect(jqIdSelectActivite: string) {
        let ws: cWS = new cWS();
        let allActivitee: string[] = ws.getAllPossibleActivitees();
        $(jqIdSelectActivite).append($('<option>', { value: "-", text: "-" }).attr('selected', 'selected'));
        allActivitee.forEach(uneActivitee => {
            $(jqIdSelectActivite).append($('<option>', { value: uneActivitee, text: uneActivitee }));
        });
    }

    // ----------------------------------------------------
    // genertaion du PDF
    // ----------------------------------------------------
    public static createAndDownloadPdf(idBulletinSalaire: number): void {
        let ws: cWS = new cWS();

        if (idBulletinSalaire > 0) {
            let pdf: iPdf = ws.generatePdf(idBulletinSalaire);
            console.log(pdf.id);
            this.DownloadPdf(pdf.id);
        }
        else {
            UIkit.modal.alert("Pas de Bulletin de salaire de defini ... createAndDownloadPdf");
        }
    }

    // ----------------------------------------------------
    // genertaion du PDF
    // ----------------------------------------------------
    public static  DownloadPdf(idPdf: number): void {
        let ws: cWS = new cWS();

        if (idPdf > 0) {
            ws.getPdf(idPdf);
        }
        else {
            UIkit.modal.alert("Pas de Bulletin de salaire de defini ... createAndDownloadPdf");
        }
    }
}