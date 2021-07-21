package sfa.fichevalerie.mysql.db.access;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

public class DbRappel extends DB implements iDB {
	final String tableName = "rappel";

	public DbRappel() {
		super(DbRappel.class.getName());
	}



	public int insertOrUpdate(int idBulletin, float nbHeure) {
		Rappel[] r = this.getAllRappels(idBulletin);
		if ((r == null) || (r.length == 0)) {
			return insert (idBulletin, nbHeure);
		}
		else {
			return update (idBulletin, nbHeure);
		}
			
	}

	private int update(int idBulletin, float nbHeure) {
		_logger.debug("Start update - id:" + idBulletin + " nbheure: " + nbHeure);
    	String sql = String.format ("update rappel set dureeenheure=%f where idBulletinSalaire=%d", nbHeure, idBulletin);
    	
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
    	String sql = String.format ("insert into rappel (idBulletinSalaire, idBulletinSalaireOrigine, dureeenheure, tarifHoraire, date, status) values (%d, %d, %f, %f, '%s', %d)", 
    			idBulletin, -1, nbHeure, -1.0f, _sdf.format(new Date()), 1);
    	
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

	
	public Rappel[] getAllRappels (int idBulletin) {
		_logger.debug("Start getAllRappels - id:" + idBulletin);
		String sql = String .format("select * from rappel where (idBulletinSalaire = %d)", idBulletin);

		ArrayList<Rappel> lRappel = new ArrayList<Rappel>();
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
					Rappel p = null;
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
						lRappel.add(p);
					}
				}
			}
		}

		_logger.debug("Nb personne au total: " +lRappel.size());

		if (lRappel.size() > 0) {
			Rappel[] rc = new Rappel[lRappel.size()];
			rc = lRappel.toArray(rc);

			return rc;
		}
		return new Rappel[] {};
	}

	@Override
	public Rappel encode(Hashtable<String, Object> hash) throws E4AException {
		Rappel rc = new Rappel();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}



}

