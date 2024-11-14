package com.ilionx.AI.Codefest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {
    private OrderItem orderItem;
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setCustomerName("John Doe");
        
        orderItem = new OrderItem();
        orderItem.setProductName("Laptop");
        orderItem.setPrice(1000.0);
        orderItem.setQuantity(2);
        orderItem.setOrder(order);
    }

    @Test
    void calculateSubtotal_ShouldReturnCorrectAmount() {
        // When
        Double subtotal = orderItem.getPrice() * orderItem.getQuantity();

        // Then
        assertEquals(2000.0, subtotal);
    }

    @Test
    void setQuantity_ShouldUpdateQuantity() {
        // When
        orderItem.setQuantity(3);

        // Then
        assertEquals(3, orderItem.getQuantity());
    }

    @Test
    void setPrice_ShouldUpdatePrice() {
        // When
        orderItem.setPrice(1200.0);

        // Then
        assertEquals(1200.0, orderItem.getPrice());
    }

    @Test
    void orderAssociation_ShouldBeCorrect() {
        assertEquals(order, orderItem.getOrder());
    }
}