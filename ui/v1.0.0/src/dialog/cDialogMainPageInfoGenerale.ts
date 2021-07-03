import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';

import cDialog from './cDialog';
import cDialogTools from './cDialogTools';
import cDialogMainPageHoraire from './cDialogMainPageHoraire'

import cWS from '../WS/cWS';
import {iPersonne, iBulletinSalaire} from '../WS/iWSMessages';

import cOutilsDivers from '../tools/cOutilsDivers';
import { cControler } from '../cControler';

export default class cDialogMainPageInfoGenerale extends cDialog {
    private static _NomPrefixe: string = 'cDialogMainPageInfoGenerale';
    
    private _idSaisieInfoGeneraleDiv: string;
    private _idSaisieInfoHoraireDiv: string;
    private _idSelectOnMois: string;
    private _idSelectOnAnnee: string;
    private _idSelectOnPersonne: string;
    private _idValideInfo: string;
    private _idSelectGenre: string;
    private _idInputNom: string;
    private _idInputTarifHoraire: string;

    private dialogueEditionNouvelleFiche: cDialogMainPageHoraire | null = null;
    private static _toggleDivStatus: boolean;



    constructor() {
        super('cDialogMainPageInfoGenerale');
        this._idSelectOnMois = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectOnMois';
        this._idSelectOnAnnee = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectOnAnnee';
        this._idSelectOnPersonne = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectOnPersonne';
        this._idValideInfo = cDialogMainPageInfoGenerale._NomPrefixe + 'idValideInfo';
        this._idSelectGenre = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectGenre';
        this._idInputNom = cDialogMainPageInfoGenerale._NomPrefixe + 'idInputNom';
        this._idInputTarifHoraire = cDialogMainPageInfoGenerale._NomPrefixe + 'idInputTarifHoraire';
        this._idSaisieInfoGeneraleDiv = cDialogMainPageInfoGenerale._NomPrefixe + '_idSaisieInfoGeneraleDiv';
        this._idSaisieInfoHoraireDiv = cDialogMainPageInfoGenerale._NomPrefixe + '_idSaisieInfoHoraireDiv';
        this.dialogueEditionNouvelleFiche = new cDialogMainPageHoraire();

        cDialogMainPageInfoGenerale._toggleDivStatus = true;
    }

