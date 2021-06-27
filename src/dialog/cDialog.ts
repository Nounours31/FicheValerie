



export default class cDialog {
    protected static _NomPrefixe : string;

    constructor(nom : string) {
        cDialog._NomPrefixe = nom;
    }

    public Draw(): HTMLDivElement { throw new Error("virtual unimplemented"); }
    public addCallBack(): void { throw new Error("virtual unimplemented"); }
}