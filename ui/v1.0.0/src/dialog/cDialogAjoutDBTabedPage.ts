import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialogAbstract from './cDialogAbstract';

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

export default class cDialogAjoutDBTabedPage extends cDialogAbstract {
    private static _NomPrefixe: string = 'cDialogAjoutDBTabedPage';

    private _idSelectGenre: string;
    private _idInputNom: string;


    constructor() {
        super('cDialogAjoutDBTabedPage');
        this._idSelectGenre = cDialogAjoutDBTabedPage._NomPrefixe + 'idSelectGenre';
        this._idInputNom = cDialogAjoutDBTabedPage._NomPrefixe + 'idInputNom';

    }

    public Draw(): string {

        let pageHTML : string = `
            <div id="cDialogAjoutDBTabedPage">
                                        <div class="uk-card uk-card-body uk-padding-small">
                                <label> Ajout si la personne n'existe pas:
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
                                                <input class="uk-input" type="text" placeholder="Nom de la personne" id="${this._idInputNom}">
                                        </div>
                                    </div>
                                </label>
                            </div>

            </div>
        `;
        return pageHTML;
    }
/*
                // regarder les nom/genre
                let genre : string = $(`#${me._idSelectGenre}`).val() as string;
                let nom : string = $(`#${me._idInputNom}`).val() as string;

                let bOK: boolean = true;
                if (genre == '-') {
                    msgKO += ' -- Pas de genre de defini';
                    bOK = false;
                }
                if  (nom == '') {
                    msgKO += ' -- Pas de nom de defini';
                    bOK = false;
                }

*/
    public addCallBack () : void {}
}