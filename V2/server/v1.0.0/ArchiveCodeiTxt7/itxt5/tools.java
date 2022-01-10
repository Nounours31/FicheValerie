package sfa.fichevalerie.pdf.modele.itxt5;

public class tools {
    private static String[] mois = {
    		"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"
    };
                          
    private static String[] jour = {
             "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
    };
                              
    public static String MoisFromInt(int iMois) {
    	return mois[iMois];
    }

    public static String entete = "Valrie FAGES née Le Pennec\n"+
            "35 Av de St Germain, Bâtiment B\n" +
            "78160 Marly Le Roi\n" +
            "Tél.: 06 70 65 88 84\n" +
            "\n" +
            "N° S.S:\t2650740192040 - 35\n" +
            "Née le 10 juillet 1965 à Mont de Marsan";

    static final String CarteVisite = "E:\\ws\\GitHubPerso\\FicheValerie\\server\\v1.0.0\\resources\\images\\CarteVisite.png";

}
