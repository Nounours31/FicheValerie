import * as _ from 'lodash';
import $ from 'jquery';


import cDialogTabPage from "./dialog/cDialogTabPage";

let iMain: cDialogTabPage = new cDialogTabPage();
$(`#TOP`).html (iMain.Draw());
iMain.addCallBack();
iMain.getSwitcherElement().show(cDialogTabPage.IndexCreationTab);


