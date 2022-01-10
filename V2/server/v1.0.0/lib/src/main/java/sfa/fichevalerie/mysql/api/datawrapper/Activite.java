package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class Activite extends ObjectWrapper implements iObjectWrapper {

	public Activite() {
		id = -1;
	}

    int id;
    int idBulletinSalaire;
    float tarifHoraire;
    String activite;
    long gmtepoch_debut;
    long gmtepoch_fin;
     
    

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIdBulletinSalaire() {
		return idBulletinSalaire;
	}



	public void setIdBulletinSalaire(int idBulletinSalaire) {
		this.idBulletinSalaire = idBulletinSalaire;
	}



	public float getTarifHoraire() {
		return tarifHoraire;
	}



	public void setTarifHoraire(float tarifHoraire) {
		this.tarifHoraire = tarifHoraire;
	}



	public String getActivite() {
		return activite;
	}



	public void setActivite(String activite) {
		this.activite = activite;
	}



	public long getGmtepoch_debut() {
		return gmtepoch_debut;
	}



	public void setGmtepoch_debut(long debut) {
		this.gmtepoch_debut = debut;
	}



	public long getGmtepoch_fin() {
		return gmtepoch_fin;
	}



	public void setGmtepoch_fin(long fin) {
		this.gmtepoch_fin = fin;
	}



	@Override
    public String toString(){
		Date debut = new Date();
		debut.setTime(this.getGmtepoch_debut());
		
		Date fin = new Date();
		fin.setTime(this.getGmtepoch_fin());
        return String.format("[Activite: (id=%d)(Activite=%s)(debut=%s)(fin=%s)(idBulletin=%d)(tarif=%f)]", 
        		getId(), getActivite(), _sdf.format(debut), _sdf.format(fin), getIdBulletinSalaire(), getTarifHoraire());
    }



	@Override
	public String[] allColone() {
		return new String[] {
				"id","idBulletinSalaire", "tarifHoraire", "activitee", "gmtepoch_debut", "gmtepoch_fin"
		};
	}

	@Override
	public void set(String key, Object val) throws E4AException {
		switch (key) {
			case "id": this.setId((Integer)val);break;
			case "idBulletinSalaire": this.setIdBulletinSalaire((Integer)val);break;
			case "tarifHoraire": this.setTarifHoraire((Float)val);break;
			case "activitee": this.setActivite((String)val);break;
			case "gmtepoch_debut": this.setGmtepoch_debut((Long)val);break;
			case "gmtepoch_fin": this.setGmtepoch_fin((Long)val);break;
			default: throw new E4AException("Activite :Key["+key+"] Inconnue");
		}
	}

}
