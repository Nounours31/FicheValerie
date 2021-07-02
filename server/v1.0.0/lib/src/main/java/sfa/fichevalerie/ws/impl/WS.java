package sfa.fichevalerie.ws.impl;

import java.util.HashMap;

import sfa.fichevalerie.tools.E4ALogger;

public abstract class WS implements iWS {

	cWsTools _tools = null;
	E4ALogger _logger = null;
	HashMap<String, Object> _args = null;
	
	protected WS () {
		_tools = new cWsTools();
		_logger = E4ALogger.getLogger(this.getClass().getCanonicalName());
		_args = new HashMap<String, Object>();
	}


	@Override
	public void setArgs(String key, int i) {
		_args.put(key, Integer.valueOf(i));
	}

	@Override
	public void setArgs(String key, String s) {
		_args.put(key, s);
	}

	@Override
	public void setArgs(String key, Object o) {
		_args.put(key, o);
	}	

	@Override
	public Object getArgs(String key) {
		return _args.get(key);
	}	
}


