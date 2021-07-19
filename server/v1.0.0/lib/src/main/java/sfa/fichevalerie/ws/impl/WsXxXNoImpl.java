package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.Response;

import sfa.fichevalerie.tools.E4AError;

public class WsXxXNoImpl extends WS {
	private String nomRecherche = "";
	public WsXxXNoImpl(String s) {
		super();
		nomRecherche = s;
	}

	@Override
	public Response run() {
		E4AError err = new E4AError("Unimplemented WS - wanted: " + nomRecherche);
		return _tools.buildFromError(err);
	}

	
	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}

}
