package sfa.fichevalerie.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class E4ALogger {
	private static eE4ALoggerLevel _envLevel = eE4ALoggerLevel.debug;
	private String _id = null;
	private final SimpleDateFormat sdf = new SimpleDateFormat("[MM-dd_HH:mm:ss]", Locale.US); 
	
	public static void setEnvLevel (String x) {
		if (x == null) {
			 _envLevel = eE4ALoggerLevel.severe;
			 return;
		}
			
		String y = x.toLowerCase();
		switch (y) {
			case "lowest" : _envLevel = eE4ALoggerLevel.lowest; break;
			case "debug" : _envLevel = eE4ALoggerLevel.debug; break;
			case "severe" : _envLevel = eE4ALoggerLevel.severe; break;
			case "allways" : _envLevel = eE4ALoggerLevel.allways; break;
			default : _envLevel = eE4ALoggerLevel.debug; break;
		}
	}

	public static eE4ALoggerLevel getEnvLevel () {
		return E4ALogger._envLevel;
	}
	
	private E4ALogger(String Id) {
		_id = Id;

		// ------------------------------------------
		// com.dassault_systemes.e4all.dns.handlers = 1handler.org.apache.juli.FileHandler
		// com.dassault_systemes.e4all.dns.level = INFO
		// ------------------------------------------
		_envLevel = eE4ALoggerLevel.debug;
	}
	
	public static E4ALogger getLogger(String Id) {
		E4ALogger x = new E4ALogger (Id);
		return x;
	}



	public void debug(String msg) {
		this._log(eE4ALoggerLevel.debug, msg);
	}

	public void info(String msg) {
		this._log(eE4ALoggerLevel.info, msg);
	}

	public void fatal(String msg) {
		this._log(eE4ALoggerLevel.severe, msg);		
	}

	public boolean isActive(eE4ALoggerLevel l) {
		boolean retour = false;
		if (E4ALogger._envLevel.isLessOrEqualThan(l)) {
			retour = true;
		}
		return retour;
	}

	private void _log (eE4ALoggerLevel l, String msg) {
		if (this.isActive(l)) {
			StringBuffer sb = new StringBuffer();
			sb.append(sdf.format(new Date()));
			sb.append(String.format("[%c>%c][%s]", l.getCode(), E4ALogger._envLevel.getCode(), _id));
			sb.append(msg);
			System.err.println(sb.toString());
		}
		return;
	}

}
