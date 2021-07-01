package sfa.fichevalerie.ws.impl;

import javax.ws.rs.core.Response;

import sfa.fichevalerie.tools.E4AError;

public class WsTest extends WS {

	public WsTest() {
		super();
	}

	@Override
	public Response run() {
		E4AError err = E4AError.None;
		return _tools.buildTxtResponse("OK - UpAndRunning", err);
	}

	
	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}

}
