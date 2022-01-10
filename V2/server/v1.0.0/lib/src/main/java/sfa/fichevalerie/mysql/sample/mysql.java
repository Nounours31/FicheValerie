package sfa.fichevalerie.mysql.sample;

import java.text.SimpleDateFormat;
import java.util.Date;



/*
 * MariaDB [(none)]> CREATE DATABASE sfa_fichevalerie;
Query OK, 1 row affected (0.082 sec)

MariaDB [(none)]> CREATE USER 'valerie'@'localhost' IDENTIFIED BY 'pwdValerie';
Query OK, 0 rows affected (0.066 sec)

MariaDB [(none)]> grant all on sfa_fichevalerie.* to 'valerie'@'localhost';
Query OK, 0 rows affected (0.002 sec)
 */

public class mysql {

	public mysql() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]){  
		try{  
/*			DB db = new DB("test");
			int idRow = db.insertAsRest("INSERT INTO `personne` (`genre`, `nom`, `date`) VALUES ('Madame', 'ZZZ', '2021-06-30 00:00:00')");
			System.out.println("idRow:" + idRow);

			cInfoFromSelect x = db.selectAsRest("select * from  `personne`");
			System.out.println(x.toString());

			int rc = db.deleteAsRest("DELETE FROM `personne` where id="+ (idRow-1));
			System.out.println("rc:" + rc);

			x = db.selectAsRest("select * from  `personne`");
			System.out.println(x.toString());

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSSZ");
			String s = sdf.format(new Date());
			rc = db.updateAsRest("update `personne` set nom='"+s+"' where id="+idRow);
			System.out.println("rc:" + rc);

			x = db.selectAsRest("select * from  `personne`");
			System.out.println(x.toString());*/
			System.out.println("toto");
		}
		catch(Exception e){ 
			System.out.println(e);
			e.printStackTrace();
		}  
	}  
}
