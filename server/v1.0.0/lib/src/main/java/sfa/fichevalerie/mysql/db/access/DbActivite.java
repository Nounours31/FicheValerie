package sfa.fichevalerie.mysql.db.access;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

public class DbActivite extends DB implements iDB {
	final String tableName = "activite";

	public DbActivite() {
		super(DbActivite.class.getName());
	}


	public int insertActivite (Activite a) {
		double x = Math.random();
		float tauxHoraire = a.getTarifHoraire();
		String sql = String.format("INSERT INTO activite (idBulletinSalaire, activitee, debut, fin, date, tarifHoraire) VALUES ('%d', '%s', '%s', '%s', '%s', %f)", 
				a.getIdBulletinSalaire(), a.getActivite(), _sdf.format(a.getDebut()), _sdf.format(a.getFin()), _sdf.format(new Date()), tauxHoraire);

		try {
			return this.insertAsRest(sql);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	public String[] getAllPossibleActivitees() {
		ArrayList<String> rc = new ArrayList<String>();
		_logger.debug("getAllPossibleActivitees START");

		cInfoFromSelect res = null;;
		try {
			res = this.selectAsRest("select nom from listactivitee");
			_logger.debug("Resultat requete: " + res.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		if (res != null) {
			for (int i = 0; i < res.size(); i++) {
				Hashtable<String, Object> row = res.get(i);
				if (row != null) {
					rc.add((String)row.get("nom"));
				}
			}
		}

		String[] retour = new String [rc.size()];
		retour= rc.toArray(retour);

		return retour;
	}

	public int insertAPossibleActivitees(String s) {
		_logger.debug("insertAPossibleActivitees START");

		try {
			int rc = this.insertAsRest(String.format("insert into listactivitee (nom) values ('%s')", s));
			_logger.debug("Resultat requete: " + rc);
			return rc;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return 0;
	}


	public Activite[] getAllActivitees(int idBulletinSalaire) {
		String sql = String.format("select * from activite where (idBulletinSalaire = %d) ORDER BY debut ASC", idBulletinSalaire);
		return this.getAllActivitees(sql);		
	}

	public Activite[] getActivitee(int intValue) {
		String sql = String.format("select * from activite where (id = %d) ORDER BY debut ASC", intValue);
		return this.getAllActivitees(sql);		
	}

	private Activite[] getAllActivitees(String sql) {
		_logger.debug("getAllActivitees START");

		ArrayList<Activite> lActivite = new ArrayList<Activite>();		
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
					Activite p = null;
					try {
						p = this.encode(row);
					}
					catch (E4AException e) {
						System.out.println(e.getMessage());
						System.out.println("Raison du KO ?" + res.dump(row));
						p = null;
					}

					if (p != null) {
						_logger.debug("Activite trouvee: " + p.toString());
						lActivite.add(p);
					}
				}
			}
		}

		_logger.debug("Nb Activite au total: " +lActivite.size());

		if (lActivite.size() > 0) {
			Activite[] rc = new Activite[lActivite.size()];
			rc = lActivite.toArray(rc);

			return rc;
		}
		return null;
	}


	@Override
	public Activite encode(Hashtable<String, Object> hash) throws E4AException {
		Activite rc = new Activite();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}



}

