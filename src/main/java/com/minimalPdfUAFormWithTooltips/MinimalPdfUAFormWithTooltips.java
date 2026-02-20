package com.minimalPdfUAFormWithTooltips;

import java.io.IOException;  
import com.itextpdf.forms.PdfAcroForm;  
import com.itextpdf.forms.fields.PdfFormCreator;  
import com.itextpdf.forms.fields.PdfTextFormField;  
import com.itextpdf.forms.fields.PdfButtonFormField;  
import com.itextpdf.forms.fields.TextFormFieldBuilder;  
import com.itextpdf.forms.fields.CheckBoxFormFieldBuilder;  
import com.itextpdf.kernel.font.PdfFont;  
import com.itextpdf.kernel.font.PdfFontFactory;  
import com.itextpdf.kernel.geom.Rectangle;  
import com.itextpdf.kernel.pdf.PdfDocument;  
import com.itextpdf.kernel.pdf.PdfWriter;  
import com.itextpdf.kernel.pdf.WriterProperties;  
import com.itextpdf.kernel.pdf.PdfUAConformance;  
import com.itextpdf.pdfua.PdfUAConfig;  
import com.itextpdf.pdfua.PdfUADocument;  
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.layout.Document;  
  
public class MinimalPdfUAFormWithTooltips {  
    public static void main(String[] args) throws IOException {  
        String dest = System.getProperty("user.dir") + "/src/main/resources/pdfua-with-interactive-form-and-tooltips.pdf";  
        WriterProperties wp = new WriterProperties()  
                .addPdfUaXmpMetadata(PdfUAConformance.PDF_UA_1);  
        PdfUADocument pdfDoc = new PdfUADocument(  
                new PdfWriter(dest, wp),  
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "Form Demo", "en-US"));  
  
        // Wrap in Document to set default font (PdfUADocument has no setFont)  
        Document document = new Document(pdfDoc);  
        String fontPath = System.getProperty("user.dir") + "/src/main/resources/fonts/Arial.ttf";  

        PdfFont font = PdfFontFactory.createFont(fontPath,  
                "WinAnsi", EmbeddingStrategy.FORCE_EMBEDDED);  
        document.setFont(font);  
  
        // Ensure AcroForm exists  
        PdfAcroForm form = PdfFormCreator.getAcroForm(pdfDoc, true);  
  
        // Text field with tooltip (TU entry. TU is the name of the key in PDF syntax)  
        PdfTextFormField textField = new TextFormFieldBuilder(pdfDoc, "fullName")  
                .setWidgetRectangle(new Rectangle(50, 700, 200, 20))  
                .setFont(font)  
                .createText();  
        textField.setValue("Jane Doe");  
        textField.setAlternativeName("Enter your full name"); // Tooltip  
        form.addField(textField);  
  
        // Checkbox with tooltip  
        PdfButtonFormField checkBox = new CheckBoxFormFieldBuilder(pdfDoc, "subscribe")  
                .setWidgetRectangle(new Rectangle(50, 650, 20, 20))  
                .createCheckBox();  
        checkBox.setValue("On");  
        checkBox.setAlternativeName("Subscribe to newsletter");  
        form.addField(checkBox);  
  
        document.close();  
    }  
}