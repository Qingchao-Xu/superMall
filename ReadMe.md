# 抖音商城-字节青训营结营项目

## 功能需求

### 基础功能

**1.认证中心**

- 分发身份令牌
- 校验身份令牌

**2.用户服务**

- 创建用户
- 登录
- 获取用户信息

**3.商品服务**

- 查询商品信息（单个商品、批量商品）

**4.购物车服务**

- 创建购物车
- 清空购物车
- 获取购物车信息

**5.订单服务**

- 创建订单

**6.结算**

- 订单结算

**7.支付**

- 支付

## 系统设计

### 技术架构
dubbo + SpringBoot + MyBatisPlus + Nacos + MySQL

![抖音商城基础架构.png](files/picture/抖音商城基础架构.png)

### 数据库设计

微服务做分库

**1.用户表-user**

| 字段名             | 字段类型         | 是否为空  | 默认值 | 备注   |
|-----------------|--------------|-------|-----|------|
| id              | bigint       | false |     | 主键   |
| created_at      | timestamp    | false |     | 创建时间 |
| updated_at      | timestamp    | false |     | 更新时间 |
| username        | varchar(50)  | false |     | 用户名  |
| email           | varchar(100) | false |     | 邮箱   |
| password_hashed | varchar(100) | false |     | 密码   |

**2.商品表-product**

| 字段名         | 字段类型         | 是否为空  | 默认值 | 备注   |
|-------------|--------------|-------|-----|------|
| id          | bigint       | false |     | 主键   |
| created_at  | timestamp    | false |     | 创建时间 |
| updated_at  | timestamp    | false |     | 更新时间 |
| name        | varchar(50)  | false |     | 商品名称 |
| description | varchar(255) | false |     | 商品描述 |
| picture     | varchar(255) | false |     | 商品图片 |
| price       | decimal      | false |     | 商品价格 |

**3.商品类别表-category**

| 字段名         | 字段类型         | 是否为空  | 默认值 | 备注   |
|-------------|--------------|-------|-----|------|
| id          | bigint       | false |     | 主键   |
| created_at  | timestamp    | false |     | 创建时间 |
| updated_at  | timestamp    | false |     | 更新时间 |
| name        | varchar(50)  | false |     | 类别名称 |
| description | varchar(255) | false |     | 类别描述 |

**4.商品_商品类别关系表-product_category**

| 字段名         | 字段类型      | 是否为空  | 默认值 | 备注       |
|-------------|-----------|-------|-----|----------|
| id          | bigint    | false |     | 主键       |
| created_at  | timestamp | false |     | 创建时间     |
| updated_at  | timestamp | false |     | 更新时间     |
| product_id  | bigint    | false |     | 商品id（外键） |
| category_id | bigint    | false |     | 类别id（外键） |

**5.购物车表-cart**

| 字段名        | 字段类型      | 是否为空  | 默认值 | 备注       |
|------------|-----------|-------|-----|----------|
| id         | bigint    | false |     | 主键       |
| created_at | timestamp | false |     | 创建时间     |
| updated_at | timestamp | false |     | 更新时间     |
| user_id    | bigint    | false |     | 用户id（外键） |
| product_id | bigint    | false |     | 商品id（外键） |
| quantity   | int       | false |     | 商品数量     |

**6.订单表-Order**

| 字段名            | 字段类型                               | 是否为空  | 默认值 | 备注         |
|----------------|------------------------------------|-------|-----|------------|
| id             | bigint                             | false |     | 主键         |
| created_at     | timestamp                          | false |     | 创建时间       |
| updated_at     | timestamp                          | false |     | 更新时间       |
| order_id       | bigint                             | false |     | 订单id(唯一索引) |
| user_id        | bigint                             | false |     | 用户id（外键）   |
| user_currency  | varchar(10)                        | false |     | 用户货币类型     |
| email          | varchar(255)                       | false |     | 用户邮箱       |
| street_address | varchar(255)                       | false |     | 用户街道地址     |
| city           | varchar(100)                       | false |     | 用户城市       |
| state          | varchar(100)                       | false |     | 用户省份       |
| country        | varchar(100)                       | false |     | 用户国家       |
| zip_code       | int                                | false |     | 用户邮编       |
| order_state    | enum('placed', 'paid', 'canceled') | false |     | 订单状态       |

**7.订单商品表-order_item**

| 字段名        | 字段类型      | 是否为空  | 默认值 | 备注       |
|------------|-----------|-------|-----|----------|
| id         | bigint    | false |     | 主键       |
| created_at | timestamp | false |     | 创建时间     |
| updated_at | timestamp | false |     | 更新时间     |
| product_id | bigint    | false |     | 商品id（外键） |
| order_id   | bigint    | false |     | 订单id（外键） |
| quantity   | int       | false |     | 商品数量     |
| cost       | decimal   | false |     | 商品价格     |

