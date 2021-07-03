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

    public Draw(): JQuery<HTMLDivElement> {
        let pageHTML: string = `
        <p>Resultats</p>
        `; 
        let x: JQuery<HTMLDivElement> = $('<div id="PdfViewerDiv"></div>');
        x.append(pageHTML);
        return x;
    }

    public addCallBack(): void {
        return;
    }
}