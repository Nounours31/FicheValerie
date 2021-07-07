import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import { cControler } from '../cControler';
import cOutilsDivers  from '../tools/cOutilsDivers';
import cDuration from '../tools/cDuration';
import cWS from '../WS/cWS';
import { iPdf } from '../WS/iWSMessages';

interface iUIDLIGNE {
    racine: number,
    annee: number,
    mois: number,
    jour: number,
    index: number,
    uid: number
}

export default class cDialogMainPageHoraire extends cDialog {
    private _myTab : UIkit.UIkitTabElement | null = null;
    
    private static uidLigneMapping: iUIDLIGNE = { racine: 0, annee: 1, mois: 2, jour: 3, index: 4, uid : 5 };
    private static _SplitTagSeparator: string = '___';

    private static _NomPrefixe: string = 'cDialogMainPageHoraire';
    private static _idLabelOfInputPersonne: string = cDialogMainPageHoraire._NomPrefixe + '_idLabelOfInputPersonne';
    private static _idLabelOfInputPeriode: string = cDialogMainPageHoraire._NomPrefixe + '_idLabelOfInputPeriode';
    private static _idLabelOfInputTarif: string = cDialogMainPageHoraire._NomPrefixe + '_idLabelOfInputTarif';


    
    private static _idTable: string = cDialogMainPageHoraire._NomPrefixe + '_idTable';
    private static _idTableBody: string = cDialogMainPageHoraire._NomPrefixe + '_idTabBody';
    private static _idHoraireDebut: string = cDialogMainPageHoraire._NomPrefixe + '_idHoraireDebut';
    private static _idHoraireFin: string = cDialogMainPageHoraire._NomPrefixe + '_idHoraireFin';
    private static _idHoraireDurrePresta: string = cDialogMainPageHoraire._NomPrefixe + '_idHoraireDurrePresta';
    private static _idCummulHoraireDurrePresta: string = cDialogMainPageHoraire._NomPrefixe + '_idCummulHoraireDurrePresta';
    private static _idSelectActivitee: string = cDialogMainPageHoraire._NomPrefixe + '_idSelectActivitee';

    private static _idButtonOK: string = cDialogMainPageHoraire._NomPrefixe + '_idButtonOK';
    private static _idButtonKO: string = cDialogMainPageHoraire._NomPrefixe + '_idButtonKO';

    

    constructor() {
        super('cDialogMainPageHoraire');
    }

    public Draw(): string {

        let pageHTML : string = `
            <div id="MainPageHoraire">
                <form style="padding-left: 10px;">
                    <fieldset style="padding-left: 10px;">
                        <legend>Saisie des infos generales</legend>

                        <label>Personne:<span id="${cDialogMainPageHoraire._idLabelOfInputPersonne}"></span></label><br/>
                        <label>Periode:<span id="${cDialogMainPageHoraire._idLabelOfInputPeriode}"></span></label><br/>
                        <label>Tarif:<span id="${cDialogMainPageHoraire._idLabelOfInputTarif}"></span></label><br/>
                        <table class="uk-table uk-table-striped" id="${cDialogMainPageHoraire._idTable}">
                            <thead>
                                <tr>
                                    <th>Jour</th>
                                    <th>+</th>
                                    <th>Activite</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Duree</th>
                                    <th>Cumulee</th>
                                    <th>-</th>
                                </tr>
                            </thead>
                            <tbody id="${cDialogMainPageHoraire._idTableBody}">
                            </tbody>
                        </table>
                        <div class="uk-button-group" style="padding-left: 10px;">
                            <td><button class="uk-button uk-button-small" style="background-color: greenyellow;" id="${cDialogMainPageHoraire._idButtonOK}" >Valide</button></td>
                            <td><button class="uk-button uk-button-small" style="background-color: red;" id="${cDialogMainPageHoraire._idButtonKO}">Annule</button></td>
                        </div>
                    </fieldset>
                </form>
            </div>
        `;
        return pageHTML;
    }

