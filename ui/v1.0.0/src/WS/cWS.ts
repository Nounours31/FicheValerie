import cToolsAjax from '../tools/cToolsAjax';
import {iPersonne, iBulletinSalaire, iActivite } from './iWSMessages';
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
        let URL = cEnv._serverURL + '/personne';
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
            URL = cEnv._serverURL + `/personne/${idOrGenre}/${nom}`;
        } else {
            URL = cEnv._serverURL + `/personne/${idOrGenre}`;
        }
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPersonne[]);
        }
        return retour;
    }

    public getBulletinSalaire(idPersonne: number, mois: number, annee: number): iBulletinSalaire[] {
        let retour : iBulletinSalaire[] = [];
        let URL = cEnv._serverURL + `/bulletinSalaire/${idPersonne}/${mois}/${annee}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iBulletinSalaire[]);
        }
        return retour;
    }
    public createBulletinSalaire(mois: number, annee: number, p: iPersonne, tarifHoraire: number): number {
        let retour: number = -1;
        let URL = cEnv._serverURL + `/bulletinSalaire`;
        let postData: iBulletinSalaire = {
            'annee': annee as number,
            'mois': mois as number,
            'tarifHoraire': tarifHoraire as number,
            'id': -1,
            'idPersonne': p.id as number
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iBulletinSalaire).id;
        }
        return retour;
    }

    public addActivite(ficheId: number, jour: number, activite: string, debut: Date, fin: Date) : number {
        let retour: number = -1;
        let URL = cEnv._serverURL + `/activite`;
        let postData: iActivite = {
            'id': -1,
            'idBulletinSalaire': ficheId,
            'activite': activite,
            'debut': debut,
            'fin': fin,
            'tarifHoraire': -1.0
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iActivite).id;
        }
        return retour;
    }

    public addNewPersonne(genre: string, nom: string): iPersonne {
        let retour : iPersonne = null;
        let URL = cEnv._serverURL + '/personne';
        let PostData: iPersonne = {
            'genre': genre,
            id: -1,
            'nom': nom
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