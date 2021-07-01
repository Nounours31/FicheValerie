package sfa.fichevalerie.mysql.api.datawrapper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;


public class BulletinSalaire extends ObjectWrapper implements iObjectWrapper {

	public BulletinSalaire() {
		id = -1;
	}

    int id;
    int idPersonne;
    int mois;
    int annee;
    Calendar date;
     
    
    public Date getDate() {
        return date.getTime();
    }
    public void setDate(Timestamp timestamp) {
    	this.date = Calendar.getInstance();
    	this.date.setTimeInMillis( timestamp.getTime());
    }
    
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

    @Override
    public String toString(){
        return String.format("[BulletinSalaire: (id=%d)(idPersonne=%d)(mois=%d)(annee=%d)(date creation=%s)]", 
        		getId(), getIdPersonne(), getMois(), getAnnee(), _sdf.format(getDate()));
    }

}