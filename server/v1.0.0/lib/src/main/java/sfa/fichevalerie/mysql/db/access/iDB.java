package sfa.fichevalerie.mysql.db.access;

import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.tools.E4AException;

public abstract interface iDB {
	public iObjectWrapper encode(Hashtable<String, Object> obj) throws E4AException;

}
