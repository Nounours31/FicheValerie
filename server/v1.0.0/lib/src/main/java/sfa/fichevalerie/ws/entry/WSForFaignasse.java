package sfa.fichevalerie.ws.entry;

import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;
import sfa.fichevalerie.mysql.db.access.DbActivite;
import sfa.fichevalerie.mysql.db.access.DbBulletinSalaire;
import sfa.fichevalerie.mysql.db.access.DbDepassementForfaitaire;
import sfa.fichevalerie.mysql.db.access.DbRappel;
import sfa.fichevalerie.tools.E4ALogger;

public class WSForFaignasse {
	private E4ALogger _logger = null;
	private SimpleDateFormat _sdf = null;
	public WSForFaignasse() {
		_logger = E4ALogger.getLogger(this.getClass().getCanonicalName());
		_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public Response parseSQL(HttpServletRequest request) {
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
        
        Response rc = this.parseSQLForBulletinSalaire(jsonObject);
        if (rc == null)
        	rc = this.parseSQLForActivitee(jsonObject);

        if (rc == null)
        	rc = this.parseSQLForExtraActivitee(jsonObject);
        
        if (rc == null)
            rc = Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("KO").build();

        return rc;
	}
	
	private Response parseSQLForExtraActivitee(JSONObject jsonObject) {
        if (jsonObject.has("retour")) {
        	_logger.debug("SQL retour is :" + jsonObject.getString("retour"));
            if (jsonObject.getString("retour").equals("iHeureReport") && jsonObject.has("infos")) {
            	_logger.debug("iHeureReport");
            	
            	JSONObject infos = jsonObject.getJSONObject("infos");
            	if (infos.has("idBulletin") && infos.has("nbHeure")) {
                	DbRappel dbr = new DbRappel();
                	int rc = dbr.insertOrUpdate(infos.getInt("idBulletin"), infos.getFloat("nbHeure"));
                    return Response.ok().type(MediaType.APPLICATION_JSON).entity(new Integer(rc)).build();
            	}
            }
            
            if (jsonObject.getString("retour").equals("iDepassementForfaitaire") && jsonObject.has("infos")) {
            	_logger.debug("iDepassementForfaitaire");
            	
            	JSONObject infos = jsonObject.getJSONObject("infos");
            	if (infos.has("idBulletin") && infos.has("nbHeure")) {
                	DbDepassementForfaitaire dbd = new DbDepassementForfaitaire();
                	int rc = dbd.insertOrUpdate(infos.getInt("idBulletin"), infos.getFloat("nbHeure"));
                    return Response.ok().type(MediaType.APPLICATION_JSON).entity(new Integer(rc)).build();
            	}
            }
        }
        return null;
	}

	private Response parseSQLForBulletinSalaire(JSONObject jsonObject) {
        if (jsonObject.has("retour")) {
        	_logger.debug("SQL retour is :" + jsonObject.getString("retour"));
            if (jsonObject.getString("retour").equals("iBulletinSalaire")) {
            	_logger.debug("iBulletinSalaire");
                DbBulletinSalaire x = new DbBulletinSalaire();
                BulletinSalaire[] y = x.RunSelect(jsonObject.getString("sql"));
                return Response.ok().type(MediaType.APPLICATION_JSON).entity(y).build();
            }
        }
        return null;
	}

	private Response parseSQLForActivitee(JSONObject jsonObject) {
        if (jsonObject.has("retour")) {
        	_logger.debug("SQL retour is :" + jsonObject.getString("retour"));
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
        return null;
	}

	public Response getExtraInfos(int idBulletin) {
		_logger.debug(String.format("getExtraInfos: idBulletin[%d]",idBulletin));
		DbDepassementForfaitaire dbdf = new DbDepassementForfaitaire();
		DbRappel dbrap = new DbRappel();
		
		try {
			DepassementForfaitaire[] addDep =  dbdf.getAllDepassementForfaitaire(idBulletin);
			_logger.debug(String.format("getExtraInfos: DepassementForfaitaire[taille - %d]",addDep.length));

			Rappel[] addRap =  dbrap.getAllRappels(idBulletin);
			_logger.debug(String.format("getExtraInfos: Rappel[taille - %d]",addRap.length));

			
			JSONArray jsonARappel = new JSONArray();
			JSONObject jsonORappel = null;
			for (Rappel r : addRap) {
				jsonORappel = new JSONObject();
				jsonORappel.putOnce("id", r.getId());			
				jsonORappel.putOnce("idBulletin", r.getIdBulletinSalaire());			
				jsonORappel.putOnce("idBulletinOrigine", r.getIdBulletinSalaireOrigine());			
				jsonORappel.putOnce("tarif", r.getTarifHoraire());			
				jsonORappel.putOnce("duree", r.getDureeenheure());			
				jsonORappel.putOnce("date", r.getDate());			
				jsonORappel.putOnce("status", r.getStatus());
				jsonARappel.put (jsonORappel);
				_logger.debug("Add a rappel");
			}
			_logger.debug("Rappel arrays : " + jsonARappel.toString(4));

			JSONArray jsonADep = new JSONArray();
			JSONObject jsonODep = null;
			for (DepassementForfaitaire d : addDep) {
				jsonODep = new JSONObject();
				jsonODep.putOnce("id", d.getId());			
				jsonODep.putOnce("idBulletin", d.getIdBulletinSalaire());			
				jsonODep.putOnce("tarif", d.getTarifHoraire());			
				jsonODep.putOnce("duree", d.getDureeenheure());			
				jsonODep.putOnce("date", d.getDate());			
				jsonADep.put (jsonODep);
				_logger.debug("Add a depassement");
			}
			_logger.debug("Depassement arrays : " + jsonADep.toString(4));

			JSONObject jsonORetour = new JSONObject();
			jsonORetour.putOnce("rappel", jsonARappel);
			jsonORetour.putOnce("depassement", jsonADep);
			_logger.debug("Add all arrays : " + jsonORetour.toString(4));
            return Response.ok().type(MediaType.APPLICATION_JSON).entity(jsonORetour.toString()).build();
        }
        catch (Exception e) {
        	_logger.fatal(e.getMessage());
        	e.printStackTrace();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("KO").build();
	}
}
