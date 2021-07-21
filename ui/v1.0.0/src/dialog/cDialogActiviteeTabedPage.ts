import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialogAbstract from './cDialogAbstract';

import cOutilsDivers  from '../tools/cOutilsDivers';
import cDuration from '../tools/cDuration';
import cWS from '../WS/cWS';
import { iActivite, iBulletinSalaire, iPdf, iPersonne } from '../WS/iWSMessages';
import cDialogTools from '../tools/cDialogTools';

interface iUIDLIGNE {
    racine: number,
    annee: number,
    mois: number,
    jour: number,
    index: number,
    uid: number
}
interface iRappelExtra {
    date?: string,
    idBulletinOrigine?: number,
    tarif: number,
    duree: number,
    id: number,
    idBulletin: number,
    status?: number
}

interface iExtraInfo {
    rappel?: iRappelExtra[],
    depassement?: iRappelExtra[]
}

export default class cDialogActiviteeTabedPage extends cDialogAbstract {
    private _myTab : UIkit.UIkitTabElement | null = null;
    
    private static uidLigneMapping: iUIDLIGNE = { racine: 0, annee: 1, mois: 2, jour: 3, index: 4, uid : 5 };
    private static _SplitTagSeparator: string = '___';

    private static _NomPrefixe: string = 'cDialogActiviteeTabedPage';
    private static _idLabelOfInputPersonne: string = cDialogActiviteeTabedPage._NomPrefixe + '_idLabelOfInputPersonne';
    private static _idLabelOfInputPeriode: string = cDialogActiviteeTabedPage._NomPrefixe + '_idLabelOfInputPeriode';
    private static _idLabelOfInputTarif: string = cDialogActiviteeTabedPage._NomPrefixe + '_idLabelOfInputTarif';


    
    private static _idTable: string = cDialogActiviteeTabedPage._NomPrefixe + '_idTable';
    private static _idTableBody: string = cDialogActiviteeTabedPage._NomPrefixe + '_idTabBody';
    private static _idHoraireDebut: string = cDialogActiviteeTabedPage._NomPrefixe + '_idHoraireDebut';
    private static _idHoraireFin: string = cDialogActiviteeTabedPage._NomPrefixe + '_idHoraireFin';
    private static _idHoraireDurrePresta: string = cDialogActiviteeTabedPage._NomPrefixe + '_idHoraireDurrePresta';
    private static _idCummulHoraireDurrePresta: string = cDialogActiviteeTabedPage._NomPrefixe + '_idCummulHoraireDurrePresta';
    private static _idSelectActivitee: string = cDialogActiviteeTabedPage._NomPrefixe + '_idSelectActivitee';

    private static _idButtonOK: string = cDialogActiviteeTabedPage._NomPrefixe + '_idButtonOK';
    private static _idButtonPDF: string = cDialogActiviteeTabedPage._NomPrefixe + '_idButtonPDF';
    private static _idButtonKO: string = cDialogActiviteeTabedPage._NomPrefixe + '_idButtonKO';

    private static _idDepassementForfaitaire: string = cDialogActiviteeTabedPage._NomPrefixe + '_idDepassementForfaitaire';
    private static _idReportPrecedent: string = cDialogActiviteeTabedPage._NomPrefixe + '_idReportPrecedent';


        // creation en DB du bulletin de salaire
    private _ws: cWS = null;
    private _idBulletinSalaire: number;

    private _inputAsTime: boolean = false;

    constructor() {
        super('cDialogActiviteeTabedPage');
        // creation en DB du bulletin de salaire
        this._ws = new cWS();
        this._idBulletinSalaire = -1;
    }

