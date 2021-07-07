package sfa.fichevalerie.ws.impl;

public enum eWsNameInCaseOfMultipleChoice {
	get ("GET"), getAll ("GETALL"), create ("CREATE");
	
	private String _nom;

	private eWsNameInCaseOfMultipleChoice (String Nom) {
		this._nom = Nom;
	}
}
