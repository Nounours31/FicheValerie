
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public setDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/07/26 01:01:50";
        return retour;
    }

    public setCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 04ac4f7779cd1d8de358c61964dcaa7618c95d22 - Commit Date:    Mon Jul 26 00:59:48 2021 +0200 - Commit branch: ";
        return retour;
    }

    public setServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

