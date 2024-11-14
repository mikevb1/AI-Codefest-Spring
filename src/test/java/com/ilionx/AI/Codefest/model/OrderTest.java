package com.ilionx.AI.Codefest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;
    private OrderItem orderItem1;
    private OrderItem orderItem2;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setCustomerName("John Doe");
        order.setOrderDate(LocalDateTime.now());
        
        orderItem1 = new OrderItem();
        orderItem1.setProductName("Laptop");
        orderItem1.setPrice(1000.0);
        orderItem1.setQuantity(2);
        orderItem1.setOrder(order);

        orderItem2 = new OrderItem();
        orderItem2.setProductName("Mouse");
        orderItem2.setPrice(50.0);
        orderItem2.setQuantity(1);
        orderItem2.setOrder(order);
        
        order.setOrderItems(new ArrayList<>());
        order.getOrderItems().add(orderItem1);
        order.getOrderItems().add(orderItem2);
    }

    @Test
    void calculateTotalAmount_ShouldReturnCorrectSum() {
        // When
        Double totalAmount = order.calculateTotalAmount();

        // Then
        assertEquals(2050.0, totalAmount); // (1000 * 2) + (50 * 1)
    }

    @Test
    void orderShouldHaveCorrectNumberOfItems() {
        assertEquals(2, order.getOrderItems().size());
    }

    @Test
    void orderItemsShouldHaveCorrectOrder() {
        order.getOrderItems().forEach(item -> 
            assertEquals(order, item.getOrder())
        );
    }

    @Test
    void setCustomerName_ShouldUpdateCustomerName() {
        // When
        order.setCustomerName("Jane Doe");

        // Then
        assertEquals("Jane Doe", order.getCustomerName());
    }
} 