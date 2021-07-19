



export default class cDialog {
    protected _NomClasse : string;

    constructor(nom : string) {
        this._NomClasse = nom;
    }

    public Draw(): string { throw new Error("virtual unimplemented"); }
    public addCallBack(): void { throw new Error("virtual unimplemented"); }
}