**8.支付记录表-payment**

| 字段名            | 字段类型      | 是否为空  | 默认值 | 备注         |
|----------------|-----------|-------|-----|------------|
| id             | bigint    | false |     | 主键         |
| created_at     | timestamp | false |     | 创建时间       |
| updated_at     | timestamp | false |     | 更新时间       |
| user_id        | bigint    | false |     | 用户id（外键）   |
| order_id       | bigint    | false |     | 订单id（外键）   |
| transaction_id | bigint    | false |     | 支付id（唯一索引） |
| amount         | decimal   | false |     | 支付金额       |
| pay_at         | timestamp | false |     | 支付时间       |

### 接口文档

**1.认证中心**

1.1分发身份令牌

- 请求方式：``GET``
- 请求路径：``/auth``
- 请求参数：

| 参数名    | 类型  | 是否必填 | 描述   |
|--------|-----|------|------|
| userId | int | 是    | 用户ID |

- 请求示例：

```bash
GET /auth?userId=1 
```

- 响应参数：

| 参数名   | 类型     | 描述 |
|-------|--------|----|
| token | string | 令牌 |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "token": "asdfjiong213wacs=2fsdaf-3-=2"
  }
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10001 | 用户不存在 |

1.2校验身份令牌

- 请求方式：``GET``
- 请求路径：``请求头中携带``
- 请求参数：

| 参数名   | 类型     | 是否必填 | 描述 |
|-------|--------|------|----|
| token | string | 是    | 令牌 |

- 请求示例：

无

- 响应参数：

| 参数名 | 类型      | 描述     |
|-----|---------|--------|
| res | boolean | 令牌是否有效 |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "res": true
  }
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10002 | 令牌已过期 |
| 10003 | 令牌无效  |
| 10004 | 令牌已注销 |

**2.用户服务**

2.1创建用户

- 请求方式：``POST``
- 请求路径：``/user/register``
- 请求参数：

| 参数名      | 类型  | 是否必填 | 描述  |
|----------|-----|------|-----|
| userName | str | 是    | 用户名 |
| email    | str | 是    | 邮箱  |
| password | str | 是    | 密码  |

- 请求示例：

```bash
{
  "userName": "test",
  "email": "test@qq.com",
  "password": "123456"
}
```

- 响应参数：

| 参数名    | 类型  | 描述   |
|--------|-----|------|
| userId | int | 用户id |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "userId": "100"
  }
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10005 | 邮箱已存在 |

2.2登录

- 请求方式：``POST``
- 请求路径：``/user/login``
- 请求参数：

| 参数名      | 类型  | 是否必填 | 描述 |
|----------|-----|------|----|
| email    | str | 是    | 邮箱 |
| password | str | 是    | 密码 |

- 请求示例：

```bash
{
  "email": "test@qq.com",
  "password": "123456"
}
```

- 响应参数：

| 参数名    | 类型  | 描述   |
|--------|-----|------|
| userId | int | 用户id |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "userId": "100"
  }
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10006 | 邮箱不存在 |
| 10007 | 密码错误  |

2.3获取用户信息

- 请求方式：``GET``
- 请求路径：``/user/info``
- 请求参数：

| 参数名    | 类型  | 是否必填 | 描述   |
|--------|-----|------|------|
| userId | int | 是    | 用户ID |

- 请求示例：

```bash
GET /user/info?userId=1
```

- 响应参数：

| 参数名      | 类型  | 描述  |
|----------|-----|-----|
| userName | str | 用户名 |
| email    | str | 邮箱  |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "userName": "test",
    "email": "test@qq.com"
  }
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10001 | 用户不存在 |

**3.商品服务**

3.1查询商品信息（单个商品）

- 请求方式：``GET``
- 请求路径：``/product/info``
- 请求参数：

| 参数名 | 类型  | 是否必填 | 描述   |
|-----|-----|------|------|
| id  | int | 是    | 商品ID |

- 请求示例：

```bash
GET /product/info?id=1
```

- 响应参数：

| 参数名         | 类型           | 描述 |
|-------------|--------------|----|
| id          | int          | 主键 |
| name        | string       | 名称 |
| description | string       | 描述 |
| picture     | string       | 图片 |
| price       | decimal      | 价格 |
| categories  | List<String> | 分类 |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "name": "商品1",
    "description": "商品描述",
    "picture": "https://example.com/product1.jpg",
    "price": 9.99,
    "categories": [
      "手机",
      "电子设备"
    ]
  }
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10009 | 商品不存在 |

3.2查询商品信息（批量商品）

- 请求方式：``GET``
- 请求路径：``/product/list``
- 请求参数：

