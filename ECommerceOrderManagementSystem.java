import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;

// ===============================
// CUSTOM EXCEPTIONS
// ===============================

class InvalidOrderException extends Exception {
    public InvalidOrderException(String message) {
        super(message);
    }
}

class ProductUnavailableException extends Exception {
    public ProductUnavailableException(String message) {
        super(message);
    }
}

class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}

class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}

// ===============================
// USER ABSTRACT CLASS
// ===============================

abstract class User implements Serializable {

    protected int userId;
    protected String name;
    protected String email;

    public User(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public abstract void displayRole();

    public String getName() {
        return name;
    }
}

// ===============================
// CUSTOMER CLASS
// ===============================

class Customer extends User {

    private String address;

    public Customer(int userId, String name, String email, String address) {
        super(userId, name, email);
        this.address = address;
    }

    @Override
    public void displayRole() {
        System.out.println(name + " is a Customer.");
    }

    public void displayCustomer() {
        System.out.println(
            "Customer ID: " + userId +
            ", Name: " + name +
            ", Email: " + email +
            ", Address: " + address
        );
    }
}

// ===============================
// ADMIN CLASS
// ===============================

class Admin extends User {

    public Admin(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public void displayRole() {
        System.out.println(name + " is an Admin.");
    }
}

// ===============================
// PRODUCT CLASS
// ===============================

class Product implements Serializable {

    private int productId;
    private String productName;
    private double price;
    private int stock;

    public Product(int productId, String productName,
                   double price, int stock) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public synchronized int getStock() {
        return stock;
    }

    // SYNCHRONIZED INVENTORY UPDATE
    public synchronized void reduceStock(int quantity)
            throws InsufficientStockException {

        if (quantity <= 0) {
            throw new InsufficientStockException(
                "Invalid quantity selected."
            );
        }

        if (stock < quantity) {
            throw new InsufficientStockException(
                "Insufficient stock for product: " + productName
            );
        }

        stock -= quantity;

        System.out.println(
            "Inventory Updated: " + productName +
            " | Remaining Stock: " + stock
        );
    }

    // METHOD OVERLOADING
    public void displayProduct() {

        System.out.println(
            productId + " | " +
            productName + " | Rs." +
            price + " | Stock: " + stock
        );
    }

    public void displayProduct(boolean detailed) {

        if (detailed) {

            System.out.println("Product ID: " + productId);
            System.out.println("Product Name: " + productName);
            System.out.println("Price: Rs." + price);
            System.out.println("Available Stock: " + stock);

        } else {
            displayProduct();
        }
    }
}

// ===============================
// PAYMENT INTERFACE
// ===============================

interface Payment {

    boolean processPayment(double amount)
            throws InvalidPaymentException;

    String getPaymentType();
}

// ===============================
// UPI PAYMENT
// ===============================

class UPIPayment implements Payment {

    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount)
            throws InvalidPaymentException {

        if (upiId == null || !upiId.contains("@")) {
            throw new InvalidPaymentException(
                "Invalid UPI ID."
            );
        }

        if (amount <= 0) {
            throw new InvalidPaymentException(
                "Invalid payment amount."
            );
        }

        System.out.println(
            "UPI Payment Successful: Rs." + amount
        );

        return true;
    }

    @Override
    public String getPaymentType() {
        return "UPI Payment";
    }
}

// ===============================
// CARD PAYMENT
// ===============================

class CardPayment implements Payment {

    private String cardNumber;

    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(double amount)
            throws InvalidPaymentException {

        if (cardNumber == null ||
            cardNumber.length() < 12) {

            throw new InvalidPaymentException(
                "Invalid Card Number."
            );
        }

        if (amount <= 0) {
            throw new InvalidPaymentException(
                "Invalid payment amount."
            );
        }

        System.out.println(
            "Card Payment Successful: Rs." + amount
        );

        return true;
    }

    @Override
    public String getPaymentType() {
        return "Card Payment";
    }
}

// ===============================
// ORDER CLASS
// ===============================

class Order implements Serializable {

    private int orderId;
    private Customer customer;
    private Product product;
    private int quantity;
    private double totalAmount;
    private String status;

    public Order(int orderId,
                 Customer customer,
                 Product product,
                 int quantity) {

        this.orderId = orderId;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;

        this.totalAmount =
            product.getPrice() * quantity;

        this.status = "CREATED";
    }

