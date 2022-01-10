package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class ActiviteEnum extends ObjectWrapper implements iObjectWrapper {

	public ActiviteEnum() {
		id = -1;
	}

    int id;
    String nom;
     
    

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}





	public String getNom() {
		return nom;
	}



	public void setNom(String activite) {
		this.nom = activite;
	}

	@Override
    public String toString(){
        return String.format("[ActiviteEnum: (id=%d)(Activite=%s)]", getId(), getNom());
    }



	@Override
	public String[] allColone() {
		return new String[] {
				"id","nom"
		};
	}

	@Override
	public void set(String key, Object val) throws E4AException {
		switch (key) {
			case "id": this.setId((Integer)val);break;
			case "nom": this.setNom((String)val);break;
			default: throw new E4AException("ActiviteEnum :Key["+key+"] Inconnue");
		}
	}

}
