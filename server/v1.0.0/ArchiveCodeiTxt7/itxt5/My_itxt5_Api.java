package sfa.fichevalerie.pdf.modele.itxt5;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class My_itxt5_Api {
    public My_itxt5_Api() {
    }

    public Document createPdf(String filename) throws DocumentException, IOException {
        // Page A4
        Document document = new Document();

        // Output
        OutputStream os = new FileOutputStream (filename);
        PdfWriter.getInstance(document, os);

        // Marges
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 36, 30, 30);
        document.setMarginMirroring(true);

        document.open();
        return document;
    }

    private PdfPTable getTable() {
        PdfPTable table = new PdfPTable(new float[] { 1, 1});
        table.setWidthPercentage(100f);
        table.getDefaultCell().setPadding(3);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        table.getDefaultCell().setBackgroundColor(BaseColor.RED);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell("Location");

        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell("Time");
        table.getDefaultCell().setBackgroundColor(BaseColor.ORANGE);


        table.getDefaultCell().setBackgroundColor(null);
        table.setHeaderRows(3);
        table.setFooterRows(1);

        table.addCell("Cell 1");
        table.addCell("Cell 2");
        table.addCell("Cell 3");
        table.addCell("Cell 4");
        table.addCell("Cell 5");

        return table;
    }

    public void addEntete (Document d) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;
        // we add the four remaining cells with addCell()
        cell = new PdfPCell();
/*
        BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font font = new Font(courier, 12, Font.NORMAL);

        courier = BaseFont.createFont (BaseFont.TIMES_ROMAN, "UTF-8", BaseFont.EMBEDDED);
        font = new Font(courier, 12, Font.NORMAL);
*/
        Font    font = FontFactory.getFont("arial.ttf", BaseFont.IDENTITY_H, true, 12);

        Paragraph p = new Paragraph();
        p.setFont(font);
        p.setAlignment(Element.ALIGN_LEFT);

        p.add("éàùè");
        System.out.println(p.toString());
        cell.addElement(p);
        table.addCell(cell);

        Image img = Image.getInstance(tools.CarteVisite);
        table.addCell(img);

        d.add(table);
    }
    public void close(Document d) {
        d.close();
    }
}
