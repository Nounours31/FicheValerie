import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIkit';
import $ from 'jquery';
import cDialogAbstract from '../dialog/cDialogAbstract';

export default class cResultMainPage extends cDialogAbstract {
    private static _NomPre: string = 'cResultMainPage';
    private static  _idTabPage: string;

    private _myTab : UIkit.UIkitTabElement | null = null;

    constructor() {
        super('cDialogMainPage');
        cResultMainPage._idTabPage = cResultMainPage._NomPre + 'idMainTab';
    }

    public Draw(): string {
        let pageHTML: string = `
        <p>Resultats</p>
        `; 
        return pageHTML;
    }

    public addCallBack(): void {
        return;
    }
}
