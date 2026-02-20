package com.specialCharactersActualText;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfUAConformance;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.pdfua.PdfUAConfig;
import com.itextpdf.pdfua.PdfUADocument;

import java.io.IOException;

public class SpecialCharactersActualText {
    private static final String FONT = System.getProperty("user.dir")
            + "/src/main/resources/fonts/iTextFreeSansWithE001Glyph.ttf";

    public static void main(String[] args) throws IOException {
        String dest = System.getProperty("user.dir") + "/src/main/resources/pdfua-glyph-actualtext.pdf";
         PdfDocument pdfDoc =  new PdfUADocument(new PdfWriter(dest),
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "Some title", "en-US"));
        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, EmbeddingStrategy.FORCE_EMBEDDED);
        Document document = new Document(pdfDoc);
        document.setFont(font); 
        Paragraph p = new Paragraph();
        Text t = new Text("\u2153");
        t.getAccessibilityProperties().setActualText("a-third");
        p.add(t);
        document.add(p);
        document.close();
    }
}
