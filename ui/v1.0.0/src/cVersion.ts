
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public getDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/08/13 18:57:57";
        return retour;
    }

    public getCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 5988c747040d475a2fae57623daf3aafd72b8df2 - Commit Date:    Fri Aug 13 18:57:46 2021 +0200 - Commit branch: ";
        return retour;
    }

    public getServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

