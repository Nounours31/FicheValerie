package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/*
export interface iPdf {
    id: number;
    idBulletinSalaire: number;
    file: string;
}

 */
public class Pdf extends ObjectWrapper implements iObjectWrapper {

	public Pdf() {
		id = -1;
	}

    int id;
    int idBulletinSalaire;
    String File;
    
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

	public String getFile() {
		return File;
	}

	public void setFile(String file) {
		File = file;
	}

	@Override
    public String toString(){
        return String.format("[Pdf: (id=%d)(file=%s)(idBulletin=%d)]", 
        		getId(), getFile(), getIdBulletinSalaire());
    }

    @Override
	public String[] allColone() {
		return new String[] {
				"id","idBulletinSalaire", "idBulletinSalaireOrigine", "status", "date"
		};
	}

	@Override
	public void set(String key, Object val) throws E4AException {
		switch (key) {
			case "id": this.setId((Integer)val);break;
			case "idBulletinSalaire": this.setIdBulletinSalaire((Integer)val);break;
			case "idBulletinSalaireOrigine": this.setIdBulletinSalaireOrigine((Integer)val);break;
			case "status": this.setStatus((Integer)val);break;
			case "date": this.setDate((Date)val);break;
			default: throw new E4AException("Rappel :Key["+key+"] Inconnue");
		}
	}
}
