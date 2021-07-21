package sfa.fichevalerie.pdf.modele.itxt7;

import sfa.fichevalerie.tools.E4ALogger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class tools {
    public static String euro = "€";
    public static String Percent = "%";
    public static String heures = "h";
    public static String tauxhoraire = "€/h";
    private static String[] mois = {
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre"
    };
    private static String[] jour = {
            "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
    };

    public static String entete = "Val\u00E9rie FAGES n\u00E9e Le Pennec\n"+
            "35 Av de St Germain, B\u00E2timent B\n" +
            "78160 Marly Le Roi\n" +
            "T\u00E9l.: 06 70 65 88 84\n" +
            "\n" +
            "N\u00B0 S.S:\t2650740192040 - 35\n" +
            "N\u00E9e le 10 juillet 1965 \u00E0 Mont de Marsan";

    public static String enteteUTF = "Valérie FAGES née Le Pennec\n"+
            "35 Av de St Germain, Bâtiment B\n" +
            "78160 Marly Le Roi\n" +
            "Tél.: 06 70 65 88 84\n" +
            "\n" +
            "N° S.S:\t2650740192040 - 35\n" +
            "Née le 10 juillet 1965 à Mont de Marsan";

    static final String CarteVisite = "E:\\ws\\GitHubPerso\\FicheValerie\\server\\v1.0.0\\resources\\images\\CarteVisite.png";

    static final private E4ALogger _log = E4ALogger.getLogger(tools.class.getCanonicalName());

    static String encodeUTF8 (String s) {
        String rc = s;
        rc = rc.replaceAll("é", "\u00E9");
        rc = rc.replaceAll("è", "\u00E8");
        rc = rc.replaceAll("â", "\u00E2");
        rc = rc.replaceAll("°", "\u00B0");
        rc = rc.replaceAll("à", "\u00E0");
        rc = rc.replaceAll("€", "\u20AC");
        rc = rc.replaceAll("ô", "\u00F4");
        return rc;
    }

    static String FromDateToHeure (Date d) {
        String retour = "";
        Calendar c = GregorianCalendar.getInstance(TimeZone.getTimeZone("CET"));
        c.setTime(d);
        _log.debug(String.format("FromDateToHeure: [%s] - c.HourOfDay [%d] - c.Minutes[%d]", d.toString(), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));
        retour = String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        return retour;
    }

    static String FromMinutesLongToHeure(long duree) {
        String retour = "";
        long heure = (duree / 60l);
        long min = duree - 60l * heure;


        retour = String.format("%02d:%02d", heure, min);
        return retour;
    }

    public static String MoisFromInt(int iMois)  {
        return mois[iMois];
    }
    public static String JourFromInt(int i) {
        return jour[i];
    }

    // -----------------------------
    // renvoie
    //     [ "nom du jour", day of month]
    //
    // day of week: 1 -> 7 1=SUNDAY 
    // day of month: 1 -> 31 
    // -----------------------------
    public static String[] JourFromDate(Date debut) {
        String[] retour = {"", ""};
        int info[] = tools.JourSemaineFromDate(debut);
        retour[0] = tools.JourFromInt(info[0]);
        retour[1] = Integer.toString(info[1]);
        return retour;
    }

    public static long FromDureeToMinutesLong(Date debut, Date fin) {
        long retour = 0l;
        long x = fin.getTime() - debut.getTime();

        // heure - minutes
        retour = (x / (1000l * 60l));
        return retour;
    }

    // -----------------------------
    // renvoie
    //     [ day of week, day of month]
    //
    // day of week: 1 -> 7 1=SUNDAY 
    // day of month: 1 -> 31 
    // -----------------------------
    public static int[] JourSemaineFromDate(Date debut) {
        Calendar c = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTime(debut);
        _log.debug(String.format("JourSemaineFromDate: [%s] - c.dayofweek[%d] - - c.dayofmonth[%d] - c.HourOfDay [%d] - c.Minutes[%d]",
                debut.toString(), c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));
        return new int[] {
            c.get(Calendar.DAY_OF_WEEK) - 1,
            c.get(Calendar.DAY_OF_MONTH)
        };
    }
}
