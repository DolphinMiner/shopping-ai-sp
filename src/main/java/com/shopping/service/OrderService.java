package com.shopping.service;

import com.shopping.entity.Order;
import com.shopping.entity.Product;
import com.shopping.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private ProductService productService;
    
    public List<Order> getAllOrders() {
        return orderMapper.findAll();
    }
    
    public Order getOrderById(Long id) {
        return orderMapper.findById(id);
    }
    
    public List<Order> getOrdersByUserId(Long userId) {
        return orderMapper.findByUserIdOrderByCreateTimeDesc(userId);
    }
    
    public Order createOrder(Long userId, Long productId, Integer quantity) {
        Product product = productService.getProductById(productId);
        if (product == null || product.getStock() < quantity) {
            return null;
        }
        
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setStatus("PENDING");
        order.setCreateTime(LocalDateTime.now());
        
        orderMapper.insert(order);
        
        // 更新库存
        productService.updateStock(productId, quantity);
        
        return order;
    }
    
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            orderMapper.update(order);
            return order;
        }
        return null;
    }
    
    public void deleteOrder(Long id) {
        orderMapper.deleteById(id);
    }
} 