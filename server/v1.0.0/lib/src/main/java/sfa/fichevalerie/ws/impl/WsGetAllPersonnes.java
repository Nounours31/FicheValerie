package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbPersonne;

public class WsGetAllPersonnes extends WS {

	public WsGetAllPersonnes() {
		super();
	}
	
	@Override
	public Response run() {
		DbPersonne db = new DbPersonne();
		
		String genre = (String) this.getArgs("genre");
		String nom = (String) this.getArgs("nom");
		
		Personne[] lP;
		if ((genre != null) && (nom != null))
			lP = db.getAllPersonnes(genre, nom);
		else
			lP = db.getAllPersonnes();
		
		_logger.debug("Personne trouvees: " + (lP == null ? "NULL ..." : lP.length));

		Response r = Response.ok().type(MediaType.APPLICATION_JSON).entity(lP).build();
		return r;
	}

	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}

}
