import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIkit';
import $ from 'jquery';
import cDialog from './cDialog';

export default class cResultMainPage extends cDialog {
    private static  _idTabPage: string;

    private _myTab : UIkit.UIkitTabElement | null = null;

    constructor() {
        super('cDialogMainPage');
        cResultMainPage._idTabPage = cResultMainPage._NomPrefixe + 'idMainTab';
    }

    public Draw(): HTMLDivElement {
        let pageHTML: string = `
        <p>Resultats</p>
        `; 
        let x : HTMLDivElement = document.createElement('div');
        x.innerHTML = pageHTML;
        return x;
    }

    public addCallBack(): void {
        return;
    }
}