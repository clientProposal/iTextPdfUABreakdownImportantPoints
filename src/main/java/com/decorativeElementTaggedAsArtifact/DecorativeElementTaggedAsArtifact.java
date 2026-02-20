package com.decorativeElementTaggedAsArtifact;

import java.io.IOException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfUAConformance;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.pdfua.PdfUAConfig;
import com.itextpdf.pdfua.PdfUADocument;

public class DecorativeElementTaggedAsArtifact {
    public static void main(String[] args) throws IOException {
        try {
            String dest = System.getProperty("user.dir")
                    + "/src/main/resources/pdfua-decorate-elements-tagged-as-artifacts.pdf";
            String fontPath = System.getProperty("user.dir") + "/src/main/resources/fonts/Arial.ttf";

            WriterProperties wp = new WriterProperties()
                    .addPdfUaXmpMetadata(PdfUAConformance.PDF_UA_1);
            PdfUADocument pdfDoc = new PdfUADocument(
                    new PdfWriter(dest, wp),
                    new PdfUAConfig(PdfUAConformance.PDF_UA_1, "Decorative Element Tagged As Artifact", "en-US"));

            Document document = new Document(pdfDoc);
            PdfFont font = PdfFontFactory.createFont(fontPath, "WinAnsi",
                    com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
            document.setFont(font);

            // Draw decorative border as artifact
            PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
            canvas.saveState()
                    .openTag(new CanvasArtifact())
                    .setLineWidth(2)
                    .setStrokeColor(com.itextpdf.kernel.colors.ColorConstants.BLACK)
                    .rectangle(50, 500, 500, 200)
                    .stroke()
                    .closeTag()
                    .restoreState();

            // Add content inside border
            document.add(new Paragraph("Border Tagged As Artifact").setMarginTop(80).setMarginLeft(60));
            document.add(new Paragraph("The decorative border is now ignored by the screenreader").setMarginLeft(60));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}