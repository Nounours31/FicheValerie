
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public setDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/07/26 00:56:38";
        return retour;
    }

    public setCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 6ea5446253010b5e6436fb7f5d8c14028e3891f1 - Commit Date:    Sun Jul 25 14:29:33 2021 +0200 - Commit branch: ";
        return retour;
    }

    public setServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