    public Draw(): string {

        let pageHTML : string = `
            <div id="cDialogActiviteeTabedPage">
                <form style="padding-left: 10px;">
                    <fieldset style="padding-left: 10px;">
                        <legend>Saisie des infos generales</legend>

                        <label>Personne:<span id="${cDialogActiviteeTabedPage._idLabelOfInputPersonne}"></span></label><br/>
                        <label>Periode:<span id="${cDialogActiviteeTabedPage._idLabelOfInputPeriode}"></span></label><br/>
                        <label>Tarif:<span id="${cDialogActiviteeTabedPage._idLabelOfInputTarif}"></span></label><br/>

                        <fieldset style="padding-left: 10px;">
                            <legend>Saisie des extras</legend>
                            <label>Depassement forfaitaire (en heure)</label>
                            <input type="text" id="${cDialogActiviteeTabedPage._idDepassementForfaitaire}"
                                    pattern="[0-9]{2}:[0-9]{2}"
                                    placeholder="hh:mm"/>
                            <span id="${cDialogActiviteeTabedPage._idDepassementForfaitaire}_span">(*)</span>
                            <br/>
                            <label>Rappel mois precedent (en heure)</label>
                            <input type="text" id="${cDialogActiviteeTabedPage._idReportPrecedent}"
                                    pattern="[0-9]{2}:[0-9]{2}"
                                    placeholder="hh:mm"/>
                            <span id="${cDialogActiviteeTabedPage._idReportPrecedent}_span">(*)</span>
                        </fieldset>

                        <table class="uk-table uk-table-striped" id="${cDialogActiviteeTabedPage._idTable}">
                            <thead>
                                <tr>
                                    <th>Jour</th>
                                    <th>Activite</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Duree</th>
                                    <th>Cumulee</th>
                                    <th>-</th>
                                </tr>
                            </thead>
                            <tbody id="${cDialogActiviteeTabedPage._idTableBody}">
                            </tbody>
                        </table>
                        <div class="uk-button-group" style="padding-left: 10px;">
                            <td><button class="uk-button uk-button-small" style="background-color: greenyellow;" id="${cDialogActiviteeTabedPage._idButtonOK}" >Valide</button></td>
                            <td><button class="uk-button uk-button-small" style="background-color: yellow;" id="${cDialogActiviteeTabedPage._idButtonPDF}">Genere bulletin salaire</button></td>
                        </div>
                    </fieldset>
                </form>
            </div>
        `;
        return pageHTML;
    }


    // ---------------------------------------------------------
    // call back des trpios bouttons du bas
    // ---------------------------------------------------------
    public addCallBack(): void {
        // call back du click OK pour envoie en DB de cette fiche
        let me: cDialogActiviteeTabedPage = this;
        $(`#${cDialogActiviteeTabedPage._idButtonOK}`).on("click", function (event: JQuery.ClickEvent) {
            event.stopImmediatePropagation();
            event.preventDefault();

            me.CollectInfoPageEtCreateFiche();
        });

        $(`#${cDialogActiviteeTabedPage._idButtonPDF}`).on("click", function (event: JQuery.ClickEvent) {
            event.stopImmediatePropagation();
            event.preventDefault();
            
            cDialogTools.createAndDownloadPdf (me._idBulletinSalaire);
        });

        $(`#${cDialogActiviteeTabedPage._idButtonKO}`).on("click", function (event: JQuery.ClickEvent) {
            event.stopImmediatePropagation();
            event.preventDefault();
            // rien pour le moment
        });
        return;
    }






