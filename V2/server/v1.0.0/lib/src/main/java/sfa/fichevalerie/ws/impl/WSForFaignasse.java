package sfa.fichevalerie.ws.impl;

import java.io.BufferedInputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;
import sfa.fichevalerie.mysql.db.access.DbBulletinSalaire;
import sfa.fichevalerie.mysql.db.access.DbDepassementForfaitaire;
import sfa.fichevalerie.mysql.db.access.DbPourFaignasse;
import sfa.fichevalerie.mysql.db.access.DbRappel;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4ALogger;

public class WSForFaignasse {
	private E4ALogger _logger = null;
	private SimpleDateFormat _sdf = null;
	public WSForFaignasse() {
		_logger = E4ALogger.getLogger(this.getClass().getCanonicalName());
		_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public Response parseSQL(HttpServletRequest request) {
		int iBuffersize = 16;
		int iFenetresize = iBuffersize;
		
        byte buffer[] = new byte[iBuffersize];
        int nbLu = 0;
        
        try {
            BufferedInputStream reader = new BufferedInputStream(request.getInputStream());
            int start = 0; 
            while ((nbLu = reader.read(buffer, start, iFenetresize)) == iFenetresize) {
            	iBuffersize += iFenetresize;
            	byte newBuffer[] = new byte [iBuffersize];
            	for (int pipoIndice = 0; pipoIndice < (start + nbLu); pipoIndice++) {
            		newBuffer[pipoIndice] = buffer[pipoIndice];
            	}
            	buffer = newBuffer;
            	start += iFenetresize;
            }
        } catch (Exception e) {
            _logger.fatal (e.getMessage());
            e.printStackTrace();
        }
        

        String input = new String (buffer, Charset.forName("UTF8"));
        _logger.debug("String:" + input);

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
        	rc = this.parseSQLForExtraActivitee(jsonObject);

        if (rc == null)
        	rc = this.parseSQLForEnvInfo(jsonObject);
        
        if (rc == null)
            rc = Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("KO").build();

        return rc;
	}
	
	private Response parseSQLForEnvInfo(JSONObject jsonObject) {
        if (jsonObject.has("retour")) {
        	_logger.debug("SQL retour is :" + jsonObject.getString("retour"));
            if (jsonObject.getString("retour").equals("iEnvInfo")) {
            	_logger.debug("iEnvInfo");
        		DbPourFaignasse db = new DbPourFaignasse();

            	if (jsonObject.has("infos")) {
            		JSONObject infos = jsonObject.getJSONObject("infos");
            	
	            	StringBuffer sql = new StringBuffer("update env set ");
	            	boolean hasChangeSOmething = false;
	            	boolean isFirst = true;
	            	
	            	if (infos.has("storePath")) {
	            		if (!isFirst)
	            			sql.append(",");
	            		sql.append ("storePath='"+infos.getString("storePath")+"'");
	            		hasChangeSOmething = true;
	            		isFirst = false;
	            	}
	            	if (infos.has("CSG")) {
	            		if (!isFirst)
	            			sql.append(",");
	            		sql.append ("CSG="+infos.getFloat("CSG"));
	            		hasChangeSOmething = true;
	            		isFirst = false;
	            	}
	            	if (infos.has("TauxImposition")) {
	            		if (!isFirst)
	            			sql.append(",");
	            		sql.append ("TauxImposition="+infos.getFloat("TauxImposition"));
	            		hasChangeSOmething = true;
	            		isFirst = false;
	            	}
	            	sql.append (" where 1");
	            	if (hasChangeSOmething) {
	            		try {
							db.updateAsRest(sql.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
	            	}
	            }
            	try {
					cInfoFromSelect response = db.selectAsRest("select * from env");
					Hashtable<String, Object> row = response.get(0);
					JSONObject retour = new JSONObject();
					String[] infosToRead = {"storePath", "CSG", "TauxImposition"};
					if (row.containsKey(infosToRead[0])) retour.put(infosToRead[0], (String)row.get(infosToRead[0])); 						
					if (row.containsKey(infosToRead[1])) retour.put(infosToRead[1], ((Float)row.get(infosToRead[1])).floatValue()); 						
					if (row.containsKey(infosToRead[2])) retour.put(infosToRead[2], ((Float)row.get(infosToRead[2])).floatValue());		
					return Response.ok().type(MediaType.APPLICATION_JSON).entity(retour.toString()).build();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }            
        }
        return null;
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
