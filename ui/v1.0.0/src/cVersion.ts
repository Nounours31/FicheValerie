
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public setDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/07/26 00:58:27";
        return retour;
    }

    public setCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 5ef35f8725cf2fd769158f59148d89b5287bd462 - Commit Date:    Mon Jul 26 00:58:14 2021 +0200 - Commit branch: ";
        return retour;
    }

    public setServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

