export default class cEnv {
    static getTomeeURL() : string {
        let retour : string = document.location.origin;
        retour = retour + '/ficheValerie-2021-07-01/v1.0.0';
        return retour;
    }
}
