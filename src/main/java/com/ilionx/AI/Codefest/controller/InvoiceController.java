package com.ilionx.AI.Codefest.controller;

import com.ilionx.AI.Codefest.model.Order;
import com.ilionx.AI.Codefest.service.InvoiceService;
import com.ilionx.AI.Codefest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderService orderService;
    
    @GetMapping("/{orderId}")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable Long orderId) throws Exception {
        Order order = orderService.getOrderById(orderId)
            .orElseThrow(Exception::new);
            
        byte[] pdfContent = invoiceService.generateInvoice(order);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
            ContentDisposition.builder("attachment")
                .filename("invoice-" + orderId + ".pdf")
                .build());
        
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
} 