package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

public interface iObjectWrapper {
    public String[] allColone();
    public void set (String key, Object val) throws E4AException;
}
