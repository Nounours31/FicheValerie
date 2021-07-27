package sfa.fichevalerie.pdf.modele.itxt7;

import java.util.ArrayList;
import java.util.Objects;

import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;

public class StructNbHeureActiviteEtSonTauxHoraire implements Comparable<StructNbHeureActiviteEtSonTauxHoraire> {

	private int uiTauxIndice = 0;
    private float taux = 0f;
    private long nbHeureMinutes = 0l;


    public int getUiTauxIndice() {
		return uiTauxIndice;
	}

	public void setUiTauxIndice(int uiTauxIndice) {
		this.uiTauxIndice = uiTauxIndice;
	}

	public StructNbHeureActiviteEtSonTauxHoraire() {
    }

	// -----------------------------------
    // Liste de tous les taux present sur le bulletin
    // -----------------------------------
    public static int[] getAllTauxByIndice(ArrayList<StructNbHeureActiviteEtSonTauxHoraire> rc) {
    		int[] retour = new int[rc.size()];
    		for (int i = 0; i < rc.size(); i++) {
				retour[i] = rc.get(i).getUiTauxIndice();
			}
    		return retour;
	}

	// -----------------------------------
    // Cherche si toute les activite on le meme taux que taux en parametre
    // -----------------------------------
    public static boolean containsTaux(ArrayList<StructNbHeureActiviteEtSonTauxHoraire> rc, float tauxActivitee) {
        for (StructNbHeureActiviteEtSonTauxHoraire x: rc) {
            if (x.getTaux() == tauxActivitee)
                return true;
        }
        return false;
    }

    // -----------------------------------
    // Recupere le premeire avctivite qui a le meme taux que l etaux en parametre
    // -----------------------------------
    public static int indexOfTaux(ArrayList<StructNbHeureActiviteEtSonTauxHoraire> rc, float tauxActivitee) {
        for (int i = 0; i < rc.size(); i++) {
            if (rc.get(i).getTaux() == tauxActivitee)
                return i;
        }
        return -1;
    }

	public static void AddNbHeureMinutes(ArrayList<StructNbHeureActiviteEtSonTauxHoraire> allDepassementTimeWithLeurTaux, long nbMinuteDepassement, float tarifHoraireSpecifique, float tarifHoraireGlobal) {
		StructNbHeureActiviteEtSonTauxHoraire x = StructNbHeureActiviteEtSonTauxHoraire.getActiviteCorrespondantACeTaux (allDepassementTimeWithLeurTaux, tarifHoraireSpecifique, tarifHoraireGlobal);
		x.AddNbHeureMinutes(nbMinuteDepassement);
	}

    // -------------------------------------------------------------------
    // Construit un hangeler d'activite
    // Ou
    //		si le taux fourni n'existe pas je fait un nouveau pool
    //				avec le taux et un nb horaire = 0
    //      Si le taux existe rendre le pool correspondant a ce taux
    // -------------------------------------------------------------------
    private static StructNbHeureActiviteEtSonTauxHoraire getActiviteCorrespondantACeTaux(ArrayList<StructNbHeureActiviteEtSonTauxHoraire> PreviousPool, float tarifHoraireSpecifique, float tarifHoraireGlobal) {
        StructNbHeureActiviteEtSonTauxHoraire courranteInfoActivite = null;
        float tauxActivitee = tarifHoraireSpecifique;
        if (tauxActivitee < 0)
            tauxActivitee = tarifHoraireGlobal;

        if (!StructNbHeureActiviteEtSonTauxHoraire.containsTaux(PreviousPool, tauxActivitee)) {
            StructNbHeureActiviteEtSonTauxHoraire infoActivite = new StructNbHeureActiviteEtSonTauxHoraire();
            infoActivite.setNbHeureMinutes(0l);
            infoActivite.setTaux(tauxActivitee);
            infoActivite.setUiTauxIndice(PreviousPool.size());
            PreviousPool.add(infoActivite);
        }
        courranteInfoActivite = PreviousPool.get(StructNbHeureActiviteEtSonTauxHoraire.indexOfTaux(PreviousPool, tauxActivitee));
        return courranteInfoActivite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StructNbHeureActiviteEtSonTauxHoraire)) return false;
        StructNbHeureActiviteEtSonTauxHoraire that = (StructNbHeureActiviteEtSonTauxHoraire) o;
        return Float.compare(that.getTaux(), getTaux()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaux());
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
    public int compareTo(StructNbHeureActiviteEtSonTauxHoraire o) {
        return Float.compare(this.getTaux(), o.getTaux());
    }

    public void AddNbHeureMinutes(long l) {
        this.nbHeureMinutes += l;
    }
}