    // ------------------------------------------------------
    // Recup de toute les info de l apage et generation des activite en DB
    // ------------------------------------------------------
    private CollectInfoPageEtCreateFiche() : void {
        let me : cDialogActiviteeTabedPage = this;
        let tableauDelapage: JQuery<HTMLTableElement> = $(`#${cDialogActiviteeTabedPage._idTable}`);

        if (me._idBulletinSalaire > 0) {
            // collect des extra
            let nbHeureDepassementForfaitaire : string = $(`#${cDialogActiviteeTabedPage._idDepassementForfaitaire}`).val() as string;
            let nbHeureReport: string = $(`#${cDialogActiviteeTabedPage._idReportPrecedent}`).val() as string;
            if ((nbHeureDepassementForfaitaire != null) && (nbHeureDepassementForfaitaire != undefined) && (nbHeureDepassementForfaitaire.length > 3)) {
                let nbHeure: number = cOutilsDivers.heureString2HeureFloat(nbHeureDepassementForfaitaire);
                me._ws.setDepassementForfaitaire(me._idBulletinSalaire, nbHeure);
            }
            if ((nbHeureReport != null) && (nbHeureReport != undefined) && (nbHeureReport.length > 3)) {
                let nbHeure : number = cOutilsDivers.heureString2HeureFloat(nbHeureReport);                
                me._ws.setHeureReport(me._idBulletinSalaire, nbHeure);
            }

            // upload des info
            tableauDelapage.find("tr").each(function (){
                // sur ma ligne
                let ligneId : string = $(this).prop ("id");
                if (ligneId.length > 1) {
                    let info : string[] = me.decodeLigneUID(ligneId);

                    if (info.length == 6) {
                        let annee: number = Number.parseInt(info[cDialogActiviteeTabedPage.uidLigneMapping.annee] as string);
                        let mois: number = Number.parseInt(info[cDialogActiviteeTabedPage.uidLigneMapping.mois] as string);
                        let jour: number = Number.parseInt(info[cDialogActiviteeTabedPage.uidLigneMapping.jour] as string);

                        let sDebutHoraire: string = $(this).find(`.${cDialogActiviteeTabedPage._idHoraireDebut}`).val() as string;
                        let sFinHoraire: string = $(this).find(`.${cDialogActiviteeTabedPage._idHoraireFin}`).val() as string;
                        
                        let inputDateType: string = $(this).find(`.${cDialogActiviteeTabedPage._idHoraireFin}`).attr('type');
                        if ( ((inputDateType == 'time') || (inputDateType == 'text')) && (sDebutHoraire.length > 3) && (sFinHoraire.length > 3)) {
                            let aDebutHoraire: string[] = sDebutHoraire.split(':');
                            let aFinHoraire: string[] = sFinHoraire.split(':');
                            
                            let heure: number = Number.parseInt(aDebutHoraire[0] as string);
                            let minute: number = Number.parseInt(aDebutHoraire[1] as string);
                            let debut: Date = new Date(annee, mois, jour, heure, minute);

                            heure = Number.parseInt(aFinHoraire[0] as string);
                            minute = Number.parseInt(aFinHoraire[1] as string);
                            let fin: Date = new Date(annee, mois, jour, heure, minute);
                            me._ws.addActivite(me._idBulletinSalaire,
                                jour, 
                                $(this).find(`.${cDialogActiviteeTabedPage._idSelectActivitee} option:selected`).text(),
                                debut,
                                fin,
                                -1.0);
                        }
                        else {
                            console.log("Deja en DB : Info=" + ligneId);
                        }
                    }
                }
            });
        }
        else {
            UIkit.modal.alert("Pas de Bulletin de salaire de defini ... CollectInfoPageEtCreateFiche");
        }
        this.refresh (this._idBulletinSalaire);
    }











    // ---------------------------------------------
    // a l'afichage de la page, mettre les bonnes info (annee, personne, ...)
    // et en fct du mois les bon jour ...
    // ---------------------------------------------
    public refresh(idBulletin : number): void {
        this._idBulletinSalaire = idBulletin;

        if (this._idBulletinSalaire > 0) {
            let bulletin : iBulletinSalaire[] =  this._ws.getBulletinSalaire (idBulletin);
            if (bulletin.length > 0) {
                let laFicheDeSalaire : iBulletinSalaire = bulletin[0];
                let personne : iPersonne[] = this._ws.getPersonne(laFicheDeSalaire.idPersonne);
                if (personne.length > 0) {
                    let unepersonne : iPersonne = personne[0];
                    $(`#${cDialogActiviteeTabedPage._idLabelOfInputPersonne}`).text(" " + cOutilsDivers.personne2String(unepersonne));
                    $(`#${cDialogActiviteeTabedPage._idLabelOfInputPeriode}`).text(" " + cOutilsDivers.periode2String(laFicheDeSalaire.mois, laFicheDeSalaire.annee));
                    $(`#${cDialogActiviteeTabedPage._idLabelOfInputTarif}`).text(" " + laFicheDeSalaire.tarifHoraire + '€');

                    this.affichageActiviteeDuMois(laFicheDeSalaire.mois, laFicheDeSalaire.annee);
                }
            }
        }
    }

