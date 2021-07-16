export default class cEnv {
    private static _serverURLDell: string = "http://localhost:8080/lib/fichevalerie/v1.0.0";
    private static _serverURLVape: string = "http://localhost:8090/FicheValerie-1.0.0/fichevalerie/v1.0.0";
    
    public static _serverURL: string = cEnv._serverURLVape;
}