export default class cEnv {
    private static _serverURLInternal: string[] = [
        "http://www.famillefages.ovh:8080/ficheValerie-2021-07-01/v1.0.0",
        "http://localhost:8080/ficheValerie-2021-07-01/v1.0.0",
        "http://localhost:8090/ficheValerie-2021-07-01/v1.0.0"
    ];

    private static PROD: number = 0;
    private static DELL: number = 1;
    private static VAPE: number = 2;
    public static _serverURL: string = cEnv._serverURLInternal[cEnv.PROD];

}