| 参数名          | 类型  | 是否必填 | 描述   |
|--------------|-----|------|------|
| page         | int | 是    | 页码   |
| pageSize     | int | 是    | 页大小  |
| categoryName | str | 否    | 类别名称 |

- 请求示例：

```bash
GET /product/list?page=1&pageSize=10&categoryName=手机
```

- 响应参数：

| 参数名         | 类型           | 描述 |
|-------------|--------------|----|
| id          | int          | 主键 |
| name        | string       | 名称 |
| description | string       | 描述 |
| picture     | string       | 图片 |
| price       | decimal      | 价格 |
| categories  | List<String> | 分类 |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "name": "商品1",
      "description": "商品描述",
      "picture": "https://example.com/product1.jpg",
      "price": 9.99,
      "categories": [
        "手机",
        "电子设备"
      ]
    },
    {
      "id": 2,
      "name": "商品2",
      "description": "商品描述",
      "picture": "https://example.com/product2.jpg",
      "price": 9.99,
      "categories": [
        "平板电脑",
        "电子设备"
      ]
    }
  ]
}
```

**4.购物车服务**

4.1创建购物车

- 请求方式：``POST``
- 请求路径：``/cart/create``
- 请求参数：

| 参数名       | 类型  | 是否必填 | 描述   |
|-----------|-----|------|------|
| userId    | int | 是    | 用户ID |
| productId | int | 是    | 商品ID |
| quantity  | int | 是    | 数量   |

- 请求示例：

```bash
{
  "userId": 1,
  "productId": 1,
  "quantity": 1
}
```

- 响应参数：
  无

- 响应示例：

```json
{
  "code": 0,
  "msg": "success"
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10001 | 用户不存在 |
| 10009 | 商品不存在 |

4.2清空购物车

- 请求方式：``GET``
- 请求路径：``/cart/clear``
- 请求参数：

| 参数名    | 类型  | 是否必填 | 描述   |
|--------|-----|------|------|
| userId | int | 是    | 用户ID |

- 请求示例：

```bash
GET /cart/clear?userId=1
```

- 响应参数：
  无

- 响应示例：

```json
{
  "code": 0,
  "msg": "success"
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10001 | 用户不存在 |

4.3获取购物车信息

- 请求方式：``GET``
- 请求路径：``/cart/info``
- 请求参数：

| 参数名    | 类型  | 是否必填 | 描述   |
|--------|-----|------|------|
| userId | int | 是    | 用户ID |

- 请求示例：

```bash
GET /cart/info?userId=1
```

- 响应参数：

| 参数名      | 类型             | 描述    |
|----------|----------------|-------|
| userId   | int            | 用户ID  |
| cartItem | List<CartItem> | 购物车物品 |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "userId": 1,
    "cartItem": [
      {
        "productId": 1,
        "quantity": 1
      },
      {
        "productId": 2,
        "quantity": 1
      }
    ]
  }
}
```

- 错误码：

| 错误码   | 错误    |
|-------|-------|
| 10001 | 用户不存在 |

**5.订单服务**

5.1创建订单

- 请求方式：``POST``
- 请求路径：``/order/create``
- 请求参数：

| 参数名           | 类型              | 是否必填 | 描述   |
|---------------|-----------------|------|------|
| userId        | int             | 是    | 用户ID |
| userCurrency  | str             | 是    | 用户货币 |
| streetAddress | str             | 是    | 街道地址 |
| city          | str             | 是    | 城市   |
| state         | str             | 是    | 省份   |
| country       | str             | 是    | 国家   |
| zipCode       | str             | 是    | 邮编   |
| email         | str             | 是    | 邮箱   |
| orderItem     | List<OrderItem> | 是    | 订单物品 |

- 请求示例：

```bash
{
  "userId": 1,
  "userCurrency": "USD",
  "streetAddress": "123 Main St",
  "city": "Anytown",
  "state": "CA",
  "country": "USA",
  "zipCode": "12345",
  "email": "test@example.com",
  "orderItem": [
    {
      "productId": 1,
      "quantity": 1
      "cost": 9.99
    },
    {
      "productId": 2,
      "quantity": 1
      "cost": 9.99
    }
  ]
}
```

- 响应参数：

| 参数名     | 类型  | 描述   |
|---------|-----|------|
| orderId | int | 订单ID |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "orderId": 1
  }
}
```

- 错误码：

| 错误码   | 错误描述   |
|-------|--------|
| 10001 | 用户不存在  |
| 10009 | 商品不存在  |
| 10016 | 货币不支持  |
| 10017 | 订单创建失败 |

5.2查询订单

- 请求方式：``GET``
- 请求路径：``/order/list``
- 请求参数：

| 参数名    | 类型  | 是否必填 | 描述   |
|--------|-----|------|------|
| userId | int | 是    | 用户ID |

- 请求示例：

```bash
GET /order/list?userId=1
```

- 响应参数：

| 参数名           | 类型              | 描述   |
|---------------|-----------------|------|
| orderItem     | List<OrderItem> | 订单物品 |
| orderId       | int             | 订单ID |
| userId        | int             | 用户ID |
| userCurrency  | str             | 用户货币 |
| streetAddress | str             | 街道地址 |
| city          | str             | 城市   |
| state         | str             | 省份   |
| country       | str             | 国家   |
| zipCode       | str             | 邮编   |
| email         | str             | 邮箱   |
| createdAt     | str             | 创建时间 |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "orderItem": [
        {
          "productId": 1,
          "quantity": 1,
          "cost": 9.99
        },
        {
          "productId": 2,
          "quantity": 1,
          "cost": 9.99
        }
      ],
      "orderId": 1,
      "userId": 1,
      "userCurrency": "USD",
      "streetAddress": "123 Main St",
      "city": "Anytown",
      "state": "CA",
      "country": "USA",
      "zipCode": "12345",
      "email": "test@example.com",
      "createdAt": "2023-02-01 12:00:00"
    },
    {
      "orderItem": [
        {
          "productId": 1,
          "quantity": 1,
          "cost": 9.99
        },
        {
          "productId": 2,
          "quantity": 1,
          "cost": 9.99
        }
      ],
      "orderId": 2,
      "userId": 1,
      "userCurrency": "USD",
      "streetAddress": "123 Main St",
      "city": "Anytown",
      "state": "CA",
      "country": "USA",
      "zipCode": "12345",
      "email": "test@example.com",
      "createdAt": "2023-02-01 12:00:00"
    }
  ]
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10001 | 用户不存在 |
| 10019 | 订单不存在 |

**6.结算**

6.1订单结算

- 请求方式：``POST``
- 请求路径：``/order/checkout``
- 请求参数：

| 参数名                       | 类型  | 是否必填 | 描述     |
|---------------------------|-----|------|--------|
| userId                    | int | 是    | 用户ID   |
| email                     | str | 是    | 邮箱     |
| streetAddress             | str | 是    | 街道地址   |
| city                      | str | 是    | 城市     |
| state                     | str | 是    | 省份     |
| country                   | str | 是    | 国家     |
| zipCode                   | str | 是    | 邮编     |
| creditCardNumber          | str | 是    | 信用卡号   |
| creditCardCVV             | str | 是    | 信用卡CVV |
| creditCardExpirationYear  | str | 是    | 信用卡过期  |
| creditCardExpirationMonth | str | 是    | 信用卡过期  |

- 请求示例：

```bash
{
  "userId": 1,
  "email": "test@example.com",
  "streetAddress": "123 Main St",
  "city": "Anytown",
  "state": "CA",
  "country": "USA",
  "zipCode": "12345",
  "creditCardNumber": "1234567890123456",
  "creditCardCVV": "123",
  "creditCardExpirationYear": "2023",
  "creditCardExpirationMonth": "02"
}
```

- 响应参数：

| 参数名           | 类型  | 描述   |
|---------------|-----|------|
| orderId       | int | 订单ID |
| transactionId | str | 交易ID |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "orderId": 1,
    "transactionId": "1234567890"
  }
}
```

