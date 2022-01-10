package sfa.fichevalerie.mysql.db.access;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

public class DbDepassementForfaitaire extends DB implements iDB {
	final String tableName = "depassementforfaitaire";

	public DbDepassementForfaitaire() {
		super(DbDepassementForfaitaire.class.getName());
	}

	
	public int insertOrUpdate(int idBulletin, float nbHeure) {
		DepassementForfaitaire[] d = this.getAllDepassementForfaitaire(idBulletin);
		if ((d == null) || (d.length == 0)) {
			return insert (idBulletin, nbHeure);
		}
		else {
			return update (idBulletin, nbHeure);
		}
			
	}

	private int update(int idBulletin, float nbHeure) {
		_logger.debug("Start update - id:" + idBulletin + " nbheure: " + nbHeure);
    	String sql = String.format ("update depassementforfaitaire set dureeenheure=%f where idBulletinSalaire=%d", nbHeure, idBulletin);
    	
        try {
        	int rc = this.updateAsRest(sql);
            return rc;
        }
        catch (Exception e) {
        	_logger.fatal(e.getMessage());
        	e.printStackTrace();
        }
        return -1;
	}
	
	private int insert (int idBulletin, float nbHeure) {
		_logger.debug("Start insert - id:" + idBulletin + " nbheure: " + nbHeure);
    	String sql = String.format ("insert into  depassementforfaitaire (idBulletinSalaire ,  dureeenheure ,  date ,  tarifHoraire ) values (%d, %f, '%s', %f)", 
    			idBulletin, nbHeure,  _sdf.format(new Date()), -1.0f);
    	
        try {
        	int rc = this.insertAsRest(sql);
            return rc;
        }
        catch (Exception e) {
        	_logger.fatal(e.getMessage());
        	e.printStackTrace();
        }
        return -1;
	}
	
	public DepassementForfaitaire[] getAllDepassementForfaitaire (int idBulletin) {
		_logger.debug("Start getAllDepassementForfaitaire - id:" + idBulletin);
		String sql = String .format("select * from depassementforfaitaire where (idBulletinSalaire = %d)", idBulletin);

		ArrayList<DepassementForfaitaire> lDepassementForfaitaire = new ArrayList<DepassementForfaitaire>();
		cInfoFromSelect res = null;;
		try {
			res = this.selectAsRest(sql);
			_logger.debug("Resultat requete: " + res.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		if (res != null) {
			for (int i = 0; i < res.size(); i++) {
				Hashtable<String, Object> row = res.get(i);
				if (row != null) {
					DepassementForfaitaire p = null;
					try {
						p = this.encode(row);
					}
					catch (E4AException e) {
						System.out.println(e.getMessage());
						System.out.println("Raison du KO ?" + res.dump(row));
						p = null;
					}

					if (p != null) {
						_logger.debug("Personne trouvee: " + p.toString());
						lDepassementForfaitaire.add(p);
					}
				}
			}
		}

		_logger.debug("Nb personne au total: " +lDepassementForfaitaire.size());

		if (lDepassementForfaitaire.size() > 0) {
			DepassementForfaitaire[] rc = new DepassementForfaitaire[lDepassementForfaitaire.size()];
			rc = lDepassementForfaitaire.toArray(rc);

			return rc;
		}
		return new DepassementForfaitaire[] {};
	}


	@Override
	public DepassementForfaitaire encode(Hashtable<String, Object> hash) throws E4AException {
		DepassementForfaitaire rc = new DepassementForfaitaire();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}
}

