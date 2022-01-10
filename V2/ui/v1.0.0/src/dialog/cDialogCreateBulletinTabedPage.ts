import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';

import cDialogAbstract from './cDialogAbstract';
import cDialogTools from '../tools/cDialogTools';
import cDialogActiviteeTabedPage from './cDialogActiviteeTabedPage'

import cWS from '../WS/cWS';
import {iPersonne, iBulletinSalaire} from '../WS/iWSMessages';

import cDialogTabPage from './cDialogTabPage';

export default class cDialogCreateBulletinTabedPage extends cDialogAbstract {
    private static _NomPrefixe: string = 'cDialogCreateBulletinTabedPage';
    
    private _idSaisieInfoGeneraleDiv: string;
    private _idSelectOnMois: string;
    private _idSelectOnAnnee: string;
    private _idSelectOnPersonne: string;
    private _idValideInfo: string;
    private _idInputTarifHoraire: string;

    private static _toggleDivStatus: boolean;

    private _dialogueEditionNouvelleFiche: cDialogActiviteeTabedPage | null = null;
    private _dialogTabPage: cDialogTabPage | null = null;


    constructor(activiteePage : cDialogActiviteeTabedPage, tabPage : cDialogTabPage) {
        super('cDialogCreateBulletinTabedPage');
        this._idSelectOnMois = cDialogCreateBulletinTabedPage._NomPrefixe + 'idSelectOnMois';
        this._idSelectOnAnnee = cDialogCreateBulletinTabedPage._NomPrefixe + 'idSelectOnAnnee';
        this._idSelectOnPersonne = cDialogCreateBulletinTabedPage._NomPrefixe + 'idSelectOnPersonne';
        this._idValideInfo = cDialogCreateBulletinTabedPage._NomPrefixe + 'idValideInfo';
        this._idInputTarifHoraire = cDialogCreateBulletinTabedPage._NomPrefixe + 'idInputTarifHoraire';
        this._idSaisieInfoGeneraleDiv = cDialogCreateBulletinTabedPage._NomPrefixe + '_idSaisieInfoGeneraleDiv';
        
        this._dialogueEditionNouvelleFiche = activiteePage;
        this._dialogTabPage = tabPage;

        cDialogCreateBulletinTabedPage._toggleDivStatus = true;
    }

    public Draw(): string {
        let pageHTML : string = `
            <div id="${this._idSaisieInfoGeneraleDiv}">
                <form style="padding-left: 10px;">
                    <fieldset style="padding-left: 10px;">
                        <legend>Saisie des infos generales</legend>

                        <!-- 
                            Choix mois annee 
                        -->
                        <div class="uk-grid" style="margin-left: 0px;">
                            <div class="uk-card uk-card-body uk-padding-small">
                                <label>Mois
                                <select class="uk-select" id="${this._idSelectOnMois}" required>
                                </select>
                                </label>
                            </div>
                            <div class="uk-card uk-card-body uk-padding-small">
                                <label>Annee
                                <select class="uk-select" id="${this._idSelectOnAnnee}" required>
                                </select>
                                </label>
                            </div>
                        </div>

                        <!--
                            Choix de la personne - lecture en DB
                        -->
                        <div class="uk-grid" style="margin-left: 0px;margin-top: 0px;">
                            <div class="uk-card uk-card-body uk-padding-small">
                                <label>Personne:
                                <select class="uk-select" id="${this._idSelectOnPersonne}" required>
                                    <option value="-" selected>-</option>
                                </select>
                                </label>
                            </div>
                        </div>

                        <!--
                            Tarif horaire par defaut
                        -->
                        <div class="uk-grid" style="margin-left: 0px;margin-top: 0px;">
                            <div class="uk-card uk-card-body  uk-padding-small" >
                                <label> Tarif horaire (en &euro;):
                                    <input class="uk-input" type="number" id="${this._idInputTarifHoraire}" min="10" max="30" value="15">
                            </div>
                        </div>

                        <!--
                            OK
                        -->
                        <div class="uk-button-group" style="padding-left: 10px;">
                            <td><button class="uk-button uk-button-small" style="background-color: greenyellow;" id="${this._idValideInfo}" >Valide</button></td>
                        </div>
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
        cDialogTools.addPersonneInSelect(jqIdSelectPersonne);

        // ------------------------------------------
        // add call back du Vaide info
        // ------------------------------------------
        let me: cDialogCreateBulletinTabedPage = this;
        $(`#${this._idValideInfo}`).on('click', function (event : JQuery.ClickEvent) {
            // je gere moi meme l'event
            event.preventDefault();
            event.stopImmediatePropagation();

            // ----
            // recup des infos
            // ----
            let mois : number = Number.parseInt($(`#${me._idSelectOnMois}`).val() as string);
            let annee: number = Number.parseInt($(`#${me._idSelectOnAnnee}`).val() as string);
            let personne: string = $(`#${me._idSelectOnPersonne} option:selected`).text();

            console.log("On OK: mois =" + mois);
            console.log("On OK: annee =" + annee);
            console.log("On OK: personne =" + personne);
            
            // ----
            // gestion de la personne
            // ----
            let ws: cWS = new cWS();
            let idPersonne: number = 0;
            let unePersonne: iPersonne[] = ws.getPersonne(idPersonne);
            if (personne == '-') {
                let msgKO: string = 'KO  --> Pas de Personne definie';
                UIkit.modal.alert(msgKO);
                return;
            }
            else {
                idPersonne = Number.parseInt ($(`#${me._idSelectOnPersonne}`).val() as string);
                unePersonne = ws.getPersonne(idPersonne);
                if (unePersonne.length != 1) {
                    let msgKO: string = 'KO  --> Pas de Personne definie en DB voir Pap\'s';
                    UIkit.modal.alert(msgKO);
                    return;
                }
            }

            // ----
            // Tarif horaire
            // ----
            let tarifHoraire : number = Number.parseFloat($(`#${me._idInputTarifHoraire}`).val() as string);

            // ----
            // gestion de la fiche existe t elle deja ?
            // ----
            let iMois : number = mois;
            let idBulletinSalaire: number = -1;
            let fiche: iBulletinSalaire[] = ws.getBulletinSalaire(idPersonne, iMois, annee);
            if (fiche.length > 0) {
                UIkit.modal.alert("KO --> cette fiche existe deja, l'editer ou la supprimer");
                return;
            }
            else {
                idBulletinSalaire = ws.createBulletinSalaire(iMois, annee, unePersonne[0], tarifHoraire);
                if (idBulletinSalaire > 0) {
                    UIkit.modal.alert("OK creation faite [pour paps - id:" + idBulletinSalaire+ "]")
                }
                else {
                    UIkit.modal.alert("KO --> Bulletin non cree - voir pap's");
                    return;
                }
            }

            // ---------------------------------------
            // OK 
            //   On affiche la div
            //   on la met a jour
            //   On cree kla fiche
            //   creer la fiche et on l'edite
            // ---------------------------------------
            me._dialogTabPage.getSwitcherElement().show(cDialogTabPage.IndexEditionTab);
            me._dialogueEditionNouvelleFiche.refresh(idBulletinSalaire);
        });
        return;
    }
}