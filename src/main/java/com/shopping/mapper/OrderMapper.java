package com.shopping.mapper;

import com.shopping.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    
    @Select("SELECT * FROM orders")
    List<Order> findAll();
    
    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order findById(Long id);
    
    @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);
    
    @Select("SELECT * FROM orders WHERE status = #{status}")
    List<Order> findByStatus(String status);
    
    @Insert("INSERT INTO orders (user_id, product_id, quantity, total_price, status, create_time) " +
            "VALUES (#{userId}, #{productId}, #{quantity}, #{totalPrice}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);
    
    @Update("UPDATE orders SET user_id = #{userId}, product_id = #{productId}, " +
            "quantity = #{quantity}, total_price = #{totalPrice}, status = #{status}, " +
            "create_time = #{createTime} WHERE id = #{id}")
    int update(Order order);
    
    @Delete("DELETE FROM orders WHERE id = #{id}")
    int deleteById(Long id);
} 