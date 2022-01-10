package sfa.fichevalerie.ws.entry;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.ActiviteEnum;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.Pdf;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbActivite;
import sfa.fichevalerie.mysql.db.access.DbBulletinSalaire;
import sfa.fichevalerie.tools.E4ABuildVersion;
import sfa.fichevalerie.tools.E4ALogger;
import sfa.fichevalerie.ws.impl.WSForFaignasse;
import sfa.fichevalerie.ws.impl.cWsFactory;
import sfa.fichevalerie.ws.impl.iWS;

@Path("v1.0.0")
public class entrypoint {
	private E4ALogger _logger = null;
	
	public entrypoint() {
		this._logger = E4ALogger.getLogger(entrypoint.class.getName());
	}

	// ----------------------------------------------------
	// Verifie juste que le tomee n'est pas HS
	// ----------------------------------------------------
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Object test() {
        return Response.ok().type(MediaType.APPLICATION_JSON).entity("OK").build();
    }

    



	// ----------------------------------------------------
	// Gestion des pdf
	// ----------------------------------------------------
    @GET
    @Path("/pdf/{idBulletin}")
    @Produces("application/pdf")
    public Response getPdfInfoFromBulletin(@PathParam("idBulletin") int idBulletin) {
        iWS ws = cWsFactory.getImpl("getPdfInfoFromBulletin");
        _logger.info(ws.whoami());

        ws.setArgs ("idBulletin", idBulletin);
        return ws.run();
    }

    @GET
    @Path("/pdf/file/{id}")
    @Produces("application/pdf")
    public Response getFile(@PathParam("id") int id) {
    	iWS ws = cWsFactory.getImpl("getPdfFile");
    	_logger.info(ws.whoami());

    	ws.setArgs ("id", id);
    	return ws.run();
    }

    @POST
    @Path("/pdf")
    @Produces("application/pdf")
    public Response createPdfFile(Pdf p) {
    	iWS ws = cWsFactory.getImpl("createPdfFile");
    	_logger.info(ws.whoami());

    	ws.setArgs ("pdf", p);
    	return ws.run();
    }


    
	// ----------------------------------------------------
	// Gestion des personnes
	// ----------------------------------------------------
    @GET
    @Path("/personne")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersonnes() {
    	iWS ws = cWsFactory.getImpl("getAllPersonnes");
    	_logger.info(ws.whoami());

    	return ws.run();
    }

    @GET
    @Path("/personne/{genre}/{nom}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonne(@PathParam("genre") String genre, @PathParam("nom") String nom) {
    	iWS ws = cWsFactory.getImpl("getAllPersonnes");
    	_logger.info(ws.whoami());

    	ws.setArgs ("genre", genre);
    	ws.setArgs ("nom", nom);    	
    	return ws.run();
    }

    @GET
    @Path("/personne/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonne(@PathParam("id") int id) {
    	iWS ws = cWsFactory.getImpl("getAllPersonnes");
    	_logger.info(ws.whoami());

    	ws.setArgs ("id", id);
    	return ws.run();
    }

    @DELETE
    @Path("/personne/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePersonne(@PathParam("id") int id) {
    	iWS ws = cWsFactory.getImpl("deletePersonne");
    	_logger.info(ws.whoami());

    	ws.setArgs ("id", id);
    	return ws.run();
    }

    @POST
    @Path("/personne")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response personne(Personne p) {
    	iWS ws = cWsFactory.getImpl("createPersonne");
    	_logger.info(ws.whoami());

    	ws.setArgs ("personne", p);
    	return ws.run();
    }


    
	// ----------------------------------------------------
	// Gestion des fiches de salaire
	// ----------------------------------------------------
    @GET
    @Path("/bulletinSalaire/{idPersonne}/{mois}/{annee}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBulletinSalaireByPersonneAndMois(@PathParam("idPersonne") int idPersonne, @PathParam("mois") int mois, @PathParam("annee") int annee) {
        iWS ws = cWsFactory.getImpl("getBulletinSalaire");
        _logger.info(ws.whoami());

        ws.setArgs ("idPersonne", idPersonne);
        ws.setArgs ("mois", mois);
        ws.setArgs ("annee", annee);
        return ws.run();
    }

    @GET
    @Path("/bulletinSalaire/{idBulletin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBulletinSalaireById(@PathParam("idBulletin") int idBulletin) {
        iWS ws = cWsFactory.getImpl("getBulletinSalaire");
        _logger.info(ws.whoami());

        ws.setArgs ("idBulletin", idBulletin);
        return ws.run();
    }

