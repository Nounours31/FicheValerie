package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.Response;

import sfa.fichevalerie.tools.E4AError;

public class WsNoImpl extends WS {
	public WsNoImpl() {
		super();
	}

	@Override
	public Response run() {
		E4AError err = new E4AError("Unimplemented WS");
		return _tools.buildFromError(err);
	}

	
	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}

}
