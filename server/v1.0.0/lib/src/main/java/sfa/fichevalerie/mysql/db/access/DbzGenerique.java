package sfa.fichevalerie.mysql.db.access;

import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.tools.E4AException;

public class DbzGenerique extends DB implements iDB {

	public DbzGenerique(String id) {
		super(id + " - " +  DbzGenerique.class.getName());
	}

	@Override
	public iObjectWrapper encode(Hashtable<String, Object> obj) throws E4AException {
		throw new E4AException("Not implemented");
	}
}

