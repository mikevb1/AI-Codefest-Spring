package com.ilionx.AI.Codefest.service;

import com.ilionx.AI.Codefest.model.Order;
import com.ilionx.AI.Codefest.model.OrderItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
public class InvoiceService {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    public byte[] generateInvoice(Order order) throws DocumentException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add header
            addHeader(document, order);
            
            // Add customer info
            addCustomerInfo(document, order);
            
            // Add order items table
            addItemsTable(document, order);
            
            // Add total amount
            addTotalAmount(document, order);
            
            // Add footer
            addFooter(document);

        } finally {
            document.close();
        }
        
        return out.toByteArray();
    }

    private void addHeader(Document document, Order order) throws DocumentException {
        Paragraph title = new Paragraph("INVOICE", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        
        Paragraph invoiceNumber = new Paragraph(
            "Invoice #: " + order.getId() + "\nDate: " + 
            order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
            NORMAL_FONT
        );
        invoiceNumber.setAlignment(Element.ALIGN_RIGHT);
        
        document.add(title);
        document.add(invoiceNumber);
        document.add(Chunk.NEWLINE);
    }

    private void addCustomerInfo(Document document, Order order) throws DocumentException {
        Paragraph customerInfo = new Paragraph();
        customerInfo.add(new Chunk("Bill To:\n", HEADER_FONT));
        customerInfo.add(new Chunk(order.getCustomerName() + "\n", NORMAL_FONT));
        customerInfo.add(new Chunk(order.getCustomerEmail() + "\n", NORMAL_FONT));
        customerInfo.setSpacingAfter(20);
        
        document.add(customerInfo);
    }

    private void addItemsTable(Document document, Order order) throws DocumentException {
        PdfPTable table = new PdfPTable(4); // 4 columns
        table.setWidthPercentage(100);
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);

        // Add table headers
        Stream.of("Product", "Price", "Quantity", "Subtotal")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
                table.addCell(header);
            });

        // Add items
        for (OrderItem item : order.getOrderItems()) {
            table.addCell(new Phrase(item.getProductName(), NORMAL_FONT));
            table.addCell(new Phrase(String.format("$%.2f", item.getPrice()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(item.getQuantity()), NORMAL_FONT));
            table.addCell(new Phrase(
                String.format("$%.2f", item.getPrice() * item.getQuantity()), 
                NORMAL_FONT
            ));
        }

        document.add(table);
    }

    private void addTotalAmount(Document document, Order order) throws DocumentException {
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidthPercentage(40);
        totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        totalTable.addCell(new Phrase("Total Amount:", HEADER_FONT));
        totalTable.addCell(new Phrase(
            String.format("$%.2f", order.getTotalAmount()), 
            HEADER_FONT
        ));

        document.add(totalTable);
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph(
            "\n\nThank you for your business!", 
            NORMAL_FONT
        );
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }
} 