    public int getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayOrder() {

        System.out.println("\n---------- ORDER DETAILS ----------");

        System.out.println("Order ID: " + orderId);

        System.out.println(
            "Customer: " + customer.getName()
        );

        System.out.println(
            "Product: " + product.getProductName()
        );

        System.out.println(
            "Quantity: " + quantity
        );

        System.out.println(
            "Total Amount: Rs." + totalAmount
        );

        System.out.println(
            "Order Status: " + status
        );

        System.out.println("-----------------------------------");
    }
}

// ===============================
// INVENTORY MANAGER
// ===============================

class InventoryManager {

    private HashMap<Integer, Product> products =
            new HashMap<>();

    public void addProduct(Product product) {

        products.put(
            product.getProductId(),
            product
        );
    }

    public Product getProduct(int productId)
            throws ProductUnavailableException {

        Product product =
            products.get(productId);

        if (product == null) {

            throw new ProductUnavailableException(
                "Product not found."
            );
        }

        return product;
    }

    public void displayAllProducts() {

        System.out.println(
            "\n========== PRODUCT LIST =========="
        );

        Iterator<Product> iterator =
            products.values().iterator();

        while (iterator.hasNext()) {

            Product product =
                iterator.next();

            product.displayProduct();
        }
    }

    public HashMap<Integer, Product>
    getProducts() {

        return products;
    }
}

// ===============================
// ORDER QUEUE
// INTER-THREAD COMMUNICATION
// ===============================

class OrderQueue {

    private LinkedList<Order> queue =
            new LinkedList<>();

    private int capacity = 5;

    public synchronized void addOrder(Order order)
            throws InterruptedException {

        while (queue.size() == capacity) {

            System.out.println(
                "Order Queue Full. Waiting..."
            );

            wait();
        }

        queue.add(order);

        System.out.println(
            "Order Added to Queue: " +
            order.getOrderId()
        );

        notifyAll();
    }

    public synchronized Order getOrder()
            throws InterruptedException {

        while (queue.isEmpty()) {

            System.out.println(
                "No Orders Available. Waiting..."
            );

            wait();
        }

        Order order = queue.removeFirst();

        notifyAll();

        return order;
    }
}

// ===============================
// ORDER PROCESSOR THREAD
// ===============================

class OrderProcessor extends Thread {

    private OrderQueue orderQueue;

    public OrderProcessor(
            String threadName,
            OrderQueue orderQueue) {

        super(threadName);

        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {

        try {

            while (!Thread.currentThread()
                    .isInterrupted()) {

                Order order =
                    orderQueue.getOrder();

                System.out.println(
                    "\n[" + getName() +
                    "] Processing Order: " +
                    order.getOrderId()
                );

                Product product =
                    order.getProduct();

                synchronized (product) {

                    product.reduceStock(
                        order.getQuantity()
                    );

                    order.setStatus(
                        "PROCESSING"
                    );

                    Thread.sleep(500);

                    order.setStatus(
                        "CONFIRMED"
                    );
                }

                System.out.println(
                    "[" + getName() +
                    "] Order Confirmed: " +
                    order.getOrderId()
                );

                order.displayOrder();
            }

        } catch (
            InterruptedException e
        ) {

            System.out.println(
                getName() +
                " stopped."
            );

        } catch (
            InsufficientStockException e
        ) {

            System.out.println(
                "Order Processing Error: " +
                e.getMessage()
            );
        }
    }
}

// ===============================
// FILE MANAGER
// SERIALIZATION
// ===============================

class FileManager {

