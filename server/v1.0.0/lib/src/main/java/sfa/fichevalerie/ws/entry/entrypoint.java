package sfa.fichevalerie.ws.entry;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.tools.E4ALogger;
import sfa.fichevalerie.ws.impl.cWsFactory;
import sfa.fichevalerie.ws.impl.iWS;

@Path("/fichevalerie/v1.0.0")
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
    
    private static final String FILE_PATH = "c:\\Android-Book.pdf";

    @GET
    @Path("/pdf/{id}")
    @Produces("application/pdf")
    public Response getFile(@PathParam("id") String input) {

        File file = new File(FILE_PATH);

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=new-android-book.pdf");
        return response.build();

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
    public Response getBulletinSalaire(@PathParam("idPersonne") int idPersonne, @PathParam("mois") int mois, @PathParam("annee") int annee) {
    	iWS ws = cWsFactory.getImpl("getBulletinSalaire");
    	_logger.info(ws.whoami());

    	ws.setArgs ("idPersonne", idPersonne);
    	ws.setArgs ("mois", mois);
    	ws.setArgs ("annee", annee);
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

    @POST
    @Path("/activite")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createActivite(Activite a) {
    	iWS ws = cWsFactory.getImpl("createActivite");
    	_logger.info(ws.whoami());

    	ws.setArgs ("activite", a);
    	return ws.run();
    }
}

