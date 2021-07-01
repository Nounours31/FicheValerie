package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.db.access.DbBulletinSalaire;

public class WsGetBulletinSalaire extends WS implements iWS {

	@Override
	public String whoami() {
		return "WsGetBulletinSalaire";
	}

	@Override
	public Response run() {
		DbBulletinSalaire db = new DbBulletinSalaire();
		
		Integer i = (Integer)this.getArgs ("idPersonne");
		int idPersonne = i.intValue();
    	
		i = (Integer) this.getArgs ("mois");
    	int mois = i.intValue();
    	
    	i = (Integer) this.getArgs ("annee");
    	int annee = i.intValue();
    	
    	BulletinSalaire[] lP = db.getAllBulletinSalaire(idPersonne, mois, annee);
		_logger.debug("Bulletin trouvees: " + (lP == null ? "NULL ..." : lP.length));

		Response r = Response.ok().type(MediaType.APPLICATION_JSON).entity(lP).build();
		return r;
	}
}
