package sfa.fichevalerie.pdf.modele.itxt7;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.itextpdf.io.IOException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.DepassementForfaitaire;
import sfa.fichevalerie.mysql.api.datawrapper.Env;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.api.datawrapper.Rappel;
import sfa.fichevalerie.mysql.db.access.DbActivite;
import sfa.fichevalerie.mysql.db.access.DbDepassementForfaitaire;
import sfa.fichevalerie.mysql.db.access.DbEnv;
import sfa.fichevalerie.mysql.db.access.DbPersonne;
import sfa.fichevalerie.mysql.db.access.DbRappel;
import sfa.fichevalerie.tools.E4ALogger;

// https://kb.itextpdf.com/home/it7kb/examples

public class ApiPdf {
    private boolean _debugMode =  false;
	private PageSize _pageSize = null;;

	static private Color _black = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{0.0f, 0.0f, 0.0f});

    static private Color _bgBlue = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{0.5f, 0.7f, 0.9f});
    static private Color _bgBisque = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{0.7f, 0.5f, 0.9f});

    static private Color _red = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{1f, 0f, 0f});
    static private Color _green = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{0f, 1f, 0f});
    static private Color _blue = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{0f, 0f, 1f});
    static private Border debugBorderRed = new DashedBorder(Border.DASHED);
    static private Border debugBorderGreen = new DashedBorder(Border.DASHED);
    static private Border debugBorderBlue = new DashedBorder(Border.DASHED);

	static private PdfFont MainFont = null;
    static private final float fontSizeDefaut = 7;
    static private final float fontSizeMois = 10;
    static private final float fontSizeHeader = fontSizeDefaut;
    static private final float fontSizeActivite = fontSizeHeader;
    // private static final float fontSizePersonne = fontSizeActivite;

    private E4ALogger _logger = null;

	public ApiPdf()  {
	    _logger = E4ALogger.getLogger(this.getClass().getCanonicalName());
	    _debugMode = false;
	    ei18n.debug(_debugMode);

	    try {
			ApiPdf.MainFont = PdfFontFactory.createFont ("Courier", PdfEncodings.CP1252, false);
            debugBorderRed.setColor(_red);
            debugBorderGreen.setColor(_green);
            debugBorderBlue.setColor(_blue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    private Document initDoc (String dest) throws FileNotFoundException {
    	WriterProperties wp = new WriterProperties();
    	wp.setPdfVersion(PdfVersion.PDF_2_0);//Initialize PDF writer
        
    	PdfWriter writer = new PdfWriter(dest, wp);
    	
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        
         _pageSize = pdf.getDefaultPageSize();
        
        
        // Initialize document
        Document document = new Document(pdf);
        document.setFont(MainFont);
        document.setFontSize(fontSizeDefaut);
        document.setFontColor(_black);
        document.setMargins(10f, 10f, 10f, 10f);
    	return document;
    }
    
    


	private void BuildEntete(Document document) throws java.io.IOException {
        Text text = new Text (tools.encodeUTF8(tools.enteteUTF));
        text.setFont(ApiPdf.MainFont);
        text.setFontColor(_black);
        text.setFontSize(ApiPdf.fontSizeHeader);

        // tableau avec Nom a gauche - image a droite
        final float coef = 1.2f;
        float[] repartition = {coef, 2.0f - coef}; 
        float taille = (float) (this._pageSize.getWidth() / 2.0);
        Table t = new Table(new float[]{taille * repartition[0], taille * repartition[1]});

        Cell cell = new Cell();        
        Paragraph p = new Paragraph();
        p.add(text);
        cell.add(p);
        cell.setBorder(Border.NO_BORDER);
        if (_debugMode) {
            cell.setBorder (debugBorderRed);
        }
        t.addCell(cell);
        
        Image fox = new Image(ImageDataFactory.create(getClass().getResource("/sfa/fichevalerie/pdf/modele/itxt7/CarteVisite.png")));
        float xPercent;
		float yPercent;
		xPercent = yPercent = 0.5f;
		fox.scale(xPercent, yPercent);
        
        p = new Paragraph();
        p.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        p.setVerticalAlignment(VerticalAlignment.MIDDLE);
        p.add(fox);
        if (_debugMode) {
            p.setBorder (debugBorderGreen);
        }

        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        cell.setTextAlignment(TextAlignment.RIGHT);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add(p);
        cell.setBorder(Border.NO_BORDER);
        if (_debugMode) {
            cell.setBorder (debugBorderBlue);
        }
        t.addCell(cell);
        document.add(t);

        // cellule vide pour un blanc
        p = new Paragraph();
        p.setHeight(30f);
        p.add("");
        if (_debugMode) {
            p.setBorder(debugBorderRed);
        }
        document.add(p);

    }




    public void createPdf(String dest, BulletinSalaire bs) throws IOException, java.io.IOException {
        Document document = this.initDoc(dest);

        BuildEntete(document);
        BuildInfoPersonne(document, bs);

        DbActivite dbActivite = new DbActivite();
        Activite[] a = dbActivite.getAllActivitees(bs.getId());

        DbRappel dbRappel = new DbRappel();
        Rappel[] rappels = dbRappel.getAllRappels(bs.getId());

        DbDepassementForfaitaire dbDepassementForfait = new DbDepassementForfaitaire();
        DepassementForfaitaire[] depasementForfait = dbDepassementForfait.getAllDepassementForfaitaire(bs.getId());

        ArrayList<StructNbHeureActiviteEtSonTauxHoraire> HeureTravailleeEnMinute = BuildActivitee(document, bs, a, rappels, depasementForfait);
        BuildImpot(document, HeureTravailleeEnMinute);

        //Close document
        document.close();
    }


    
    private void BuildInfoPersonne(Document document, BulletinSalaire bs) {
        Paragraph p = new Paragraph();

        StringBuffer sb = new StringBuffer();
        sb.append("Mois de ");
        sb.append(tools.MoisFromInt(bs.getMois()));
        sb.append(" ");
        sb.append(bs.getAnnee());
        sb.append(" - Référence: " + bs.getId());
        p.add(tools.encodeUTF8(sb.toString()));

        p.setTextAlignment(TextAlignment.CENTER);
        p.setVerticalAlignment(VerticalAlignment.BOTTOM);
        p.setBackgroundColor(_bgBlue, 0.2f);
        p.setFontSize(fontSizeMois);
        if (_debugMode) {
            p.setBorder(debugBorderGreen);
        }
        document.add(p);

        p = new Paragraph();
        DbPersonne dbPers = new DbPersonne();
        Personne[] aPers = dbPers.getAllPersonnes(bs.getIdPersonne());

        sb = new StringBuffer();
        sb.append("Heures de présence auprès de ");
        Personne pers = aPers[0];
        sb.append(pers.getGenre());
        sb.append(" ");
        sb.append(pers.getNom());
        p.add(tools.encodeUTF8(sb.toString()));

        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        if (_debugMode) {
            p.setBorder(debugBorderBlue);
        }
        document.add(p);


    }


    private ArrayList<StructNbHeureActiviteEtSonTauxHoraire> BuildActivitee(Document document, BulletinSalaire bulletinSalaire, Activite[] a, Rappel[] r, DepassementForfaitaire[] d) {
        ArrayList<StructNbHeureActiviteEtSonTauxHoraire> allActiviteTimeWithLeurTaux = new ArrayList<StructNbHeureActiviteEtSonTauxHoraire>();
        float tailleTable2 = (float) (this._pageSize.getWidth());
        Table t = new Table(new float[]{
                tailleTable2 * 0.1f,
                tailleTable2 * 0.01f,
                tailleTable2 * 0.39f,
                tailleTable2 * 0.125f,
                tailleTable2 * 0.125f,
                tailleTable2 * 0.125f,
                tailleTable2 * 0.125f
        });
        
        int nbLineTotal = 0;

        Cell cell = new Cell(2,2);
        cell.setBorder(Border.NO_BORDER);
        t.addCell(cell);

        cell = new Cell(2,1);
        Paragraph p = new Paragraph(new Text(tools.encodeUTF8(ei18n.activite_libelle.nls())).setBold());
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        if (_debugMode) {
            p.setBorder(debugBorderBlue);
        }
        cell.add(p);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        t.addCell(cell);

        cell = new Cell(1,2);
        p = new Paragraph(tools.encodeUTF8(ei18n.activite_HoraireJournalier.nls())).setBold();
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(2,1);
        p = new Paragraph(tools.encodeUTF8(ei18n.activite_NbHeure.nls())).setBold();
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(2,1);
        p = new Paragraph(tools.encodeUTF8(ei18n.activite_NbHeureCumulatif.nls())).setBold();
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(1,1);
        p = new Paragraph(tools.encodeUTF8(ei18n.activite_HoraireJournalier_debut.nls())).setBold();
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(1,1);
        p = new Paragraph(ei18n.activite_HoraireJournalier_fin.nls()).setBold();
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        cell.add(p);
        t.addCell(cell);

        nbLineTotal++;
        
        
        Long nbHeureTravailleeTotale = 0l;


    	boolean hasActiviteMultiple = false;
        int nbActiviteMultiple = 0;
        boolean isFirstRow = false;

        for (int iIndice = 0; iIndice < a.length;  iIndice++) {
        	Activite uneActivite =  a[iIndice];
            _logger.debug(uneActivite.toString());

            String info = "";
        	boolean borderRight = true; 
        	boolean borderLeft = true; 
        	GregorianCalendar debut = new GregorianCalendar();
        	debut.setTimeZone(TimeZone.getTimeZone("GMT"));
        	GregorianCalendar fin =new  GregorianCalendar ();
        	fin.setTimeZone(TimeZone.getTimeZone("GMT"));
            debut.setTimeInMillis(uneActivite.getGmtepoch_debut());
            fin.setTimeInMillis(uneActivite.getGmtepoch_fin());

            String infoJour[] = tools.JourFromDate(debut);
            int[] jourSemaine = tools.JourSemaineFromDate(debut);
            int iJourSemaine = jourSemaine[0];

            // ---------------------------
            // affichage juor et date
            // ---------------------------
            // y a t il plusieures activite le meme jour ?
            if (!hasActiviteMultiple) {
            	int iNbActiviteCeJour = 0;
	            Activite activiteSuivante = uneActivite;
	            GregorianCalendar JourActiviteeSuivate =  new GregorianCalendar();
	            JourActiviteeSuivate.setTimeZone(TimeZone.getTimeZone("GMT"));
	            JourActiviteeSuivate.setTimeInMillis(activiteSuivante.getGmtepoch_debut());
	            
	            while (JourActiviteeSuivate.get(Calendar.DAY_OF_MONTH) == debut.get(Calendar.DAY_OF_MONTH)) {
	            	iNbActiviteCeJour++;
	            	if ((iIndice + iNbActiviteCeJour) >= a.length)
	            		break;
	                JourActiviteeSuivate.setTimeInMillis(a[iIndice + iNbActiviteCeJour].getGmtepoch_debut());
	            }

	            if (iNbActiviteCeJour > 1) {
	            	hasActiviteMultiple = true;
	            	nbActiviteMultiple = iNbActiviteCeJour;
	            	isFirstRow = true;
	            }
	            else {
	            	nbActiviteMultiple = 1;
	            }
            }
            
            // il y a des activite multiple?
            if (isFirstRow || !hasActiviteMultiple) {
            	borderRight = false; borderLeft = true;
            	t.addCell (this.builCell (iJourSemaine, infoJour[0], borderRight, borderLeft, nbActiviteMultiple));

            	borderRight = true; borderLeft = false;
            	t.addCell (this.builCell (iJourSemaine, infoJour[1], borderRight, borderLeft, nbActiviteMultiple));
            	isFirstRow = false;
            }
            
        	// oui et trouve au tour d'avant
        	// decrementer le rowspan
        	if (hasActiviteMultiple) {
        		nbActiviteMultiple--;
	        	if (nbActiviteMultiple == 0) {
	        		// c'est la derniere
	        		hasActiviteMultiple = false;
	        	}
	        }

            // ---------------------------
            // affichage activitee
            // ---------------------------
        	// info = String.format("%s [Taux %d]",uneActivite.getActivite(), courranteInfoActivite.getUiTauxIndice());
        	info = String.format("%s",uneActivite.getActivite());
        	borderRight = true; borderLeft = true;
            t.addCell (this.builCell (iJourSemaine, info, borderRight, borderLeft));

            // ---------------------------
            // heure de debut
            // ---------------------------

            boolean center = true;
            info = tools.FromDateToHeure(debut);
        	borderRight = true; borderLeft = true;
            t.addCell (this.builCell (iJourSemaine, info, borderRight, borderLeft, center));

            // ---------------------------
            // heure de fin
            // ---------------------------
            info = tools.FromDateToHeure(fin);
        	borderRight = true; borderLeft = true;
            t.addCell (this.builCell (iJourSemaine, info, borderRight, borderLeft, center));

            // ---------------------------
            // duree activite
            // ---------------------------
            info = tools.FromMinutesLongToHeure(tools.FromDureeToMinutesLong(debut, fin));
        	borderRight = true; borderLeft = true;
            t.addCell (this.builCell (iJourSemaine, info, borderRight, borderLeft, center));

            // ---------------------------
            // duree totale
            // ---------------------------
            long dureeActivite = tools.FromDureeToMinutesLong(debut, fin);
            nbHeureTravailleeTotale += dureeActivite;
            info = tools.FromMinutesLongToHeure(nbHeureTravailleeTotale);
        	borderRight = true; borderLeft = true;
            t.addCell (this.builCell (iJourSemaine, info, borderRight, borderLeft, center));

            StructNbHeureActiviteEtSonTauxHoraire.AddNbHeureMinutes (allActiviteTimeWithLeurTaux, dureeActivite, uneActivite.getTarifHoraire(), bulletinSalaire.getTarifHoraire()); 
            nbLineTotal++;
        }
        document.add(t);
        
        // --------------------------------------------
        // changer de page
        // --------------------------------------------
        if ((nbLineTotal > 41) && (nbLineTotal < 46)) {
        	document.add(new AreaBreak());
        	nbLineTotal = 0;
        }        
        
        t = new Table(new float[]{
                tailleTable2 * 0.1f,
                tailleTable2 * 0.01f,
                tailleTable2 * 0.39f,
                tailleTable2 * 0.125f,
                tailleTable2 * 0.125f,
                tailleTable2 * 0.125f,
                tailleTable2 * 0.125f
        });
        
        // --------------------------------------------
        // Ligne blanche
        // --------------------------------------------
        cell = new Cell(1,7);
        cell.setBorder(Border.NO_BORDER);
        t.addCell(cell);

        // --------------------------------------------
        // S'il y a un depassement foafaitaire le mettre
        // --------------------------------------------
        if ((d != null) && (d.length > 0)) {

            float nbHeureDepassement = 0f;

            ArrayList<StructNbHeureActiviteEtSonTauxHoraire> allDepassementTimeWithLeurTaux = new ArrayList<StructNbHeureActiviteEtSonTauxHoraire>(); 
            for (DepassementForfaitaire unDepassementForfait : d) {
                nbHeureDepassement = unDepassementForfait.getDureeenheure();
                StructNbHeureActiviteEtSonTauxHoraire.AddNbHeureMinutes (allDepassementTimeWithLeurTaux, (long)(nbHeureDepassement * 60.0f), unDepassementForfait.getTarifHoraire(), bulletinSalaire.getTarifHoraire());
                StructNbHeureActiviteEtSonTauxHoraire.AddNbHeureMinutes (allActiviteTimeWithLeurTaux, (long)(nbHeureDepassement * 60.0f), unDepassementForfait.getTarifHoraire(), bulletinSalaire.getTarifHoraire());
            }

            
            cell = new Cell(allDepassementTimeWithLeurTaux.size(),5);
            cell.add (new Paragraph(new Text(ei18n.activite_depassementforfaitaire.nls())));
            t.addCell(cell);

            for (StructNbHeureActiviteEtSonTauxHoraire depassementACeTaux : allDepassementTimeWithLeurTaux) {
                long nbMinutesLongDepassement = depassementACeTaux.getNbHeureMinutes();

                cell = new Cell(1,1);
                cell.add(new Paragraph(new Text(tools.FromMinutesLongToHeure(nbMinutesLongDepassement))).setTextAlignment(TextAlignment.CENTER));
                t.addCell(cell);				

                nbHeureTravailleeTotale += nbMinutesLongDepassement;
                cell = new Cell(1, 1);
                cell.add(new Paragraph(new Text(tools.FromMinutesLongToHeure(nbHeureTravailleeTotale))).setTextAlignment(TextAlignment.CENTER));
                t.addCell(cell);
			}

        }

        float nbHeureReportMoisPrecedent = 0f;
        if ((r != null) && (r.length > 0)) {
        	
            ArrayList<StructNbHeureActiviteEtSonTauxHoraire> allRappelTimeWithLeurTaux = new ArrayList<StructNbHeureActiviteEtSonTauxHoraire>(); 
            for (Rappel unRappel : r) {
                nbHeureReportMoisPrecedent = unRappel.getDureeenheure();
                StructNbHeureActiviteEtSonTauxHoraire.AddNbHeureMinutes (allRappelTimeWithLeurTaux, (long)(nbHeureReportMoisPrecedent * 60.0f), unRappel.getTarifHoraire(), bulletinSalaire.getTarifHoraire());
                StructNbHeureActiviteEtSonTauxHoraire.AddNbHeureMinutes (allActiviteTimeWithLeurTaux, (long)(nbHeureReportMoisPrecedent * 60.0f), unRappel.getTarifHoraire(), bulletinSalaire.getTarifHoraire());
            }

            cell = new Cell(allRappelTimeWithLeurTaux.size(),5);
            cell.add (new Paragraph(new Text(ei18n.activite_RappelPrecedent.nls())));
            t.addCell(cell);


            for (StructNbHeureActiviteEtSonTauxHoraire rappelACeTaux : allRappelTimeWithLeurTaux) {
                long nbMinutesLongDepassement = rappelACeTaux.getNbHeureMinutes();

                cell = new Cell(1,1);
                cell.add(new Paragraph(new Text(tools.FromMinutesLongToHeure(nbMinutesLongDepassement))).setTextAlignment(TextAlignment.CENTER));
                t.addCell(cell);				

                nbHeureTravailleeTotale += nbMinutesLongDepassement;
                cell = new Cell(1, 1);
                cell.add(new Paragraph(new Text(tools.FromMinutesLongToHeure(nbHeureTravailleeTotale))).setTextAlignment(TextAlignment.CENTER));
                t.addCell(cell);
			}
        }
        cell = new Cell(1,6);
        cell.add (new Paragraph(new Text(ei18n.activite_TotalHoraire.nls())));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(tools.FromMinutesLongToHeure(nbHeureTravailleeTotale))).setTextAlignment(TextAlignment.CENTER));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        nbLineTotal += 3;
        document.add(t);

        // changer de page
        if ((nbLineTotal > 39) && (nbLineTotal < 46)) {
        	document.add(new AreaBreak());
        	nbLineTotal = 0;
        }        
        
        return allActiviteTimeWithLeurTaux;
    }



    private Cell builCell(int iJourSemaine, String info, boolean borderRight, boolean borderLeft, int rowSpan) {
        return builCell (iJourSemaine, info, borderRight, borderLeft, false, rowSpan);
	}


	private Cell builCell(int i, String info, boolean borderRight, boolean borderLeft) {
        return builCell (i, info, borderRight, borderLeft, false, 1);
    }

	private Cell builCell(int i, String info, boolean borderRight, boolean borderLeft, boolean center) {
        return builCell (i, info, borderRight, borderLeft, center, 1);
    }

	private Cell builCell(int i, String info, boolean borderRight, boolean borderLeft, boolean center, int rowSpan) {
        Color bgC[] = {
                null,
                _bgBlue,
                _bgBisque
        };

        int hasBG= 0;
        if (i % 2 == 1)
            hasBG = 1;
        String jour = tools.JourFromInt(i % 7);
        if (jour.toLowerCase().equals("samedi") || jour.toLowerCase().equals("dimanche"))
            hasBG = 2;


        Paragraph p = new Paragraph(info);
        p.setFontSize(fontSizeActivite);
        if (center)
            p.setTextAlignment(TextAlignment.CENTER);

        Cell cell = new Cell(rowSpan,1).add(p);
        if (center) {
            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        }
        if (rowSpan > 1)
        	cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        
        if (!borderRight)
            cell.setBorderRight(Border.NO_BORDER);

        if (!borderLeft)
            cell.setBorderLeft(Border.NO_BORDER);

        if (hasBG > 0) cell.setBackgroundColor(bgC[hasBG], 0.2f);
        return cell;
    }

    
    private void BuildImpot(Document document, ArrayList<StructNbHeureActiviteEtSonTauxHoraire> allInfos) {
        Paragraph p = new Paragraph();
        p.add("");
        document.add(p);

        float tailleTable2 = (float) (this._pageSize.getWidth());

        Table t = new Table(new float[]{
                tailleTable2 * 0.60f,
                tailleTable2 * 0.1f,
                tailleTable2 * 0.1f,
                tailleTable2 * 0.1f,
                tailleTable2 * 0.1f
        });
        // Header
        Cell cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(ei18n.impot_libelle.nls()).setBold()).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(ei18n.impot_base.nls()).setBold()).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(ei18n.impot_taux.nls()).setBold()).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(ei18n.impot_gain.nls()).setBold()).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(ei18n.impot_retenu.nls()).setBold()).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);

        // ligne salaire brut
        float gainTotal = 0f;
        cell = new Cell(allInfos.size() + 1,1);
        cell.add (new Paragraph(ei18n.impot_salaire_brut.nls()).setBold().setTextAlignment(TextAlignment.RIGHT)).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        for (StructNbHeureActiviteEtSonTauxHoraire uneInfo : allInfos) {
            cell = new Cell(1,1);
            cell.add (new Paragraph(new Text(tools.FromMinutesLongToHeure(uneInfo.getNbHeureMinutes()) )).setTextAlignment(TextAlignment.CENTER));
            t.addCell(cell);
            cell = new Cell(1,1);
            cell.add (new Paragraph(new Text(String.format("%02.2f", uneInfo.getTaux()) + tools.encodeUTF8(tools.tauxhoraire))).setTextAlignment(TextAlignment.CENTER));
            t.addCell(cell);
            cell = new Cell(1,1);
            float gain = uneInfo.getTaux() * uneInfo.getNbHeureMinutes() / 60f;
            gainTotal += gain;
            cell.add (new Paragraph(new Text(String.format("%02.2f", gain) + tools.encodeUTF8(tools.euro))).setTextAlignment(TextAlignment.CENTER));
            t.addCell(cell);
            cell = new Cell(1,1);
            cell.add (new Paragraph(new Text("")));
            t.addCell(cell);

        }
        cell = new Cell(1,4);
        cell.add (new Paragraph(new Text(String.format("%s: %02.2f%s",tools.encodeUTF8(ei18n.TotalBrut.nls()), gainTotal, tools.encodeUTF8(tools.euro))).setTextAlignment(TextAlignment.CENTER)));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);

        // ligne CSG
        DbEnv dbEnv = new DbEnv();
        Env env = dbEnv.getEnv();

        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_CSG.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(String.format("%02.2f", gainTotal))).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(String.format("%02.2f", env.getCSG() * 100f) + tools.encodeUTF8(tools.Percent))).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);
        cell = new Cell(1,1);
        float CSG = gainTotal * (-1) * env.getCSG();
        cell.add (new Paragraph(new Text(String.format("%02.2f", CSG ) + tools.encodeUTF8(tools.euro))).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);

        // ligne impot revenu
        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_imposition.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(String.format("%02.2f", gainTotal))).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(String.format("%02.2f", env.getTauxImposition() * 100f) + tools.encodeUTF8(tools.Percent))).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);
        cell = new Cell(1,1);
        float impot = gainTotal * (-1) * env.getTauxImposition();
        cell.add (new Paragraph(new Text(String.format("%02.2f", impot ) + tools.encodeUTF8(tools.euro))).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);

        // ligne salaire net
        gainTotal += (CSG + impot);

        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_salairenet.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(""));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(""));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text(String.format("%02.2f", gainTotal)+ tools.encodeUTF8(tools.euro))).setTextAlignment(TextAlignment.CENTER));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);


        document.add(t);
    }

}
