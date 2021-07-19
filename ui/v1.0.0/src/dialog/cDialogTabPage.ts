import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialogAbstract';

import cDialogCreateBulletinTabedPage from './cDialogCreateBulletinTabedPage';
import cDialogActiviteeTabedPage from './cDialogActiviteeTabedPage';
import cDialogSearchBulletinTabedPage from './cDialogSearchBulletinTabedPage'

import {iPersonne} from '../WS/iWSMessages';
import cDialogAjoutDBTabedPage from './cDialogAjoutDBTabedPage';

export default class cDialogTabPage extends cDialog {
    private static _NomPrefixe: string = 'cDialogTabPage';
    private static _idTabPage: string = cDialogTabPage._NomPrefixe + "_cDialogTabPage";
    private static _idSwitcher: string = cDialogTabPage._NomPrefixe + "_cDialogTabSwitcher";


    private iAddActivitee: cDialogActiviteeTabedPage = null;
    private iCreate: cDialogCreateBulletinTabedPage = null;
    private iSearch: cDialogSearchBulletinTabedPage = null;
    private iAddInDB: cDialogAjoutDBTabedPage = null;

    public static IndexCreationTab: number = 0;
    public static IndexSearchTab: number = 1;
    public static IndexEditionTab: number = 2;
    public static IndexAjoutDBTab: number = 3;

    constructor() {
        super('cDialogTabPage');
        this.iAddActivitee = new cDialogActiviteeTabedPage();
        this.iCreate = new cDialogCreateBulletinTabedPage(this.iAddActivitee, this);
        this.iSearch = new cDialogSearchBulletinTabedPage(this.iAddActivitee, this);
        this.iAddInDB = new cDialogAjoutDBTabedPage();
    }

    public Draw(): string {
        let retour : string = `
            <div id="${cDialogTabPage._idTabPage}">
                <ul class="uk-tab" id="switcher_js-control" "data-uk-tab="{connect:'#${cDialogTabPage._idSwitcher}'}">
                    <li id="tab1"><a href="">Creation</a></li>          <!-- IndexCreationTab = 0 -->
                    <li id="tab2"><a href="">Recherche</a></li>         <!-- IndexSearchTab = 1 -->
                    <li id="tab3"><a href="">Edition</a></li>           <!-- IndexEditionTab = 2 -->
                    <li id="tab5"><a href="">Ajout en DB</a></li>       <!-- IndexAjoutDBTab = 3 -->
                </ul>
                <ul id="${cDialogTabPage._idSwitcher}" class="uk-switcher uk-margin">
                    <li>`+ this.iCreate.Draw() + `</li>         
                    <li>`+ this.iSearch.Draw() +`</li>          
                    <li>`+ this.iAddActivitee.Draw() +`</li>    
                    <li>`+ this.iAddInDB.Draw() +`</li>
                </ul>
            <div>
        `;
        return retour;
    }

    public addCallBack(): void {
        this.iCreate.addCallBack();
        this.iAddActivitee.addCallBack();
        this.iSearch.addCallBack();
        this.iAddInDB.addCallBack();
    }

    public getSwitcherElement(): UIkit.UIkitSwitcherElement {
        let retour: UIkit.UIkitSwitcherElement = null;
        
        retour = UIkit.switcher("#switcher_js-control");
        return retour;
    }



}