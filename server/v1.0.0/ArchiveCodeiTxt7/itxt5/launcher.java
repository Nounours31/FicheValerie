package sfa.fichevalerie.pdf.modele.itxt5;

// https://kb.itextpdf.com/home/it5kb/examples

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import java.io.IOException;

public class launcher {
    public static void main(String[] args) throws DocumentException, IOException {
        // new AlternatingBackgroundV2().createPdf("e:\\tmp\\titi.pdf");
        MySample x = new MySample();
        x.build();
    }
}
