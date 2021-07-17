package sfa.fichevalerie.mysql.db.access;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class DbDepassementForfaitaire extends DB implements iDB {
	final String tableName = "depassementforfaitaire";

	public DbDepassementForfaitaire() {
		super(DbDepassementForfaitaire.class.getName());
	}



	public DepassementForfaitaire[] getAllDepassementForfaitaire (int idBulletin) {
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
		return null;
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

