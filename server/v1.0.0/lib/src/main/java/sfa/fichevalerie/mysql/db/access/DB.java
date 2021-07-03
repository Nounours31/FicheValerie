package sfa.fichevalerie.mysql.db.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import sfa.fichevalerie.mysql.db.tools.cInfoFromSelect;
import sfa.fichevalerie.tools.E4ALogger;

public abstract class DB implements iDB {
	final static SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected String _DBTableName = null;
	protected String _DBUser = "valerie";
	protected String _DBPassword = "pwdValerie";
	protected String _DBDataBase = "sfa_fichevalerie";
	protected String _DBPort = "3307";
	protected String _DBHost = "localhost";

	protected E4ALogger _logger = null;

	private final String _InstallType = "dell";


	public DB(String tableName) {
		_DBTableName = tableName;
		_logger = E4ALogger.getLogger(_DBTableName);
		this.init();
	}




	private void init() {
		switch (this._InstallType) {
		case "dell":
			this._DBUser = "valerie";
			this._DBPassword = "pwdValerie";
			this._DBDataBase = "sfa_fichevalerie";
			this._DBPort = "3307";
			this._DBHost = "localhost";
			break;

		case "free":
			this._DBUser = "pierre.fages";
			this._DBPassword = "frifri";
			this._DBDataBase = "pierre_fages";
			this._DBPort = "3306";
			this._DBHost = "sql.free.fr";
			break;

		case "ovh":
			this._DBUser = "poids";
			this._DBPassword = "poids";
			this._DBDataBase = "poids";
			this._DBPort = "3307";
			this._DBHost = "localhost";
			break;

		default:
			break;
		}
	}

	private Connection connect() throws Exception  {
		Connection con=null;
		try {
			String User = this._DBUser;
			String Password = this._DBPassword;
			String BaseName = this._DBDataBase;
			String Port = this._DBPort;
			String Host = this._DBHost;
	
			Class.forName("com.mysql.jdbc.Driver");  
			//String jdbcString = String.format("jdbc:mysql://%s:%s/%s", Host, Port, BaseName);
			String jdbcString = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Host, Port, BaseName);
			con=DriverManager.getConnection(jdbcString, User, Password);  
		}
		finally {
			this._logger.debug("connect status: " + (con == null));
		}
		return con;
	}

	private void close(Connection c) throws SQLException {
		c.close();
		this._logger.debug("connect clos");
	}

	public cInfoFromSelect selectAsRest (String sql) throws Exception {
		Connection c =  this.connect();
		cInfoFromSelect rc = this.select(c, sql);
		this.close(c);
		return rc;
	}

	private cInfoFromSelect select (Connection c, String sql) throws SQLException  {
		this._logger.debug("DBAccess::select SQL[" + sql + "]");
		cInfoFromSelect ret = new cInfoFromSelect();

		Statement stmt=c.createStatement();  
		ResultSet rs=stmt.executeQuery(sql);  

		ret.buildFromResultSet(rs);
		return ret;
	}



	public int insertAsRest (String sql) throws Exception  {
		Connection c =  this.connect();
		int rc = this.insert(c, sql);
		this.close(c);
		return rc;        
	}

	private int  insert (Connection c, String sql) throws SQLException {
		int retour = -1;
		this._logger.debug("DBAccess::insert SQL["+sql+"]");

		Statement stmt=c.createStatement();  
		boolean rc = stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);  
		ResultSet rs = stmt.getGeneratedKeys(); 
		if(rs.next()) {  
			retour = rs.getInt(1);
		}
		return retour;    
	}

	public int updateAsRest (String sql) throws Exception  {
		Connection link =  this.connect();
		int rc = this.update(link, sql);
		this.close(link);
		return rc;
	}

	/* UPDATE queries */
	private int update (Connection link, String sql) throws Exception  {
		return this._others(link, sql, true);
	}


	public int deleteAsRest (String sql) throws Exception  {
		Connection link =  this.connect();
		int rc = this.delete(link, sql);
		this.close(link);
		return rc;
	}

	private int delete (Connection link, String sql) throws Exception  {
		return this._others(link, sql, false);
	}


	/* others queries */
	private int _others (Connection link, String sql, boolean updateQuery) throws Exception  {

		Statement stmt=link.createStatement();  
		if (updateQuery) {
			this._logger.debug("DBAccess::Upate SQL["+sql+"]");
		}
		else {
			this._logger.debug("DBAccess::Delete SQL["+sql+"]");
		}
		int rc = stmt.executeUpdate(sql);
		return rc;
	}


}
