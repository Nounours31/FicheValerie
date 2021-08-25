
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public getDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/08/25 17:56:39";
        return retour;
    }

    public getCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 68f2b7003d60c88749ef6daa311174c479543195 - Commit Date:    Mon Aug 23 20:39:43 2021 +0200 - Commit branch: ";
        return retour;
    }

    public getServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

