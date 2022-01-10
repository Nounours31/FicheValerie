export default class cDuration {
    
    private durationMilliSec : number;

    constructor() {
        this.durationMilliSec = 0.0;
    }

    public setActivitees(debut: Date, fin: Date) : void {
        this.durationMilliSec = fin.getTime() - debut.getTime();
    }

    public set(annee: number, mois: number, jour: number, heureDebut: string, heureFin: string) : void {
        let infoDebut: string[] = heureDebut.split(':');
        let infoFin: string[] = heureFin.split(':');

        if ((infoDebut.length == 2) && (infoFin.length == 2)) {
            let heure: number = Number.parseInt(infoDebut[0]);
            let minute: number = Number.parseInt(infoDebut[1]);
            let debut: Date = new Date(annee, mois, jour, heure, minute, 0, 0);

            heure = Number.parseInt(infoFin[0]);
            minute = Number.parseInt(infoFin[1]);
            let fin: Date = new Date(annee, mois, jour, heure, minute, 0, 0);

            this.durationMilliSec = fin.getTime() - debut.getTime();
        }
    }

    public asMilliSec(): number {
        return this.durationMilliSec;
    }

    public asSec(): number {
        return this.asMilliSec() / 1000.0;
    }

    public asMinute(): number {
        return this.asSec() / 60.0;
    }
    public asHour(): number {
        return this.asMinute() / 60.0;
    }

    public asDay(): number {
        return this.asHour() / 24.0;
    }


}