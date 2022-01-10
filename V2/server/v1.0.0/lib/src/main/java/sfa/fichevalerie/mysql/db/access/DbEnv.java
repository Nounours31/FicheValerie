package sfa.fichevalerie.mysql.db.access;

import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.Env;
import sfa.fichevalerie.mysql.api.datawrapper.Pdf;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.pdf.modele.itxt7.ApiPdf;
import sfa.fichevalerie.tools.E4AException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class DbEnv extends DB  implements iDB {
	final static String dbRepositoryStore = "E:\\WS\\GitHubPerso\\FicheValerie\\store";
	final String tableName = "pdf";

	public DbEnv()  {
		super(DbEnv.class.getName());
	}

	public Env getEnv() {
		_logger.debug("getEnv START");
		Env rc = null;

		cInfoFromSelect res = null;;
		try {
			res = this.selectAsRest("select * from env");
			_logger.debug("Resultat requete: " + res.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		if (res != null) {
			for (int i = 0; i < res.size(); i++) {
				Hashtable<String, Object> row = res.get(i);
				if (row != null) {
					Env p = null;
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
						rc = p;
					}
				}
			}
		}
		return rc;
	}

	@Override
	public Env encode(Hashtable<String, Object> hash) throws E4AException {
		Env rc = new Env();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}

}
