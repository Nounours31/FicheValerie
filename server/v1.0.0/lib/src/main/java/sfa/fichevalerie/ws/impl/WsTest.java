package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.Response;

import sfa.fichevalerie.tools.E4AError;

public class WsTest extends WS {

	public WsTest() {
		super();
	}

	
	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}

}
