import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import cWS from '../WS/cWS';
import {iPersonne, iBulletinSalaire} from '../WS/iWSMessages';

import cOutilsDivers from '../tools/cOutilsDivers';

export default class cDialogMainPageInfoGenerale extends cDialog {
    private _idSelectOnMois: string;
    private _idSelectOnAnnee: string;
    private _idSelectOnPersonne: string;
    private _idValideInfo: string;
    private _idSelectGenre: string;
    private _idInputNom: string;



    constructor() {
        super('cDialogMainPageInfoGenerale');
        this._idSelectOnMois = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectOnMois';
        this._idSelectOnAnnee = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectOnAnnee';
        this._idSelectOnPersonne = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectOnPersonne';
        this._idValideInfo = cDialogMainPageInfoGenerale._NomPrefixe + 'idValideInfo';
        this._idSelectGenre = cDialogMainPageInfoGenerale._NomPrefixe + 'idSelectGenre';
        this._idInputNom = cDialogMainPageInfoGenerale._NomPrefixe + 'idInputNom';
    }

    public Draw(): HTMLDivElement {
        let pageHTML : string = `
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
                    <div class="uk-button-group" style="padding-left: 10px;">
                        <td><button class="uk-button uk-button-small" style="background-color: greenyellow;" id="${this._idValideInfo}" >Valide</button></td>
                        <td><button class="uk-button uk-button-small" style="background-color: ;"disabled>Modif</button></td>
                    </div>
                </fieldset>
           </form>
        `;
        let x : HTMLDivElement = document.createElement('div');
        x.innerHTML = pageHTML;
        return x;
    }

    public addCallBack(): void {
        // ------------------------------------------
        // add des mois dans le selected de mois
        // ------------------------------------------
        let d : Date = new Date();
        let iIndiceMoisCourant : number = d.getMonth();
        let monthNames: Array<string> = ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"];
        for (let i: number = 0; i < 12; i++) {
            let m: string = monthNames[i];
            if (i == iIndiceMoisCourant)
                $(`#${this._idSelectOnMois}`).append($('<option>', { value: m, text: m }).attr('selected', 'selected'));
            else
                $(`#${this._idSelectOnMois}`).append($('<option>', { value: m, text: m }));
        }
 
 
        // ------------------------------------------
        // add des annees dans le selected des annees
        // ------------------------------------------
        let iIndiceAnneeCourant : number = d.getFullYear();
        for (let i: number = 2020; i < 2030; i++) {
            if (i == iIndiceAnneeCourant)
                $(`#${this._idSelectOnAnnee}`).append($('<option>', { value: i, text: i }).attr('selected', 'selected'));
            else
                $(`#${this._idSelectOnAnnee}`).append($('<option>', { value: i, text: i }));
        }


        // ------------------------------------------
        // add des personnes dans le selecteur
        // ------------------------------------------
        let ws: cWS = new cWS();
        let allPersonnes: iPersonne[] = ws.getAllPersonnes();
        allPersonnes.forEach(unePersonne => {
            $(`#${this._idSelectOnPersonne}`).append($('<option>', { value: unePersonne.id, text: (unePersonne.genre + "  " + unePersonne.nom) })); 
        });

        // ------------------------------------------
        // add call back du Vaide info
        // ------------------------------------------
        let me: cDialogMainPageInfoGenerale = this;
        $(`#${this._idValideInfo}`).on('click', function (event : JQuery.ClickEvent) {
            event.preventDefault();
            event.stopImmediatePropagation();

            // ----
            // recup des infos
            // ----
            let mois : string = $(`#${me._idSelectOnMois}`).val() as string;
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
                    let p: iPersonne = ws.getPersonne(genre, nom);
                    if (p == null) { 
                        p = ws.addNewPersonne(genre, nom);
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
            let iMois : number = cOutilsDivers.MoisFromNomToInt(mois);
            let fiche: iBulletinSalaire[] = ws.getBulletinSalaire(idPersonne, iMois, annee);
            if (fiche.length > 0) {
                UIkit.modal.alert("KO --> cette fiche existe deja, la detruire d'abord");
                return;
            }
        });
        return;
    }
}