    // ---------------------------------------------
    // a l'afichage de la page, mettre les bonnes info (annee, personne, ...)
    // et en fct du mois les bon jour ...
    // ---------------------------------------------
    public refresh(): void {
        let c: cControler = cControler.getInstance();

        $(`#${cDialogMainPageHoraire._idLabelOfInputPersonne}`).text(" " + cOutilsDivers.personne2String (c.personne));
        $(`#${cDialogMainPageHoraire._idLabelOfInputPeriode}`).text(" " + cOutilsDivers.periode2String(c.mois, c.annee));
        $(`#${cDialogMainPageHoraire._idLabelOfInputTarif}`).text(" " + c.tarifHoraire + '€');

        this.updateMoisSurLaPage(c.mois, c.annee);
    }

    public addCallBack(): void {
        // call back du click OK pour envoie en DB de cette fiche
        let me: cDialogMainPageHoraire = this;
        $(`#${cDialogMainPageHoraire._idButtonOK}`).on("click", function (event: JQuery.ClickEvent) {
            event.stopImmediatePropagation();
            event.preventDefault();

            me.CollectInfoPageEtCreateFiche();
        });

        $(`#${cDialogMainPageHoraire._idButtonKO}`).on("click", function (event: JQuery.ClickEvent) {
            event.stopImmediatePropagation();
            event.preventDefault();
            // rien pour le moment
        });
        return;
    }

    private CollectInfoPageEtCreateFiche() : void {
        let me : cDialogMainPageHoraire = this;
        let tableauDelapage: JQuery<HTMLTableElement> = $(`#${cDialogMainPageHoraire._idTable}`);

        // creation en DB du bulletin de salaire
        let c: cControler = cControler.getInstance();
        let ws: cWS = new cWS();
        let ficheId : number = ws.createBulletinSalaire (c.mois, c.annee, c.personne, c.tarifHoraire);

        // upload des info
        tableauDelapage.find("tr").each(function (){
            // sur ma ligne
            let ligneId : string = $(this).prop ("id");
            if (ligneId.length > 1) {
                let info : string[] = me.decodeLigneUID(ligneId);

                if (info.length == 6) {
                    let annee: number = Number.parseInt(info[cDialogMainPageHoraire.uidLigneMapping.annee] as string);
                    let mois: number = Number.parseInt(info[cDialogMainPageHoraire.uidLigneMapping.mois] as string);
                    let jour: number = Number.parseInt(info[cDialogMainPageHoraire.uidLigneMapping.jour] as string);

                    let sDebutHoraire: string = $(this).find(`.${cDialogMainPageHoraire._idHoraireDebut}`).val() as string;
                    let sFinHoraire: string = $(this).find(`.${ cDialogMainPageHoraire._idHoraireFin }`).val() as string;
                    
                    if ((sDebutHoraire.length > 3) && (sFinHoraire.length > 3)) { 
                        let aDebutHoraire: string[] = sDebutHoraire.split(':');
                        let aFinHoraire: string[] = sFinHoraire.split(':');
                        
                        let heure: number = Number.parseInt(aDebutHoraire[0] as string);
                        let minute: number = Number.parseInt(aDebutHoraire[1] as string);
                        let debut: Date = new Date(annee, mois, jour, heure, minute);

                        heure = Number.parseInt(aFinHoraire[0] as string);
                        minute = Number.parseInt(aFinHoraire[1] as string);
                        let fin: Date = new Date(annee, mois, jour, heure, minute);
                        ws.addActivite(ficheId,
                            jour, 
                            $(this).find(`.${cDialogMainPageHoraire._idSelectActivitee} option:selected`).text(),
                            debut,
                            fin,
                            -1.0);
                    }
                }
            }
        });
        let pdf: iPdf = ws.generatePdf(ficheId);
        console.log(pdf.id);

        ws.getPdf(pdf.id);
    }

