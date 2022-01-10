package sfa.fichevalerie.mysql.db.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class cInfoFromSelect {
	
	private ArrayList<Hashtable<String, Object>> _data = null;
	private ArrayList<String> _colonnes = null;
	private ArrayList<String> _colonnesLabel = null;
	
	public cInfoFromSelect() {
	}
	
	public void buildFromResultSet(ResultSet rs) throws SQLException {
		_data = new ArrayList<Hashtable<String, Object>>();
		_colonnes = new ArrayList<String>();
		_colonnesLabel = new ArrayList<String>();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++)  {
			_colonnes.add(rsmd.getColumnName(i));
			_colonnesLabel.add(rsmd.getColumnLabel(i));
		}

		while(rs.next()) {  
			Hashtable<String, Object> x = new Hashtable<String, Object>();
			for (String s : _colonnes) {
				x.put(s, rs.getObject(s));		
			}
			_data.add(x);
		}
	}

	public ArrayList<String> getColone() {
		return this._colonnes;
	}
	
	public ArrayList<String> getColoneLabel() {
		return this._colonnesLabel;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("--------------------------------------" + System.lineSeparator());
		sb.append("-- cInfoFromSelect" + System.lineSeparator());
		sb.append("\tNb row: " + (_data == null ? 0 : _data.size()) + System.lineSeparator());
		sb.append("\tColonnes: "  + System.lineSeparator());
		sb.append("\t" + this.dumpColones());
		sb.append(System.lineSeparator());
		sb.append("\t" + this.dumpData());
		sb.append("--------------------------------------" + System.lineSeparator());
		return sb.toString();
	}

	private String dumpColones() {
		StringBuffer sb = new StringBuffer();
		sb.append("\tColonnes: " +  System.lineSeparator());
		if (this._colonnes != null) {
			for (int i = 0; i < this._colonnes.size(); i++) { 
				sb.append("\t\t" + this._colonnes + "\t\t" + this._colonnesLabel.get(i) + System.lineSeparator());			
			}
		}
		else {
			sb.append("\t\tEmpty" + System.lineSeparator());			
		}
		return sb.toString();
	}

	private String dumpData() {
		StringBuffer sb = new StringBuffer();
		sb.append("\tData: " +  System.lineSeparator());
		if (this._data != null) {
			for (int i = 0; i < this._data.size(); i++) { 
				sb.append("\t\t------------" + System.lineSeparator());					
				sb.append("\t\trow ["+i+"]:" + System.lineSeparator());					
				Hashtable<String, Object> x = this._data.get(i);
				
				for (String key : this._colonnes) {
					sb.append("\t\t\t" + key + ": " + x.get(key) + System.lineSeparator());					
				}
			}
		}
		else {
			sb.append("\t\tEmpty" + System.lineSeparator());			
		}
		return sb.toString();
	}

	public Hashtable<String, Object> get(int i) {
		if (_data != null)
			return _data.get(i);
		return null;
	}
	public int size() {
		if (_data != null)
			return _data.size();
		return 0;
	}

	public String dump(Hashtable<String, Object> x) {
		StringBuffer sb = new StringBuffer();
		for (String key : x.keySet()) {
			sb.append("Key:" + key + " --- Valeur: " + x.get(key) + System.lineSeparator());					
		}
		return sb.toString();
	}
}