    public Draw(): string {
        let pageHTML : string = `
            <div id="${this._idSaisieInfoGeneraleDiv}">
                <form style="padding-left: 10px;">
                    <fieldset style="padding-left: 10px;">
                        <legend>Saisie des infos generales</legend>

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
                        <div class="uk-grid" style="margin-left: 0px;margin-top: 0px;">
                            <div class="uk-card uk-card-body uk-padding-small">
                                <label>Personne:
                                <select class="uk-select" id="${this._idSelectOnPersonne}" required style="">
                                    <option value="-" selected>-</option>
                                </select>
                                </label>
                            </div>
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
                        <div class="uk-grid" style="margin-left: 0px;margin-top: 0px;">
                            <div class="uk-card uk-card-body" style="padding-top: 0px; padding-right: 0px;">
                                <label> Tarif horaire (en &euro;):
                                    <input class="uk-input" type="number" id="${this._idInputTarifHoraire}" min="10" max="30" value="15">
                            </div>
                        </div>
                        <div class="uk-button-group" style="padding-left: 10px;">
                            <td><button class="uk-button uk-button-small" style="background-color: greenyellow;" id="${this._idValideInfo}" >Valide</button></td>
                            <td><button class="uk-button uk-button-small" style="background-color: ;"disabled>Modif</button></td>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div id="${this._idSaisieInfoHoraireDiv}">
                ${this.dialogueEditionNouvelleFiche.Draw()}
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
        // Zone d'edition
        //   1. On la cache
        //   2. Ajout des call back de la zone d'edition
        // ------------------------------------------
        this.toggleDiv();
        this.dialogueEditionNouvelleFiche.addCallBack();


        // ------------------------------------------
        // add call back du Vaide info
        // ------------------------------------------
        let me: cDialogMainPageInfoGenerale = this;
        $(`#${this._idValideInfo}`).on('click', function (event : JQuery.ClickEvent) {
            // je gere moi meme l'event
            event.preventDefault();
            event.stopImmediatePropagation();

            // ----
            // recup des infos
            // ----
            let mois : number = $(`#${me._idSelectOnMois}`).val() as number;
            let annee: number = $(`#${me._idSelectOnAnnee}`).val() as number;
            let personne: string = $(`#${me._idSelectOnPersonne} option:selected`).text();

            console.log("On OK: mois =" + mois);
            console.log("On OK: annee =" + annee);
            console.log("On OK: personne =" + personne);
            
            // ----
            // gestion de la personne
            // ----
            let idPersonne: number = 0;
            if (personne == '-') {
                let msgKO: string = 'KO  --> Pas de Personne definie';

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

                if (!bOK) {
                    UIkit.modal.alert(msgKO);
                    return;
                }
                else {
                    let ws : cWS = new cWS();
                    let lP: iPersonne[] = ws.getPersonne(genre, nom);
                    if ((lP == null) || (lP.length == 0)){ 
                        let p : iPersonne = ws.addNewPersonne(genre, nom);
                        idPersonne = p.id;

                        // unset des selection
                        $.each($(`#${me._idSelectGenre} option:selected`), function () {
                            $(this).prop('selected', false);
                        });
                        $(`#${me._idInputNom}`).val("");
                        
                        // ajout de la personne et selection
                        $(`#${me._idSelectOnPersonne}`).append($('<option>', { value: p.id, text: (p.genre + "  " + p.nom) }));
                        $.each($(`#${me._idSelectOnPersonne} option`), function () {
                            if ($(this).val() == p.id)
                                $(this).prop('selected', true);
                        });
                        UIkit.modal.confirm("OK personne ajoutée");
                    }
                    else {
                        UIkit.modal.alert('Cette personne existe deja: ' + genre + ' ' + nom);
                        return;
                    }
                }
            }
            else {
                idPersonne = $(`#${me._idSelectOnPersonne}`).val() as number;
            }


            // ----
            // gestion de la fiche existe t elle deja ?
            // ----
            let ws : cWS = new cWS();
            //let iMois : number = cOutilsDivers.MoisFromNomToInt(mois);
            let iMois : number = mois;
            let fiche: iBulletinSalaire[] = ws.getBulletinSalaire(idPersonne, iMois, annee);
            if (fiche.length > 0) {
                UIkit.modal.alert("KO --> cette fiche existe deja, la detruire d'abord");
                return;
            }

            let c : cControler = cControler.getInstance();
            c.mois = iMois
            c.annee = annee;
            c.idPersonne = idPersonne;
            c.tarifHoraire = ($(`#${me._idInputTarifHoraire}`).val() as unknown) as number;
            
            let lPer: iPersonne[] = ws.getPersonne(idPersonne);
            c.personne = lPer[0];


            // ---------------------------------------
            // OK 
            //   On affiche la div
            //   on la met a jour
            //   On cree kla fiche
            //   creer la fiche et on l'edite
            // ---------------------------------------
            me.switchDiv();
            me.dialogueEditionNouvelleFiche.refresh();
        });
        return;
    }

    private switchDiv(): void {
        cDialogMainPageInfoGenerale._toggleDivStatus = !cDialogMainPageInfoGenerale._toggleDivStatus;
        this.toggleDiv();
    }

    private toggleDiv(): void {
        if (cDialogMainPageInfoGenerale._toggleDivStatus) {
            $(`#${this._idSaisieInfoGeneraleDiv}`).show(400);
            $(`#${this._idSaisieInfoHoraireDiv}`).hide(400);
        }
        else {
            $(`#${this._idSaisieInfoGeneraleDiv}`).hide(400);
            $(`#${this._idSaisieInfoHoraireDiv}`).show(400);
        }
    }
}