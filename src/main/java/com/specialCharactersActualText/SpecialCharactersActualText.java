package com.specialCharactersActualText;

import com.itextpdf.io.image.ImageDataFactory;  
import com.itextpdf.kernel.pdf.PdfUAConformance;  
import com.itextpdf.kernel.pdf.PdfWriter;  
import com.itextpdf.kernel.pdf.WriterProperties;  
import com.itextpdf.layout.Document;  
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontCharacteristics;
import com.itextpdf.pdfua.PdfUAConfig;  
import com.itextpdf.pdfua.PdfUADocument;  
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;  

import java.io.FileOutputStream;  
import java.io.IOException;  
  
public class SpecialCharactersActualText {  
    public static void main(String[] args) throws IOException {  
        String dest = System.getProperty("user.dir") + "/src/main/resources/pdfua-actualtext.pdf";  

        String fontPath = System.getProperty("user.dir") + "/src/main/resources/fonts/menlo.ttc"; // embeddable font with glyphs for special chars  
  
        WriterProperties wp = new WriterProperties()  
                .addPdfUaXmpMetadata(PdfUAConformance.PDF_UA_1);  
        PdfUADocument pdfDoc = new PdfUADocument(  
                new PdfWriter(new FileOutputStream(dest), wp),  
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "Doc Title", "en-US"));  
        Document document = new Document(pdfDoc);  
  
        BasicFontProvider provider = new BasicFontProvider(false, false, false);  
        provider.addFont(fontPath + ",0");
        document.setFont(provider.getPdfFont(provider.getFontSelector(  
                java.util.Collections.singletonList("Menlo"),  
                new FontCharacteristics(), null).bestMatch()));  
  
        // Paragraph with special characters; set ActualText for accessibility  
        Paragraph p = new Paragraph("∜");  
        p.getAccessibilityProperties().setActualText("fourth root");  
        document.add(p);  
  
        document.close();  
    }  
}