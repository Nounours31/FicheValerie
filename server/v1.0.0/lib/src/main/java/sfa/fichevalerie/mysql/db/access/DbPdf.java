package sfa.fichevalerie.mysql.db.access;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import sfa.fichevalerie.mysql.api.datawrapper.*;
import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.pdf.modele.itxt7.ApiPdf;
import sfa.fichevalerie.tools.E4AException;

public class DbPdf extends DB  implements iDB {
	final String tableName = "pdf";
	static String dbRepositoryStore = null;
	public DbPdf()  {
		super(DbPdf.class.getName());
	}


	@Override
	public Pdf encode(Hashtable<String, Object> hash) throws E4AException {
		Pdf rc = new Pdf();
		for (String key: rc.allColone()) {
			rc.set(key, hash.get(key));
		}
		return rc;
	}

	private static synchronized String getStorePath () {
		if (DbPdf.dbRepositoryStore == null) {
			String sql = "select * from env";
			try {
				DB x = new DbPdf();
				cInfoFromSelect iInfos = x.selectAsRest(sql);
				DbPdf.dbRepositoryStore = (String)iInfos.get(0).get("storePath");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			DbPdf.dbRepositoryStore = "/tmp";
		}
		return DbPdf.dbRepositoryStore;
	}
	
	private int getlastPdfVersion (int idBulletinSalaire) {
		String sql = String.format("select version from pdf where (idBulletinSalaire=%d) order by version desc", idBulletinSalaire);
		try {
			cInfoFromSelect iInfos = this.selectAsRest(sql);
			if ((iInfos != null) && (iInfos.size() > 0)) {
				return ((Integer)iInfos.get(0).get("version")).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Pdf createPdf(Pdf p) {
		_logger.debug("START - In Create PDF");
		Pdf retour = null;
		int idBulletinSalaire = p.getIdBulletinSalaire();
		
		DbBulletinSalaire dbBs = new DbBulletinSalaire();
		BulletinSalaire bs = dbBs.getAllBulletinSalaire(idBulletinSalaire);

		
		String UID = java.util.UUID.randomUUID().toString();
		
		String FileName = UID + ".pdf";
		String SubPath = String.format("%d%s%s%s%s", idBulletinSalaire, File.separator, UID.substring(0, 2), File.separator, FileName);
		String Path = DbPdf.getStorePath() + File.separator + SubPath;
		File newPdf = new File (Path);
		_logger.debug("PDF fullpath: " + newPdf.getAbsolutePath());

		File dirFile = newPdf.getParentFile();
		if (!dirFile.exists()) 
			dirFile.mkdirs();
		
		ApiPdf pdfCreator = new ApiPdf();
		try {
			pdfCreator.createPdf(newPdf.getAbsolutePath(), bs);
			if (newPdf.exists() && newPdf.length() > 0) {
				SubPath = this.escapeStringForMySQL(SubPath);
				String sql = String.format("insert into pdf (idBulletinSalaire, file, version, date) values (%d, '%s', %d, '%s')",
						idBulletinSalaire, SubPath, this.getlastPdfVersion(idBulletinSalaire) + 1, _sdf.format(new Date()));
				int id = this.insertAsRest(sql);
				
				retour = this.getPdf(id);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return retour;
	}


	public Pdf getPdf(int id) {
		String sql = String.format("select * from pdf where (id = %d)", id);
		Pdf[] x = this.getPdf(sql);
		if ((x != null) && (x.length > 0))
			return x[0];
		return null;
	}


	public Pdf[] getPdfFromBulletin(int idBulletin) {
		String sql = String.format("select * from pdf where (idBulletinSalaire = %d) order by date asc", idBulletin);
		Pdf[] x = this.getPdf(sql);
		return x;
	}

	private Pdf[] getPdf(String sql) {
		cInfoFromSelect res = null;;
		try {
			res = this.selectAsRest(sql);
			_logger.debug("Resultat requete: " + res.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		ArrayList<Pdf> lPdf = new ArrayList<Pdf>();
		if (res != null) {
			for (int i = 0; i < res.size(); i++) {
				Hashtable<String, Object> row = res.get(i);
				if (row != null) {
					Pdf p = null;
					try {
						p = this.encode(row);
					}
					catch (E4AException e) {
						System.out.println(e.getMessage());
						System.out.println("Raison du KO ?" + res.dump(row));
						p = null;
					}

					if (p != null) {
						_logger.debug("PDF trouvee: " + p.toString());
						lPdf.add(p);
					}
				}
			}
		}
		
		_logger.debug("Nb PDF au total: " +lPdf.size());
		
		if (lPdf.size() > 0) {
			Pdf[] rc = new Pdf[lPdf.size()];
			rc = lPdf.toArray(rc);
			return rc;
		}
		return null;
	}

	public static File getFileFromPdf(Pdf p) {
		return new File (DbPdf.getStorePath() + File.separator + p.getFile());
	}
}
