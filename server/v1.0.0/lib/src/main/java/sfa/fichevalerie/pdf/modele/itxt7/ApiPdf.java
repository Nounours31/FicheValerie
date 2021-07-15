package sfa.fichevalerie.pdf.modele.itxt7;

import java.io.FileNotFoundException;

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
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
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


import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbPersonne;

// https://kb.itextpdf.com/home/it7kb/examples

public class ApiPdf {
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
	static private float fontSize = 12;
	
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
    	return document;
    }
    
    


	private void BuildEntete(Document document) throws java.io.IOException {
        Text text = new Text (tools.entete);
        text.setFont(ApiPdf.MainFont);
        text.setFontColor(_black);
        text.setFontSize(ApiPdf.fontSize);
        
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

        p = new Paragraph();
        p.setHeight(60f);
        p.add("");
        if (_debugMode) {
            p.setBorder(debugBorderRed);
        }
        document.add(p);

        p = new Paragraph();
        p.add("Mois de Luin 2021");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setVerticalAlignment(VerticalAlignment.BOTTOM);
        p.setBackgroundColor(_bgBlue, 0.2f);
        p.setFontSize(20f);
        if (_debugMode) {
            p.setBorder(debugBorderGreen);
        }
        document.add(p);

        p = new Paragraph();
        p.add("Hure de presence aupres de mr Matinu");
        p.setTextAlignment(TextAlignment.CENTER);
        if (_debugMode) {
            p.setBorder(debugBorderBlue);
        }
        document.add(p);


        float tailleTable2 = (float) (this._pageSize.getWidth());
        t = new Table(new float[]{
                tailleTable2 * 0.1f,
                tailleTable2 * 0.01f,
                tailleTable2 * 0.39f,
                tailleTable2 * 0.2f,
                tailleTable2 * 0.2f,
                tailleTable2 * 0.05f,
                tailleTable2 * 0.05f
        });


        cell = new Cell(2,2);
        cell.setBorder(Border.NO_BORDER);
        t.addCell(cell);

        cell = new Cell(2,1);
        p = new Paragraph("Activitee");
        p.setTextAlignment(TextAlignment.CENTER);
        if (_debugMode) {
            p.setBorder(debugBorderBlue);
        }
        cell.add(p);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        t.addCell(cell);

        cell = new Cell(1,2);
        p = new Paragraph("Horaire");
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(2,1);
        p = new Paragraph("Nb Heure");
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(2,1);
        p = new Paragraph("Nb Heure Total");
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(1,1);
        p = new Paragraph("Debut");
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        t.addCell(cell);

        cell = new Cell(1,1);
        p = new Paragraph("Fin");
        p.setTextAlignment(TextAlignment.CENTER);
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

            info = "12:00";
            t.addCell (this.builCell (i, info, true, true));

            info = "14:00";
            t.addCell (this.builCell (i, info, true, true));

            info = "02:00";
            t.addCell (this.builCell (i, info, true, true));

            info = "04:00";
            t.addCell (this.builCell (i, info, true, true));
        }
        document.add(t);
    }

    private Cell builCell(int i, String info) {
        return builCell (i, info, true, true);
    }

    private Cell builCell(int i, String info, boolean borderRight, boolean borderLeft) {
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
        p.setFontSize(8f);
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

        BuildResume(document);



        //Close document
        document.close();
    }

    public void createPdf(String dest) throws IOException, java.io.IOException {
        Document document = this.initDoc(dest);

        BuildEntete(document);
        BuildResume(document);



        //Close document
        document.close();
    }




	private void BuildResume(Document document) {
		// TODO Auto-generated method stub
		
	}








	private void BuildInfoPersonne(Document document, BulletinSalaire bs) {
		String periode = tools.MoisFromInt(bs.getMois());
		periode += (" " + bs.getAnnee());
    	Text text = new Text (String.format("Mois de %s\n", periode));
        text.setFont(ApiPdf.MainFont);
        text.setFontColor(_black);
        text.setFontSize(ApiPdf.fontSize);
        text.setBackgroundColor(_bgBlue);
        
        Paragraph p = new Paragraph();
        p.add(text);
        p.setTextAlignment(TextAlignment.LEFT);
        document.add(p);
        
        DbPersonne x = new DbPersonne();
        Personne[] pers = x.getAllPersonnes(bs.getIdPersonne());
        text = new Text("Heures de presence aupres de: " + pers[0].getGenre() + " " + pers[0].getNom());

        text.setFont(ApiPdf.MainFont);
        text.setFontColor(_black);
        text.setFontSize(ApiPdf.fontSize);
        
        p = new Paragraph();
        p.add(text);
        p.setTextAlignment(TextAlignment.LEFT);
        document.add(p);
	}

	
	private void BuildActivitee(Document document, BulletinSalaire bs, Activite[] a) {
		final float coef = 1.0f / 7.0f;
        float[] repartition = {
        		coef, 
        		coef, 
        		coef, 
        		coef, 
        		coef, 
        		coef, 
        		}; 
        float taille = (float) (this._pageSize.getWidth());
        Table t = new Table(new float[]{
        		taille * repartition[0], 
        		taille * repartition[1], 
        		taille * repartition[2], 
        		taille * repartition[3], 
        		taille * repartition[4], 
        		taille * repartition[5], 
        		taille * repartition[6]});
        t.setFont(MainFont);
        t.setFontColor(_black);
        t.setTextAlignment(TextAlignment.LEFT);
        t.addCell("");
        t.addCell("");
        t.addCell("Activitee");
        t.addCell("Debut");
        t.addCell("Fin");
        t.addCell("Nb Heure");
        t.addCell("Nb Heure cumulee");
        
        
	}


}
