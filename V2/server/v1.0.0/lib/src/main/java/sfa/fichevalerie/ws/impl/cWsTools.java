package sfa.fichevalerie.ws.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sfa.fichevalerie.tools.E4AError;
import sfa.fichevalerie.tools.E4ALogger;


public class cWsTools {
	private E4ALogger _logger;
	
	public cWsTools() {
		_logger = E4ALogger.getLogger("WSImpl");
	}

	
	// -------------------------------------------------
	// constriction generique de la reponse XML
	// -------------------------------------------------
	protected Response buildTxtResponse(String s, E4AError err) {
		Response rc = null;
		_logger.debug(String.format("::buildTxtResponse [Object = %s]", s));
		_logger.debug(String.format("::buildTxtResponse [err = %s]", err.toString()));
		try {
			if (err.SUCCEEDED()) {
				rc = Response.ok().type(MediaType.TEXT_PLAIN).entity(s).build();
			}
			else {
				rc = Response.status(err.getHTTPrc()).type(MediaType.TEXT_PLAIN).entity(s).build();
			}
		}
		catch (Exception e) {
			rc = Response.status(500).type(MediaType.APPLICATION_XML).entity("<Error>"+e.getMessage()+"</Error>").build();
			_logger.fatal(String.format("::buildResponse [Erreur 500 --> %s]", e.getMessage()));
		}
		return rc;
	}




	public Response buildFromError(E4AError err) {
		Response rc = null;
		_logger.debug(String.format("::buildFromError [err = %s]", err.toString()));
		try {
			if (err.SUCCEEDED()) {
				rc = Response.ok().type(MediaType.APPLICATION_JSON).entity(err.toString()).build();
			}
			else {
				rc = Response.status(err.getHTTPrc()).type(MediaType.TEXT_PLAIN).entity(err.toString()).build();
			}
		}
		catch (Exception e) {
			rc = Response.status(500).type(MediaType.APPLICATION_JSON).entity(e.getMessage()).build();
			_logger.fatal(String.format("::buildResponse [Erreur 500 --> %s]", e.getMessage()));
		}
		return rc;
	}
	
}
