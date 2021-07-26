import 'uikit/dist/js/uikit-icons'
import 'uikit/dist/css/uikit.css';

import $ from 'jquery';
import cDialogAbstract from './cDialogAbstract';

import cWS from '../WS/cWS';
import cDialogTools from '../tools/cDialogTools';
import cVersion from '../cVersion';


export default class cDialogInfoDebugTabedPage extends cDialogAbstract {
    private static _NomPrefixe: string = 'cDialogInfoDebugTabedPage';
    private ws: cWS = null;
    constructor() {
        super('cDialogInfoDebugTabedPage');
        this.ws = new cWS();
    }

    public Draw(): string {            
        let pageHTML : string = `
            <div id="cDialogInfoDebugTabedPage_Master">
                <button id="cDialogInfoDebugTabedPage_RefreshButton">Refresh</button>
            </div>
        `;
        return pageHTML;
    }

    private DrawOnRefresh(): string {
        let infosVersion: cVersion = new cVersion();
        let uiBuild: string = infosVersion.getDateBuild();
        let uiCommit: string = infosVersion.getCommitVersionBuild();
        let uiBuildServer: string = infosVersion.getServerBuild();
        let serverConf: any = this.ws.getSetEnvInfo();
        let serverLevel: string = this.ws.setServerDebug("level");

            
        let pageHTML : string = `
            <div id="cDialogInfoDebugTabedPage">
                <table class="cDialogInfoDebugTabedPage_table">
                    <tr><td>version UI Build</td><td>${uiBuild}</td></tr>
                    <tr><td>version UI Commit</td><td>${uiCommit}</td></tr>
                    <tr><td>version Server Build</td><td >${uiBuildServer}</td></tr>
                </table>
                
                <table class="cDialogInfoDebugTabedPage_table">
                    <tr>
                        <td>Switch server trace:</td>
                        <td>${serverLevel}</td>
                        <td>
                            <select id="cDialogInfoDebugTabedPage_switchServerlogInfo">
                                <option value="">--Please choose an option--</option>
                                <option value="debug">Debug</option>
                                <option value="info">Info</option>
                                <option value="fatal">Fatal</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="3">Server Conf</td>
                        <td>storePath: ${serverConf.storePath}</td>
                        <td><input type="text" id="cDialogInfoDebugTabedPage_StorePathInfo" minlength="4" size="${serverConf.storePath.length + 2}" value="${serverConf.storePath}"/> </td>
                    </tr>
                    <tr>
                            <td>CSG: ${serverConf.CSG}</td>
                            <td><input type="number" id="cDialogInfoDebugTabedPage_CSGInfo" min="0.0" max="1.0" step="0.001" value="${serverConf.CSG}"/></td>

                    </tr>
                    <tr>
                            <td>TauxImposition: ${serverConf.TauxImposition}</td>
                            <td><input type="number" id="cDialogInfoDebugTabedPage_TauxImpositionInfo" min="0.0" max="1.0" step="0.001" value="${serverConf.TauxImposition}"/></td>
                    </tr>
                    <tr>
                        <td><button id="cDialogInfoDebugTabedPage_RefreshButton">Refresh</button></td>
                        <td></td>
                        <td><button id="cDialogInfoDebugTabedPage_UpdateButton">Update DB ? attention !</button></td>
                    </tr>
                </table>
                
                
                        
                </div>
            </div>
        `;
        return pageHTML;
    }

    public addCallBack(): void {
        let me: cDialogInfoDebugTabedPage = this;
        $(`#cDialogInfoDebugTabedPage_RefreshButton`).on('click', { 'me': me },  me.OnRefreshButton);
    }


    private OnRefreshButton (event: JQuery.ClickEvent) : boolean {
        event.preventDefault();
        event.stopImmediatePropagation();

        let me: cDialogInfoDebugTabedPage = event.data.me;
        $(`#cDialogInfoDebugTabedPage_Master`).empty();
        $(`#cDialogInfoDebugTabedPage_Master`).html(me.DrawOnRefresh());

        // on se rebranche :)
        $(`#cDialogInfoDebugTabedPage_RefreshButton`).on('click', { 'me': me }, me.OnRefreshButton);
        
        // update DB
        $(`#cDialogInfoDebugTabedPage_UpdateButton`).on('click', function (event: JQuery.ClickEvent) {
            event.preventDefault();
            event.stopImmediatePropagation();

            let val: string = $(`#cDialogInfoDebugTabedPage_switchServerlogInfo`).val() as string;
            if (val != "")
                me.ws.setServerDebug(val);

            let StorePath: string = $(`#cDialogInfoDebugTabedPage_StorePathInfo`).val() as string;
            let CSG: number = Number.parseFloat($(`#cDialogInfoDebugTabedPage_CSGInfo`).val() as string);
            let TauxImposition: number = Number.parseFloat($(`#cDialogInfoDebugTabedPage_TauxImpositionInfo`).val() as string);
            me.ws.getSetEnvInfo(StorePath, CSG, TauxImposition);

            $(`#cDialogInfoDebugTabedPage_RefreshButton`).trigger('click');
            return true;
        });

        // style
        $(`.cDialogInfoDebugTabedPage_table`).css("border", "dotted 1px green");
        $(`.cDialogInfoDebugTabedPage_table tr`).css("border", "dotted 1px green");
        $(`.cDialogInfoDebugTabedPage_table td`).css("border", "dotted 1px green");

        return true;
    }
}