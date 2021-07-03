import * as _ from 'lodash';
import $ from 'jquery';


import cDialogMainPage from "./dialog/cDialogMainPage";

let iMain: cDialogMainPage = new cDialogMainPage();
$(`#TOP`).html (iMain.Draw());
iMain.addCallBack();



/*
import $ from 'jquery';
import { UnsetAdditionalOptions } from 'tapable';
import UIkit from 'UIKit';

import cDialog from './cDialog';
import cDialogMainPage from './cDialogMainPage';

export default class cMainPage extends cDialog {
    private static  _idTabPage: string;


    constructor() {
        super('cMainPage');
    }

    public Draw(): string | null {
        let iDialogMainPage: cDialogMainPage = new cDialogMainPage();
        divMain.append();

        iDialogMainPage.addCallBack();

        return null;
    }

    public addCallBack(): void {
        return;
    }
}
*/