    // ---------------------------------------------
    // a l'afichage des jours de la page
    // et en fct du mois les bon jour ...
    // ---------------------------------------------
    private affichageActiviteeDuMois(mois: number, annee: number) : void {
        let dateDebutmois: Date = new Date(annee, mois, 1);
        let dateCourante: Date = new Date(dateDebutmois);

        $(`#${cDialogActiviteeTabedPage._idTableBody}`).empty();

        if (this._idBulletinSalaire > 0) {
            // --------------------------------------------------------------------
            // est ce qu'il y a des extra ?
            // --------------------------------------------------------------------
            let allExtra: iExtraInfo = this._ws.getExtra(this._idBulletinSalaire) as iExtraInfo;
            if ((allExtra.depassement != null) && (allExtra.depassement.length > 0)) {
                let InputWithInfo: JQuery<HTMLInputElement> = $(`#${cDialogActiviteeTabedPage._idDepassementForfaitaire}`);
                InputWithInfo.empty();
                InputWithInfo.val(cOutilsDivers.heureFloat2HeureString(allExtra.depassement[0].duree));

                let spanWithInfo: JQuery<HTMLSpanElement> = $(`#${cDialogActiviteeTabedPage._idDepassementForfaitaire}_span`);
                spanWithInfo.empty();
                spanWithInfo.append(`(lu depuis la FromDB)`);
            }
            if ((allExtra.rappel != null) && (allExtra.depassement.length > 0)) {
                let InputWithInfo: JQuery<HTMLInputElement> = $(`#${cDialogActiviteeTabedPage._idReportPrecedent}`);
                InputWithInfo.empty();
                InputWithInfo.val(cOutilsDivers.heureFloat2HeureString(allExtra.rappel[0].duree));

                let spanWithInfo: JQuery<HTMLSpanElement> = $(`#${cDialogActiviteeTabedPage._idReportPrecedent}_span`);
                spanWithInfo.empty();
                spanWithInfo.append(`(lu depuis la FromDB)`);
            }
            console.log(allExtra);



            let allActiviteeDejaEnDB : iActivite[] = this._ws.getAllActivitee(this._idBulletinSalaire);
            // -----------------------------------
            // ligne journaliere
            // -----------------------------------
            while ((dateCourante.getMonth() == dateDebutmois.getMonth())) {
                console.log(dateCourante.toDateString());
                let jourDeLaSemaine: string = cOutilsDivers.SemaineFromIntToNom(dateCourante.getDay());
                let jourDuMois: number = dateCourante.getDate();
                let indice: number = 0;

                let allActiviteeDuJourEnDB: iActivite[] = this.filtreActiviteDuJour(dateCourante, allActiviteeDejaEnDB);
                let nbActiviteDuJourEnDB: number = allActiviteeDuJourEnDB.length;
                let nbActiviteDuJour: number = nbActiviteDuJourEnDB + 1;

                let uidLigne: string = "";
                let uneligne: string = "";

                let bHasPrintFirstRow : boolean = false;
                let i : number = 0;
                for (i = 0; i < nbActiviteDuJourEnDB; i++) {
                    let curentACtiviteToPrint: iActivite = allActiviteeDuJourEnDB[i];

                    let duree: cDuration = new cDuration();
                    duree.setActivitees(curentACtiviteToPrint.debut, curentACtiviteToPrint.fin);

                    uidLigne = this.createLigneUID(annee, mois, jourDuMois, indice++);
                    uneligne += `<tr id="tr${uidLigne}">`;
                    if (!bHasPrintFirstRow) {
                        uneligne += `<td rowspan="${nbActiviteDuJour}" style="vertical-align : middle;">${jourDeLaSemaine} ${jourDuMois} ${cOutilsDivers.periode2String(mois, annee)} </td>`;
                        bHasPrintFirstRow = true;
                    }
                    uneligne +=`    <td> 
                                        <textarea rows="1" cols="${curentACtiviteToPrint.activite.length}" readonly>${curentACtiviteToPrint.activite}</textarea>
                                    </td>
                                    <td> 
                                        <textarea rows="1" cols="30"
                                            id="${cDialogActiviteeTabedPage._idHoraireDebut + uidLigne}" 
                                            class="${cDialogActiviteeTabedPage._idHoraireDebut}"
                                            readonly>${curentACtiviteToPrint.debut}</textarea>
                                    </td>
                                    <td> 
                                        <textarea rows="1" cols="30"
                                            id="${cDialogActiviteeTabedPage._idHoraireFin + uidLigne}" 
                                            class="${cDialogActiviteeTabedPage._idHoraireFin}"
                                            readonly>${curentACtiviteToPrint.fin}</textarea>
                                    </td>
                                    <td id="${cDialogActiviteeTabedPage._idHoraireDurrePresta + uidLigne}" class="${cDialogActiviteeTabedPage._idHoraireDurrePresta}">${cOutilsDivers.heureFloat2HeureString(duree.asHour())}</td>
                                    <td id="${cDialogActiviteeTabedPage._idCummulHoraireDurrePresta + uidLigne}">xx</td>
                                    <td><button style="background-color: #ff5733;"
                                        class="uk-button uk-button-default uk-button-small RemoveActiviteInDB_ID_ACTIVITEE"
                                        id="RemoveActiviteInDB_ID_ACTIVITEE_${curentACtiviteToPrint.id}">-</button>
                                    </td>
                                </tr>
                    `;

                }
                uidLigne = this.createLigneUID(annee, mois, jourDuMois, indice++);
                uneligne += `<tr id="tr${uidLigne}">`;
                if (!bHasPrintFirstRow) {
                    uneligne += `<td>${jourDeLaSemaine} ${jourDuMois} ${cOutilsDivers.periode2String(mois, annee)} </td>`;
                    bHasPrintFirstRow = true;
                }
                uneligne += `
                        <td>
                            <select class="uk-select ${cDialogActiviteeTabedPage._idSelectActivitee}"></select>
                        </td>`;
                if (this._inputAsTime) {
                    uneligne += `
                        <td><input type="time" id="${cDialogActiviteeTabedPage._idHoraireDebut + uidLigne}" class="${cDialogActiviteeTabedPage._idHoraireDebut}" name="${cDialogActiviteeTabedPage._idHoraireDebut + uidLigne}"> </td>
                        <td><input type="time" id="${cDialogActiviteeTabedPage._idHoraireFin + uidLigne}" class="${cDialogActiviteeTabedPage._idHoraireFin}" name="${cDialogActiviteeTabedPage._idHoraireFin + uidLigne}"> </td>
                    `;
                }
                else {
                    uneligne += `
                        <td><input type="text" id="${cDialogActiviteeTabedPage._idHoraireDebut + uidLigne}" 
                                    class="${cDialogActiviteeTabedPage._idHoraireDebut}" name="${cDialogActiviteeTabedPage._idHoraireDebut + uidLigne}"
                                    pattern="[0-9]{2}:[0-9]{2}"
                                    placeholder="hh:mm"></td>
                        <td><input type="text" id="${cDialogActiviteeTabedPage._idHoraireFin + uidLigne}" class="${cDialogActiviteeTabedPage._idHoraireFin}" 
                                    name="${cDialogActiviteeTabedPage._idHoraireFin + uidLigne}"
                                    pattern="[0-9]{2}:[0-9]{2}"
                                    placeholder="hh:mm"></td>
                    `;
                }
                uneligne += `
                        <td id="${cDialogActiviteeTabedPage._idHoraireDurrePresta + uidLigne}" class="${cDialogActiviteeTabedPage._idHoraireDurrePresta}"></td>
                        <td id="${cDialogActiviteeTabedPage._idCummulHoraireDurrePresta + uidLigne}"></td>
                        <td></td >
                    </tr>
                `;
                $(`#${cDialogActiviteeTabedPage._idTableBody}`).append(uneligne);
                dateCourante.setDate(dateCourante.getDate() + 1);
            }

            // -----------------------------------
            // update des activite from DB
            // -----------------------------------
            cDialogTools.addActiviteInSelect(`.${cDialogActiviteeTabedPage._idSelectActivitee}`);


            // -----------------------------------
            // Changement d'un debut
            // -----------------------------------
            let me: cDialogActiviteeTabedPage = this;
            $(`.${cDialogActiviteeTabedPage._idHoraireDebut}`).on("change", function (event: JQuery.ChangeEvent) {
                console.log(event.type);
                event.stopImmediatePropagation();
                event.preventDefault();


                let targetId: string = event.currentTarget.id;
                me.updateDeUneDureeDePrestation(targetId);

            });

            // -----------------------------------
            // Changement d'une fin
            // -----------------------------------
            $(`.${cDialogActiviteeTabedPage._idHoraireFin}`).on("change", function (event: JQuery.ChangeEvent) {
                console.log(event.type);
                event.stopImmediatePropagation();
                event.preventDefault();

                let targetId: string = event.currentTarget.id;
                me.updateDeUneDureeDePrestation(targetId);
            });

            // -------------------------------------
            // suppression d'une activite
            // -------------------------------------
            $(".RemoveActiviteInDB_ID_ACTIVITEE").on('click', function (event: JQuery.ClickEvent) {
                console.log(event.type);
                event.stopImmediatePropagation();
                event.preventDefault();

                let targetId: string = event.currentTarget.id;
                let activiteId: string = targetId.replace("RemoveActiviteInDB_ID_ACTIVITEE_", "");

                let activite : iActivite[] = me._ws.getActivitee(activiteId);
                UIkit.modal.confirm('Veux tu vraiment detruire cette activitee?' + cOutilsDivers.ActiviteeToString (activite[0])).then(function() {
                    console.log('Confirmed.')
                    me._ws.deleteActivite(activiteId);
                    me.refresh (me._idBulletinSalaire);
                }, function () {
                    console.log('Rejected.')
                });
                return true;
            });



            // update de la duree de travail mise en place par les activite de la DB
            this.updateDesDureeTotalDeTravail();
        }
    }


