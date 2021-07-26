#!/bin/bash

# version de git:
comitBranch=$(git log HEAD^..HEAD | grep "commit" | awk 'BEGIN{FS="("}{print $2}' | tr -d ')')
comitId=$(git log HEAD^..HEAD | grep "commit" | awk '{print $2}')
commitDate=$(git log HEAD^..HEAD | grep "Date:" | sed 's/Date://g')
buildDate=$(date  '+%Y/%m/%d %H:%M:%S')

echo "
import cWS from \"./WS/cWS\";
export default class cVersion {
    constructor() {
    }

    public getDateBuild(): string {
        let retour = \"\";
        retour += \"Build Date: ${buildDate}\";
        return retour;
    }

    public getCommitVersionBuild(): string {
        let retour = \"\";
        retour += \"Commit Id: ${comitId} - Commit Date: ${commitDate} - Commit branch: ${comitBranch}\";
        return retour;
    }

    public getServerBuild(): string {
        let ws : cWS = new cWS();
        let retour : string = ws.getServerBuildVersion();
        return retour;
    }
}
" > /E/WS/GitHubPerso/FicheValerie/ui/v1.0.0/src/cVersion.ts
ls -al /E/WS/GitHubPerso/FicheValerie/ui/v1.0.0/src/cVersion.ts
printf "OK mise  ajour de UI\n"

serverFile="/E/WS/GitHubPerso/FicheValerie/server/v1.0.0/lib/src/main/java/sfa/fichevalerie/tools/E4ABuildVersion.java"
cat ${serverFile} | sed -e "s|String retourTAGGED=\".*\";|String retourTAGGED=\"${buildDate}\";|g" > /tmp/toto
mv /tmp/toto ${serverFile}
ls -al ${serverFile}
printf "OK mise  ajour de Server\n"

echo "changer E:\WS\GitHubPerso\FicheValerie\ui\v1.0.0\src\WS\cEnv.ts" 
echo "OK continue Y/N ?"
read a

cd /E/WS/GitHubPerso/FicheValerie/server/v1.0.0
. graddle.sh
gradle war
cp /E/WS/GitHubPerso/FicheValerie/server/v1.0.0/lib/build/libs/*.war  /E/WS/GitHubPerso/FicheValerie/Deliveries/war
echo "OK cp WAR"

cd /E/WS/GitHubPerso/FicheValerie/ui/v1.0.0
npm run build:prod
cp /E/WS/GitHubPerso/FicheValerie/ui/v1.0.0/main.html /E/WS/GitHubPerso/FicheValerie/Deliveries/ui
cp -r /E/WS/GitHubPerso/FicheValerie/ui/v1.0.0/dist /E/WS/GitHubPerso/FicheValerie/Deliveries/ui
cp -r /E/WS/GitHubPerso/FicheValerie/ui/v1.0.0/images /E/WS/GitHubPerso/FicheValerie/Deliveries/ui
echo "OK cp UI"

cd /E/WS/GitHubPerso/FicheValerie/
tar -cvf Deliveries.tar Deliveries
mv Deliveries.tar Deliveries
echo "Ok Tar"
