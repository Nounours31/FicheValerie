package sfa.fichevalerie.pdf.modele;

public class tools {
    static String[] mois = {
    		"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"
    };
                          
    static String[] jour = {
             "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
    };
                              
    public static String MoisFromInt(int iMois) {
    	return mois[iMois];
    }

}
