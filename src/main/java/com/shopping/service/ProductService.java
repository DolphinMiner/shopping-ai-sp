package com.shopping.service;

import com.shopping.entity.Product;
import com.shopping.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }
    
    public Product getProductById(Long id) {
        return productMapper.findById(id);
    }
    
    public List<Product> searchProducts(String keyword) {
        return productMapper.findByNameContainingIgnoreCase(keyword);
    }
    
    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            productMapper.insert(product);
        } else {
            productMapper.update(product);
        }
        return product;
    }
    
    public void deleteProduct(Long id) {
        productMapper.deleteById(id);
    }
    
    public boolean updateStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        if (product != null && product.getStock() >= quantity) {
            product.setStock(product.getStock() - quantity);
            productMapper.update(product);
            return true;
        }
        return false;
    }
} 