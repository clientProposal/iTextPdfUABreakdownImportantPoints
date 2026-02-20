package com.pdfUaWithImageAndTitle;  
  
import com.itextpdf.io.image.ImageDataFactory;  
import com.itextpdf.kernel.font.PdfFont;  
import com.itextpdf.kernel.pdf.PdfUAConformance;  
import com.itextpdf.kernel.pdf.PdfWriter;  
import com.itextpdf.kernel.pdf.WriterProperties;  
import com.itextpdf.kernel.pdf.tagging.StandardRoles;  
import com.itextpdf.layout.Document;  
import com.itextpdf.layout.element.Image;  
import com.itextpdf.layout.element.Paragraph;  
import com.itextpdf.layout.font.FontCharacteristics;  
import com.itextpdf.layout.font.FontInfo;  
import com.itextpdf.layout.font.FontSelector;  
import com.itextpdf.pdfua.PdfUAConfig;  
import com.itextpdf.pdfua.PdfUADocument;  
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;  
  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.util.Collections;  
  
public class PdfUaWithImageAndTitle {  
    public static void main(String[] args) throws IOException {  
        String dest = System.getProperty("user.dir") + "/src/main/resources/pdfua-with-image.pdf";  
        String imagePath = System.getProperty("user.dir") + "/src/main/resources/images/cat.jpg";  
        String fontPath = System.getProperty("user.dir") + "/src/main/resources/fonts/Arial.ttf";  
  
        // 1) Create PdfUADocument with PDF/UA XMP metadata and config (sets tagging, title, language)  
        WriterProperties wp = new WriterProperties()  
                .addPdfUaXmpMetadata(PdfUAConformance.PDF_UA_1);  
        PdfUADocument pdfDoc = new PdfUADocument(  
                new PdfWriter(new FileOutputStream(dest), wp),  
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "Document Title", "en-US"));  
        Document document = new Document(pdfDoc);  
  
        // 2) Use an embeddable font to satisfy PDF/UA font-embedding requirement  
        BasicFontProvider provider = new BasicFontProvider(false, false, false);  
        provider.addFont(fontPath);  
        // Resolve the added font by its family (or alias) to avoid null selector  
        FontSelector selector = provider.getFontSelector(  
                Collections.singletonList(provider.getFontSet().getFonts().iterator().next().getDescriptor().getFamilyNameLowerCase()),  
                new FontCharacteristics(), null);  
        FontInfo fontInfo = selector.bestMatch();  
        PdfFont pdfFont = provider.getPdfFont(fontInfo);  
        document.setFont(pdfFont);  
  
        // 3) Add a title (optional; already set via PdfUAConfig)  
        document.add(new Paragraph("Sample Title"));  
  
        // 4) Add an image with alternate description (required for PDF/UA)  
        Image img = new Image(ImageDataFactory.create(imagePath));  
        img.getAccessibilityProperties().setRole(StandardRoles.FIGURE);  
        img.getAccessibilityProperties().setAlternateDescription("A cat");  
        document.add(img);  
        document.close();  
    }  
}