package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/*
export interface iActivite {
    id: number;
    idBulletinSalaire: number;
    tarifHoraire: number;
    activite: string;
    debut: Date;
    fin: Date;
}
 */
public class Activite extends ObjectWrapper implements iObjectWrapper {

	public Activite() {
		id = -1;
	}

    int id;
    int idBulletinSalaire;
    float tarifHoraire;
    String activite;
    Date debut;
    Date fin;
     
    

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



	public Date getDebut() {
		return debut;
	}



	public void setDebut(Date debut) {
		this.debut = debut;
	}



	public Date getFin() {
		return fin;
	}



	public void setFin(Date fin) {
		this.fin = fin;
	}



	@Override
    public String toString(){
        return String.format("[Activite: (id=%d)(Activite=%s)(debut=%s)(fin=%s)(idBulletin=%d)(tarif=%f)]", 
        		getId(), getActivite(), _sdf.format(getDebut()), _sdf.format(getFin()), getIdBulletinSalaire(), getTarifHoraire());
    }



	@Override
	public String[] allColone() {
		return new String[] {
				"id","idBulletinSalaire", "tarifHoraire", "activitee", "debut", "fin"
		};
	}

	@Override
	public void set(String key, Object val) throws E4AException {
		switch (key) {
			case "id": this.setId((Integer)val);break;
			case "idBulletinSalaire": this.setIdBulletinSalaire((Integer)val);break;
			case "tarifHoraire": this.setTarifHoraire((Float)val);break;
			case "activitee": this.setActivite((String)val);break;
			case "debut": this.setDebut((Date)val);break;
			case "fin": this.setFin((Date)val);break;
			default: throw new E4AException("Activite :Key["+key+"] Inconnue");
		}
	}

}
