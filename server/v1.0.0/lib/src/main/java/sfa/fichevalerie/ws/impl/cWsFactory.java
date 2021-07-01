package sfa.fichevalerie.ws.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sfa.fichevalerie.mysql.api.datawrapper.Personne;

public class cWsFactory {
	
	public static iWS getImpl (String wsNom) {
		iWS rc = null;
		switch (wsNom) {
			case "test" : rc =  new WsTest(); break;
			case "getAllPersonnes" : rc =  new WsGetAllPersonnes(); break;
			case "getBulletinSalaire" : rc =  new WsGetBulletinSalaire(); break;
			case "createPersonne" : rc =  new WsCreatePersonne(); break;
			default : rc = new WsNoImpl();
		}
		return rc;
	}

}
