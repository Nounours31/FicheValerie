
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public getDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/08/13 18:51:15";
        return retour;
    }

    public getCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 8992a2cc1b68db378b92a56c414eddfd140441d6 - Commit Date:    Fri Aug 13 18:50:08 2021 +0200 - Commit branch: ";
        return retour;
    }

    public getServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

