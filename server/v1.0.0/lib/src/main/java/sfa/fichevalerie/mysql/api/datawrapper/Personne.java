package sfa.fichevalerie.mysql.api.datawrapper;

import java.util.Date;

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

}
