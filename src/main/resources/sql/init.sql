-- 创建数据库
CREATE DATABASE IF NOT EXISTS shopping_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_db;

-- 删除已存在的表
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

-- 创建用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建商品表
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建订单表
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- 插入测试用户数据
INSERT INTO users (username, password, email, phone, address) VALUES
('admin', '123456', 'admin@example.com', '13800000000', '北京市朝阳区管庄路1号'),
('user1', 'password', 'user1@example.com', '13800000001', '上海市浦东新区陆家嘴环路1000号'),
('user2', 'password', 'user2@example.com', '13800000002', '广州市天河区天河路123号'),
('user3', '123456', 'user3@example.com', '13800000003', '深圳市南山区科技园南区');

-- 插入测试商品数据
INSERT INTO products (name, description, price, stock, image_url) VALUES
('iPhone 15 Pro', '苹果最新款智能手机，配备A17 Pro芯片，钛合金材质，支持5G网络', 8999.00, 50, 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=300&h=300&fit=crop'),
('MacBook Pro 16英寸', '苹果专业级笔记本电脑，M3 Max芯片，32GB内存，1TB存储', 25999.00, 30, 'https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=300&h=300&fit=crop'),
('iPad Air', '轻薄便携的平板电脑，10.9英寸Liquid Retina显示屏', 4599.00, 80, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=300&h=300&fit=crop'),
('AirPods Pro', '主动降噪无线耳机，空间音频，透明模式', 1999.00, 100, 'https://images.unsplash.com/photo-1600294037681-c80b4cb5b434?w=300&h=300&fit=crop'),
('Apple Watch Series 9', '健康监测智能手表，GPS+蜂窝网络版本', 3199.00, 60, 'https://images.unsplash.com/photo-1546868871-7041f2a55e12?w=300&h=300&fit=crop'),
('三星 Galaxy S24 Ultra', '安卓旗舰手机，S Pen手写笔，200MP主摄像头', 7999.00, 40, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=300&h=300&fit=crop'),
('华为 MateBook X Pro', '华为高端商务笔记本，3K触控屏，英特尔第13代处理器', 8999.00, 35, 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=300&h=300&fit=crop'),
('小米13 Ultra', '小米影像旗舰，徕卡专业摄影，骁龙8 Gen2处理器', 5999.00, 45, 'https://images.unsplash.com/photo-1565849904461-04a58ad377e0?w=300&h=300&fit=crop'),
('索尼 WH-1000XM5', '行业领先的无线降噪耳机，30小时续航', 2299.00, 70, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop'),
('戴尔 XPS 13', '超薄商务笔记本，InfinityEdge显示屏，第13代英特尔处理器', 7499.00, 25, 'https://images.unsplash.com/photo-1588872657578-7efd1f1555ed?w=300&h=300&fit=crop'),
('Nintendo Switch OLED', '任天堂游戏机，7英寸OLED屏幕，掌机+主机双模式', 2099.00, 55, 'https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?w=300&h=300&fit=crop'),
('LG OLED C3 65英寸', '4K OLED智能电视，杜比视界，游戏优化模式', 12999.00, 15, 'https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=300&h=300&fit=crop');

-- 插入测试订单数据
INSERT INTO orders (user_id, product_id, quantity, total_price, status, create_time) VALUES
(1, 1, 1, 8999.00, 'PAID', '2024-01-15 10:30:00'),
(1, 4, 2, 3998.00, 'PAID', '2024-01-16 14:20:00'),
(2, 2, 1, 25999.00, 'PENDING', '2024-01-17 09:15:00'),
(2, 3, 1, 4599.00, 'PAID', '2024-01-17 16:45:00'),
(3, 5, 1, 3199.00, 'PAID', '2024-01-18 11:00:00'),
(3, 6, 1, 7999.00, 'PENDING', '2024-01-18 15:30:00'),
(4, 8, 1, 5999.00, 'PAID', '2024-01-19 13:20:00'),
(4, 9, 1, 2299.00, 'PAID', '2024-01-19 17:10:00');

-- 查询测试数据
SELECT '用户表数据' as table_name;
SELECT * FROM users;

SELECT '商品表数据' as table_name;
SELECT * FROM products;

SELECT '订单表数据' as table_name;
SELECT * FROM orders; 