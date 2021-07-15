package sfa.fichevalerie.pdf.modele.itxt5;

import com.itextpdf.text.Document;

public class MySample {
    public MySample() {
    }

    public void build () {
        My_itxt5_Api api = new My_itxt5_Api();

        try {
            Document d = api.createPdf("e:\\tmp\\toto.pdf");
            api.addEntete(d);
            //api.addBody();
            api.close(d);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