    // ---------------------------------------------
    // a l'afichage des jours de la page
    // et en fct du mois les bon jour ...
    // ---------------------------------------------
    private updateMoisSurLaPage (mois: number, annee: number) {
        let dateDebutmois: Date = new Date(annee, mois, 1);
        let dateCourante: Date = new Date (dateDebutmois);

        let idebug : number = 0;
        while ((dateCourante.getMonth() == dateDebutmois.getMonth()) && (idebug < 5)) {
            idebug++;
            console.log(dateCourante.toDateString());
            let jourDeLaSemaine: string = cOutilsDivers.SemaineFromIntToNom(dateCourante.getDay());
            let jourDuMois : number = dateCourante.getDate();
            let indice:number = 0;

            let uidLigne: string = this.createLigneUID(annee, mois, jourDuMois, indice);
            let uneligne : string = `
                <tr id="tr${uidLigne}">
                    <td>${jourDeLaSemaine} ${jourDuMois} ${cOutilsDivers.periode2String(mois, annee)}</td>
                    <td> <button class="uk-button uk-button-default uk-button-small" style = "background-color: greenyellow;">+</button></td>
                    <td>
                        <select class="uk-select ${cDialogMainPageHoraire._idSelectActivitee}">
                            <option value="-" selected>-</option>
                            <option value="Madame">Cuisine</option>
                            <option value="Monsieur">Aide a la personne</option>
                            <option value="Monsieur">Jeux</option>
                        </select>
                    </td>
                    <td><input type="time" id="${cDialogMainPageHoraire._idHoraireDebut + uidLigne}" class="${cDialogMainPageHoraire._idHoraireDebut}" name="${cDialogMainPageHoraire._idHoraireDebut + uidLigne}"> </td>
                    <td><input type="time" id="${cDialogMainPageHoraire._idHoraireFin + uidLigne}" class="${cDialogMainPageHoraire._idHoraireFin}" name="${cDialogMainPageHoraire._idHoraireFin + uidLigne}"> </td>
                    <td id="${cDialogMainPageHoraire._idHoraireDurrePresta + uidLigne}" class="${cDialogMainPageHoraire._idHoraireDurrePresta}"></td>
                    <td id="${cDialogMainPageHoraire._idCummulHoraireDurrePresta + uidLigne}"></td>
                    <td><button class="uk-button uk-button-default uk-button-small">-</button></td >
                </tr>
            `;
            $(`#${cDialogMainPageHoraire._idTableBody}`).append(uneligne);
            dateCourante.setDate(dateCourante.getDate()+1);
        }

        // call back de mise jour d'un horaire
        let me: cDialogMainPageHoraire = this;
        $(`.${cDialogMainPageHoraire._idHoraireDebut}`).on("change", function (event: JQuery.ChangeEvent) {
            console.log(event.type);
            event.stopImmediatePropagation();
            event.preventDefault();


            let targetId : string = event.currentTarget.id;
            me.ReadHoraireAndParse(targetId);

        });

        $(`.${cDialogMainPageHoraire._idHoraireFin}`).on("change", function (event: JQuery.ChangeEvent) {
            console.log(event.type);
            event.stopImmediatePropagation();
            event.preventDefault();

            let targetId: string = event.currentTarget.id;
            me.ReadHoraireAndParse(targetId);
        });

    }

