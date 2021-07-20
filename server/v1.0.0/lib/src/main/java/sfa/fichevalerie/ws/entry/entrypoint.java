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
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.Pdf;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbActivite;
import sfa.fichevalerie.mysql.db.access.DbBulletinSalaire;
import sfa.fichevalerie.tools.E4ALogger;
import sfa.fichevalerie.ws.impl.cWsFactory;
import sfa.fichevalerie.ws.impl.iWS;

@Path("v1.0.0")
public class entrypoint {
	private E4ALogger _logger = null;
	
	public entrypoint() {
		this._logger = E4ALogger.getLogger(entrypoint.class.getName());
	}

    @POST
    @Path("/mois") 
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHostedZone(@Context UriInfo uriInfo) {
    	_logger.info("=====================================================================================================================================");
    	_logger.info("=============>  POST /hostedzone -> createHostedZone");
    	_logger.info("=====================================================================================================================================");
    	iWS ws = cWsFactory.getImpl("mois");
    	_logger.info(ws.whoami());
		
    	return ws.run();    	
    }


	// ----------------------------------------------------
	// WS de test pour la probe
	// Verifie juste que le tomee n'est pas HS
	// ----------------------------------------------------
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Object test() {
    	_logger.info("=====================================================================================================================================");
    	_logger.info("=============> GET test ");
    	_logger.info("=====================================================================================================================================");

    	iWS ws = cWsFactory.getImpl("test");
    	_logger.info(ws.whoami());

    	
		return ws.run();    	
    }


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

    @GET
    @Path("/debug/${sLevel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setDebug(@PathParam("sLevel") String sLevel) {
        iWS ws = cWsFactory.getImpl("setDebug");
        _logger.info(ws.whoami());
        E4ALogger.setEnvLevel (sLevel);
        return Response.ok().type(MediaType.APPLICATION_JSON).entity("OK").build();
    }

    @GET
    @Path("/sql/infosExtras/${idBulletin}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getExtraInfos(@Context HttpServletRequest request) {
        iWS ws = cWsFactory.getImpl("setDebug");
        _logger.info(ws.whoami());
        E4ALogger.setEnvLevel (sLevel);
        return Response.ok().type(MediaType.APPLICATION_JSON).entity("OK").build();
    	
    }
    	
    @POST
    @Path("/sql")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response parseSQL(@Context HttpServletRequest request) {
        iWS ws = cWsFactory.getImpl("parseSQL");
        _logger.info(ws.whoami());

        int buffer[] = new int[1024];
        int i, j = 0;
        try {
            BufferedInputStream reader = new BufferedInputStream(request.getInputStream());
            while (((i = reader.read()) != -1) && (j < 1024))
                buffer[j++] = i;
        } catch (Exception e) {
            _logger.fatal (e.getMessage());
            e.printStackTrace();
        }

        String input = new String (buffer, 0, j);
        _logger.debug("String:");
        _logger.debug(input);

        JSONObject jsonObject = null;
        try {
            jsonObject =  new JSONObject(input);
            _logger.debug("JSON: " + jsonObject.toString());
        } catch (Exception e) {
            _logger.fatal (e.getMessage());
            e.printStackTrace();
        }
        if (jsonObject.has("retour")) {
        	_logger.debug("SQL retour is :" + jsonObject.getString("retour"));
            if (jsonObject.getString("retour").equals("iBulletinSalaire")) {
            	_logger.debug("iBulletinSalaire");
                DbBulletinSalaire x = new DbBulletinSalaire();
                BulletinSalaire[] y = x.RunSelect(jsonObject.getString("sql"));
                return Response.ok().type(MediaType.APPLICATION_JSON).entity(y).build();
            }
            if (jsonObject.getString("retour").equals("iListActivitee")) {
            	_logger.debug("iListActivitee");
                if (jsonObject.has("sql")) {
                	_logger.debug("sql: " + jsonObject.getString("sql"));
                    DbActivite x = new DbActivite();
                    if (jsonObject.getString("sql").equals("getAllPossibleActivitees")) {
                        String[] y = x.getAllPossibleActivitees();
                        return Response.ok().type(MediaType.APPLICATION_JSON).entity(y).build();
                    }
                }
                if (jsonObject.getJSONArray("infos") != null ) {
                	_logger.debug("infos: array dispo");
                	JSONArray infos = jsonObject.getJSONArray("infos");
                	if (!infos.isEmpty() && (infos.length() == 2) && (infos.getString(0).equals("addPossibleActivitee"))) {
                		 DbActivite x = new DbActivite();
                		 String s = infos.getString(1);
                         int y = x.insertAPossibleActivitees(s);

                         _logger.debug("infos: addPossibleActivitee : " + s);
                         
                         return Response.ok().type(MediaType.APPLICATION_JSON).entity(new Integer(y)).build();
                	}
                	else {
                    	_logger.debug("infos: length" + infos.length());
                    	for (int pipoIndex = 0; pipoIndex < infos.length(); pipoIndex++) {
                    		_logger.debug(String.format("infos: [%d] : %s",pipoIndex, infos.getString(pipoIndex)));
						} 
                	}
                }
            }
        }
        return Response.serverError().type(MediaType.APPLICATION_JSON).entity("KO").build();
    }
}

