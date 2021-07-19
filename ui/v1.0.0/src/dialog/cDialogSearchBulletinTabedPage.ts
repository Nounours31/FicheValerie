import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialogAbstract from './cDialogAbstract';

import cWS from '../WS/cWS';
import {iPersonne, iBulletinSalaire, iPdf} from '../WS/iWSMessages';

import cOutilsDivers from '../tools/cOutilsDivers';
import cDialogTools from '../tools/cDialogTools';
import cDialogTabPage from './cDialogTabPage';
import cDialogActiviteeTabedPage from './cDialogActiviteeTabedPage';

export default class cDialogSearchBulletinTabedPage extends cDialogAbstract {
    private static _NomPrefixe: string = 'cDialogSearchBulletinTabedPage';
    
    private _idSelectOnMois: string;
    private _idSelectOnAnnee: string;
    private _idSelectOnPersonne: string;
    private _idValideInfo: string;
    private _idSelectGenre: string;
    private _idResultat: string;
    private _idInputTarifHoraire: string;


    // creation en DB du bulletin de salaire
    private _ws: cWS = null;
    private _dialogTabPage: cDialogTabPage = null;
    private _dialogActiviteTabedPage: cDialogActiviteeTabedPage = null;

    constructor(dialogActiviteTabedPage: cDialogActiviteeTabedPage, dialogTabPage : cDialogTabPage) {
        super('cDialogSearchBulletinTabedPage');
        this._idSelectOnMois = cDialogSearchBulletinTabedPage._NomPrefixe + 'idSelectOnMois';
        this._idSelectOnAnnee = cDialogSearchBulletinTabedPage._NomPrefixe + 'idSelectOnAnnee';
        this._idSelectOnPersonne = cDialogSearchBulletinTabedPage._NomPrefixe + 'idSelectOnPersonne';
        this._idValideInfo = cDialogSearchBulletinTabedPage._NomPrefixe + 'idValideInfo';
        this._idSelectGenre = cDialogSearchBulletinTabedPage._NomPrefixe + 'idSelectGenre';
        this._idResultat = cDialogSearchBulletinTabedPage._NomPrefixe + '_idResultat';
        this._idInputTarifHoraire = cDialogSearchBulletinTabedPage._NomPrefixe + 'idInputTarifHoraire';
        this._ws = new cWS();
        this._dialogTabPage = dialogTabPage;
        this._dialogActiviteTabedPage = dialogActiviteTabedPage;
    }

    public Draw(): string {
        let pageHTML : string = `
            <div id="cDialogSearchBulletinTabedPage">
                <form style="padding-left: 10px;">
                    <fieldset style="padding-left: 10px;">
                        <legend>Edition d'une fiche</legend>

                            <div class="uk-card uk-card-body uk-padding-small">
                                <label>Filtre sur la personne:
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
                                            <label> Nom:
                                                <select class="uk-select" id="${this._idSelectOnPersonne}">
                                                    <option value="-" selected>-</option>
                                                </select>
                                        </div>
                                    </div>
                                </label>
                            </div>
                        <div class="uk-grid" style="margin-left: 0px;">
                            <div class="uk-card uk-card-body uk-padding-small">
                                <label>filtre mois:
                                <select class="uk-select" id="${this._idSelectOnMois}" required>
                                </select>
                                </label>
                            </div>
                            <div class="uk-card uk-card-body uk-padding-small">
                                <label>filtre année:
                                <select class="uk-select" id="${this._idSelectOnAnnee}" required>
                                </select>
                                </label>
                            </div>
                        </div>
                        <div class="uk-button-group" style="padding-left: 10px;">
                            <td><button class="uk-button uk-button-small" style="background-color: greenyellow;" id="${this._idValideInfo}" >Valide</button></td>
                        </div>
                    </fieldset>
            </form>
                <form style="padding-left: 10px;">
                    <fieldset style="padding-left: 10px;">
                        <legend>Trouvees:</legend>

                        <table class="uk-table uk-table-striped">
                            <thead>
                                <tr>
                                    <th>id</th>
                                    <th>Personne</th>
                                    <th>Date</th>
                                    <th>NbPdfDispo</th>
                                    <th>Voir dernier pdf</th>
                                    <th>lien vers les fiches</th>
                                </tr>
                            </thead>
                            <tbody id="${this._idResultat}"></tbody>
                        </table>
                    </fieldset>
            </form>
        </div>
        `;
        return pageHTML;
    }

