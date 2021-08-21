import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialogAbstract from './cDialogAbstract';

import cOutilsDivers  from '../tools/cOutilsDivers';
import cWS from '../WS/cWS';
import { iActivite, iActiviteEnum, iPersonne } from '../WS/iWSMessages';
import cDialogTools from '../tools/cDialogTools';

interface iUIDLIGNE {
    racine: number,
    annee: number,
    mois: number,
    jour: number,
    index: number,
    uid: number
}

export default class cDialogAjoutDBTabedPage extends cDialogAbstract {
    private static _NomPrefixe: string = 'cDialogAjoutDBTabedPage';

    private _idSelectGenre: string;
    private _idInputNom: string;
    private _idInputActivitee: string;
    private _idSelectActivitee: string;
    private _idSelectPersonne: string;
    private _idButtonPersonneAdd: string;
    private _idButtonPersonneMoins: string;
    private _idButtonActiviteeAdd: string;
    private _idButtonActiviteeMoins: string;


    constructor() {
        super('cDialogAjoutDBTabedPage');
        this._idSelectGenre = cDialogAjoutDBTabedPage._NomPrefixe + 'idSelectGenre';
        this._idInputNom = cDialogAjoutDBTabedPage._NomPrefixe + 'idInputNom';

        this._idInputActivitee = cDialogAjoutDBTabedPage._NomPrefixe + '_idInputActivitee';
        this._idSelectActivitee = cDialogAjoutDBTabedPage._NomPrefixe + '_idSelectActivitee';
        this._idSelectPersonne = cDialogAjoutDBTabedPage._NomPrefixe + '_idSelectPersonne';

        this._idButtonPersonneAdd = cDialogAjoutDBTabedPage._NomPrefixe + '_idButtonPersonneAdd';
        this._idButtonPersonneMoins = cDialogAjoutDBTabedPage._NomPrefixe + '_idButtonPersonneMoins';
        this._idButtonActiviteeAdd = cDialogAjoutDBTabedPage._NomPrefixe + '_idButtonActiviteeAdd';
        this._idButtonActiviteeMoins = cDialogAjoutDBTabedPage._NomPrefixe + '_idButtonActiviteeMoins';
    }

    public Draw(): string {

        let pageHTML : string = `
            <div id="cDialogAjoutDBTabedPage">
                <div class="uk-card uk-card-body uk-padding-small">
                    <table border="1" class="uk-table uk-table-striped">
                        <tr><th colspan="3" style="text-align: center; background-color: black; color: white;">Gestion des personnes</th></tr>
                        <tr><th style="text-align: center;">Existantes xx</th>     <th style="text-align: center;">Ajout</th>      <th style="text-align: center;">retrait</th></tr>
                        <tr>
                            <td rowspan="2">
                                <select class="uk-select ${this._idSelectPersonne}"></select>
                            </td>
                            <td>
                                <div class="uk-grid">
                                    <div class="uk-card uk-card-body" style="padding-top: 0px; padding-bottom: 0px; padding-left: 50px;">
                                        <label>Genre:
                                            <select class="uk-select" id="${this._idSelectGenre}">
                                                <option value="-" selected>-</option>
                                                <option value="Madame">Madame</option>
                                                <option value="Monsieur">Monsieur</option>
                                            </select>
                                        </label>
                                    </div>
                                    <div class="uk-card uk-card-body" style="padding-top: 0px; padding-right: 0px;">
                                        <label> Nom: <input class="uk-input" type="text" placeholder="Nom:" id="${this._idInputNom}"/>
                                        </label>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <select class="uk-select ${this._idSelectPersonne}" id="${this._idSelectPersonne}"></select>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center;">
                                <button style="background-color: yellowgreen;" class="uk-button uk-button-default uk-button-small" id="${this._idButtonPersonneAdd}">Ajout</button>
                            </td>
                            <td style="text-align: center;">
                                <button style="background-color: #fd9669;" class="uk-button uk-button-default uk-button-small" id="${this._idButtonPersonneMoins}">Retrait</button>
                            </td>
                        </tr>
                    </table
                </div>

                <div class="uk-card uk-card-body uk-padding-small">
                    <table border="1" class="uk-table uk-table-striped">
                        <tr><th colspan="3" style="text-align: center; background-color: black; color: white;">Gestion des activitées</th></tr>
                        <tr><th style="text-align: center;">Existantes xx</th>     <th style="text-align: center;">Ajout</th>      <th style="text-align: center;">retrait</th></tr>
                        <tr>
                            <td rowspan="2">
                                Existantes: <select class="uk-select ${this._idSelectActivitee}"></select>
                            </td>

                            <td >
                                <label> Nom: <input class="uk-input" type="text" placeholder="Nom de l'activit&eacute;e" id="${this._idInputActivitee}">
                                </label>
                            </td>

                            <td>
                                <select class="uk-select ${this._idSelectActivitee}" id="${this._idSelectActivitee}"></select>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center;">
                                <button style="background-color: yellowgreen;" class="uk-button uk-button-default uk-button-small" id="${this._idButtonActiviteeAdd}">Ajout</button>
                            </td>
                            <td style="text-align: center;">
                                <button style="background-color: #fd9669;" class="uk-button uk-button-default uk-button-small" id="${this._idButtonActiviteeMoins}">Retrait</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        `;
        return pageHTML;
    }

