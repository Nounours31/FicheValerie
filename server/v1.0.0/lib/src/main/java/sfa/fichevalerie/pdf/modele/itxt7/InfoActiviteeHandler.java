package sfa.fichevalerie.pdf.modele.itxt7;

import org.apache.commons.math3.stat.inference.TestUtils;
import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;

import java.util.ArrayList;
import java.util.Objects;

public class InfoActiviteeHandler implements Comparable<InfoActiviteeHandler> {

        int tauxIndice = 0;
        float taux = 0f;
        long nbHeureMinutes = 0l;


    public InfoActiviteeHandler() {
    }

    public static boolean containsTaux(ArrayList<InfoActiviteeHandler> rc, float tauxActivitee) {
        for (InfoActiviteeHandler x: rc) {
            if (x.getTaux() == tauxActivitee)
                return true;
        }
        return false;
    }

    public static int indexOfTaux(ArrayList<InfoActiviteeHandler> rc, float tauxActivitee) {
        for (int i = 0; i < rc.size(); i++) {
            if (rc.get(i).getTaux() == tauxActivitee)
                return i;
        }
        return -1;
    }

    public static InfoActiviteeHandler getHandler(ArrayList<InfoActiviteeHandler> rc, Activite uneActivite, BulletinSalaire bs) {
        float tauxActivitee = uneActivite.getTarifHoraire();
        float tauxParDefaut = bs.getTarifHoraire();
        return InfoActiviteeHandler.getHandler(rc, tauxActivitee, tauxParDefaut);
    }

    public static InfoActiviteeHandler getHandler(ArrayList<InfoActiviteeHandler> rc, DepassementForfaitaire dep, BulletinSalaire bs) {
        float tauxActivitee = dep.getTarifHoraire();
        float tauxParDefaut = bs.getTarifHoraire();
        return InfoActiviteeHandler.getHandler(rc, tauxActivitee, tauxParDefaut);
    }

    public static InfoActiviteeHandler getHandler(ArrayList<InfoActiviteeHandler> rc, Rappel rap, BulletinSalaire bs) {
        float tauxActivitee = rap.getTarifHoraire();
        float tauxParDefaut = bs.getTarifHoraire();
        return InfoActiviteeHandler.getHandler(rc, tauxActivitee, tauxParDefaut);
    }

    private static InfoActiviteeHandler getHandler(ArrayList<InfoActiviteeHandler> rc, float tauxActivitee, float tauxParDefaut) {
        InfoActiviteeHandler courranteInfoActivite = null;
        if (tauxActivitee < 0)
            tauxActivitee = tauxParDefaut;

        if (!InfoActiviteeHandler.containsTaux(rc, tauxActivitee)) {
            InfoActiviteeHandler infoActivite = new InfoActiviteeHandler();
            infoActivite.setNbHeureMinutes(0l);
            infoActivite.setTaux(tauxActivitee);
            infoActivite.setTauxIndice(rc.size());
            courranteInfoActivite = infoActivite;
            rc.add(infoActivite);
        }
        else {
            courranteInfoActivite = rc.get(InfoActiviteeHandler.indexOfTaux(rc, tauxActivitee));
        }
        return courranteInfoActivite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoActiviteeHandler)) return false;
        InfoActiviteeHandler that = (InfoActiviteeHandler) o;
        return Float.compare(that.getTaux(), getTaux()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTauxIndice(), getTaux());
    }

    public int getTauxIndice() {
        return tauxIndice;
    }

    public void setTauxIndice(int tauxIndice) {
        this.tauxIndice = tauxIndice;
    }

    public float getTaux() {
        return taux;
    }

    public void setTaux(float taux) {
        this.taux = taux;
    }

    public long getNbHeureMinutes() {
        return nbHeureMinutes;
    }

    public void setNbHeureMinutes(long nbHeureMinutes) {
        this.nbHeureMinutes = nbHeureMinutes;
    }

    @Override
    public int compareTo(InfoActiviteeHandler o) {
        return Float.compare(this.getTaux(), o.getTaux());
    }

    public void AddNbHeureMinutes(long l) {
        this.nbHeureMinutes += l;
    }
}
