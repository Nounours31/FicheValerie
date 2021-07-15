package sfa.fichevalerie.pdf.modele.itxt7;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

import com.itextpdf.io.IOException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.otf.Glyph;
import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfString;
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

public class sample1 {

	public sample1() {
		// TODO Auto-generated constructor stub
	}
	
    public static final String CarteVisite = "E:\\ws\\GitHubPerso\\FicheValerie\\server\\v1.0.0\\resources\\images\\CarteVisite.png";
        
    public static final String DEST = "E:\\tmp\\test.pdf";
    
    public static void main(String args[]) throws IOException, java.io.IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        // sample1.createPdf(DEST);

        ApiPdf x = new ApiPdf();
        x.createPdf(DEST);
    }
    
/*
    public static void createPdf(String dest) throws IOException, java.io.IOException {
    	WriterProperties wp = new WriterProperties();
    	wp.setPdfVersion(PdfVersion.PDF_2_0);//Initialize PDF writer
        
    	PdfWriter writer = new PdfWriter(dest, wp);
    	
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        
        PageSize mediabox = pdf.getDefaultPageSize();
        
        
        // Initialize document
        Document document = new Document(pdf);
        
        // Compose Paragraph
        Text text = new Text("aa");
        text.setFont(PdfFontFactory.createFont ("Courier", PdfEncodings.CP1252, false));
        text.setText(tools.entete);
        
		float[] colorValues = new float[]{0.0f, 0.0f, 0.0f};
		Color c = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), colorValues);
        text.setFontColor(c);

        // final float coef = 1.5f;
        final float coef = 1.0f;
        float[] repartition = {coef, 2.0f - coef}; 
        float taille = (float) (mediabox.getWidth() / 2.0);
        Table t = new Table(new float[]{taille * repartition[0], taille * repartition[1]});
        // t.setWidth(100);

        
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
        
     
        // Add Paragraph to document
        document.add(t);
        
       
        text = new Text ("Mois de juin 2019");
        text.setFont(PdfFontFactory.createFont ("Courier"));
        
		float[] colorValues2 = new float[]{0.0f, 0.0f, 0.0f};
		Color c2 = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), colorValues2);
        text.setFontColor(c);

        float[] colorValuesBG = new float[]{0.5f, 0.7f, 0.9f};
		Color c3 = Color.makeColor(PdfColorSpace.makeColorSpace(PdfName.DeviceRGB), colorValuesBG);
        text.setBackgroundColor(c3);

        
        p = new Paragraph();
        p.add(text);
        p.setFontSize(8);
        p.setTextAlignment(TextAlignment.CENTER);
        document.add(p);
        
        
        //Close document
        document.close();
    }
*/
}
