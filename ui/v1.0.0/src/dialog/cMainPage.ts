import $ from 'jquery';

import cDialog from './cDialog';
import cDialogMainPage from './cDialogMainPage';

export default class cMainPage extends cDialog {
    private static  _idTabPage: string;


    constructor() {
        super('cMainPage');
    }

    public CreateMainPageDiv(idParentDiv : string ) {
        let divMain: JQuery<HTMLDivElement> = $("<div id='MainPage'></div>");
        $(`#${idParentDiv}`).append(divMain);

        let iDialogMainPage: cDialogMainPage = new cDialogMainPage();
        divMain.append(iDialogMainPage.Draw());

        iDialogMainPage.addCallBack();
    }

    public Draw(): JQuery<HTMLDivElement> | null {
       return null;
    }

    public addCallBack(): void {
        return;
    }
}