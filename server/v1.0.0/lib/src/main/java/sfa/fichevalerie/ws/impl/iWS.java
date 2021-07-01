package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.Response;

public interface iWS {
	public Response run();

	public String whoami();

	public void setArgs(String string, int idPersonne);
	public void setArgs(String string, String mois);

	public void setArgs(String string, Object p);
	public Object getArgs(String string);
}
