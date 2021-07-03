import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import { cControler } from '../cControler';
import cOutilsDivers  from '../tools/cOutilsDivers';

export default class cDialogMainPageHoraire extends cDialog {
    private static  _idTabPage: string;

    private _myTab : UIkit.UIkitTabElement | null = null;

    constructor() {
        super('cDialogMainPageHoraire');
        cDialogMainPageHoraire._idTabPage = cDialogMainPageHoraire._NomPrefixe + 'idMainTab';
    }

    public Draw(): JQuery<HTMLDivElement> {

        let pageHTML : string = `
            <form style="padding-left: 10px;">
                <fieldset style="padding-left: 10px;">
                    <legend>Saisie des infos generales</legend>

                    <label>Personne:<span id="toto"></span></label><br/>
                    <label>Periode:<span id="titi"></span></label><br/>
                    <label>Tarif:<span id="tata"></span></label><br/>
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
                        <tbody>
                            <tr>
                                <td>Mardi 1 janvier</td>
                                <td><button class="uk-button uk-button-default uk-button-small" style="background-color: greenyellow;">+</button></td>
                                <td>Menage</td>
                                <td><input type="time" id="appt" name="appt" min="09:00" max="18:00" required></td>
                                <td><input type="time" id="appt" name="appt" min="09:00" max="18:00" required></td>
                                <td>1:00</td>
                                <td>1:00</td>
                                <td><button class="uk-button uk-button-default uk-button-small">-</button></td>
                            </tr>
                            <tr>
                                <td>Mercredi 2 janvier</td>
                                <td><button class="uk-button uk-button-default uk-button-small">+</button></td>
                                <td>Menage</td>
                                <td><input type="time" id="appt" name="appt" min="09:00" max="18:00" required></td>
                                <td><input type="time" id="appt" name="appt" min="09:00" max="18:00" required></td>
                                <td>1:00</td>
                                <td>1:00</td>
                                <td><button class="uk-button uk-button-default uk-button-small">-</button></td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
            </form>
        `;
        let x: JQuery<HTMLDivElement> = $("<div id='InfosHorairesDivBuilder'></div>");
        x.append(pageHTML);
        return x;
    }

    public refresh(): void {
        let c: cControler = cControler.getInstance();
        $(`#toto`).text(cOutilsDivers.personne2String (c.personne));
        $(`#titi`).text(c.mois + '/' + c.annee);
        $(`#tata`).text(c.tarifHoraire + '€');
    }

    public addCallBack(): void {
        return;
    }
}