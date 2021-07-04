package sfa.fichevalerie.mysql.db.access;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

public class DbBulletinSalaire extends DB implements iDB {
	final String tableName = "bulletinsalaire";

	public DbBulletinSalaire() {
		super(DbBulletinSalaire.class.getName());
		// TODO Auto-generated constructor stub
	}

	public int insertBulletinSalaire (BulletinSalaire bs) {
		String sql = String.format("insert into bulletinsalaire (idPersonne, mois, annee, date) values (%d, %d, %d, '%s')", 
				bs.getIdPersonne(), bs.getMois(), bs.getAnnee(), _sdf.format(new Date()));
		
		try {
			return this.insertAsRest(sql);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	public BulletinSalaire[] getAllBulletinSalaire(int idPersonne, int mois, int annee) {
		String sql = String.format("select * from bulletinsalaire where ((idPersonne=%d) and (mois=%d) and (annee=%d))", idPersonne, mois, annee);
		return this.getAllBulletinSalaire(sql);
	}
	public BulletinSalaire[] getAllBulletinSalaire() {
		String sql = "select * from bulletinsalaire";
		return this.getAllBulletinSalaire(sql);		
	}
	private BulletinSalaire[] getAllBulletinSalaire(String sql) {
		_logger.debug("getAllPersonnes START");
		
		ArrayList<BulletinSalaire> lBulletinSalaire = new ArrayList<BulletinSalaire>();		
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
					BulletinSalaire p = null;
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
						lBulletinSalaire.add(p);
					}
				}
			}
		}
		
		_logger.debug("Nb personne au total: " +lBulletinSalaire.size());
		
		if (lBulletinSalaire.size() > 0) {
			BulletinSalaire[] rc = new BulletinSalaire[lBulletinSalaire.size()];
			rc = lBulletinSalaire.toArray(rc);
			
			return rc;
		}
		return null;
	}

	@Override
	public BulletinSalaire encode(Hashtable<String, Object> hash) throws E4AException {
		BulletinSalaire rc = new BulletinSalaire();
		if (hash.containsKey("id"))
			rc.setId(((Integer)hash.get("id")).intValue());
		
		if (hash.containsKey("idPersonne"))
			rc.setIdPersonne(((Integer)hash.get("idPersonne")).intValue());
		
		if (hash.containsKey("mois"))
			rc.setMois(((Integer)hash.get("mois")).intValue());

		if (hash.containsKey("annee"))
			rc.setAnnee(((Integer)hash.get("annee")).intValue());
		
		return rc;	
	}

}
