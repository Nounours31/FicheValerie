import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import cWS from '../WS/cWS';
import {iPersonne, iBulletinSalaire} from '../WS/iWSMessages';

import cOutilsDivers from '../tools/cOutilsDivers';
import { cControler } from '../cControler';
import cDialogTools from './cDialogTools';

export default class cDialogMainPageInfoEdition extends cDialog {
    private static _NomPrefixe: string = 'cDialogMainPageInfoEdition';
    
    private _idSelectOnMois: string;
    private _idSelectOnAnnee: string;
    private _idSelectOnPersonne: string;
    private _idValideInfo: string;
    private _idSelectGenre: string;
    private _idInputNom: string;
    private _idInputTarifHoraire: string;



    constructor() {
        super('cDialogMainPageInfoEdition');
        this._idSelectOnMois = cDialogMainPageInfoEdition._NomPrefixe + 'idSelectOnMois';
        this._idSelectOnAnnee = cDialogMainPageInfoEdition._NomPrefixe + 'idSelectOnAnnee';
        this._idSelectOnPersonne = cDialogMainPageInfoEdition._NomPrefixe + 'idSelectOnPersonne';
        this._idValideInfo = cDialogMainPageInfoEdition._NomPrefixe + 'idValideInfo';
        this._idSelectGenre = cDialogMainPageInfoEdition._NomPrefixe + 'idSelectGenre';
        this._idInputNom = cDialogMainPageInfoEdition._NomPrefixe + 'idInputNom';
        this._idInputTarifHoraire = cDialogMainPageInfoEdition._NomPrefixe + 'idInputTarifHoraire';
    }

    public Draw(): string {
        let pageHTML : string = `
            <div id="MainPageInfoGenerales">
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
                                                <select class="uk-select" id="${this._idInputNom}">
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
                            <td><button class="uk-button uk-button-small" style="background-color: ;"disabled>Modif</button></td>
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
                                    <th>mois/Annee</th>
                                    <th>version</th>
                                    <th>lien vers les fiches</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Mme toto</td>
                                    <td>02/2021</td>
                                    <td>Nbversion</td>
                                    <td><button class="uk-button uk-button-default uk-button-small">voir la derniere</button></td>
                                    <td><button class="uk-button uk-button-default uk-button-small">editer la derniere</button></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>Mme titi</td>
                                    <td>02/2021</td>
                                    <td>Nbversion</td>
                                    <td><button class="uk-button uk-button-default uk-button-small">voir la derniere</button></td>
                                    <td><button class="uk-button uk-button-default uk-button-small">editer la derniere</button></td>
                                </tr>
                            </tbody>
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
        cDialogTools.addMoisInSelect(jqIdSelectMois);


        // ------------------------------------------
        // add des annees dans le selected des annees
        // ------------------------------------------
        let jqIdSelectAnnee: string = `#${this._idSelectOnAnnee}`;
        cDialogTools.addAnneeInSelect(jqIdSelectAnnee);


        // ------------------------------------------
        // add des personnes dans le selecteur
        // ------------------------------------------
        let jqIdSelectPersonne: string = `#${this._idSelectOnPersonne}`;
        cDialogTools.addAnneeInSelect(jqIdSelectPersonne);

        return;
    }
}