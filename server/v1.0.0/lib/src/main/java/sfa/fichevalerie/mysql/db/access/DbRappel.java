package sfa.fichevalerie.mysql.db.access;

import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;
import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class DbRappel extends DB implements iDB {
	final String tableName = "rappel";

	public DbRappel() {
		super(DbRappel.class.getName());
	}

	public Rappel[] getAllRappels (int idBulletin) {
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
		return null;
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

