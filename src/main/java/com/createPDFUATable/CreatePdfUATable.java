package com.createPDFUATable;

import com.itextpdf.kernel.pdf.*;  
import com.itextpdf.layout.*;  
import com.itextpdf.layout.element.*;  
import com.itextpdf.layout.properties.*;  
import com.itextpdf.kernel.pdf.tagging.StandardRoles;  
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;  
  
public class CreatePdfUATable {  
    public static void main(String[] args) throws Exception {  
        String dest = System.getProperty("user.dir") + "/src/main/resources/pdfua-table3x3.pdf";
        PdfWriter writer = new PdfWriter(dest);  
        PdfDocument pdf = new PdfDocument(writer);  
        pdf.setTagged();
        Document doc = new Document(pdf);  
  
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1}))  
                .useAllAvailableWidth();  
  
        for (int i = 1; i <= 3; i++) {  
            Cell header = new Cell().add(new Paragraph("Header " + i));  
            header.getAccessibilityProperties().setRole(StandardRoles.TH);  
            header.getAccessibilityProperties().addAttributes(  
                    new PdfStructureAttributes("Table").addEnumAttribute("Scope", "Column"));  
            table.addHeaderCell(header);  
        }  
  
        for (int r = 1; r <= 2; r++) {  
            for (int c = 1; c <= 3; c++) {  
                table.addCell(new Cell().add(new Paragraph("Row " + r + ", Col " + c)));  
            }  
        }  
  
        doc.add(table);  
        doc.close();  
    }  
}