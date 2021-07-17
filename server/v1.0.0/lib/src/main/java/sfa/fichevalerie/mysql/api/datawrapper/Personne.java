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
    public String[] allColone() {
        return new String[] {
                "id","nom", "genre"
        };
    }

    @Override
    public void set(String key, Object val) throws E4AException {
        switch (key) {
            case "id": this.setId((Integer)val);break;
            case "nom": this.setNom((String)val);break;
            case "genre": this.setGenre((String)val);break;
            default: throw new E4AException("Rappel :Key["+key+"] Inconnue");
        }
    }
}