    // ---------------------------------------------------------
    // Parmis toutes les activite du mois, ne garder que celle du jour apour l'affichage
    // ---------------------------------------------------------
    private filtreActiviteDuJour(dateCourante: Date, allActiviteeDejaEnDB: iActivite[]): iActivite[] {
        let retour : iActivite[] = [];
        allActiviteeDejaEnDB.forEach(element => {
            if (element.debut.getDate() == dateCourante.getDate())
                retour.push (element);
        });
        return retour;
    }
    

    // ---------------------------------------------------------
    // Appeller par les call back de fin de modif des input de type time poru le calcul de la duree
    // Il faut aussi tenir comptre des activite lues depuis la DB
    //              dans la somme totale horaire
    // ---------------------------------------------------------
    private updateDeUneDureeDePrestation(eventTargetId: string): void {
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
        let ligne_annee: string = info[cDialogActiviteeTabedPage.uidLigneMapping.annee];
        let ligne_mois: string = info[cDialogActiviteeTabedPage.uidLigneMapping.mois];
        let ligne_jour: string = info[cDialogActiviteeTabedPage.uidLigneMapping.jour];
        let ligne_index: string = info[cDialogActiviteeTabedPage.uidLigneMapping.index];

        let duree: cDuration = new cDuration();
        duree.set(Number.parseInt(ligne_annee), Number.parseInt(ligne_mois), Number.parseInt(ligne_jour), heureDebut, heureFin);

        // mise a jour de la duree de cette activite
        let idCelluleToChange: string = this.encodeLigneUID(cDialogActiviteeTabedPage._idHoraireDurrePresta, info[cDialogActiviteeTabedPage.uidLigneMapping.uid]);
        let nbHeure: number = duree.asHour();
        let sNbHeure: string = cOutilsDivers.heureFloat2HeureString(nbHeure);
        $(`#${idCelluleToChange}`).text(sNbHeure);

        // Update Page duree
        this.updateDesDureeTotalDeTravail();
    }