- 错误码：

| 错误码   | 错误描述   |
|-------|--------|
| 10001 | 用户不存在  |
| 10009 | 商品不存在  |
| 10017 | 订单创建失败 |
| 10018 | 支付失败   |

**7.支付**

7.1支付

- 请求方式：``POST``
- 请求路径：``/payment/pay``
- 请求参数：

| 参数名                       | 类型  | 是否必填 | 描述     |
|---------------------------|-----|------|--------|
| amount                    | int | 是    | 金额     |
| creditCardNumber          | str | 是    | 信用卡号   |
| creditCardCVV             | str | 是    | 信用卡CVV |
| creditCardExpirationYear  | str | 是    | 信用卡过期  |
| creditCardExpirationMonth | str | 是    | 信用卡过期  |
| userId                    | int | 是    | 用户ID   |
| orderId                   | int | 是    | 订单ID   |

- 请求示例：

```bash
{
  "amount": 100,
  "creditCardNumber": "1234567890123456",
  "creditCardCVV": "123",
  "creditCardExpirationYear": "2023",
  "creditCardExpirationMonth": "02",
  "userId": 1,
  "orderId": 1
}
```

- 响应参数：

| 参数名           | 类型  | 描述   |
|---------------|-----|------|
| transactionId | int | 交易ID |

- 响应示例：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "transactionId": 1
  }
}
```

- 错误码：

| 错误码   | 错误描述  |
|-------|-------|
| 10001 | 用户不存在 |
| 10019 | 订单不存在 |
| 10018 | 支付失败  |

