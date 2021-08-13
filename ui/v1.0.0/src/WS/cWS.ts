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
        let URL = cEnv.getTomeeURL() + '/personne';
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
            URL = cEnv.getTomeeURL() + `/personne/${idOrGenre}/${nom}`;
        } else {
            URL = cEnv.getTomeeURL() + `/personne/${idOrGenre}`;
        }
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPersonne[]);
        }
        return retour;
    }



    public getBulletinSalaire(idPersonneOrIdBulletin: number, mois: number = null, annee: number = null): iBulletinSalaire[] {
        let retour: iBulletinSalaire[] = [];
        let URL : string = "";
        if ((mois == null) || (annee == null))
            URL = cEnv.getTomeeURL() + `/bulletinSalaire/${idPersonneOrIdBulletin}`;
        else
            URL = cEnv.getTomeeURL() + `/bulletinSalaire/${idPersonneOrIdBulletin}/${mois}/${annee}`;

        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iBulletinSalaire[]);
        }
        return retour;
    }


    public createBulletinSalaire(mois: number, annee: number, p: iPersonne, tarifHoraire: number): number {
        let retour: number = -1;
        let URL = cEnv.getTomeeURL() + `/bulletinSalaire`;
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
        let URL = cEnv.getTomeeURL() + `/activitee/bulletin/${idBulletinSalaire}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iActivite[]);
        }
        return retour;
    }
    public getActivitee(activiteId: string): iActivite[] {
        let retour: iActivite[] = [];
        let URL = cEnv.getTomeeURL() + `/activitee/${activiteId}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iActivite[]);
        }
        return retour;
    }




    public addActivite(ficheId: number, jour: number, activite: string, debut: Date, fin: Date, tarifHoraire: number) : number {
        let retour: number = -1;
        let URL = cEnv.getTomeeURL() + `/activitee`;
        let postData: iActivite = {
            'id': -1,
            'idBulletinSalaire': ficheId,
            'activite': activite,
            'gmtepoch_debut': debut.getTime(),
            'gmtepoch_fin': fin.getTime(),
            'tarifHoraire': tarifHoraire
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iActivite).id;
        }
        return retour;
    }

    public deleteActivite (sActiviteId : string) : boolean {
        let URL = cEnv.getTomeeURL() + `/activitee/${sActiviteId}`;
        let oResp: boolean = this.t.sendDeleteWS(URL);
        return true;
    }

    public generatePdf(ficheId: number) : iPdf {
        let retour: iPdf = null;
        let URL = cEnv.getTomeeURL() + `/pdf`;
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
        let URL = cEnv.getTomeeURL() + `/pdf/file/${ficheId}`;
        let oResp: boolean = this.t.GetPDFFileWS(URL, 'ficheSalaire.pdf');
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPdf);
        }
        return retour;
    }

    public getAllPDFFromBulletinId(ficheId: number): iPdf[] {
        let retour: iPdf[] = null;
        let URL = cEnv.getTomeeURL() + `/pdf/${ficheId}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as iPdf[]);
        }
        return retour;
    }
    

    public addNewPersonne(genre: string, nom: string): iPersonne {
        let retour : iPersonne = null;
        let URL = cEnv.getTomeeURL() + '/personne';
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



    // ------------------------------------------------
    // les cas tordu par lequel je passe par du SQL
    // ------------------------------------------------
    public setHeureReport(_idBulletinSalaire: number, nbHeure: number) {
        let URL = cEnv.getTomeeURL() + `/sql`;
        let postData: Object = {
            'infos': { 'idBulletin' : _idBulletinSalaire, 'nbHeure' : nbHeure},
            'retour': 'iHeureReport',
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        return;
    }
    public setDepassementForfaitaire(_idBulletinSalaire: number, nbHeure: number) {
        let URL = cEnv.getTomeeURL() + `/sql`;
        let postData: Object = {
            'infos': { 'idBulletin' : _idBulletinSalaire, 'nbHeure' : nbHeure},
            'retour': 'iDepassementForfaitaire',
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        return;
    }

    public getHeureReport(_idBulletinSalaire: number): object {
        return this.getExtra(_idBulletinSalaire);
    }
    public getDepassementForfaitaire(_idBulletinSalaire: number) : object {
        return this.getExtra(_idBulletinSalaire);
    }
    public getExtra(_idBulletinSalaire: number) : object {
        let retour: object = {};
        let URL = cEnv.getTomeeURL() + `/sql/infosExtras/${_idBulletinSalaire}`;
        let oResp: boolean = this.t.sendGetWS(URL);
        if (this.t.status) {
            retour = ((this.t.data as unknown) as object);
        }
        return retour;
    }

    
    public getBulletinSalaireFromSQL(sql: string): iBulletinSalaire[] {
        let retour: iBulletinSalaire[] = [];
        let URL = cEnv.getTomeeURL() + `/sql`;
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
        let URL = cEnv.getTomeeURL() + `/sql`;
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
        let URL = cEnv.getTomeeURL() + `/sql`;
        let postData: Object = {
            'infos': [ 'addPossibleActivitee', activitee ],
            'retour': 'iListActivitee',
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        return;
    }


    // --------------------------------
    // WS De gestion de l'envt
    // --------------------------------
    public getSetEnvInfo(storePath?: string, CSG? : number, TauxImposition? : number): any {
        let retour: string[] = [];
        let URL = cEnv.getTomeeURL() + `/sql`;
        let postData: any = {
            'retour': 'iEnvInfo',
            'infos': {},
        }
        if ((storePath != undefined) && (storePath.length > 3)) {
            postData.infos.storePath = storePath;
        }
        if (CSG != undefined) {
            postData.infos.CSG = CSG;
        }
        if (TauxImposition != undefined) {
            postData.infos.TauxImposition = TauxImposition;
        }
        let oResp: boolean = this.t.sendPostWS(URL, postData);
        if (this.t.status) {
            retour = (this.t.data as any);
        }
        return retour;
    }


    public getServerBuildVersion() : string {
        let retour: string = "";
        let URL = cEnv.getTomeeURL() + `/build`;
        let oResp: boolean = this.t.sendGetWS(URL, 'text');
        if (this.t.status) {
            retour = ((this.t.data as unknown) as string);
        }
        return retour;
   }

    public setServerDebug(level : string): string {
        let retour: string = "";
        let URL = cEnv.getTomeeURL() + `/debug/${level}`;
        let oResp: boolean = this.t.sendGetWS(URL, 'text');
        if (this.t.status) {
            retour = ((this.t.data as unknown) as string);
        }
        return retour;
   }

    public get data() : JSON { return this._data; }
}