import * as _ from 'lodash';


import cMainPage from "./dialog/cMainPage";
import cDialogMainPage from "./dialog/cDialogMainPage";
import cResultMainPage from "./dialog/cResultMainPage";

let dialogDiv: HTMLElement = document.getElementById('iDialog');
let resultDiv: HTMLElement = document.getElementById('iResultat');

let iMain : cMainPage = new cMainPage();
let iDialog : cDialogMainPage = new cDialogMainPage();
let iResult : cResultMainPage = new cResultMainPage();


iMain.positionneDivs('iPositionneur');

dialogDiv.appendChild(iDialog.Draw());
iDialog.addCallBack();

//resultDiv.appendChild(iResult.Draw());
iResult.addCallBack();