    @POST
    @Path("/bulletinSalaire")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBulletinSalaire(BulletinSalaire p) {
    	iWS ws = cWsFactory.getImpl("createBulletinSalaire");
    	_logger.info(ws.whoami());

    	ws.setArgs ("BulletinSalaire", p);
    	return ws.run();
    }


    

    // ----------------------------------------------------
	// Gestion des activitees d'une fiche de salaire
	// ----------------------------------------------------
    @DELETE
    @Path("/activitee/{idActivitee}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteActivitee(@PathParam("idActivitee") int idActivitee) {
        iWS ws = cWsFactory.getImpl("deleteActivitee");
        _logger.info(ws.whoami());

        ws.setArgs ("idActivitee", idActivitee);
        return ws.run();
    }

    @GET
    @Path("/activitee/bulletin/{idBulletin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiviteFromIdBulletinSalaire(@PathParam("idBulletin") int idBulletin) {
        iWS ws = cWsFactory.getImpl("getActiviteeByBulletin");
        _logger.info(ws.whoami());

        ws.setArgs ("idBulletin", idBulletin);
        return ws.run();
    }

    @GET
    @Path("/activitee/{idActivitee}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivite(@PathParam("idActivitee") int idActivitee) {
        iWS ws = cWsFactory.getImpl("getActivitee");
        _logger.info(ws.whoami());

        ws.setArgs ("idActivitee", idActivitee);
        return ws.run();
    }

    @POST
    @Path("/activitee")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createActivite(Activite a) {
        iWS ws = cWsFactory.getImpl("createActivitee");
        _logger.info(ws.whoami());

        ws.setArgs ("activite", a);
        return ws.run();
    }

    // ----------------------------------------------------
	// Gestion de la liste des activitees possible (ActiviteEnum)
	// ----------------------------------------------------
    @DELETE
    @Path("/activiteeEnum/{idActiviteeEnum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteActiviteeEnum(@PathParam("idActiviteeEnum") int idActiviteeEnum) {
        iWS ws = cWsFactory.getImpl("deleteActiviteeEnum");
        _logger.info(ws.whoami());

        ws.setArgs ("idActiviteeEnum", idActiviteeEnum);
        return ws.run();
    }

    @GET
    @Path("/activiteeEnum")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPossibleActiviteEnum() {
        iWS ws = cWsFactory.getImpl("getActiviteeEnum");
        _logger.info(ws.whoami());
        return ws.run();
    }


    @POST
    @Path("/activiteeEnum")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createActiviteEnum(ActiviteEnum a) {
        iWS ws = cWsFactory.getImpl("createActiviteeEnum");
        _logger.info(ws.whoami());

        ws.setArgs ("ActiviteeEnum", a);
        return ws.run();
    }

    
    
    // ----------------------------------------------------
	// Divers
	// ----------------------------------------------------
    // -------
    // get des info de build
    // -------
    @GET
    @Path("/build")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBuild() {
        _logger.info("getBuild");
        E4ABuildVersion versionBuilder = new E4ABuildVersion();
        String rc = versionBuilder.getBuildVersion();
        return Response.ok().type(MediaType.TEXT_PLAIN).entity(rc).build();
    }

    // -------
    // set des traces
    // -------
    @GET
    @Path("/debug/{sLevel}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setDebug(@PathParam("sLevel") String sLevel) {
        iWS ws = cWsFactory.getImpl("setDebug");
        _logger.info(ws.whoami());
        
        if (!sLevel.toLowerCase().equals("level"))
        	E4ALogger.setEnvLevel (sLevel);
        
        String s = _logger.getLogLevel();
        _logger.fatal("Now log level is set to: " + _logger.getLogLevel());
        return Response.ok().type(MediaType.TEXT_PLAIN).entity(s).build();
    }

    // -------
    // envoie de sql en dur / json en dur POUR recup des info extra et tres specifique
    // -------
    @GET
    @Path("/sql/infosExtras/{idBulletin}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getExtraInfos(@PathParam("idBulletin") int idBulletin) {
    	WSForFaignasse wsff = new WSForFaignasse();
    	return wsff.getExtraInfos(idBulletin);    	
    }
    	
    
    @POST
    @Path("/sql")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response parseSQL(@Context HttpServletRequest request) {
    	WSForFaignasse wsff = new WSForFaignasse();
    	return wsff.parseSQL(request);
    }
}

