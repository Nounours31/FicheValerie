package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.omg.PortableServer.POAPackage.AdapterAlreadyExistsHelper;
import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.db.access.DbActivite;

public class WsActivte extends WS implements iWS {

	@Override
	public Response post() {
		DbActivite bs = new DbActivite();
		Activite p = (Activite)this.getArgs ("activite");
		_logger.debug("BulletinSalaire to create: " + p.toString());

		int id = bs.insertActivite(p);

		Response r;
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
		DbActivite bs = new DbActivite();
		int idBulletin = (Integer)this.getArgs ("idBulletin");
		_logger.debug("activite to get: " + idBulletin);

		Activite[] a = bs.getAllActivitees(idBulletin);

		return Response.ok().type(MediaType.APPLICATION_JSON).entity(a).build();
	}

	@Override
	public Response delete() {
		DbActivite bs = new DbActivite();
		int idActivitee = (Integer)this.getArgs ("idActivitee");
		_logger.debug("activite to delete: " + idActivitee);

		try {
			int i = bs.deleteAsRest(String.format("delete from activite where (id = %d)", idActivitee));
			return Response.ok().type(MediaType.APPLICATION_JSON).entity(new Integer(i)).build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(new Integer(0)).build();
	}

	@Override
	public String whoami() {
		return this.getClass().getName();
	}
}
