package com.classicLeathers.classicLeathersTool;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;

public class PdfUtils {
    private String fileName;

    public PdfUtils(String fileName) {
        this.fileName = fileName;
    }

    public String SaveAsPdf(Table table) {
        String file = "D:\\Docs\\" + fileName + ".pdf";
        PdfDocument pdfDoc
                = null;
        try {
            pdfDoc = new PdfDocument(new PdfWriter(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Document doc = new Document(pdfDoc);
        table.useAllAvailableWidth();
        doc.add(table);
        doc.close();
        return "File saved Successfully. FilePath: "+file;
    }
}
