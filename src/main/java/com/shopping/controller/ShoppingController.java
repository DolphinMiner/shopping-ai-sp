package com.shopping.controller;

import com.shopping.entity.Order;
import com.shopping.entity.Product;
import com.shopping.entity.User;
import com.shopping.service.CartService;
import com.shopping.service.OrderService;
import com.shopping.service.ProductService;
import com.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShoppingController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> doLogin(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        
        if (userService.authenticate(username, password)) {
            User user = userService.getUserByUsername(username);
            response.put("success", true);
            response.put("userId", user.getId());
            response.put("username", user.getUsername());
        } else {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/cart/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(@RequestParam Long userId, 
                                                        @RequestParam Long productId, 
                                                        @RequestParam Integer quantity) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            cartService.addToCart(userId, productId, quantity);
            response.put("success", true);
            response.put("message", "商品已添加到购物车");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "添加失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/cart/{userId}")
    public String cart(@PathVariable Long userId, Model model) {
        Map<String, Object> cartDetails = cartService.getCartDetails(userId);
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("userId", userId);
        return "cart";
    }
    
    @PostMapping("/cart/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFromCart(@RequestParam Long userId, 
                                                             @RequestParam Long productId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            cartService.removeFromCart(userId, productId);
            response.put("success", true);
            response.put("message", "商品已从购物车移除");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "移除失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/order/{userId}/{productId}")
    public String orderPage(@PathVariable Long userId, @PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        User user = userService.getUserById(userId);
        
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        
        return "order";
    }
    
    @PostMapping("/order/create")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createOrder(@RequestParam Long userId, 
                                                          @RequestParam Long productId, 
                                                          @RequestParam Integer quantity) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Order order = orderService.createOrder(userId, productId, quantity);
            if (order != null) {
                response.put("success", true);
                response.put("orderId", order.getId());
                response.put("message", "订单创建成功");
            } else {
                response.put("success", false);
                response.put("message", "库存不足或商品不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "创建订单失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/orders/{userId}")
    public String userOrders(@PathVariable Long userId, Model model) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userId);
        return "orders";
    }
    
    @GetMapping("/order/detail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return "redirect:/";
        }
        
        Product product = productService.getProductById(order.getProductId());
        User user = userService.getUserById(order.getUserId());
        
        model.addAttribute("order", order);
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        
        return "order-detail";
    }
    
    @PostMapping("/order/pay")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> payOrder(@RequestParam Long orderId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Order order = orderService.updateOrderStatus(orderId, "PAID");
            if (order != null) {
                response.put("success", true);
                response.put("message", "支付成功");
            } else {
                response.put("success", false);
                response.put("message", "订单不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "支付失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
} 