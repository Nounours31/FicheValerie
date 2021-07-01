import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import UIkit from 'UIKit';
import $ from 'jquery';
import cDialog from './cDialog';

export default class cMainPage extends cDialog {
    private static  _idTabPage: string;

    private _myTab : UIkit.UIkitTabElement | null = null;

    constructor() {
        super('cMainPage');
    }

    public positionneDivs(mainDivId : String) {
        $(`#${mainDivId}`).addClass('uk-flex');
    }
    public Draw(): HTMLDivElement | null {
       return null;
    }

    public addCallBack(): void {
        return;
    }
}