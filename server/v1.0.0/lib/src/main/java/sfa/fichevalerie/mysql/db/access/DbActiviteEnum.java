package sfa.fichevalerie.mysql.db.access;

import java.util.ArrayList;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.ActiviteEnum;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

public class DbActiviteEnum extends DB implements iDB {
	final String tableName = "listactivite";

	public DbActiviteEnum() {
		super(DbActiviteEnum.class.getName());
	}


	public int insertActiviteEnum (ActiviteEnum a) {
		/*_logger.info("insertActiviteEnum - date debut: " + _sdf.format(a.getGmtepoch_debut()));
		
		double x = Math.random();
		float tauxHoraire = a.getTarifHoraire();
		String sql = String.format("INSERT INTO activite (idBulletinSalaire, activitee, gmtepoch_debut, gmtepoch_fin, date, tarifHoraire) VALUES ('%d', '%s', '%s', '%s', '%s', %f)", 
				a.getIdBulletinSalaire(), a.getActiviteEnum(), a.getGmtepoch_debut(), a.getGmtepoch_fin(), _sdf.format(new Date()), tauxHoraire);

		try {
			return this.insertAsRest(sql);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}*/
		return 0;
	}

	public ActiviteEnum[] getAllPossibleActiviteEnum() {
		ArrayList<ActiviteEnum> rc = new ArrayList<ActiviteEnum>();
		_logger.debug("getAllPossibleActiviteEnumes START");

		cInfoFromSelect res = null;;
		try {
			res = this.selectAsRest("select id, nom from listactivitee");
			_logger.debug("Resultat requete: " + res.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		if (res != null) {
			for (int i = 0; i < res.size(); i++) {
				Hashtable<String, Object> row = res.get(i);
				if (row != null) {
					ActiviteEnum a = new ActiviteEnum();
					a.setId((Integer)row.get("id"));
					a.setNom((String)row.get("nom"));
					rc.add(a);
				}
			}
		}

		ActiviteEnum[] retour = new ActiviteEnum [rc.size()];
		retour= rc.toArray(retour);

		return retour;
	}

	public int insertAPossibleActiviteEnumes(String s) {
		_logger.debug("insertAPossibleActiviteEnumes START");

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

/*
	public ActiviteEnum[] getAllActiviteEnumes(int idBulletinSalaire) {
		String sql = String.format("select * from activite where (idBulletinSalaire = %d) ORDER BY gmtepoch_debut ASC", idBulletinSalaire);
		return this.getAllActiviteEnumes(sql);		
	}

	public ActiviteEnum[] getActiviteEnume(int intValue) {
		String sql = String.format("select * from activite where (id = %d) ORDER BY gmtepoch_debut ASC", intValue);
		return this.getAllActiviteEnumes(sql);		
	}
*/

	@Override
	public ActiviteEnum encode(Hashtable<String, Object> hash) throws E4AException {
		ActiviteEnum rc = new ActiviteEnum();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}



}

