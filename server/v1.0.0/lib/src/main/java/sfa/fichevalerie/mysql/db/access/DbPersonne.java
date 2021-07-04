package sfa.fichevalerie.mysql.db.access;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

public class DbPersonne extends DB  implements iDB {
	final String tableName = "personne";
	final String attr[] = {
			"id", "genre", "nom", "date"
	};

	public DbPersonne()  {
		super(DbPersonne.class.getName());
	}
	
	public Personne[] getAllPersonnes(String genre, String nom) {
		String sql = String.format("select * from personne where ((genre = '%s') and (nom = '%s')) order by nom",
				genre, nom);
		
		return getAllPersonnes(sql);
	}

	public Personne[] getAllPersonnes(int id) {
		String sql = String.format("select * from personne where (id = %d)",
				id);
		
		return getAllPersonnes(sql);
	}

	public Personne[] getAllPersonnes () {
		String sql = "select * from personne order by nom";		
		return getAllPersonnes(sql);
	}
	public Personne[] getAllPersonnes (String sql) {
		_logger.debug("getAllPersonnes START sql: "+sql);
		
		ArrayList<Personne> lPersonn = new ArrayList<Personne>();
		
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
					Personne p = null;
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
						lPersonn.add(p);
					}
				}
			}
		}
		
		_logger.debug("Nb personne au total: " +lPersonn.size());
		
		if (lPersonn.size() > 0) {
			Personne[] rc = new Personne[lPersonn.size()];
			rc = lPersonn.toArray(rc);
			
			return rc;
		}
		return null;
	}

	@Override
	public Personne encode(Hashtable<String, Object> hash) throws E4AException {
		Personne rc = new Personne();
		if (hash.containsKey("id"))
			rc.setId(((Integer)hash.get("id")).intValue());
		
		if (hash.containsKey("genre"))
			rc.setGenre((String)hash.get("genre"));
		
		if (hash.containsKey("nom"))
			rc.setNom((String)hash.get("nom"));
				
		return rc;
	}

	public int createPersonne(Personne p) throws Exception {
		String sql = String.format("insert into personne (genre, nom, date) values ('%s', '%s', '%s')",
				p.getGenre(), p.getNom(), _sdf.format(new Date()));
		
		return this.insertAsRest(sql);
	}


	
}
