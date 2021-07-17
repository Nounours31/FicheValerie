package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

import java.util.Date;
import java.util.Hashtable;

/*
export interface iPersonne  {
    id: number;
    genre: string;
    nom: string;
}
 */

public class Personne extends ObjectWrapper implements iObjectWrapper{

	public Personne() {
		id = -1;
	}

    String nom;
    String genre;
    int id;
     
    public String getNom() {
        return nom;
    }
    public void setNom(String firstName) {
        this.nom = firstName;
    }
    
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
       
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    
    @Override
    public String toString(){
        return String.format("[Personne: (id=%d)(genre=%s)(nom=%s)]", getId(), getGenre(), getNom());
    }




    @Override
    public Personne encode(Hashtable<String, Object> hash) throws E4AException {
        Personne rc = new Personne();
        if (hash.containsKey("id"))
            rc.setId(((Integer)hash.get("id")).intValue());

        if (hash.containsKey("genre"))
            rc.setGenre((String)hash.get("genre"));

        if (hash.containsKey("nom"))
            rc.setNom((String)hash.get("nom"));

        return rc;
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