    // -------------------------------------------------------------
    // Mise a jour de la colone duree totale de travail 
    // -------------------------------------------------------------
    private updateDesDureeTotalDeTravail() : void {
        // mise  ajour de la page (soome des activite)
        let NbHeureCumilee: number = 0.0;
        let x: number = 0.0;

        let me: cDialogActiviteeTabedPage = this;

        $(`.${cDialogActiviteeTabedPage._idHoraireDurrePresta}`).each(function () {
            let celluleValeur: string = $(this).text();

            if ((celluleValeur != null) && (celluleValeur.length > 0)) {
                x = cOutilsDivers.heureString2HeureFloat ($(this).text());
                NbHeureCumilee += x;
                console.log("x: " + x + "  " + $(this).prop('id'));
                console.log("cumul: " + NbHeureCumilee);
                if (x > 0) {
                    let local_aTargetId: string[] = me.decodeLigneUID($(this).prop('id'));
                    let local_uidLigne: string = local_aTargetId[cDialogActiviteeTabedPage.uidLigneMapping.uid];
                    let local_idCelluleToChange: string = me.encodeLigneUID(cDialogActiviteeTabedPage._idCummulHoraireDurrePresta, local_uidLigne);
                    console.log(local_idCelluleToChange);
                    let sNbHeureCumulee : string = cOutilsDivers.heureFloat2HeureString(NbHeureCumilee);
                    $(`#${local_idCelluleToChange}`).text(sNbHeureCumulee);
                }
            }
        });
    }


