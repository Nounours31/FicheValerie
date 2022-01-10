package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.Response;

public class WsDebug extends WS {

	public WsDebug() {
		super();
	}


	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}

}
