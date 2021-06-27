import * as _ from 'lodash';

import cDialogMainPage from "./dialog/cDialogMainPage";

let dialogDiv: HTMLElement = document.getElementById('iDialog');
let resultDiv: HTMLElement = document.getElementById('iResultat');

let iDialog : cDialogMainPage = new cDialogMainPage();

dialogDiv.appendChild(iDialog.Draw());
iDialog.addCallBack();

resultDiv.appendChild(iDialog.Draw());
iDialog.addCallBack();