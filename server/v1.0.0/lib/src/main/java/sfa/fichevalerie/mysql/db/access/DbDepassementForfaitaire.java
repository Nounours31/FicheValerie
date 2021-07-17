package sfa.fichevalerie.mysql.db.access;

import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.tools.E4AException;

import java.util.Date;
import java.util.Hashtable;

public class DbDepassementForfaitaire extends DB implements iDB {
	final String tableName = "activite";

	public DbDepassementForfaitaire() {
		super(DbDepassementForfaitaire.class.getName());
	}


	@Override
	public DepassementForfaitaire encode(Hashtable<String, Object> hash) throws E4AException {
		DepassementForfaitaire rc = new DepassementForfaitaire();
		if (hash.containsKey("id")) rc.setId(((Integer)hash.get("id")).intValue());
		if (hash.containsKey("idBulletinSalaire")) rc.setIdBulletinSalaire(((Integer)hash.get("idBulletinSalaire")).intValue());
		if (hash.containsKey("tarifHoraire")) rc.setTarifHoraire(((Float)hash.get("tarifHoraire")).floatValue());
		if (hash.containsKey("activitee")) rc.setActivite((String)hash.get("activitee"));
		if (hash.containsKey("debut")) rc.setDebut((Date)hash.get("debut"));
		if (hash.containsKey("fin")) rc.setFin((Date)hash.get("fin"));

		return rc;	
	}

	@Override
	public iObjectWrapper encode(Hashtable<String, Object> hash) throws E4AException {
		Personne rc = new Personne();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}
}

