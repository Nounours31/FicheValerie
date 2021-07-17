package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

/*
export interface iPdf {
    id: number;
    idBulletinSalaire: number;
    file: string;
}

 */
public class Env extends ObjectWrapper implements iObjectWrapper {

	public Env() {
		id = -1;
	}

    int id;
    float CSG;
    float TauxImposition;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCSG() {
		return CSG;
	}

	public void setCSG(float CSG) {
		this.CSG = CSG;
	}

	public float getTauxImposition() {
		return TauxImposition;
	}

	public void setTauxImposition(float tauxImposition) {
		TauxImposition = tauxImposition;
	}

	@Override
	public String[] allColone() {
		return new String[] {
				"id","CSG", "TauxImposition"
		};
	}

	@Override
	public void set(String key, Object val) throws E4AException {
		switch (key) {
			case "id": this.setId((Integer)val);break;
			case "CSG": this.setCSG((Float)val);break;
			case "TauxImposition": this.setTauxImposition((Float)val);break;
			default: throw new E4AException("Rappel :Key["+key+"] Inconnue");
		}
	}
}
