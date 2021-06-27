import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'uikit';
import $ from 'jquery';
import cDialogGraph from './cDialogGraph';
import cDialogSaisie from './cDialogSaisie';
import cDialog from './cDialog';

export default class cDialogMainPage extends cDialog {
    private static  _idTabPage: string;
    private static  _idSwitcherPage: string;
    private static  _idSaisieTab: string ;
    private static  _idGraphTab: string ;

    private _myTab : UIkit.UIkitTabElement | null = null;

    constructor() {
        super('cDialogMainPage');
        cDialogMainPage._idTabPage = cDialogMainPage._NomPrefixe + 'idMainTab';
        cDialogMainPage._idSwitcherPage  = cDialogMainPage._NomPrefixe + 'idMainSwitcher';
        cDialogMainPage._idSaisieTab  = cDialogMainPage._NomPrefixe + 'idSaisieTab';
        cDialogMainPage._idGraphTab  = cDialogMainPage._NomPrefixe + 'idGraphTab';
    }

    public Draw(): HTMLDivElement {
        let iDialogGraph: cDialogGraph = new cDialogGraph();
        let iDialogSaisie: cDialogSaisie = new cDialogSaisie();
        let graphDiv: HTMLDivElement = iDialogGraph.Draw();
        let saisieDiv: HTMLDivElement = iDialogSaisie.Draw();

        let pageHTML : string = `
            <ul id="${cDialogMainPage._idTabPage}" class="uk-tab" data-uk-tab="{connect:'#${cDialogMainPage._idSwitcherPage}'}">
                <li><a href="">Saisie</a></li>
                <li><a href="">Graph</a></li>
            </ul>
            <ul id="${cDialogMainPage._idSwitcherPage}" class="uk-switcher uk-margin">
                <li class="${cDialogMainPage._idSaisieTab}"></li>
                <li class="${cDialogMainPage._idGraphTab}"></li>
            </ul>
        `; 
        let x : HTMLDivElement = document.createElement('div');
        x.innerHTML = pageHTML;
        x.getElementsByClassName(`${cDialogMainPage._idSaisieTab}`)[0].append(saisieDiv);
        x.getElementsByClassName(`${cDialogMainPage._idGraphTab}`)[0].append(graphDiv);

        return x;
    }

    public addCallBack(): void {
        let iDialogSaisie: cDialogSaisie = new cDialogSaisie();
        iDialogSaisie.addCallBack();
    }
}