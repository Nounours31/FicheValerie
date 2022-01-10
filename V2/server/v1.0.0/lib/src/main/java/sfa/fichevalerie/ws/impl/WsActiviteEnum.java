package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.ActiviteEnum;
import sfa.fichevalerie.mysql.db.access.DbActiviteEnum;

public class WsActiviteEnum extends WS implements iWS {

	
	@Override
	public Response post() {
		ActiviteEnum p = (ActiviteEnum)this.getArgs ("ActiviteeEnum");
		_logger.info("ActiviteeEnum to create: " + p.toString());

		DbActiviteEnum x = new DbActiviteEnum();
        int y = x.insertAPossibleActiviteEnumes(p.getNom());

		Response r;
		if (y > 0) {
			p.setId(y);
			r = Response.ok().type(MediaType.APPLICATION_JSON).entity(p).build();
		}
		else
			r = Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(p).build();
		return r;
	}


	@Override
	public Response get() {
		DbActiviteEnum x = new DbActiviteEnum();
    	ActiviteEnum[] y = x.getAllPossibleActiviteEnum();
        return Response.ok().type(MediaType.APPLICATION_JSON).entity(y).build();
	}

	@Override
	public Response delete() {
		DbActiviteEnum bs = new DbActiviteEnum();
		int idActivitee = (Integer)this.getArgs ("idActiviteeEnum");
		_logger.debug("activite to delete: " + idActivitee);

		try {
			int i = bs.deleteAsRest(String.format("delete from listactivitee where (id = %d)", idActivitee));
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
