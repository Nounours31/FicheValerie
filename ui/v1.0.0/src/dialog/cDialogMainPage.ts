import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import cDialogMainPageInfoGenerale from './cDialogMainPageInfoGenerale';
import cDialogMainPageHoraires from './cDialogMainPageHoraire';

import {iPersonne} from '../WS/iWSMessages';

export default class cDialogMainPage extends cDialog {
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

    public Draw(): JQuery<HTMLDivElement> {
        let iInfoGenerales: cDialogMainPageInfoGenerale = new cDialogMainPageInfoGenerale();
        let iInfoHoraires: cDialogMainPageHoraires = new cDialogMainPageHoraires();

        let divInfosGenerales: JQuery<HTMLDivElement> = $("<div id='InfosGeneralesDiv'></div>");
        let x: JQuery<HTMLElement> = iInfoGenerales.Draw();
        divInfosGenerales.append(x);

        let divHoraires: JQuery<HTMLDivElement> = $("<div id='InfosHorairesDiv'></div>");
        let y: JQuery<HTMLElement> = iInfoHoraires.Draw();
        divHoraires.append(y);

        let divAggregator: JQuery<HTMLDivElement> = $("<div id='InfosAggregatorDiv'></div>");
        divAggregator.append(divInfosGenerales);
        divAggregator.append(divHoraires);

        return divAggregator;
    }

    public addCallBack(): void {
        let iInfoGenerales: cDialogMainPageInfoGenerale = new cDialogMainPageInfoGenerale();
        let iInfoHoraires: cDialogMainPageHoraires = new cDialogMainPageHoraires();

        iInfoGenerales.addCallBack();
        iInfoHoraires.addCallBack();

        this.toggleDiv();

        let me : cDialogMainPage = this;
        $(`#InfosAggregatorDiv`).on('InfoGeneralesDefined', function () {
            me.switchDiv();
            iInfoHoraires.refresh();
        });

    }

    private switchDiv(): void {
        cDialogMainPage._toggleDivStatus = !cDialogMainPage._toggleDivStatus;
        this.toggleDiv();
    }

    private toggleDiv(): void {
        if (cDialogMainPage._toggleDivStatus) {
            $(`#InfosGeneralesDiv`).show(400);
            $(`#InfosHorairesDiv`).hide(400);
        }
        else {
            $(`#InfosGeneralesDiv`).hide(400);
            $(`#InfosHorairesDiv`).show(400);
        }
    }

}