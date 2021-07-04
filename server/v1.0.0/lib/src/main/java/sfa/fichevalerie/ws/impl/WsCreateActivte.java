package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.db.access.DbActivite;

public class WsCreateActivte extends WS implements iWS {

	@Override
	public Response run() {
		DbActivite bs = new DbActivite();
		Activite p = (Activite)this.getArgs ("activite");
		_logger.debug("BulletinSalaire to create: " + p.toString());

		int id = bs.insertActivite(p);

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
	public String whoami() {
		return this.getClass().getName();
	}
}
