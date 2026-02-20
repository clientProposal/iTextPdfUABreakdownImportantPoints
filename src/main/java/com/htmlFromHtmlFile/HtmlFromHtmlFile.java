package com.htmlFromHtmlFile;  
  
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;  
import java.nio.file.Paths;  
  
import com.itextpdf.html2pdf.ConverterProperties;  
import com.itextpdf.html2pdf.HtmlConverter;  
import com.itextpdf.kernel.pdf.PdfUAConformance;  
import com.itextpdf.kernel.pdf.PdfWriter;  
import com.itextpdf.kernel.pdf.WriterProperties;  
import com.itextpdf.pdfua.PdfUAConfig;  
import com.itextpdf.pdfua.PdfUADocument;  
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;  

  
public class HtmlFromHtmlFile {  
    String htmlFilePath = System.getProperty("user.dir") + "/src/main/resources/html/formWithTooltips.html";  
    String outputPdf = System.getProperty("user.dir") + "/src/main/resources/pdfua-form-with-tooltips.pdf";  
  
    public static void main(String[] args) throws IOException {  
        new HtmlFromHtmlFile().createPdfUaFromHtml();  
    }  
  
    public void createPdfUaFromHtml() throws IOException {  
        // 1. Read HTML content from file  
        String html = new String(Files.readAllBytes(Paths.get(htmlFilePath)), StandardCharsets.UTF_8);
  
        // 2. WriterProperties: inject PDF/UA XMP metadata  
        WriterProperties wp = new WriterProperties()  
                .addPdfUaXmpMetadata(PdfUAConformance.PDF_UA_1);  
  
        // 3. PdfUADocument: enables PDF/UA validation and sets required catalog entries  
        //    - Tagging is enabled automatically  
        //    - DisplayDocTitle is set to true  
        //    - Language and title are set from PdfUAConfig  
        PdfUADocument pdfDoc = new PdfUADocument(  
                new PdfWriter(outputPdf, wp),  
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "Heading Hierarchy Example", "en-US"));  
  
        // 4. Font provider: embed a font (PDF/UA requires all fonts to be embedded)  
        BasicFontProvider provider = new BasicFontProvider(false, false, false);  
        provider.addFont(System.getProperty("user.dir") + "/src/main/resources/fonts/Arial.ttf");  
  
        // 5. ConverterProperties: bind the font provider to pdfHTML  
        ConverterProperties props = new ConverterProperties()  
                .setFontProvider(provider);  
  
        // 6. Convert HTML to PDF using the PdfUADocument; pdfHTML emits structure elements because the document is tagged  
        HtmlConverter.convertToPdf(html, pdfDoc, props);  
  
        // 7. Close: triggers PDF/UA validation and writes the file  
        pdfDoc.close();  
    }  
}