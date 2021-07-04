package sfa.fichevalerie.ws.impl;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbPersonne;

public class WsCreatePersonne extends WS implements iWS {

	@Override
	public String whoami() {
		return "WsCreatePersonne";
	}

	@Override
	public Response run() {
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
}
