package com.ilionx.AI.Codefest.service;

import com.ilionx.AI.Codefest.model.Order;
import com.ilionx.AI.Codefest.model.OrderItem;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        order.setId(1L);
        order.setCustomerName("John Doe");
        order.setCustomerEmail("john.doe@example.com");
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();

        OrderItem item1 = new OrderItem();
        item1.setProductName("Gaming Laptop");
        item1.setPrice(1299.99);
        item1.setQuantity(1);
        item1.setOrder(order);
        items.add(item1);

        OrderItem item2 = new OrderItem();
        item2.setProductName("Wireless Mouse");
        item2.setPrice(49.99);
        item2.setQuantity(2);
        item2.setOrder(order);
        items.add(item2);

        order.setOrderItems(items);
        order.calculateTotalAmount();
        return order;
    }
} 