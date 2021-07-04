package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.db.access.DbBulletinSalaire;

public class WsCreateBulletinSalaire  extends WS implements iWS {

	@Override
	public Response run() {
		DbBulletinSalaire bs = new DbBulletinSalaire();
		BulletinSalaire p = (BulletinSalaire)this.getArgs ("BulletinSalaire");
		_logger.debug("BulletinSalaire to create: " + p.toString());

		int id = bs.insertBulletinSalaire(p);

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
