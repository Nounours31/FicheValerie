package sfa.fichevalerie.pdf.modele.itxt7;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbPersonne;

// https://kb.itextpdf.com/home/it7kb/examples

public class ApiPdf {
    private static final SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean _debugMode =  true;
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
    private static final float fontSizePersonne = fontSizeActivite;

	public ApiPdf()  {
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
        
        
        Image fox = new Image(ImageDataFactory.create(tools.CarteVisite));
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

    private Cell builCell(int i, String info) {
	    return builCell (i, info, true, true, false);
    }

    private Cell builCell(int i, String info, boolean borderRight, boolean borderLeft) {
        return builCell (i, info, borderRight, borderLeft, false);
    }

    private Cell builCell(int i, String info, boolean borderRight, boolean borderLeft, boolean center) {
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

        Cell cell = new Cell(1,1).add(p);
        if (!borderRight)
            cell.setBorderRight(Border.NO_BORDER);

        if (!borderLeft)
            cell.setBorderLeft(Border.NO_BORDER);

        if (hasBG > 0) cell.setBackgroundColor(bgC[hasBG], 0.2f);
        return cell;
    }


    public void createPdf(String dest, BulletinSalaire bs, Activite[] a) throws IOException, java.io.IOException {
        Document document = this.initDoc(dest);

        BuildEntete(document);
        BuildInfoPersonne(document, bs);
        BuildActivitee(document, bs, a);
        BuildImpot(document, bs, a);

        //Close document
        document.close();
    }

    public void createPdf(String dest) throws IOException, java.io.IOException {
        Document document = this.initDoc(dest);

        BuildEntete(document);
        BuildInfoPersonne(document);
        BuildActivitee(document);
        BuildImpot(document);

        //Close document
        document.close();
    }



    private void BuildInfoPersonne(Document document) {
        Paragraph p = new Paragraph();
        p.add("Mois de Luin 2021");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setVerticalAlignment(VerticalAlignment.BOTTOM);
        p.setBackgroundColor(_bgBlue, 0.2f);
        p.setFontSize(fontSizeMois);
        if (_debugMode) {
            p.setBorder(debugBorderGreen);
        }
        document.add(p);

        p = new Paragraph();
        p.add("Hure de presence aupres de mr Matinu");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(fontSizeActivite);
        if (_debugMode) {
            p.setBorder(debugBorderBlue);
        }
        document.add(p);


    }
    private void BuildInfoPersonne(Document document, BulletinSalaire bs) {
        Paragraph p = new Paragraph();

        StringBuffer sb = new StringBuffer();
        sb.append("Mois de ");
        sb.append(tools.MoisFromInt(bs.getMois()));
        sb.append(" ");
        sb.append(bs.getAnnee());
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

    private void BuildActivitee(Document document) {
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

        int hasBG = 0;
        String jour = "";

        for (int i = 1; i < 31; i++) {
            String info = "";
            info = tools.JourFromInt(i % 7);
            t.addCell (this.builCell (i, info, false, true));

            info = Integer.toString(i);
            t.addCell (this.builCell (i, info, true, false));

            info = "Cuisine";
            t.addCell (this.builCell (i, info, true, true));

            boolean center = true;
            info = "12:00";
            t.addCell (this.builCell (i, info, true, true, center));

            info = "14:00";
            t.addCell (this.builCell (i, info, true, true, center));

            info = "02:00";
            t.addCell (this.builCell (i, info, true, true, center));

            info = "04:00";
            t.addCell (this.builCell (i, info, true, true, center));
        }

        cell = new Cell(1,7);
        cell.setBorder(Border.NO_BORDER);
        t.addCell(cell);

        cell = new Cell(1,5);
        cell.add (new Paragraph(new Text(ei18n.activite_depassementforfaitaire.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("01:00")));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("10:00")));
        t.addCell(cell);

        cell = new Cell(1,6);
        cell.add (new Paragraph(new Text(ei18n.activite_totalMensuel.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("125:00")));
        t.addCell(cell);

        cell = new Cell(1,6);
        cell.add (new Paragraph(new Text(ei18n.activite_RappelPrecedent.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("05:00")));
        t.addCell(cell);

        cell = new Cell(1,6);
        cell.add (new Paragraph(new Text(ei18n.activite_TotalHoraire.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("135:00")));
        t.addCell(cell);

        document.add(t);
    }
    private void BuildActivitee(Document document, BulletinSalaire bs, Activite[] a) {
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

        int hasBG = 0;
        String jour = "";

        int i = a.length;
        int j = 0;

        while (j < i) {
            j++;

            String info = "";
            Date debut = a[j].getDebut();
            Calendar gc = GregorianCalendar.getInstance();
            gc.setTime(debut);
            int jourOfTheWeek = gc.get(Calendar.DAY_OF_WEEK);

            info = tools.JourFromInt(jourOfTheWeek % 7);
            t.addCell (this.builCell (i, info, false, true));

            info = Integer.toString(gc.get(Calendar.DAY_OF_MONTH));
            t.addCell (this.builCell (i, info, true, false));

            info = a[j].getActivite();
            t.addCell (this.builCell (i, info, true, true));

            boolean center = true;
            info = _sdf.format(gc.getTime());
            t.addCell (this.builCell (i, info, true, true, center));

            info = _sdf.format(a[j].getFin());
            t.addCell (this.builCell (i, info, true, true, center));

            info = String.format("%d", (a[j].getFin().getTime() - a[j].getDebut().getTime()) / (1000 * 60 * 60));
            t.addCell (this.builCell (i, info, true, true, center));

            info = "04:00";
            t.addCell (this.builCell (i, info, true, true, center));
        }

        cell = new Cell(1,7);
        cell.setBorder(Border.NO_BORDER);
        t.addCell(cell);

        cell = new Cell(1,5);
        cell.add (new Paragraph(new Text(ei18n.activite_depassementforfaitaire.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("01:00")));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("10:00")));
        t.addCell(cell);

        cell = new Cell(1,6);
        cell.add (new Paragraph(new Text(ei18n.activite_totalMensuel.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("125:00")));
        t.addCell(cell);

        cell = new Cell(1,6);
        cell.add (new Paragraph(new Text(ei18n.activite_RappelPrecedent.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("05:00")));
        t.addCell(cell);

        cell = new Cell(1,6);
        cell.add (new Paragraph(new Text(ei18n.activite_TotalHoraire.nls())));
        t.addCell(cell);

        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("135:00")));
        t.addCell(cell);

        document.add(t);
    }

    private void BuildImpot(Document document) {
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
        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_salaire_brut.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("325:00" + tools.encodeUTF8(tools.heures))));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("15" + tools.encodeUTF8(tools.tauxhoraire))));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("45" + tools.encodeUTF8(tools.euro))));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);

        // ligne CSG
        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_CSG.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("gain du dessus")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("9.7" + tools.encodeUTF8(tools.Percent))));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("-27.50" + tools.encodeUTF8(tools.euro))));
        t.addCell(cell);

        // ligne impot revenu
        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_imposition.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("gain du dessus")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("9.90" + tools.encodeUTF8(tools.Percent))));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("-27.50" + tools.encodeUTF8(tools.euro))));
        t.addCell(cell);

        // ligne salaire net
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
        cell.add (new Paragraph(new Text("77.5"+ tools.encodeUTF8(tools.euro))));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);


        document.add(t);
    }
    private void BuildImpot(Document document, BulletinSalaire bs, Activite[] a) {
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
        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_salaire_brut.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("325:00" + tools.encodeUTF8(tools.heures))));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("15" + tools.encodeUTF8(tools.tauxhoraire))));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("45" + tools.encodeUTF8(tools.euro))));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        cell.setBackgroundColor(_bgBlue);
        t.addCell(cell);

        // ligne CSG
        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_CSG.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("gain du dessus")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("9.7" + tools.encodeUTF8(tools.Percent))));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("-27.50" + tools.encodeUTF8(tools.euro))));
        t.addCell(cell);

        // ligne impot revenu
        cell = new Cell(1,1);
        cell.add (new Paragraph(ei18n.impot_imposition.nls()).setBold().setTextAlignment(TextAlignment.RIGHT));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("gain du dessus")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("9.90" + tools.encodeUTF8(tools.Percent))));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("-27.50" + tools.encodeUTF8(tools.euro))));
        t.addCell(cell);

        // ligne salaire net
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
        cell.add (new Paragraph(new Text("77.5"+ tools.encodeUTF8(tools.euro))));
        t.addCell(cell);
        cell = new Cell(1,1);
        cell.add (new Paragraph(new Text("")));
        t.addCell(cell);


        document.add(t);
    }

}