    public addCallBack(): void {

        // ------------------------------------------
        // add des mois dans le selected de mois
        // ------------------------------------------
        let jqIdSelectMois: string = `#${this._idSelectOnMois}`;
        cDialogTools.addMoisInSelect(jqIdSelectMois, true);


        // ------------------------------------------
        // add des annees dans le selected des annees
        // ------------------------------------------
        let jqIdSelectAnnee: string = `#${this._idSelectOnAnnee}`;
        cDialogTools.addAnneeInSelect(jqIdSelectAnnee, true);


        // ------------------------------------------
        // add des personnes dans le selecteur
        // ------------------------------------------
        let jqIdSelectPersonne: string = `#${this._idSelectOnPersonne}`;
        cDialogTools.addPersonneInSelect(jqIdSelectPersonne);

        let me : cDialogSearchBulletinTabedPage = this;
        $(`#${this._idValideInfo}`).on ('click', function (event) {
            event.preventDefault();
            event.stopPropagation();

            // get des info
            let genre: string = $(`#${me._idSelectGenre}`).val() as string;
            let sIdPersonne: string = $(`#${me._idSelectOnPersonne}`).val() as string;
            let idPersonne : number = 0;
            if (sIdPersonne != "-")
                idPersonne = Number.parseInt(sIdPersonne);

            let mois: string = $(`#${me._idSelectOnMois}`).val() as string;
            let annee: string = $(`#${me._idSelectOnAnnee}`).val() as string;
            let iMois : number = -1;
            if (mois != "-")
                iMois = Number.parseInt(mois);

            let iAnnee: number = -1;
            if (annee != "-")
                iAnnee = Number.parseInt(annee);

            let bHasPrevious : boolean = false;
            let sql: string = "select * from bulletinsalaire where (";
            if ((genre.length > 2) && (idPersonne < 1)) {
                sql += `(nom like '%${genre}%')`;
                bHasPrevious = true;
            }

            if ((idPersonne > 0)) {
                if (bHasPrevious)
                    sql += " and ";
                sql += `(idPersonne = ${idPersonne})`;
                bHasPrevious = true;
            }

            if ((iMois >= 0)) {
                if (bHasPrevious)
                    sql += " and ";
                sql += `(mois = ${iMois})`;
                bHasPrevious = true;
            }

            if ((iAnnee > 0)) {
                if (bHasPrevious)
                    sql += " and ";
                sql += `(annee = ${iAnnee})`;
                bHasPrevious = true;
            }

            if (!bHasPrevious)
                sql += "1";

            sql += ") order by idPersonne asc, annee asc, mois asc";

            console.log ("SQL: " + sql);
            
            let AllFicheSalaire : iBulletinSalaire[] = me._ws.getBulletinSalaireFromSQL (sql);
            $(`#${me._idResultat}`).empty();

            
            AllFicheSalaire.forEach(element => {
                let unePersonne: iPersonne[] = me._ws.getPersonne(element.idPersonne);
                let pdfIds: iPdf[] = me._ws.getAllPDFFromBulletinId(element.id);
                let nbPDF: number = 0;
                let idPDF: number = 0;
                if (pdfIds != null) {
                    nbPDF = pdfIds.length;
                    idPDF = pdfIds[nbPDF - 1].id;
                }
                let uneLigne = `
                    <tr id="TR_FROM_SEARCH_${element.id}">
                        <td>${element.id}</td>
                        <td>${unePersonne[0].genre} ${unePersonne[0].nom} [${element.idPersonne}]</td>
                        <td>${cOutilsDivers.MoisFromIntToNom(element.mois)} ${element.annee}</td>
                        <td>${nbPDF}</td>
                        <td><button class="uk-button uk-button-default uk-button-small PDF_FROM_SEARCH" id="BUTTON_PDF_FROM_SEARCH_${idPDF}">voir dernier PDF</button></td>
                        <td><button class="uk-button uk-button-default uk-button-small EDITER_FROM_SEARCH" id="BUTTON_EDIT_FROM_SEARCH_${element.id}">editer</button></td>
                    </tr>
                `;
                $(`#${me._idResultat}`).append(uneLigne);
            });

            $(".PDF_FROM_SEARCH").on("click", function (event) { 
                event.preventDefault();
                event.stopPropagation();
                console.log (this.getAttribute("id"));

                let sIdPDF: string = this.getAttribute("id").replace("BUTTON_PDF_FROM_SEARCH_", "");
                let idPdf : number = Number.parseInt(sIdPDF);
                cDialogTools.DownloadPdf (idPdf);
            });

            $(".EDITER_FROM_SEARCH").on("click", function (event) { 
                event.preventDefault();
                event.stopPropagation();
                console.log(this.getAttribute("id"));
                let sIdBulletin: string = this.getAttribute("id").replace("BUTTON_EDIT_FROM_SEARCH_", "");
                let idBulletin : number = Number.parseInt(sIdBulletin);

                me._dialogTabPage.getSwitcherElement().show(cDialogTabPage.IndexEditionTab);
                me._dialogActiviteTabedPage.refresh(idBulletin);
            });
            return false;
        });
        return;
    }
}