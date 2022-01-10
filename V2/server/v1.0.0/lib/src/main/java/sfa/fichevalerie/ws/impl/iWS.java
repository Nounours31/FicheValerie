package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.Response;

public interface iWS {
	public Response run();

	public Response get();
	public Response post();
	public Response delete();

	public String whoami();

	public void setType (eWsTypeGetPost s);
	public eWsTypeGetPost getType ();

	public void setArgs(String string, int idPersonne);
	public void setArgs(String string, String mois);

	public void setArgs(String string, Object p);
	public Object getArgs(String string);
}
