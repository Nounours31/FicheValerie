package sfa.fichevalerie.pdf.modele;

import java.io.FileNotFoundException;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;


import sfa.fichevalerie.mysql.api.datawrapper.Activite;
import sfa.fichevalerie.mysql.api.datawrapper.BulletinSalaire;
import sfa.fichevalerie.mysql.api.datawrapper.Personne;
import sfa.fichevalerie.mysql.db.access.DbPersonne;

public class ApiPdf {

	private PageSize _mediabox = null;;
	static private Color _black = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{0.0f, 0.0f, 0.0f});
	static private Color _bgBlue = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), new float[]{0.5f, 0.7f, 0.9f});

	static private PdfFont MainFont = null;
	static private float fontSize = 12;
	
	public ApiPdf()  {
		try {
			ApiPdf.MainFont = PdfFontFactory.createFont ("Courier");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private static final String CarteVisite = "E:\\ws\\GitHubPerso\\FicheValerie\\server\\v1.0.0\\resources\\images\\CarteVisite.png";
    
    private Document initDoc (String dest) throws FileNotFoundException {
    	WriterProperties wp = new WriterProperties();
    	wp.setPdfVersion(PdfVersion.PDF_2_0);//Initialize PDF writer
        
    	PdfWriter writer = new PdfWriter(dest, wp);
    	
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        
         _mediabox = pdf.getDefaultPageSize();
        
        
        // Initialize document
        Document document = new Document(pdf);
    	return document;
    }
    
    


	private void BuildEntete(Document document) throws java.io.IOException {
        // Compose Paragraph
        StringBuffer sText = new StringBuffer ();
        sText.append("Valérie FAGES née Le Pennec" + System.lineSeparator());
        sText.append("35 Av de St Germain – Bâtiment B" + System.lineSeparator());
        sText.append("78160 Marly Le Roi" + System.lineSeparator());
        sText.append("Tél.: 06 70 65 88 84" + System.lineSeparator());
        sText.append(System.lineSeparator());
        sText.append("N° S.S:	2650740192040 - 35" + System.lineSeparator());
        sText.append("Née le 10 juillet 1965 à Mont de Marsan" + System.lineSeparator());

        Text text = new Text (sText.toString());
        text.setFont(ApiPdf.MainFont);
        text.setFontColor(_black);
        text.setFontSize(ApiPdf.fontSize);
        
        final float coef = 1.0f;
        float[] repartition = {coef, 2.0f - coef}; 
        float taille = (float) (this._mediabox.getWidth() / 2.0);
        Table t = new Table(new float[]{taille * repartition[0], taille * repartition[1]});

        
        Cell cell = new Cell();        
        Paragraph p = new Paragraph();
        p.add(text);
        cell.add(p);
        cell.setBorder(Border.NO_BORDER);
        t.addCell(cell);
        
        
        Image fox = new Image(ImageDataFactory.create(CarteVisite));
        float xPercent;
		float yPercent;
		xPercent = yPercent = 0.5f;
		fox.scale(xPercent, yPercent);
        
        p = new Paragraph();
        p.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        p.add(fox);
        p.setTextAlignment(TextAlignment.RIGHT);
        
        cell = new Cell();        
        cell.add(p);
        cell.setBorder(Border.NO_BORDER);
        cell.setTextAlignment(TextAlignment.RIGHT);
        t.addCell(cell);
        
        document.add(t);		
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
        float taille = (float) (this._mediabox.getWidth());
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
