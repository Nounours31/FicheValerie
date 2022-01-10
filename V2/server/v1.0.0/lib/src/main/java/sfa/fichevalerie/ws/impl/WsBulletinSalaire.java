package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.db.access.DbBulletinSalaire;

public class WsBulletinSalaire extends WS implements iWS {


	@Override
	public Response post() {

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
	public Response get() {
		DbBulletinSalaire db = new DbBulletinSalaire();
		BulletinSalaire[] lP = new BulletinSalaire[] {};

		_logger.debug("Bulletin Get");
		_logger.debug(this.dumpArgs());

		Integer idBulletin = (Integer)this.getArgs ("idBulletin");
		if (idBulletin != null) {
			BulletinSalaire b = db.getAllBulletinSalaire(idBulletin.intValue());
			if (b != null)
				lP = new BulletinSalaire[] { b };

		}
		else {
			Integer i = (Integer) this.getArgs("idPersonne");
			if (i != null) {
				int idPersonne = i.intValue();

				i = (Integer) this.getArgs("mois");
				int mois = i.intValue();

				i = (Integer) this.getArgs("annee");
				int annee = i.intValue();

				lP = db.getAllBulletinSalaire(idPersonne, mois, annee);
			}
		}
		_logger.debug("Bulletin trouvees: " + (lP == null ? "NULL ..." : lP.length));
		Response r = Response.ok().type(MediaType.APPLICATION_JSON).entity(lP).build();
		return r;
	}

	@Override
	public String whoami() {
		return this.getClass().getName();
	}
}
