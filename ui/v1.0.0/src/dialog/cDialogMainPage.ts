import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import cDialogMainPageInfoGenerale from './cDialogMainPageInfoGenerale';
import cDialogMainPageHoraires from './cDialogMainPageHoraire';
import cDialogMainPageInfoEdition from './cDialogMainPageInfoEdition'

import {iPersonne} from '../WS/iWSMessages';

export default class cDialogMainPage extends cDialog {
    private static _NomPrefixe: string = 'cDialogMainPage';
    private static _idTabPage: string;
    private static _idInfoDiv: string;
    private static _idActiviteDiv: string;
    private static _toggleDivStatus : boolean;

    private currentPersonne: iPersonne;
    private currentAnnee: number;
    private currentMois: number;

    constructor() {
        super('cDialogMainPage');
        cDialogMainPage._idTabPage = cDialogMainPage._NomPrefixe + 'idMainTab';
        cDialogMainPage._idInfoDiv = cDialogMainPage._NomPrefixe + '_idInfoDiv';
        cDialogMainPage._idActiviteDiv = cDialogMainPage._NomPrefixe + '_idActiviteDiv';

        cDialogMainPage._toggleDivStatus = true;

        this.currentPersonne = null;
        this.currentAnnee = 0;
        this.currentMois = 0;
    }

    public Draw(): string {
        let iInfoGenerales: cDialogMainPageInfoGenerale = new cDialogMainPageInfoGenerale();
        let iInfoHoraires: cDialogMainPageHoraires = new cDialogMainPageHoraires();
        let iEdition: cDialogMainPageInfoEdition = new cDialogMainPageInfoEdition();

        let retour : string = `
            <div id='MainPage'>
                <ul class="uk-tab" data-uk-tab="{connect:'#my-id3'}">
                    <li id="tab1"><a href="">Creation</a></li>
                    <li id="tab2"><a href="">Edition</a></li>
                    <li id="tab3"><a href="">Modification</a></li>
                    <li id="tab4"><a href="">Ajout en DB</a></li>
                </ul>
                <ul id="my-id3" class="uk-switcher uk-margin">
                    <li>`+ iInfoGenerales.Draw() + `</li>
                    <li>`+ iEdition.Draw() +`</li>
                    <li>`+ iInfoHoraires.Draw() +`</li>
                </ul>
            <div>

        `;
        return retour;
    }

    public addCallBack(): void {
        let iInfoGenerales: cDialogMainPageInfoGenerale = new cDialogMainPageInfoGenerale();
        let iInfoHoraires: cDialogMainPageHoraires = new cDialogMainPageHoraires();
        let iEdition: cDialogMainPageInfoEdition = new cDialogMainPageInfoEdition();

        iInfoGenerales.addCallBack();
        iInfoHoraires.addCallBack();
        iEdition.addCallBack();
    }




}