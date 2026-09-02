-- ============================================
-- E-COMMERCE ORDER MANAGEMENT SYSTEM DATABASE
-- ============================================

-- CREATE DATABASE

CREATE DATABASE IF NOT EXISTS ecommerce;

USE ecommerce;

-- ============================================
-- CUSTOMERS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS customers (

```
customer_id INT PRIMARY KEY,

customer_name VARCHAR(100) NOT NULL,

email VARCHAR(100) UNIQUE NOT NULL,

address VARCHAR(255)
```

);

-- ============================================
-- PRODUCTS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS products (

```
product_id INT PRIMARY KEY,

product_name VARCHAR(150) NOT NULL,

price DOUBLE NOT NULL,

stock INT NOT NULL
```

);

-- ============================================
-- ORDERS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS orders (

```
order_id INT PRIMARY KEY,

customer_id INT,

product_id INT,

quantity INT NOT NULL,

total_amount DOUBLE NOT NULL,

status VARCHAR(50) DEFAULT 'CREATED',

FOREIGN KEY (customer_id)
REFERENCES customers(customer_id),

FOREIGN KEY (product_id)
REFERENCES products(product_id)
```

);

-- ============================================
-- TRANSACTIONS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS transactions (

```
transaction_id INT AUTO_INCREMENT PRIMARY KEY,

order_id INT,

payment_type VARCHAR(50),

amount DOUBLE,

payment_status VARCHAR(50),

transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (order_id)
REFERENCES orders(order_id)
```

);

-- ============================================
-- INSERT SAMPLE CUSTOMERS
-- ============================================

INSERT INTO customers VALUES

(101, 'Susan', '[susan@email.com](mailto:susan@email.com)', 'Chennai'),

(102, 'Arun', '[arun@email.com](mailto:arun@email.com)', 'Chennai'),

(103, 'Rahul', '[rahul@email.com](mailto:rahul@email.com)', 'Bangalore');

-- ============================================
-- INSERT SAMPLE PRODUCTS
-- ============================================

INSERT INTO products VALUES

(1, 'Laptop', 50000.00, 5),

(2, 'Smartphone', 25000.00, 10),

(3, 'Headphones', 2000.00, 20),

(4, 'Keyboard', 1500.00, 15),

(5, 'Mouse', 800.00, 25);

-- ============================================
-- INSERT SAMPLE ORDER
-- ============================================

INSERT INTO orders VALUES

(1001, 101, 1, 2, 100000.00, 'CONFIRMED');

-- ============================================
-- INSERT SAMPLE TRANSACTION
-- ============================================

INSERT INTO transactions
(order_id, payment_type, amount, payment_status)

VALUES

(1001, 'UPI', 100000.00, 'SUCCESS');

-- ============================================
-- RETRIEVE ALL CUSTOMERS
-- ============================================

SELECT * FROM customers;

-- ============================================
-- RETRIEVE ALL PRODUCTS
-- ============================================

SELECT * FROM products;

-- ============================================
-- RETRIEVE ALL ORDERS
-- ============================================

SELECT * FROM orders;

-- ============================================
-- UPDATE PRODUCT STOCK
-- ============================================

UPDATE products

SET stock = 3

WHERE product_id = 1;

-- ============================================
-- UPDATE ORDER STATUS
-- ============================================

UPDATE orders

SET status = 'DELIVERED'

WHERE order_id = 1001;

-- ============================================
-- DELETE EXAMPLE
-- ============================================

-- DELETE FROM products
-- WHERE product_id = 5;

-- ============================================
-- JOIN QUERY
-- ============================================

SELECT

```
orders.order_id,

customers.customer_name,

products.product_name,

orders.quantity,

orders.total_amount,

orders.status
```

FROM orders

JOIN customers

ON orders.customer_id = customers.customer_id

JOIN products

ON orders.product_id = products.product_id;

-- ============================================
-- END OF DATABASE SCRIPT
-- ============================================
