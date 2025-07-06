# 简单电商购物网站

这是一个基于Spring Boot + JDK21的简单电商购物网站项目，使用MySQL作为数据库，Redis作为缓存，实现了基本的商品展示、购物车、订单管理功能。

## 技术栈

- **后端框架**: Spring Boot 3.2.0
- **Java版本**: JDK 21
- **数据库**: MySQL 8.0
- **缓存**: Redis 7.0
- **前端**: Thymeleaf + HTML + CSS + JavaScript
- **构建工具**: Maven
- **ORM**: Spring Data JPA

## 功能特性

- ✅ 商品展示页面
- ✅ 用户登录系统
- ✅ 购物车功能（基于Redis）
- ✅ 订单创建和管理
- ✅ 支付功能（模拟）
- ✅ 响应式设计
- ✅ 简洁的用户界面

## 数据库设计

### 用户表 (users)
- id: 用户ID（主键）
- username: 用户名
- password: 密码
- email: 邮箱
- phone: 电话
- address: 地址

### 商品表 (products)
- id: 商品ID（主键）
- name: 商品名称
- description: 商品描述
- price: 价格
- stock: 库存
- image_url: 图片URL

### 订单表 (orders)
- id: 订单ID（主键）
- user_id: 用户ID（外键）
- product_id: 商品ID（外键）
- quantity: 数量
- total_price: 总价
- status: 订单状态
- create_time: 创建时间

## 快速开始

### 1. 环境准备

确保您的系统已安装：
- JDK 21
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+

### 2. 数据库配置

1. 启动MySQL服务
2. 执行SQL脚本初始化数据库：
   ```bash
   mysql -u root -p < src/main/resources/sql/init.sql
   ```

### 3. Redis配置

1. 启动Redis服务：
   ```bash
   redis-server
   ```

### 4. 应用配置

修改 `src/main/resources/application.yml` 文件中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shopping_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root
    password: 您的MySQL密码
```

### 5. 运行应用

1. 克隆项目：
   ```bash
   git clone <repository-url>
   cd shopping-ai-sp
   ```

2. 编译项目：
   ```bash
   mvn clean compile
   ```

3. 运行应用：
   ```bash
   mvn spring-boot:run
   ```

4. 访问应用：
   - 打开浏览器访问：http://localhost:8080
   - 默认端口为8080

## 测试账户

系统已预置以下测试账户：

| 用户名 | 密码 | 描述 |
|--------|------|------|
| admin | 123456 | 管理员账户 |
| user1 | password | 测试用户1 |
| user2 | password | 测试用户2 |
| user3 | 123456 | 测试用户3 |

## 使用说明

### 1. 首页浏览
- 访问首页查看商品列表
- 无需登录即可浏览商品

### 2. 用户登录
- 点击"登录"按钮进入登录页面
- 使用测试账户登录

### 3. 购物车功能
- 登录后可以将商品加入购物车
- 购物车数据存储在Redis中
- 可以查看和管理购物车内容

### 4. 订单管理
- 直接购买商品或从购物车购买
- 填写收货信息
- 模拟支付流程
- 查看订单历史

## 项目结构

```
shopping-ai-sp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── shopping/
│   │   │           ├── ShoppingApplication.java
│   │   │           ├── config/
│   │   │           │   └── RedisConfig.java
│   │   │           ├── controller/
│   │   │           │   └── ShoppingController.java
│   │   │           ├── entity/
│   │   │           │   ├── User.java
│   │   │           │   ├── Product.java
│   │   │           │   └── Order.java
│   │   │           ├── repository/
│   │   │           │   ├── UserRepository.java
│   │   │           │   ├── ProductRepository.java
│   │   │           │   └── OrderRepository.java
│   │   │           └── service/
│   │   │               ├── UserService.java
│   │   │               ├── ProductService.java
│   │   │               ├── OrderService.java
│   │   │               └── CartService.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── sql/
│   │       │   └── init.sql
│   │       └── templates/
│   │           ├── index.html
│   │           ├── login.html
│   │           ├── cart.html
│   │           ├── order.html
│   │           └── orders.html
│   └── test/
└── pom.xml
```

## 开发说明

### 添加新商品
可以直接在数据库中添加新商品，或者扩展管理功能。

### 扩展功能
- 用户注册功能
- 商品分类管理
- 订单详情页面
- 支付网关集成
- 商品搜索功能

## 注意事项

1. 这是一个演示项目，密码未加密存储
2. 支付功能为模拟实现
3. 图片使用的是Unsplash的外链，生产环境建议上传到自己的服务器
4. Redis配置使用默认设置，生产环境建议配置密码

## 许可证

MIT License

## 联系方式

如有问题，请通过Issue或邮件联系。