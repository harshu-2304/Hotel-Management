package com.hotel;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.IOException;

public class PDFGenerator {

    public static String generateReceipt(String title, String items, String method, double amount, String date) {
        String fileName = "Receipt_" + System.currentTimeMillis() + ".pdf";
        String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + fileName;

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            Paragraph heading = new Paragraph(title)
                    .setFontSize(20)
                    .setBold()
                    //.setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLACK);
            document.add(heading);

            document.add(new Paragraph("\n"));

            // Info
            document.add(new Paragraph("Date/Time: " + date));
            document.add(new Paragraph("Payment Method: " + method));
            document.add(new Paragraph("Total Amount: ₹" + String.format("%.2f", amount)));
            document.add(new Paragraph("Ordered Items:\n" + items));

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}

