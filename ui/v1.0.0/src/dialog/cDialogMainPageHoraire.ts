import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import { cControler } from '../cControler';
import cOutilsDivers  from '../tools/cOutilsDivers';
import cDuration from '../tools/cDuration';

export default class cDialogMainPageHoraire extends cDialog {
    private _myTab : UIkit.UIkitTabElement | null = null;

    private static _SplitTagSeparator: string = '___';

    private static _NomPrefixe: string = 'cDialogMainPageHoraire';
    private static _idLabelOfInputPersonne: string = cDialogMainPageHoraire._NomPrefixe + '_idLabelOfInputPersonne';
    private static _idLabelOfInputPeriode: string = cDialogMainPageHoraire._NomPrefixe + '_idLabelOfInputPeriode';
    private static _idLabelOfInputTarif: string = cDialogMainPageHoraire._NomPrefixe + '_idLabelOfInputTarif';


    private static _idTabBody: string = cDialogMainPageHoraire._NomPrefixe + '_idTabBody';
    private static _idHoraireDebut: string = cDialogMainPageHoraire._NomPrefixe + '_idHoraireDebut';
    private static _idHoraireFin: string = cDialogMainPageHoraire._NomPrefixe + '_idHoraireFin';
    private static _idHoraireDurrePresta: string = cDialogMainPageHoraire._NomPrefixe + '_idHoraireDurrePresta';
    private static _idCummulHoraireDurrePresta: string = cDialogMainPageHoraire._NomPrefixe + '_idCummulHoraireDurrePresta';
    private static _idSelectActivitee: string = cDialogMainPageHoraire._NomPrefixe + '_idSelectActivitee';

    

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
                        <table class="uk-table uk-table-striped">
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
                            <tbody id="${cDialogMainPageHoraire._idTabBody}">
                            </tbody>
                        </table>
                        <div class="uk-button-group" style="padding-left: 10px;">
                            <td><button class="uk-button uk-button-small" style="background-color: greenyellow;" id="xxxxxxx" >Valide</button></td>
                            <td><button class="uk-button uk-button-small" style="background-color: red;" id="yyyyyyy">Annule</button></td>
                        </div>
                    </fieldset>
                </form>
            </div>
        `;
        return pageHTML;
    }

    public refresh(): void {
        let c: cControler = cControler.getInstance();

        $(`#${cDialogMainPageHoraire._idLabelOfInputPersonne}`).text(" " + cOutilsDivers.personne2String (c.personne));
        $(`#${cDialogMainPageHoraire._idLabelOfInputPeriode}`).text(" " + cOutilsDivers.periode2String(c.mois, c.annee));
        $(`#${cDialogMainPageHoraire._idLabelOfInputTarif}`).text(" " + c.tarifHoraire + '€');

        this.updateMoisSurLaPage(c.mois, c.annee);
    }

    public addCallBack(): void {
        return;
    }

    private updateMoisSurLaPage (mois: number, annee: number) {
        let dateDebutmois: Date = new Date(annee, mois, 1);
        let dateCourante: Date = new Date (dateDebutmois);

        let idebug : number = 0;
        while ((dateCourante.getMonth() == dateDebutmois.getMonth()) && (idebug < 5)) {
            idebug++;
            console.log(dateCourante.toDateString());
            let jourDeLaSemaine: string = cOutilsDivers.SemaineFromIntToNom(dateCourante.getDay());
            let jourDuMois : number = dateCourante.getDate();

            let uidLigne: string = cDialogMainPageHoraire._SplitTagSeparator + annee + '-' + mois + '-' + jourDuMois + '-0';
            let uneligne : string = `
                <tr>
                    <td>${jourDeLaSemaine} ${jourDuMois} ${cOutilsDivers.periode2String(mois, annee)}</td>
                    <td> <button class="uk-button uk-button-default uk-button-small" style = "background-color: greenyellow;">+</button></td>
                    <td>
                        <select class="uk-select" id="${cDialogMainPageHoraire._idSelectActivitee}">
                            <option value="-" selected>-</option>
                            <option value="Madame">ActiviteeA</option>
                            <option value="Monsieur">Aide a la personne</option>
                        </select>
                    </td>
                    <td><input type="time" id="${cDialogMainPageHoraire._idHoraireDebut + uidLigne}" class="${cDialogMainPageHoraire._idHoraireDebut}" name="${cDialogMainPageHoraire._idHoraireDebut + uidLigne}"> </td>
                    <td><input type="time" id="${cDialogMainPageHoraire._idHoraireFin + uidLigne}" class="${cDialogMainPageHoraire._idHoraireFin}" name="${cDialogMainPageHoraire._idHoraireFin + uidLigne}"> </td>
                    <td id="${cDialogMainPageHoraire._idHoraireDurrePresta + uidLigne}" class="${cDialogMainPageHoraire._idHoraireDurrePresta}"></td>
                    <td id="${cDialogMainPageHoraire._idCummulHoraireDurrePresta + uidLigne}"></td>
                    <td><button class="uk-button uk-button-default uk-button-small">-</button></td >
                </tr>
            `;
            $(`#${cDialogMainPageHoraire._idTabBody}`).append(uneligne);
            dateCourante.setDate(dateCourante.getDate()+1);
        }

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

        let aTargetId: string[] = eventTargetId.split(cDialogMainPageHoraire._SplitTagSeparator);
        let uidLigne : string = aTargetId[1];
        let info: string[] = uidLigne.split('-');

        let ligne_annee: string = info[0];
        let ligne_mois: string = info[1];
        let ligne_jour: string = info[2];
        let ligne_index: string = info[3];

        let duree: cDuration = new cDuration();
        duree.set(Number.parseInt(ligne_annee), Number.parseInt(ligne_mois), Number.parseInt(ligne_jour), heureDebut, heureFin);

        let idCelluleToChange: string = cDialogMainPageHoraire._idHoraireDurrePresta + cDialogMainPageHoraire._SplitTagSeparator + uidLigne;
        let nbHeure : number = duree.asHour();
        $(`#${idCelluleToChange}`).text(nbHeure as unknown as string);

        let letNbHeureCumilee: number = 0.0;
        let x: number = 0.0;
        console.log("-------------------------------------------------------");
        $(`.${cDialogMainPageHoraire._idHoraireDurrePresta}`).each(function () {
            let celluleValeur: string = $(this).text();
            
            if ((celluleValeur != null) && (celluleValeur.length > 0)) {
                x = Number.parseFloat($(this).text());
                letNbHeureCumilee += x;
                console.log("x: " + x + "  " + $(this).prop('id'));
                console.log("cumul: " + letNbHeureCumilee);
                if (x > 0) {
                    let local_aTargetId: string[] = $(this).prop('id').split(cDialogMainPageHoraire._SplitTagSeparator);
                    let local_uidLigne: string = local_aTargetId[1];
                    let local_idCelluleToChange: string = cDialogMainPageHoraire._idCummulHoraireDurrePresta + cDialogMainPageHoraire._SplitTagSeparator + local_uidLigne;
                    console.log(local_idCelluleToChange);
                    $(`#${local_idCelluleToChange}`).text(letNbHeureCumilee as unknown as string);
                }
            }
        });

    }
}