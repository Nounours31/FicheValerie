import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialogAbstract from './cDialogAbstract';

import cOutilsDivers  from '../tools/cOutilsDivers';
import cDuration from '../tools/cDuration';
import cWS from '../WS/cWS';
import { iPdf, iPersonne } from '../WS/iWSMessages';
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
    private _idButtonPersonne: string;
    private _idButtonActivitee: string;


    constructor() {
        super('cDialogAjoutDBTabedPage');
        this._idSelectGenre = cDialogAjoutDBTabedPage._NomPrefixe + 'idSelectGenre';
        this._idInputNom = cDialogAjoutDBTabedPage._NomPrefixe + 'idInputNom';

        this._idInputActivitee = cDialogAjoutDBTabedPage._NomPrefixe + '_idInputActivitee';
        this._idSelectActivitee = cDialogAjoutDBTabedPage._NomPrefixe + '_idSelectActivitee';
        this._idSelectPersonne = cDialogAjoutDBTabedPage._NomPrefixe + '_idSelectPersonne';

        this._idButtonPersonne = cDialogAjoutDBTabedPage._NomPrefixe + '_idButtonPersonne';
        this._idButtonActivitee = cDialogAjoutDBTabedPage._NomPrefixe + '_idButtonActivitee';
    }

    public Draw(): string {

        let pageHTML : string = `
            <div id="cDialogAjoutDBTabedPage">
                <div class="uk-card uk-card-body uk-padding-small">
                    <label> Ajout d'une personne:
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
                            <div class="uk-card uk-card-body" style="padding-top: 0px; padding-right: 0px;">
                                <label> Existantes: <select class="uk-select" id="${this._idSelectPersonne}"></select>
                                </label>
                            </div>
                        </div>
                        <td><button style="background-color: yellowgreen;" class="uk-button uk-button-default uk-button-small" id="${this._idButtonPersonne}">Ajout Personne</button>
                    </label>
                </div>

                <div class="uk-card uk-card-body uk-padding-small">
                    <label> Ajout d'activit&eacute;e:
                        <div class="uk-grid">
                            <div class="uk-card uk-card-body" style="padding-top: 0px; padding-right: 0px;">
                                <label> Nom: <input class="uk-input" type="text" placeholder="Nom de l'activit&eacute;e" id="${this._idInputActivitee}">
                                </label>
                            </div>
                            <div class="uk-card uk-card-body" style="padding-top: 0px; padding-right: 0px;">
                                </label> Existantes: <select class="uk-select" id="${this._idSelectActivitee}"></select>
                                </label>
                            </div>
                        </div>
                        <td><button style="background-color: yellowgreen;" class="uk-button uk-button-default uk-button-small" id="${this._idButtonActivitee}">Ajout Activite</button>
                    </label>
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
        cDialogTools.addPersonneInSelect(`#${this._idSelectPersonne}`, bTiret);

        // -----------------------------------------
        // Affichage des activitee de la db
        // -----------------------------------------
        cDialogTools.addActiviteInSelect(`#${this._idSelectActivitee}`);

        // -----------------------------------------
        // Ajout en  db
        // -----------------------------------------
        let me: cDialogAjoutDBTabedPage = this;
        $(`#${this._idButtonPersonne}`).on('click', function (event: JQuery.ClickEvent) {
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
            cDialogTools.addPersonneInSelect(`#${me._idSelectPersonne}`, bTiret);

            return true;
        });

        $(`#${this._idButtonActivitee}`).on('click', function (event: JQuery.ClickEvent) {
            event.preventDefault();
            event.stopImmediatePropagation();

            let activitee: string = $(`#${me._idInputActivitee}`).val() as string;

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
            let allActivitees: string[] = ws.getAllPossibleActivitees();
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
            cDialogTools.addActiviteInSelect(`#${me._idSelectActivitee}`); 
            return true;
        });
    }

    private isActiviteExisteDansLaListe(activitee: string, allActivitees: string[]): boolean {
        activitee = activitee.toLowerCase();
        activitee = cOutilsDivers.replaceAll(activitee, " ", "");
        let retour: boolean = false;
        allActivitees.forEach(a => {
            a = a.toLowerCase();
            a = cOutilsDivers.replaceAll(a, " ", "");
            if (a == activitee) {
                retour =  true;
            }
        });
        return retour;
    }
}