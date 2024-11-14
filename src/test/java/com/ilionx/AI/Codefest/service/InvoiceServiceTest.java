package com.ilionx.AI.Codefest.service;

import com.ilionx.AI.Codefest.model.Order;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class InvoiceServiceTest {
    
    @Autowired
    private InvoiceService invoiceService;
    
    @Test
    void generateInvoice_ShouldCreatePdfBytes() throws DocumentException {
        // Given
        Order order = createSampleOrder();
        
        // When
        byte[] pdfContent = invoiceService.generateInvoice(order);
        
        // Then
        assertNotNull(pdfContent);
        assertTrue(pdfContent.length > 0);
    }
    
    private Order createSampleOrder() {
        Order order = new Order();
        // Set up sample order data
        return order;
    }
} 