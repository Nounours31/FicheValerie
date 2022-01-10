package sfa.fichevalerie.pdf.modele.itxt5;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;

public class AlternatingBackground implements PdfPTableEvent {

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
        PdfPTableEvent event = new AlternatingBackground();
        for (Date day : days) {
            PdfPTable table = getTable(day);
            table.setTableEvent(event);
            document.add(table);
            document.newPage();
        }
        // step 5
        document.close();
    }

    /**
     * Creates a table with film festival screenings.
     *
     * @param day a film festival day
     * @return a table with screenings.
     * @throws DocumentException
     * @throws IOException
     */
    public PdfPTable getTable(Date day)
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(new float[]{2, 1, 2, 5, 1});
        table.setWidthPercentage(100f);
        table.getDefaultCell().setPadding(3);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        table.getDefaultCell().setColspan(5);
        table.getDefaultCell().setBackgroundColor(BaseColor.RED);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(day.toString());
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setColspan(1);
        table.getDefaultCell().setBackgroundColor(BaseColor.ORANGE);
        for (int i = 0; i < 2; i++) {
            table.addCell("Location");
            table.addCell("Time");
            table.addCell("Run Length");
            table.addCell("Title");
            table.addCell("Year");
        }
        table.getDefaultCell().setBackgroundColor(null);
        table.setHeaderRows(3);
        table.setFooterRows(1);
        List<String> screenings = new ArrayList<String>();
        screenings.add("1");
        screenings.add("2");
        for (String screening : screenings) {
            table.addCell("screening.getLocation()");
            table.addCell(String.format("%1$tH:%1$tM", new Date().getTime()));
            table.addCell(String.format("%d '", 120));
            table.addCell("movie.getMovieTitle()");
            table.addCell(String.valueOf(2021));
        }
        return table;
    }

    /**
     * Draws a background for every other row.
     *
     * @see com.itextpdf.text.pdf.PdfPTableEvent#tableLayout(
     *com.itextpdf.text.pdf.PdfPTable, float[][], float[], int, int,
     * com.itextpdf.text.pdf.PdfContentByte[])
     */
    public void tableLayout(PdfPTable table, float[][] widths, float[] heights,
                            int headerRows, int rowStart, PdfContentByte[] canvases) {
        int columns;
        Rectangle rect;
        int footer = widths.length - table.getFooterRows();
        int header = table.getHeaderRows() - table.getFooterRows() + 1;
        for (int row = header; row < footer; row += 2) {
            columns = widths[row].length - 1;
            rect = new Rectangle(widths[row][0], heights[row],
                    widths[row][columns], heights[row + 1]);
            rect.setBackgroundColor(BaseColor.YELLOW);
            rect.setBorder(Rectangle.NO_BORDER);
            canvases[PdfPTable.BASECANVAS].rectangle(rect);
        }
    }
}

