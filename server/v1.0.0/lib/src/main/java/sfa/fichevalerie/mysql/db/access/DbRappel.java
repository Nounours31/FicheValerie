package sfa.fichevalerie.mysql.db.access;

import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;
import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.tools.E4AException;

import java.util.Date;
import java.util.Hashtable;

public class DbRappel extends DB implements iDB {
	final String tableName = "activite";

	public DbRappel() {
		super(DbRappel.class.getName());
	}


	@Override
	public iObjectWrapper encode(Hashtable<String, Object> hash) throws E4AException {
		Rappel rc = new Rappel();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}
}

