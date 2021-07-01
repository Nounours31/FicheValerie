package sfa.fichevalerie.mysql.api.datawrapper;

import java.util.Date;


public class Personne extends ObjectWrapper implements iObjectWrapper{

	public Personne() {
		id = -1;
	}

    String nom;
    String genre;
    Date date;
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
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date timestamp) {
    	this.date = timestamp;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    
    @Override
    public String toString(){
        return String.format("[Personne: (id=%d)(genre=%s)(nom=%s)(date creation=%s)]", 
        		getId(), getGenre(), getNom(), _sdf.format(getDate()));
    }

}
