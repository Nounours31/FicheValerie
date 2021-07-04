package sfa.fichevalerie.mysql.db.access;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.iObjectWrapper;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4AException;

public class DbActivite extends DB implements iDB {
	final String tableName = "activite";

	public DbActivite() {
		super(DbActivite.class.getName());
	}


	public int insertActivite (Activite a) {
		String sql = String.format("INSERT INTO activite (idBulletinSalaire, activitee, debut, fin, date, tarifHoraire) VALUES ('%d', '%s', '%s', '%s', '%s', %f)", 
				a.getIdBulletinSalaire(), a.getActivite(), _sdf.format(a.getDebut()), _sdf.format(a.getFin()), _sdf.format(new Date()), a.getTarifHoraire());
		
		try {
			return this.insertAsRest(sql);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}


	@Override
	public iObjectWrapper encode(Hashtable<String, Object> obj) throws E4AException {
		return null;
	}


}
