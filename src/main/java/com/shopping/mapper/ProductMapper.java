package com.shopping.mapper;

import com.shopping.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    
    @Select("SELECT * FROM products")
    List<Product> findAll();
    
    @Select("SELECT * FROM products WHERE id = #{id}")
    Product findById(Long id);
    
    @Select("SELECT * FROM products WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Product> findByNameContainingIgnoreCase(String name);
    
    @Insert("INSERT INTO products (name, description, price, stock, image_url) " +
            "VALUES (#{name}, #{description}, #{price}, #{stock}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);
    
    @Update("UPDATE products SET name = #{name}, description = #{description}, " +
            "price = #{price}, stock = #{stock}, image_url = #{imageUrl} WHERE id = #{id}")
    int update(Product product);
    
    @Delete("DELETE FROM products WHERE id = #{id}")
    int deleteById(Long id);
} 