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
	protected String _DBUser = "";
	protected String _DBPassword = "";
	protected String _DBDataBase = "";
	protected String _DBPort = "";
	protected String _DBHost = "";

	protected E4ALogger _logger = null;

	// private final String _InstallType = "dell";
	// private final String _InstallType = "vape";
	private final String _InstallType = "ovh";


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
			this._DBPort = "3306";
			this._DBHost = "localhost";
			break;

		case "vape":
			this._DBUser = "valerie";
			this._DBPassword = "pwdValerie";
			this._DBDataBase = "sfa_fichevalerie";
			this._DBPort = "3306";
			this._DBHost = "localhost";
			break;

		case "ovh":
			this._DBUser = "valerie";
			this._DBPassword = "pwdValerie";
			this._DBDataBase = "sfa_fichevalerie";
			this._DBPort = "3306";
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
	
			Class.forName("com.mysql.cj.jdbc.Driver");  
			String jdbcString = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Host, Port, BaseName);
			this._logger.debug("JDBC: " + jdbcString);
			
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
		try {
			cInfoFromSelect rc = this.select(c, sql);
			return rc;
		}
		finally {
			this.close(c);
		}
	}

	private cInfoFromSelect select (Connection c, String sql) throws SQLException  {
		this._logger.debug("DBAccess::select SQL[" + sql + "]");
		cInfoFromSelect ret = new cInfoFromSelect();

		Statement stmt=c.createStatement();  
		ResultSet rs=null;
		try {
			rs=stmt.executeQuery(sql);  
		}
		catch (SQLException e) {
			_logger.fatal("SQL:" + sql);
			_logger.fatal("SQL error:" + e.getMessage());
			throw e;
		}

		ret.buildFromResultSet(rs);
		return ret;
	}



	public int insertAsRest (String sql) throws Exception  {
		Connection c =  this.connect();
		try {
			int rc = this.insert(c, sql);
			return rc;        
		}
		finally {
			this.close(c);
		}
	}

	private int  insert (Connection c, String sql) throws SQLException {
		int retour = -1;
		this._logger.debug("DBAccess::insert SQL["+sql+"]");

		Statement stmt=c.createStatement();  
		try {
			boolean rc = stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);  
			this._logger.debug("RC:["+rc+"]");
			// if (rc) {
				ResultSet rs = stmt.getGeneratedKeys(); 
				if(rs.next()) {  
					retour = rs.getInt(1);
				}
				this._logger.debug("Retour:["+retour+"]");
			// }
		}
		catch (SQLException e) {
			_logger.fatal("SQL:" + sql);
			_logger.fatal("SQL error:" + e.getMessage());
			throw e;
		}
		return retour;    
	}

	public int updateAsRest (String sql) throws Exception  {
		Connection link =  this.connect();
		int rc = -1;
		try {
			rc = this.update(link, sql);
		}
		finally {
			this.close(link);
		}
		return rc;
	}

	/* UPDATE queries */
	private int update (Connection link, String sql) throws Exception  {
		return this._others(link, sql, true);
	}


	public int deleteAsRest (String sql) throws Exception  {
		Connection link =  this.connect();
		int rc = -1;
		try {
			rc = this.delete(link, sql);
		}
		finally {
			this.close(link);
		}
		return rc;
	}

	private int delete (Connection link, String sql) throws Exception  {
		return this._others(link, sql, false);
	}


	/* others queries */
	private int _others (Connection c, String sql, boolean updateQuery) throws Exception  {

		Statement stmt=c.createStatement();  
		if (updateQuery) {
			this._logger.debug("DBAccess::Upate SQL["+sql+"]");
		}
		else {
			this._logger.debug("DBAccess::Delete SQL["+sql+"]");
		}
		int rc = -1;
		try {
			rc = stmt.executeUpdate(sql);
		}
		catch (SQLException e) {
			_logger.fatal("SQL:" + sql);
			_logger.fatal("SQL error:" + e.getMessage());
			throw e;
		}
		
		return rc;
	}

	protected String escapeStringForMySQL(String s1) {
		String s = escapeStdStringForMySQL(s1);
		s = escapeWildcardsForMySQL(s);
		return s;
	}
	
	protected String escapeStdStringForMySQL(String s1) {
        String s = s1.replace("\\", "\\\\");
        s = s.replace("\b","\\b");
        s = s.replace("\n","\\n");
        s = s.replace("\r", "\\r");
        s = s.replace("\t", "\\t");
        s = s.replace("\\x1A", "\\Z");
        s = s.replace("\\x00", "\\0");
        s = s.replace("'", "\\'");
        s = s.replace("\"", "\\\"");
        return s;
    }

	protected String escapeWildcardsForMySQL(String s1) {
        String s = s1.replace("%", "\\%");
        s = s.replace("_","\\_");
        return s;
    }
}