    public addCallBack(): void {
        // -----------------------------------------
        // Affichage des personnes de la db
        // -----------------------------------------
        let bTiret : boolean = true;
        cDialogTools.addPersonneInSelect(`.${this._idSelectPersonne}`, bTiret);

        // -----------------------------------------
        // Affichage des activitee de la db
        // -----------------------------------------
        cDialogTools.addActiviteInSelect(`.${this._idSelectActivitee}`);

        // -----------------------------------------
        // Ajout en  db
        // -----------------------------------------
        let me: cDialogAjoutDBTabedPage = this;
        $(`#${this._idButtonPersonneAdd}`).on('click', function (event: JQuery.ClickEvent) {
            event.preventDefault();
            event.stopImmediatePropagation();

            let genre: string = $(`#${me._idSelectGenre}`).val() as string;
            let nom: string = $(`#${me._idInputNom}`).val() as string;
            let bOK: boolean = true;
            let msgKO : string = "Erreur dans la saisie: "
            if (genre == '-') {
                msgKO += ' -- Pas de genre de defini';
                bOK = false;
            }
            if  (nom == '') {
                msgKO += ' -- Pas de nom de defini';
                bOK = false;
            }
            if (!bOK) {
                UIkit.modal.alert(msgKO);
                return false;
            }

            // check if exist
            let ws: cWS = new cWS();
            let allPersonne: iPersonne[] = ws.getPersonne(genre, nom);
            if ((allPersonne != null) && (allPersonne.length > 0)) {
                UIkit.modal.alert("Cette personne existe deja en base");
                return false;
            }

            let newPers : iPersonne = ws.addNewPersonne(genre, nom);
            if ((newPers == undefined) || (newPers == null) || (newPers.id < 1)) {
                UIkit.modal.alert("impossible de creer cette personne voir pap's");
                return false;
            }
            
            UIkit.modal.alert("OK creation de : " + genre + " " + nom + "[" + newPers.id + "]");
            let bTiret : boolean = true;
            cDialogTools.addPersonneInSelect(`.${me._idSelectPersonne}`, bTiret);

            return true;
        });

        $(`#${this._idButtonActiviteeAdd}`).on('click', function (event: JQuery.ClickEvent) {
            event.preventDefault();
            event.stopImmediatePropagation();

            let activitee: string = $(`#${me._idInputActivitee}`).val() as string;
            $(`#${me._idInputActivitee}`).val("");

            let bOK: boolean = true;
            let msgKO : string = "Erreur dans la saisie: "
            if  (activitee == '') {
                msgKO += ' -- Pas de nom d\'activitee de defini';
                bOK = false;
            }
            if (!bOK) {
                UIkit.modal.alert(msgKO);
                return false;
            }

            // check if exist
            let ws: cWS = new cWS();
            let allActivitees: iActiviteEnum[] = ws.getAllPossibleActivitees();
            if ((allActivitees != null) && (allActivitees.length > 0)) {
                if (me.isActiviteExisteDansLaListe(activitee, allActivitees)) {
                    UIkit.modal.alert("Cette activitee existe deja en base");
                    return false;
                }
            }
            
            ws.addAPossibleActivitee(activitee);
            allActivitees = ws.getAllPossibleActivitees();
            if ((allActivitees == null) || (!me.isActiviteExisteDansLaListe(activitee, allActivitees))) {
                UIkit.modal.alert("impossible de creer cette activitee voir pap's");
                return false;
            }
            
            UIkit.modal.alert("OK creation de : " + activitee );
            cDialogTools.addActiviteInSelect(`.${me._idSelectActivitee}`); 
            return true;
        });



        // -----------------------------------------
        // REMOVE en  db
        // -----------------------------------------
        $(`#${this._idButtonPersonneMoins}`).on('click', function (event: JQuery.ClickEvent) {
            event.preventDefault();
            event.stopImmediatePropagation();

            // recup du nom
            let personneId: string = $(`#${me._idSelectPersonne}`).val() as string;

            // check if exist
            let ws: cWS = new cWS();
            let rc : boolean =  ws.deletePersone(personneId);

            if (rc)
                UIkit.modal.alert("OK delete");
            let bTiret: boolean = true;
            cDialogTools.addPersonneInSelect(`.${me._idSelectPersonne}`, bTiret);

            return true;
        });

        $(`#${this._idButtonActiviteeMoins}`).on('click', function (event: JQuery.ClickEvent) {
            event.preventDefault();
            event.stopImmediatePropagation();

            // recup du nom
            let sActiviteId: string = $(`#${me._idSelectActivitee}`).val() as string;

            // check if exist
            let ws: cWS = new cWS();
            let rc: boolean = ws.deletePossibleActivite(sActiviteId);

            if (rc)
                UIkit.modal.alert("OK delete");
            let bTiret: boolean = true;
            cDialogTools.addActiviteInSelect(`.${me._idSelectActivitee}`);

            return true;
        });
    }

    private isActiviteExisteDansLaListe(activitee: string, allActivitees: iActiviteEnum[]): boolean {
        activitee = activitee.toLowerCase();
        activitee = cOutilsDivers.replaceAll(activitee, " ", "");
        let retour: boolean = false;
        allActivitees.forEach(a => {
            let aName = a.nom.toLowerCase();
            aName = cOutilsDivers.replaceAll(aName, " ", "");
            if (aName == activitee) {
                retour =  true;
            }
        });
        return retour;
    }
}