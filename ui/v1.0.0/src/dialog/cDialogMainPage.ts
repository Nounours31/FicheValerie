import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

import cDialogMainPageInfoGenerale from './cDialogMainPageInfoGenerale';
import cDialogMainPageHoraires from './cDialogMainPageHoraire';

import {iPersonne} from '../WS/iWSMessages';

export default class cDialogMainPage extends cDialog {
    private static  _idTabPage: string;

    private currentPersonne: iPersonne;
    private currentAnnee: number;
    private currentMois: number;

    constructor() {
        super('cDialogMainPage');
        cDialogMainPage._idTabPage = cDialogMainPage._NomPrefixe + 'idMainTab';
        cDialogMainPage._idInfoDiv = cDialogMainPage._NomPrefixe + '_idInfoDiv';
        cDialogMainPage._idActiviteDiv = cDialogMainPage._NomPrefixe + '_idActiviteDiv';

        this.currentPersonne = null;
        this.currentAnnee = 0;
        this.currentMois = 0;
    }

    public Draw(): HTMLDivElement {
        let iInfoGenerales: cDialogMainPageInfoGenerale = new cDialogMainPageInfoGenerale();
        let iInfoHoraires: cDialogMainPageHoraires = new cDialogMainPageHoraires();
        let x: HTMLDivElement = document.createElement('div id=``');
        x.appendChild(iInfoGenerales.Draw());

        let y : HTMLDivElement = document.createElement('div');
        y.appendChild(iInfoHoraires.Draw());
        
        let z: HTMLDivElement = document.createElement('div');
        z.appendChild(x);
        z.appendChild(y);
        return z;
    }

    public addCallBack(): void {
        let iInfoGenerales: cDialogMainPageInfoGenerale = new cDialogMainPageInfoGenerale();
        let iInfoHoraires: cDialogMainPageHoraires = new cDialogMainPageHoraires();

        iInfoGenerales.addCallBack();
        iInfoHoraires.addCallBack();
    }

    protected setMois(mois: string) : void {
        this.currentMois = Number.parseInt (mois);

    }
    protected setAnnee(annee: number) : void {
        this.currentAnnee = annee;

    }
    protected setPersonne(p: iPersonne) : void {
        this.currentPersonne = p;
    }
}