package com.ilionx.AI.Codefest.controller;

import com.ilionx.AI.Codefest.model.Order;
import com.ilionx.AI.Codefest.model.OrderItem;
import com.ilionx.AI.Codefest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        if (!orderService.getOrderById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        order.setId(id);
        return ResponseEntity.ok(orderService.updateOrder(order));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderService.getOrderById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> addOrderData() {
        
        // Create sample orders
        Order[] orders = new Order[3];
        OrderItem[] items = new OrderItem[3];
        String[] names = {"John Doe", "Jane Smith", "Bob Johnson"};
        String[] products = {"Laptop", "Smartphone", "Headphones"};
        Double[] prices = {999.99, 599.99, 149.99};
        String[] statuses = {"PENDING", "COMPLETED", "SHIPPED"};

        for (int i = 0; i < 3; i++) {
            orders[i] = new Order();
            items[i] = new OrderItem();
            
            orders[i].setCustomerName(names[i]);
            items[i].setQuantity(10);
            items[i].setPrice(prices[i]);
            items[i].setProductName(products[i]);
            items[i].setOrder(orders[i]);
            orders[i].setOrderItems(List.of(items[i]));
            orders[i].setOrderDate(LocalDateTime.now().minusDays(i));
            orders[i].setStatus(statuses[i]);
            orders[i].calculateTotalAmount();
            orderService.createOrder(orders[i]);
        }

        return ResponseEntity.ok("Sample order data has been loaded successfully!");
    }
} 