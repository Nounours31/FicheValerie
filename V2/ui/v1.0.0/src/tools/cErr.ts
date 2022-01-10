
export default class cErr {
    private _mess : string = "";
    private _id : number = 0;
    
    constructor (id: number, mess: string) {
        this._id = id;
        this._mess = mess;
    }

    public succeeded () : boolean {
        return (this._id == 0);
    }

    public toString () : string {
        let x : string = "[Err: ";
        x = this._id.toString ();
        x  += " - Msg: " + this._mess + "]";
        return x;
    }

    public static s_ok() : cErr {
        let r : cErr = new cErr(0, "");
        return r;
    }
}