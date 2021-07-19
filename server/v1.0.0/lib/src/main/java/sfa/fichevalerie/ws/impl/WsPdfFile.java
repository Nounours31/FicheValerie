package sfa.fichevalerie.ws.impl;

import java.io.File;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import sfa.fichevalerie.mysql.api.datawrapper.Pdf;
import sfa.fichevalerie.mysql.db.access.DbPdf;

public class WsPdfFile extends WS implements iWS {
	public WsPdfFile(){}

	@Override
	public Response get() {
		_logger.debug("pdf get");
		DbPdf db = new DbPdf();

		Integer idInt = (Integer)this.getArgs ("id");
		Integer idBulletin = (Integer)this.getArgs ("idBulletin");

		if (idInt != null) {
			Pdf p = null;
			int id = idInt.intValue();
			_logger.debug("pdf id" + id);
			try {
				p = db.getPdf(id);
				_logger.debug("pdf p" + p.toString());
				if (p != null) {
					File f = DbPdf.getFileFromPdf(p);
					_logger.debug("pdf file a renvoyer: " + f.getAbsolutePath());
					_logger.debug("pdf file a renvoyer, existe ? : " + f.exists());
					ResponseBuilder response = Response.ok((Object) f);
					response = response.header("Content-Disposition", "attachment; filename=" + p.getFile());
					response = response.header("Content-Type", "application/pdf");
					return response.build();
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		if (idBulletin != null) {
			int _idBulletin = idBulletin.intValue();
			_logger.debug("pdf idBulletin" + _idBulletin);
			try {
				Pdf p[] = db.getPdfFromBulletin(_idBulletin);
				return Response.ok().type(MediaType.APPLICATION_JSON).entity(p).build();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return Response.serverError().type(MediaType.APPLICATION_JSON).entity("KO").build();
		}


		return null;

	}

	@Override
	public Response post() {
		DbPdf db = new DbPdf();

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

	@Override
	public String whoami() {
		return this.getClass().getCanonicalName();
	}
}
