package sfa.fichevalerie.ws.impl;

public enum eWsTypeGetPost {
	get ("GET"), post ("POST"), delete ("DELETE");
	
	private String _nom;
	private eWsTypeGetPost(String Nom) {
		this._nom = Nom;
	}
}
