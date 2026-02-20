package com.taggedHtmlXmpPfdUa;  
  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import com.itextpdf.html2pdf.ConverterProperties;  
import com.itextpdf.html2pdf.HtmlConverter;  
import com.itextpdf.kernel.pdf.PdfUAConformance;  
import com.itextpdf.kernel.pdf.PdfWriter;  
import com.itextpdf.kernel.pdf.WriterProperties;  
import com.itextpdf.pdfua.PdfUAConfig;  
import com.itextpdf.pdfua.PdfUADocument;  
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;  
  
public class TaggedHtmlXmpPfdUa {  
    public static void main(String[] args) throws IOException {  
        // 1. Input HTML and output file path  
        String html = "<h1>Title</h1><p>Hello, PDF/UA!</p>";  
        String dest = System.getProperty("user.dir") + "/src/main/resources/output-ua.pdf";  
  
        // 2. WriterProperties: inject PDF/UA XMP metadata (required for PDF/UA identification)  
        // XMP metadata: Identifies the file as PDF/UA to validators and assistive tools

        WriterProperties wp = new WriterProperties()  
                .addPdfUaXmpMetadata(PdfUAConformance.PDF_UA_1);   

        // 3. PdfUADocument: enables PDF/UA validation and sets required catalog entries  
        // - Tagging is enabled automatically  
        // - DisplayDocTitle is set to true  
        // - Language and title are set from PdfUAConfig  

        PdfUADocument pdfDoc = new PdfUADocument(  
                new PdfWriter(new FileOutputStream(dest), wp),  
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "Document Title", "en-US")); 
  
        // PdfUADocument internally calls setTagged

        // 4. Font provider: avoid non-embeddable standard fonts (PDF/UA requires all fonts to be embedded)  
        BasicFontProvider provider = new BasicFontProvider(false, false, false); 
        // no system fonts. Must be embedded.
        provider.addFont(System.getProperty("user.dir") + "/src/main/resources/fonts/Arial.ttf");
  
        // 5. ConverterProperties: bind the font provider to pdfHTML so it uses the embeddable font  
        ConverterProperties props = new ConverterProperties()  
            .setFontProvider(provider);  
  
        // 6. Convert HTML to PDF using the PdfUADocument; pdfHTML will emit structure elements because the document is tagged  
        HtmlConverter.convertToPdf(html, pdfDoc, props);  
  
        // 7. Close: triggers PDF/UA validation (e.g., font embedding) and writes the file  
        pdfDoc.close(); // validation runs here; non-embedded fonts would throw PdfUAConformanceException   
    }  
}