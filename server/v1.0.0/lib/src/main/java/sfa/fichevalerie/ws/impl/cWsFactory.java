package sfa.fichevalerie.ws.impl;

public class cWsFactory {
	
	public static iWS getImpl (String wsNom) {
		iWS rc = null;
		switch (wsNom) {
			case "getAllPersonnes" : rc =  new WsPersonne(); rc.setType(eWsTypeGetPost.get); break;
			case "createPersonne" : rc =  new WsPersonne(); rc.setType(eWsTypeGetPost.post); break;

			case "getBulletinSalaire" : rc =  new WsBulletinSalaire(); rc.setType(eWsTypeGetPost.get); break;
			case "createBulletinSalaire" : rc =  new WsBulletinSalaire(); ; rc.setType(eWsTypeGetPost.post); break;

			case "getActivitee" : rc =  new WsActivte(); rc.setType(eWsTypeGetPost.get);break;
			case "getActiviteeByBulletin" : rc =  new WsActivte(); rc.setType(eWsTypeGetPost.get);break;
			case "createActivitee" : rc =  new WsActivte(); rc.setType(eWsTypeGetPost.post); break;
			case "deleteActivitee" : rc =  new WsActivte(); rc.setType(eWsTypeGetPost.delete); break;

			case "createPdfFile" : rc =  new WsPdfFile(); rc.setType(eWsTypeGetPost.post); break;
			case "getPdfFile" :
			case "getPdfInfoFromBulletin" : rc =  new WsPdfFile(); rc.setType(eWsTypeGetPost.get); break;

			default : rc = new WsXxXNoImpl(wsNom);
		}
		return rc;
	}

}