    private ReadHoraireAndParse (eventTargetId : string) : void {
        // est ce que le Start est valuee ?
        let targetIdDebut: string = eventTargetId.replace('Fin', 'Debut');
        let heureDebut: string = $(`#${targetIdDebut}`).val() as string;
        if ((heureDebut == null) || (heureDebut.length < 3)) {
            // non ... 
            return;
        }

        // est ce que le end est valuee ?
        let targetIdFin: string = eventTargetId.replace('Debut', 'Fin');
        let heureFin: string = $(`#${targetIdFin}`).val() as string;
        if ((heureFin == null) || (heureFin.length < 3)) {
            // non ... 
            return;
        }

        // ok calcul de la durre
        let info: string[] = this.decodeLigneUID(eventTargetId);
        let ligne_annee: string = info[cDialogMainPageHoraire.uidLigneMapping.annee];
        let ligne_mois: string = info[cDialogMainPageHoraire.uidLigneMapping.mois];
        let ligne_jour: string = info[cDialogMainPageHoraire.uidLigneMapping.jour];
        let ligne_index: string = info[cDialogMainPageHoraire.uidLigneMapping.index];

        let duree: cDuration = new cDuration();
        duree.set(Number.parseInt(ligne_annee), Number.parseInt(ligne_mois), Number.parseInt(ligne_jour), heureDebut, heureFin);

        // mise a jour de la duree de cette activite
        let idCelluleToChange: string = this.encodeLigneUID(cDialogMainPageHoraire._idHoraireDurrePresta, info[cDialogMainPageHoraire.uidLigneMapping.uid]);
        let nbHeure : number = duree.asHour();
        $(`#${idCelluleToChange}`).text(nbHeure as unknown as string);

        // mise  ajour de la page (soome des activite)
        let letNbHeureCumilee: number = 0.0;
        let x: number = 0.0;

        let me : cDialogMainPageHoraire = this;
        console.log("-------------------------------------------------------");
        $(`.${cDialogMainPageHoraire._idHoraireDurrePresta}`).each(function () {
            let celluleValeur: string = $(this).text();
            
            if ((celluleValeur != null) && (celluleValeur.length > 0)) {
                x = Number.parseFloat($(this).text());
                letNbHeureCumilee += x;
                console.log("x: " + x + "  " + $(this).prop('id'));
                console.log("cumul: " + letNbHeureCumilee);
                if (x > 0) {
                    let local_aTargetId: string[] = me.decodeLigneUID($(this).prop('id'));
                    let local_uidLigne: string = local_aTargetId[cDialogMainPageHoraire.uidLigneMapping.uid];
                    let local_idCelluleToChange: string = me.encodeLigneUID(cDialogMainPageHoraire._idCummulHoraireDurrePresta, local_uidLigne);
                    console.log(local_idCelluleToChange);
                    $(`#${local_idCelluleToChange}`).text(letNbHeureCumilee as unknown as string);
                }
            }
        });
    }


    private decodeLigneUID(targetId: string): string[] {
        let retour : string[] = [];

        if (targetId == null)
            return retour;

        let localArray: string[] = targetId.split(cDialogMainPageHoraire._SplitTagSeparator);
        retour[cDialogMainPageHoraire.uidLigneMapping.racine] = localArray[0];

        if (localArray.length > 1) {
            retour[cDialogMainPageHoraire.uidLigneMapping.uid] = localArray[1];
            let localArray2: string[] = localArray[1].split('-');
            retour[cDialogMainPageHoraire.uidLigneMapping.annee] = localArray2[0];
            if (localArray2.length > 1)
                retour[cDialogMainPageHoraire.uidLigneMapping.mois] = localArray2[1];
            if (localArray2.length > 2)
                retour[cDialogMainPageHoraire.uidLigneMapping.jour] = localArray2[2];
            if (localArray2.length > 3)
                retour[cDialogMainPageHoraire.uidLigneMapping.index] = localArray2[3];
        }

        return retour;
    }

    private createLigneUID(annee: number, mois: number, jourDuMois: number, indice: number): string {
        return cDialogMainPageHoraire._SplitTagSeparator + annee + '-' + mois + '-' + jourDuMois + '-' + indice;
    }

    private encodeLigneUID(root: string, uid: string): string {
        return root + cDialogMainPageHoraire._SplitTagSeparator + uid;
    }
}