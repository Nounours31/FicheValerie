package sfa.fichevalerie.ws.impl;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbActiviteEnum;
import sfa.fichevalerie.mysql.db.access.DbPersonne;

public class WsPersonne extends WS implements iWS {

	@Override
	public String whoami() {
		return "WsCreatePersonne";
	}

	@Override
	public Response post() {
		DbPersonne db = new DbPersonne();
		Personne p = (Personne)this.getArgs ("personne");
		_logger.debug("Persone to create: " + p.toString());
    	
    	int id = -1;
    	try {
    		id = db.createPersonne(p);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		id = -1;
    	}
		_logger.debug("createPersonne: " + id);

		Response r = null;
		if (id > 0) {
			p.setId(id);
			r = Response.ok().type(MediaType.APPLICATION_JSON).entity(p).build();			
		}
		else
			r = Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(p).build();
		return r;
	}

	@Override
	public Response get() {
		DbPersonne db = new DbPersonne();

		String genre = (String) this.getArgs("genre");
		String nom = (String) this.getArgs("nom");

		Integer  x = ((Integer) this.getArgs("id"));
		int  id = (x != null)?x.intValue():-1;

		Personne[] lP;
		if ((genre != null) && (nom != null))
			lP = db.getAllPersonnes(genre, nom);
		else if (id >= 0) {
			lP = db.getAllPersonnes(id);
		}
		else
			lP = db.getAllPersonnes();

		_logger.debug("Personne trouvees: " + (lP == null ? "NULL ..." : lP.length));

		Response r = Response.ok().type(MediaType.APPLICATION_JSON).entity(lP).build();
		return r;
	}
	
	@Override
	public Response delete() {
		DbPersonne bs = new DbPersonne();
		int id = (Integer)this.getArgs ("id");
		_logger.debug("Personne to delete: " + id);

		try {
			int i = bs.deleteAsRest(String.format("delete from personne where (id = %d)", id));
			return Response.ok().type(MediaType.APPLICATION_JSON).entity(new Integer(i)).build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(new Integer(0)).build();
	}

}
