import * as _ from 'lodash';
import $ from 'jquery';


import cDialogMainPage from "./dialog/cDialogMainPage";

let iMain: cDialogMainPage = new cDialogMainPage();
$(`#TOP`).html (iMain.Draw());
iMain.addCallBack();