    // ---------------------------------------------------------
    // je decode une ligne pour avoir toute ses info a aprtir de l'UID de lal igne
    // ---------------------------------------------------------
    private decodeLigneUID(targetId: string): string[] {
        let retour: string[] = [];

        if (targetId == null)
            return retour;

        let localArray: string[] = targetId.split(cDialogActiviteeTabedPage._SplitTagSeparator);
        retour[cDialogActiviteeTabedPage.uidLigneMapping.racine] = localArray[0];

        if (localArray.length > 1) {
            retour[cDialogActiviteeTabedPage.uidLigneMapping.uid] = localArray[1];
            let localArray2: string[] = localArray[1].split('-');
            retour[cDialogActiviteeTabedPage.uidLigneMapping.annee] = localArray2[0];
            if (localArray2.length > 1)
                retour[cDialogActiviteeTabedPage.uidLigneMapping.mois] = localArray2[1];
            if (localArray2.length > 2)
                retour[cDialogActiviteeTabedPage.uidLigneMapping.jour] = localArray2[2];
            if (localArray2.length > 3)
                retour[cDialogActiviteeTabedPage.uidLigneMapping.index] = localArray2[3];
        }

        return retour;
    }

    // ---------------------------------------------------------
    // les uid de  lignes du tableau pour s'y retrouver
    // la je encode par un split
    // ---------------------------------------------------------
    private createLigneUID(annee: number, mois: number, jourDuMois: number, indice: number): string {
        return cDialogActiviteeTabedPage._SplitTagSeparator + annee + '-' + mois + '-' + jourDuMois + '-' + indice;
    }

    // ---------------------------------------------------------
    // idem encode et decode
    // ---------------------------------------------------------
    private encodeLigneUID(root: string, uid: string): string {
        return root + cDialogActiviteeTabedPage._SplitTagSeparator + uid;
    }
}