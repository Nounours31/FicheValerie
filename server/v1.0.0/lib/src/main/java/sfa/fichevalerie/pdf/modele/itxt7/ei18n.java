package sfa.fichevalerie.pdf.modele.itxt7;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public enum ei18n {
    defaut (0, "inconnu"),
    TotalBrut(2, "Total brut"),
    CSG(3, "CSG"),
    impot(4, "Impôt sur le revenu"),
    TotalNet(5, "Net total"),
    RecapPrecedent(6, "Récapitilatif mois precedent"),
    RecapMensuel(7, "Récapitulatif mois courant"),
    // activitee
    activite_depassementforfaitaire(8, "Dépassement fortaitaire"),
    activite_totalMensuel(9,"Total mensuel"),
    activite_RappelPrecedent(10, "Rappel mois prédédent"),
    activite_TotalHoraire(11, "Volume horaire total"),

    activite_libelle (1, "Activitée"),
    activite_HoraireJournalier(12, "Horaires" ),
    activite_NbHeure(13, "Nb heure"),
    activite_NbHeureCumulatif(14, "Nb Heure Total"),
    activite_HoraireJournalier_debut(15, "Début"),
    activite_HoraireJournalier_fin(16, "Fin" ),

    // import
    impot_libelle(17, "Libellé"),
    impot_base(18, "Base"),
    impot_taux( 19, "Taux"),
    impot_gain(20, "Gain"),
    impot_retenu(21, "Retenue"),
    impot_salaire_brut(22, "Salaire brut à payer"),
    impot_CSG(23, "CSG [Déductible et non déductible de l'impôt]"),
    impot_imposition(24, "Impôt prélevé à la source"),
    impot_salairenet(25, "Salaire net");


    private final int _debug = 1;
    private final int _code;
    private final String _val;

    private ei18n(int code, String val) {
        _code = code;
        _val = val;
    }

    public String nls() {
        String retour = "";
        if (_debug != 0)
            retour = "NLS:" + this._val;
        else
            retour = this._val;

        retour = tools.encodeUTF8(retour);
        return retour;
    }

}
