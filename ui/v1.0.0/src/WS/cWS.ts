import cToolsAjax from '../tools/cToolsAjax';
import {iPersonne, iBulletinSalaire, iActivite, iPdf } from './iWSMessages';
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


    getBulletinSalaireFromSQL(sql: string): iBulletinSalaire[] {
        let retour: iBulletinSalaire[] = [];
        let URL = cEnv._serverURL + `/sql`;
        let postData: Object = {
            'sql': sql,
            'retour': 'iBulletinSalaire',
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iBulletinSalaire[]);
        }
        return retour;
    }

    public getAllPossibleActivitees(): string[] {
        let retour: string[] = [];
        let URL = cEnv._serverURL + `/sql`;
        let postData: Object = {
            'sql': 'getAllPossibleActivitees',
            'retour': 'iListActivitee',
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as string[]);
        }
        return retour;
    }
    public addAPossibleActivitee(activitee: string) : void {
        let retour: string[] = [];
        let URL = cEnv._serverURL + `/sql`;
        let postData: Object = {
            'infos': [ 'addPossibleActivitee', activitee ],
            'retour': 'iListActivitee',
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        return;
    }


    public getBulletinSalaire(idPersonneOrIdBulletin: number, mois: number = null, annee: number = null): iBulletinSalaire[] {
        let retour: iBulletinSalaire[] = [];
        let URL : string = "";
        if ((mois == null) || (annee == null))
            URL = cEnv._serverURL + `/bulletinSalaire/${idPersonneOrIdBulletin}`;
        else
            URL = cEnv._serverURL + `/bulletinSalaire/${idPersonneOrIdBulletin}/${mois}/${annee}`;

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

    public getAllActivitee(idBulletinSalaire: number): iActivite[] {
        let retour: iActivite[] = [];
        let URL = cEnv._serverURL + `/activitee/${idBulletinSalaire}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iActivite[]);
        }
        this.updateDateActiviteAfterWS (retour);
        return retour;
    }


    private updateDateActiviteAfterWS(retour: iActivite[]) {
        for (let i : number = 0; i < retour.length; i++) {
            let sDebut: string = retour[i].debut as unknown as string;
            sDebut = sDebut.replace("[UTC]", "");
            retour[i].debut = new Date(sDebut);

            let sFin: string = retour[i].fin as unknown as string;
            sFin = sFin.replace("[UTC]", "");
            retour[i].fin = new Date(sFin);
        }
    }

    public addActivite(ficheId: number, jour: number, activite: string, debut: Date, fin: Date, tarifHoraire: number) : number {
        let retour: number = -1;
        let URL = cEnv._serverURL + `/activitee`;
        let postData: iActivite = {
            'id': -1,
            'idBulletinSalaire': ficheId,
            'activite': activite,
            'debut': debut,
            'fin': fin,
            'tarifHoraire': tarifHoraire
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iActivite).id;
        }
        return retour;
    }

    public deleteActivite (sActiviteId : string) : boolean {
        let URL = cEnv._serverURL + `/activitee/${sActiviteId}`;
        let oResp: boolean = this.t.sendDeleteWS(URL);
        return true;
    }

    public generatePdf(ficheId: number) : iPdf {
        let retour: iPdf = null;
        let URL = cEnv._serverURL + `/pdf`;
        let postData: iPdf = {
            'id': -1,
            'idBulletinSalaire': ficheId,
            'file': ''
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPdf);
        }
        return retour;
    }

    public getPdf(ficheId: number): iPdf {
        let retour: iPdf = null;
        let URL = cEnv._serverURL + `/pdf/file/${ficheId}`;
        let oResp: boolean = this.t.GetPDFFileWS(URL, 'ficheSalaire.pdf');
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPdf);
        }
        return retour;
    }

    public getAllPDFFromBulletinId(ficheId: number): iPdf[] {
        let retour: iPdf[] = null;
        let URL = cEnv._serverURL + `/pdf/${ficheId}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPdf[]);
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