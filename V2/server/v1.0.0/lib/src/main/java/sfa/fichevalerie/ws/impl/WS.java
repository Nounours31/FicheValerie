package sfa.fichevalerie.ws.impl;

import java.util.HashMap;

import sfa.fichevalerie.tools.E4ALogger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class WS implements iWS {

	cWsTools _tools = null;
	E4ALogger _logger = null;
	HashMap<String, Object> _args = null;
	private eWsTypeGetPost _type;

	protected WS () {
		_tools = new cWsTools();
		_logger = E4ALogger.getLogger(this.getClass().getCanonicalName());
		_args = new HashMap<String, Object>();
	}

	@Override
	public Response run() {
		if (_type.equals(eWsTypeGetPost.get))
			return get();
		else if (_type.equals(eWsTypeGetPost.post))
			return post();
		else if (_type.equals(eWsTypeGetPost.delete))
			return delete();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("Unknown " + _type.name()).build();
	}

	@Override
	public Response get() {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("Not impl").build();
	}

	@Override
	public Response post() {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("Not impl").build();
	}

	@Override
	public Response delete() {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity("Not impl").build();
	}


	@Override
	public eWsTypeGetPost getType() { return _type;}

	@Override
	public void setType(eWsTypeGetPost x) { _type = x;}


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

	public String dumpArgs() {
		StringBuffer sb = new StringBuffer();
		for (String key: _args.keySet()) {
			sb.append(String.format("[key:%s|class:%s|val:%s]\n", key, _args.get(key).getClass().getCanonicalName(), _args.get(key).toString()));
		}
		return sb.toString();
	}
}


