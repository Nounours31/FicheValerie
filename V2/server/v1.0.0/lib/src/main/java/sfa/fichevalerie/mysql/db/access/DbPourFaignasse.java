package sfa.fichevalerie.mysql.db.access;

import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.tools.E4AException;

public class DbPourFaignasse extends DB implements iDB {
	final String tableName = "DbPourFaignasse";

	public DbPourFaignasse() {
		super(DbPourFaignasse.class.getName());
	}


	@Override
	public iObjectWrapper encode(Hashtable<String, Object> hash) throws E4AException {
		throw new E4AException("DbPourFaignasse: Not impemented ... ");
	}
}