    public static void saveOrders(
            ArrayList<Order> orders,
            String fileName) {

        try {

            ObjectOutputStream output =
                new ObjectOutputStream(
                    new FileOutputStream(fileName)
                );

            output.writeObject(orders);

            output.close();

            System.out.println(
                "Orders successfully saved."
            );

        } catch (IOException e) {

            System.out.println(
                "File Writing Error: " +
                e.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")

    public static ArrayList<Order>
    loadOrders(String fileName) {

        try {

            ObjectInputStream input =
                new ObjectInputStream(
                    new FileInputStream(fileName)
                );

            ArrayList<Order> orders =
                (ArrayList<Order>)
                input.readObject();

            input.close();

            return orders;

        } catch (
            IOException |
            ClassNotFoundException e
        ) {

            System.out.println(
                "File Reading Error: " +
                e.getMessage()
            );

            return new ArrayList<>();
        }
    }
}

// ===============================
// JDBC DATABASE MANAGER
// ===============================

class DatabaseManager {

    private static final String URL =
        "jdbc:mysql://localhost:3306/ecommerce";

    private static final String USER =
        "root";

    private static final String PASSWORD =
        "root";

    public Connection connect() {

        try {

            return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
            );

        } catch (SQLException e) {

            System.out.println(
                "Database Connection Error: " +
                e.getMessage()
            );

            return null;
        }
    }

    // INSERT OPERATION

    public void insertCustomer(
            Customer customer) {

        String query =
            "INSERT INTO customers VALUES (?, ?, ?)";

        try (
            Connection con = connect();
            PreparedStatement ps =
                con.prepareStatement(query)
        ) {

            // Example JDBC structure

            System.out.println(
                "Customer Insert Operation Executed."
            );

        } catch (SQLException e) {

            System.out.println(
                e.getMessage()
            );
        }
    }

    // RETRIEVAL OPERATION

    public void retrieveProducts() {

        String query =
            "SELECT * FROM products";

        try (
            Connection con = connect();
            Statement statement =
                con.createStatement();
            ResultSet result =
                statement.executeQuery(query)
        ) {

            while (result.next()) {

                System.out.println(
                    result.getInt("product_id") +
                    " " +
                    result.getString("product_name")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                e.getMessage()
            );
        }
    }

    // UPDATE OPERATION

    public void updateStock(
            int productId,
            int stock) {

        String query =
            "UPDATE products SET stock=? " +
            "WHERE product_id=?";

        try (
            Connection con = connect();
            PreparedStatement ps =
                con.prepareStatement(query)
        ) {

            ps.setInt(1, stock);
            ps.setInt(2, productId);

            ps.executeUpdate();

            System.out.println(
                "Product Stock Updated."
            );

        } catch (SQLException e) {

            System.out.println(
                e.getMessage()
            );
        }
    }

    // DELETE OPERATION

    public void deleteProduct(
            int productId) {

        String query =
            "DELETE FROM products " +
            "WHERE product_id=?";

        try (
            Connection con = connect();
            PreparedStatement ps =
                con.prepareStatement(query)
        ) {

            ps.setInt(1, productId);

            ps.executeUpdate();

            System.out.println(
                "Product Deleted."
            );

        } catch (SQLException e) {

            System.out.println(
                e.getMessage()
            );
        }
    }
}

// ===============================
// AWT USER INTERFACE
// ===============================

class ECommerceGUI extends Frame
        implements ActionListener {

    Label title;

    Label productLabel;
    Label quantityLabel;
    Label paymentLabel;

    TextField productField;
    TextField quantityField;
    TextField paymentField;

    Button orderButton;
    Button statusButton;

    TextArea outputArea;

    MenuBar menuBar;
    Menu fileMenu;
    MenuItem exitItem;

    public ECommerceGUI() {

        setTitle(
            "E-Commerce Order Management System"
        );

        setSize(600, 500);

        setLayout(
            new FlowLayout()
        );

        // MENU

        menuBar = new MenuBar();

        fileMenu =
            new Menu("File");

        exitItem =
            new MenuItem("Exit");

        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        setMenuBar(menuBar);

        // CONTROLS

        title = new Label(
            "E-COMMERCE ORDER MANAGEMENT SYSTEM"
        );

        productLabel =
            new Label("Product ID:");

        quantityLabel =
            new Label("Quantity:");

        paymentLabel =
            new Label("Payment Type:");

        productField =
            new TextField(10);

        quantityField =
            new TextField(10);

        paymentField =
            new TextField(10);

        orderButton =
            new Button("Place Order");

        statusButton =
            new Button("Track Status");

        outputArea =
            new TextArea(12, 60);

        add(title);

        add(productLabel);
        add(productField);

        add(quantityLabel);
        add(quantityField);

        add(paymentLabel);
        add(paymentField);

        add(orderButton);
        add(statusButton);

        add(outputArea);

        orderButton.addActionListener(this);

        statusButton.addActionListener(this);

        exitItem.addActionListener(
            new ActionListener() {

                public void actionPerformed(
                        ActionEvent e) {

                    System.exit(0);
                }
            }
        );

        addWindowListener(
            new WindowAdapter() {

                public void windowClosing(
                        WindowEvent e) {

                    System.exit(0);
                }
            }
        );

        setVisible(true);
    }

    @Override
    public void actionPerformed(
            ActionEvent e) {

        String productId =
            productField.getText();

        String quantity =
            quantityField.getText();

        String payment =
            paymentField.getText();

        if (e.getSource() ==
                orderButton) {

            outputArea.append(
                "\nOrder Request Received\n"
            );

            outputArea.append(
                "Product ID: " +
                productId + "\n"
            );

            outputArea.append(
                "Quantity: " +
                quantity + "\n"
            );

            outputArea.append(
                "Payment: " +
                payment + "\n"
            );

            outputArea.append(
                "Order Placed Successfully!\n"
            );
        }

        if (e.getSource() ==
                statusButton) {

            outputArea.append(
                "\nOrder Status: PROCESSING\n"
            );
        }
    }
}

// ===============================
// MAIN CLASS
// ===============================

public class ECommerceOrderManagementSystem {

    public static void main(
            String[] args) {

        System.out.println(
            "===================================="
        );

        System.out.println(
            " E-COMMERCE ORDER MANAGEMENT SYSTEM"
        );

        System.out.println(
            "===================================="
        );

        // USERS

        Customer customer1 =
            new Customer(
                101,
                "Susan",
                "susan@email.com",
                "Chennai"
            );

        Customer customer2 =
            new Customer(
                102,
                "Arun",
                "arun@email.com",
                "Chennai"
            );

        Admin admin =
            new Admin(
                1,
                "Administrator",
                "admin@email.com"
            );

        customer1.displayRole();

        admin.displayRole();

        // COLLECTION FRAMEWORK

        ArrayList<Customer> customers =
            new ArrayList<>();

        customers.add(customer1);
        customers.add(customer2);

        HashSet<String> categories =
            new HashSet<>();

        categories.add("Electronics");
        categories.add("Fashion");
        categories.add("Books");

        // INVENTORY

        InventoryManager inventory =
            new InventoryManager();

        Product laptop =
            new Product(
                1,
                "Laptop",
                50000,
                5
            );

        Product mobile =
            new Product(
                2,
                "Smartphone",
                25000,
                10
            );

        Product headphones =
            new Product(
                3,
                "Headphones",
                2000,
                20
            );

        inventory.addProduct(laptop);
        inventory.addProduct(mobile);
        inventory.addProduct(headphones);

        inventory.displayAllProducts();

        // CREATE ORDERS

        Order order1 =
            new Order(
                1001,
                customer1,
                laptop,
                2
            );

        Order order2 =
            new Order(
                1002,
                customer2,
                laptop,
                3
            );

        // COLLECTION OF ORDERS

        ArrayList<Order> orders =
            new ArrayList<>();

        orders.add(order1);
        orders.add(order2);

        // PAYMENT

        try {

            Payment payment =
                new UPIPayment(
                    "susan@upi"
                );

            payment.processPayment(
                order1.getTotalAmount()
            );

            System.out.println(
                "Payment Method: " +
                payment.getPaymentType()
            );

        } catch (
            InvalidPaymentException e
        ) {

            System.out.println(
                "Payment Error: " +
                e.getMessage()
            );
        }

        // MULTITHREADING

        OrderQueue orderQueue =
            new OrderQueue();

        OrderProcessor processor1 =
            new OrderProcessor(
                "Processor-1",
                orderQueue
            );

        OrderProcessor processor2 =
            new OrderProcessor(
                "Processor-2",
                orderQueue
            );

        // THREAD PRIORITIES

        processor1.setPriority(
            Thread.MAX_PRIORITY
        );

        processor2.setPriority(
            Thread.NORM_PRIORITY
        );

        processor1.start();

        processor2.start();

        // ADD ORDERS TO QUEUE

        try {

            orderQueue.addOrder(order1);

            orderQueue.addOrder(order2);

        } catch (
            InterruptedException e
        ) {

            System.out.println(
                "Thread Interrupted."
            );
        }

        // SERIALIZATION

        FileManager.saveOrders(
            orders,
            "orders.dat"
        );

        // LOAD SERIALIZED DATA

        ArrayList<Order> loadedOrders =
            FileManager.loadOrders(
                "orders.dat"
            );

        System.out.println(
            "\nLoaded Orders: " +
            loadedOrders.size()
        );

        // STRING HANDLING

        String message =
            "Welcome to E-Commerce System";

        System.out.println(
            "\n" + message.toUpperCase()
        );

        System.out.println(
            "Message Length: " +
            message.length()
        );

        // OPEN AWT GUI

        new ECommerceGUI();
    }
}
