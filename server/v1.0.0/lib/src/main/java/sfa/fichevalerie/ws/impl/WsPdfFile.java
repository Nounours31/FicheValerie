package sfa.fichevalerie.ws.impl;

import java.io.File;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.Pdf;
import sfa.fichevalerie.mysql.db.access.DbPdf;

public class WsPdfFile extends WS implements iWS {
	eWsNameInCaseOfMultipleChoice _type;
	
	public WsPdfFile(eWsNameInCaseOfMultipleChoice type) {
		_type = type;
	}

	@Override
	public Response run() {
		DbPdf db = new DbPdf();
		
		if (this._type.equals(eWsNameInCaseOfMultipleChoice.create)) {
			Pdf p = (Pdf)this.getArgs ("pdf");
			_logger.debug("pdf to create: " + p.toString());
	    	
			p.setId(-1);
	    	try {
	    		p = db.createPdf(p);
	    	}
	    	catch (Exception e) {
	    		System.out.println(e.getMessage());
	    		e.printStackTrace();
	    		p.setId(-1);
	    	}
			_logger.debug("createPdf: " + (p == null ? "NULL" : p.getId()));
	
			Response resp = null;
			if (p != null && p.getId() > 0) {
				resp = Response.status(Status.ACCEPTED).type(MediaType.APPLICATION_JSON).entity(p).build();
			}
			else
				resp = Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(p).build();
			return resp;
		}
		else if (this._type.equals(eWsNameInCaseOfMultipleChoice.get)) {
			_logger.debug("pdf get");
			Pdf p = null;
			int id = ((Integer)this.getArgs ("id")).intValue();	    	
			_logger.debug("pdf id" + id);
	    	try {
	    		p = db.getPdf(id);
				_logger.debug("pdf p" + p.toString());
	    		if (p != null) {
	    			File f = DbPdf.getFileFromPdf (p);
	    			_logger.debug("pdf file a renvoyer: " + f.getAbsolutePath());
	    			_logger.debug("pdf file a renvoyer, existe ? : " + f.exists());
	    			ResponseBuilder response = Response.ok((Object)f);
	    			response = response.header("Content-Disposition", "attachment; filename="+p.getFile());
	    			response = response.header("Content-Type", "application/pdf");
	    	        return response.build();
	    		}
	    			
	    	}
	    	catch (Exception e) {
	    		System.out.println(e.getMessage());
	    		e.printStackTrace();
	    	}
	
			return null;
			
		}
		else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("Unknown " + this._type.name()).build();			
		}
	}

	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}
}
