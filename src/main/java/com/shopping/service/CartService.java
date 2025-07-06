package com.shopping.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CartService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ProductService productService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private String getCartKey(Long userId) {
        return "cart:" + userId;
    }
    
    public void addToCart(Long userId, Long productId, Integer quantity) {
        String cartKey = getCartKey(userId);
        Map<String, Object> cart = getCart(userId);
        
        if (cart.containsKey(productId.toString())) {
            Integer existingQuantity = (Integer) cart.get(productId.toString());
            cart.put(productId.toString(), existingQuantity + quantity);
        } else {
            cart.put(productId.toString(), quantity);
        }
        
        redisTemplate.opsForValue().set(cartKey, cart, 24, TimeUnit.HOURS);
    }
    
    public void removeFromCart(Long userId, Long productId) {
        String cartKey = getCartKey(userId);
        Map<String, Object> cart = getCart(userId);
        cart.remove(productId.toString());
        redisTemplate.opsForValue().set(cartKey, cart, 24, TimeUnit.HOURS);
    }
    
    public Map<String, Object> getCart(Long userId) {
        String cartKey = getCartKey(userId);
        Map<String, Object> cart = (Map<String, Object>) redisTemplate.opsForValue().get(cartKey);
        return cart == null ? new HashMap<>() : cart;
    }
    
    public void clearCart(Long userId) {
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }
    
    public Map<String, Object> getCartDetails(Long userId) {
        Map<String, Object> cart = getCart(userId);
        Map<String, Object> cartDetails = new HashMap<>();
        
        for (Map.Entry<String, Object> entry : cart.entrySet()) {
            Long productId = Long.parseLong(entry.getKey());
            Product product = productService.getProductById(productId);
            if (product != null) {
                Map<String, Object> productInfo = new HashMap<>();
                productInfo.put("product", product);
                productInfo.put("quantity", entry.getValue());
                cartDetails.put(entry.getKey(), productInfo);
            }
        }
        
        return cartDetails;
    }
} 