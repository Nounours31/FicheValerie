package sfa.fichevalerie.pdf.modele.itxt5;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlternatingBackgroundV2 {

    /**
     * The resulting PDF file.
     */
    public static final String RESULT
            = "results/part1/chapter05/alternating.pdf";

    /**
     * Creates a PDF document.
     *
     * @param filename the path to the new PDF document
     * @throws DocumentException
     * @throws IOException
     */
    public void createPdf(String filename) throws DocumentException, IOException {
        // Create a database connection
        // step 1
        Document document = new Document(PageSize.A4.rotate());
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        List<Date> days = new ArrayList<>();
        days.add(new Date());
        days.add(new Date());
        PdfPTable table = getTable();
        document.add(table);
        // step 5
        document.close();
    }

    /**
     * Creates a table with film festival screenings.
     *
     * @return a table with screenings.
     * @throws DocumentException
     * @throws IOException
     */
    public PdfPTable getTable()
            throws DocumentException, IOException {
        // a table with three columns
        PdfPTable table = new PdfPTable(3);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        cell.setColspan(3);
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
}

