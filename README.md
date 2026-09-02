# E-Commerce Order Management System

## Project Description

The E-Commerce Order Management System is a Java-based application developed to manage customers, products, inventory, orders, payments, and order processing. The system demonstrates various Core Java concepts and provides a foundation for building a scalable real-world e-commerce application.

The application supports concurrent order processing and ensures inventory consistency when multiple customers attempt to purchase the same product simultaneously.

## Features

* Customer and Admin management
* Product and Inventory management
* Order creation and processing
* Multiple payment methods
* Concurrent order processing using Multithreading
* Inventory synchronization
* Exception handling using custom exceptions
* Inter-thread communication using wait() and notifyAll()
* Thread priorities
* Java AWT-based user interface
* Event handling
* File handling using Java I/O Streams
* Object Serialization and Deserialization
* JDBC database connectivity
* CRUD operations for database records

---

## Java Concepts Used

### Object-Oriented Programming

* Classes and Objects
* Inheritance
* Polymorphism
* Abstraction
* Interfaces
* Method Overloading

### Collection Framework

* ArrayList
* HashSet
* HashMap
* Generics
* Iterator

### Exception Handling

Custom exceptions used:

* InvalidOrderException
* ProductUnavailableException
* InsufficientStockException
* InvalidPaymentException

### Multithreading

* Thread creation
* Concurrent order processing
* Synchronization
* Thread priorities
* Inter-thread communication
* wait()
* notifyAll()

### GUI

The application uses Java AWT components such as:

* Frame
* Label
* TextField
* Button
* TextArea
* MenuBar
* Menu
* MenuItem

Event handling is implemented using:

* ActionListener
* WindowAdapter

### Java I/O

* FileOutputStream
* FileInputStream
* ObjectOutputStream
* ObjectInputStream
* Serialization

### JDBC

Database operations include:

* Insert
* Update
* Delete
* Retrieve

## Requirements

### Software Requirements

* Java JDK 8 or above
* MySQL Server
* MySQL Connector/J
* Eclipse / IntelliJ IDEA / VS Code / NetBeans


## How to Run

### Step 1: Clone the Repository

### Step 2: Navigate to Project Folder

cd ECommerceOrderManagementSystem


### Step 3: Compile the Java Program


javac ECommerceOrderManagementSystem.java


### Step 4: Run the Program


java ECommerceOrderManagementSystem


## Database Setup

1. Open MySQL.
2. Execute the `database.sql` file.
3. Create the required database and tables.
4. Update the JDBC username and password in the Java program.

Example:


private static final String URL =
"jdbc:mysql://localhost:3306/ecommerce";

private static final String USER = "root";

private static final String PASSWORD = "root";

Synchronization ensures that multiple threads cannot update the inventory simultaneously.

This prevents:

* Race conditions
* Negative inventory
* Duplicate stock allocation
* Inconsistent product availability

## Exception Handling

The system handles different exceptional situations such as:

| Exception                   | Description                                            |
| --------------------------- | ------------------------------------------------------ |
| InvalidOrderException       | Raised when order details are invalid                  |
| ProductUnavailableException | Raised when a requested product is unavailable         |
| InsufficientStockException  | Raised when requested quantity exceeds available stock |
| InvalidPaymentException     | Raised when payment details are invalid                |

## Future Enhancements

* Shopping cart functionality
* User authentication
* Secure password encryption
* Online payment gateway integration
* REST API integration
* Spring Boot implementation
* Cloud database deployment
* Order delivery tracking
* Email notifications
* Microservices architecture


## Conclusion

The E-Commerce Order Management System demonstrates the application of important Core Java concepts in a real-world scenario. The system efficiently manages customers, products, orders, payments, and inventory while supporting concurrent order processing.

Synchronization ensures inventory consistency, exception handling improves reliability, and modular object-oriented design improves scalability and maintainability.

## Author

Developed as an academic project for demonstrating Core Java concepts and real-world application development.
