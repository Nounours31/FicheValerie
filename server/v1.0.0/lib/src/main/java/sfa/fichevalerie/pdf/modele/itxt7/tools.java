package sfa.fichevalerie.pdf.modele.itxt7;

public class tools {
    private static String[] mois = {
            "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"
    };

    private static String[] jour = {
            "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
    };

    public static String MoisFromInt(int iMois)  {
        return mois[iMois];
    }
    public static String JourFromInt(int i) {
        return jour[i];
    }

    public static String entete = "Val\u00E9rie FAGES n\u00E9e Le Pennec\n"+
            "35 Av de St Germain, B\u00E2timent B\n" +
            "78160 Marly Le Roi\n" +
            "T\u00E9l.: 06 70 65 88 84\n" +
            "\n" +
            "N\u00B0 S.S:\t2650740192040 - 35\n" +
            "N\u00E9e le 10 juillet 1965 \u00E0 Mont de Marsan";

    static final String CarteVisite = "E:\\ws\\GitHubPerso\\FicheValerie\\server\\v1.0.0\\resources\\images\\CarteVisite.png";

}
