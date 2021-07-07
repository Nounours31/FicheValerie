import { iPersonne } from "./WS/iWSMessages";

export class cControler {
    private static instance: cControler;

    private _idPersonne: number;
    private _pPersonne: iPersonne;
    private _tarifHoraire: number;
    private _mois: number;
    private _annee: number;

    /**
     * The Singleton's constructor should always be private to prevent direct
     * construction calls with the `new` operator.
     */
    private constructor() { }

    /**
     * The static method that controls the access to the singleton instance.
     *
     * This implementation let you subclass the Singleton class while keeping
     * just one instance of each subclass around.
     */
    public static getInstance(): cControler {
        if (!cControler.instance) {
            cControler.instance = new cControler();
        }

        return cControler.instance;
    }

    /**
     * Finally, any singleton should define some business logic, which can be
     * executed on its instance.
     */

    get idPersonne(): number {
        return this._idPersonne;
    }

    set idPersonne(id: number) {
        if (id < 1) {
            throw new Error("fullName has a max length of " + id);
        }

        this._idPersonne = id;
    }



    get tarifHoraire(): number {
        return this._tarifHoraire;
    }

    set tarifHoraire(id: number) {
        if ((id > 30) || (id < 10)) {
            throw new Error("fullName has a max length of " + id);
        }

        this._tarifHoraire = id;
    }

    get mois(): number {
        return this._mois;
    }

    set mois(id: number) {
        if ((id > 12) || (id < 0)) {
            throw new Error("fullName has a max length of " + id);
        }

        this._mois = id;
    }

    get annee(): number {
        return this._annee;
    }

    set annee(id: number) {
        if ((id > 2030) || (id < 2020)) {
            throw new Error("fullName has a max length of " + id);
        }

        this._annee = id;
    }

    get personne(): iPersonne {
        return this._pPersonne;
    }

    set personne(id: iPersonne) {
        this._pPersonne = id;
    }
}