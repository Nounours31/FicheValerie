import cToolsAjax from '../tools/cToolsAjax';
import {iPersonne, iBulletinSalaire } from './iWSMessages';
import cErr from '../tools/cErr';
import cEnv from './cEnv';

export default class cWS {
    private t : cToolsAjax =  null;
    private _data : JSON = null;

    constructor() {
        this.t = new cToolsAjax();
    }


    public getAllPersonnes(): iPersonne[] {
        let retour : iPersonne[] = [];
        let URL = cEnv._serverURL + '/getAllPersonnes';
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPersonne[]);
        }
        return retour;
    }

    public getPersonne(id: number): iPersonne[];
    public getPersonne(genre: string, nom: string): iPersonne[];
    public getPersonne(idOrGenre?: number|string, nom?: string): iPersonne[] {
        let retour: iPersonne[] = null;
        let URL : string = "";
        // if (typeof abc === "number") {
        if (nom !== undefined) {
            URL = cEnv._serverURL + `/getPersonne/${idOrGenre}/${nom}`;
        } else {
            URL = cEnv._serverURL + `/getPersonne/${idOrGenre}`;
        }
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPersonne[]);
        }
        return retour;
    }

    public getBulletinSalaire(idPersonne: number, mois: number, annee: number): iBulletinSalaire[] {
        let retour : iBulletinSalaire[] = [];
        let URL = cEnv._serverURL + `/getBulletinSalaire/${idPersonne}/${mois}/${annee}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iBulletinSalaire[]);
        }
        return retour;
    }

    public addNewPersonne(genre: string, nom: string): iPersonne {
        let retour : iPersonne = null;
        let URL = cEnv._serverURL + '/personne';
        let PostData: iPersonne = {
            'genre': genre,
            id: -1,
            'nom': nom,
            'date': '1900-01-01T00:00:00'
        };
        let oResp: boolean = this.t.sendPostWS(URL, PostData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPersonne);
        }
        console.log('creation de ' + genre + ' ' + nom);
        return retour;
    }

    public get data() : JSON { return this._data; }
}