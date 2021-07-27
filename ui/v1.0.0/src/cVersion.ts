
import cWS from "./WS/cWS";
export default class cVersion {
    constructor() {
    }

    public getDateBuild(): string {
        let retour = "";
        retour += "Build Date: 2021/07/27 20:40:40";
        return retour;
    }

    public getCommitVersionBuild(): string {
        let retour = "";
        retour += "Commit Id: 36733faec57079e76a78fbd737e9be3fdcc4dc38
62fee1b9ccaeb02f02ed42b008821a68fea6001b
0a796aa1d056673ef46b8871786264f4d4bb35d7
4587216f4e25f7acea18cebbc8c9926201034da8
4970130800f304de0e3b28fc4bbabf6149128e8d
4624fb480ccf8517c5105579b1162a74490700d8
1ddd05763da118726015a78e04394053852908da
commit
e0e799c5410202c5c1775c6a855402f14ae7ef54
commit - Commit Date:    Tue Jul 27 20:39:23 2021 +0200
   Tue Jul 27 20:37:18 2021 +0200
   Tue Jul 27 20:30:08 2021 +0200
   Tue Jul 27 18:53:01 2021 +0200
   Tue Jul 27 18:50:27 2021 +0200
   Tue Jul 27 16:17:05 2021 +0000
   Tue Jul 27 12:54:04 2021 +0200
   Tue Jul 27 12:51:34 2021 +0200 - Commit branch: ";
        return retour;
    }

    public getServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}

