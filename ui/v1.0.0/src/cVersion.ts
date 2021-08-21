
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public getDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/08/21 20:16:12";
        return retour;
    }

    public getCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 7ffa8788132ac108b6372f49374a69ce482de6d2 - Commit Date:    Fri Aug 13 19:00:26 2021 +0200 - Commit branch: ";
        return retour;
    }

    public getServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

