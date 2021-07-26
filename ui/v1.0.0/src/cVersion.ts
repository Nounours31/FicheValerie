
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public getDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/07/26 16:49:33";
        return retour;
    }

    public getCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: ec69a9bc38bc51889c6148560b86dfae05b52739 - Commit Date:    Mon Jul 26 01:03:54 2021 +0200 - Commit branch: ";
        return retour;
    }

    public getServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

