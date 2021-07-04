package sfa.fichevalerie.mysql.api.datawrapper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/*

export interface iBulletinSalaire {
    id: number;
    idPersonne: number;
    mois: string;
    annee: number;
    tarifHoraire: number;
}


 */
public class BulletinSalaire extends ObjectWrapper implements iObjectWrapper {

	public BulletinSalaire() {
		id = -1;
	}

    int id;
    int idPersonne;
    int mois;
    int annee;
    float tarifHoraire;
     
        
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
	public int getIdPersonne() {
		return idPersonne;
	}
	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public float getTarifHoraire() {
		return tarifHoraire;
	}
	public void setTarifHoraire(float tarifHoraire) {
		this.tarifHoraire = tarifHoraire;
	}

    @Override
    public String toString(){
        return String.format("[BulletinSalaire: (id=%d)(idPersonne=%d)(mois=%d)(annee=%d)(tarifHoraire=%f)]", 
        		getId(), getIdPersonne(), getMois(), getAnnee(), getTarifHoraire());
    }

}
