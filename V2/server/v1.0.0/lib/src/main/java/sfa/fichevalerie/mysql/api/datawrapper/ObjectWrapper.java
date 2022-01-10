package sfa.fichevalerie.mysql.api.datawrapper;

import java.text.SimpleDateFormat;

public abstract class ObjectWrapper implements iObjectWrapper {
	final static SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
