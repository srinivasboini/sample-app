package com.srini.demo.streams;

import com.srini.demo.streams.model.*;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    // -------------------------------------------------------------
    // Basic Data
    // -------------------------------------------------------------
    public static final List<Integer> NUMBERS = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public static final List<Integer> NUMBERS_WITH_DUPLICATES = List.of(5, 3, 9, 1, 3, 5, 8, 2, 9, 4);

    public static final List<String> NAMES = List.of(
            "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah"
    );

    public static final List<String> SENTENCES = List.of(
            "Java Streams are powerful and concise",
            "Functional programming in modern Java is fun",
            "Streams provide parallel execution capabilities"
    );

    public static final List<String> ANAGRAM_WORDS = List.of(
            "listen", "silent", "enlist", "rat", "tar", "art", "evil", "vile", "live", "hello"
    );

    // -------------------------------------------------------------
    // Employees
    // -------------------------------------------------------------
    public static final Employee ALICE = new Employee(
            1L, "Alice Johnson", "IT", 95000.0, "Female", 29,
            List.of("Java", "Spring", "Docker", "Kubernetes"), "New York"
    );

    public static final Employee BOB = new Employee(
            2L, "Bob Smith", "Sales", 60000.0, "Male", 35,
            List.of("Negotiation", "CRM", "Cold Calling"), "Chicago"
    );

    public static final Employee CHARLIE = new Employee(
            3L, "Charlie Brown", "IT", 120000.0, "Male", 42,
            List.of("Java", "Architecture", "Cloud", "Go"), "New York"
    );

    public static final Employee DIANA = new Employee(
            4L, "Diana Prince", "HR", 72000.0, "Female", 31,
            List.of("Recruiting", "Employee Relations", "Compensation"), "San Francisco"
    );

    public static final Employee EVAN = new Employee(
            5L, "Evan Wright", "Finance", 85000.0, "Male", 26,
            List.of("Accounting", "Excel", "Financial Modeling"), "Chicago"
    );

    public static final Employee FIONA = new Employee(
            6L, "Fiona Gallagher", "IT", 80000.0, "Female", 24,
            List.of("Python", "React", "Docker"), "Austin"
    );

    public static final Employee GEORGE = new Employee(
            7L, "George Clark", "Sales", 90000.0, "Male", 38,
            List.of("Enterprise Sales", "B2B", "CRM"), "Austin"
    );

    public static final Employee HANNAH = new Employee(
            8L, "Hannah Abbott", "Finance", 110000.0, "Female", 45,
            List.of("Taxation", "Auditing", "Financial Modeling"), "New York"
    );

    public static final Employee IAN = new Employee(
            9L, "Ian Malcolm", "HR", 65000.0, "Male", 28,
            List.of("Talent Acquisition", "Onboarding"), "San Francisco"
    );

    public static final Employee JULIA = new Employee(
            10L, "Julia Roberts", "IT", 105000.0, "Female", 33,
            List.of("Java", "AWS", "Microservices"), "Austin"
    );

    public static final List<Employee> EMPLOYEES = List.of(
            ALICE, BOB, CHARLIE, DIANA, EVAN, FIONA, GEORGE, HANNAH, IAN, JULIA
    );

    // -------------------------------------------------------------
    // Customers
    // -------------------------------------------------------------
    public static final Customer CUST_JOHN = new Customer(1L, "John Doe", "john@example.com", CustomerTier.REGULAR);
    public static final Customer CUST_EMILY = new Customer(2L, "Emily Davis", "emily@example.com", CustomerTier.GOLD);
    public static final Customer CUST_MICHAEL = new Customer(3L, "Michael Scott", "michael@dundermifflin.com", CustomerTier.PLATINUM);
    public static final Customer CUST_SARAH = new Customer(4L, "Sarah Connor", "sarah@resistance.org", CustomerTier.GOLD);
    public static final Customer CUST_TOM = new Customer(5L, "Tom Riddle", "tom@hogwarts.edu", CustomerTier.REGULAR);

    public static final List<Customer> CUSTOMERS = List.of(
            CUST_JOHN, CUST_EMILY, CUST_MICHAEL, CUST_SARAH, CUST_TOM
    );

    // -------------------------------------------------------------
    // Products
    // -------------------------------------------------------------
    public static final Product PROD_LAPTOP = new Product(101L, "MacBook Pro", "Electronics", 2200.0, 15);
    public static final Product PROD_PHONE = new Product(102L, "iPhone 15", "Electronics", 999.0, 25);
    public static final Product PROD_HEADPHONES = new Product(103L, "Sony WH-1000XM5", "Electronics", 350.0, 40);
    public static final Product PROD_BOOK_JAVA = new Product(104L, "Effective Java", "Books", 45.0, 100);
    public static final Product PROD_BOOK_CLEAN = new Product(105L, "Clean Code", "Books", 40.0, 80);
    public static final Product PROD_COFFEE_BEANS = new Product(106L, "Arabica Whole Beans", "Groceries", 20.0, 200);
    public static final Product PROD_MUG = new Product(107L, "Ceramic Mug", "Groceries", 15.0, 150);
    public static final Product PROD_TSHIRT = new Product(108L, "Developer Cotton T-Shirt", "Clothing", 25.0, 60);

    public static final List<Product> PRODUCTS = List.of(
            PROD_LAPTOP, PROD_PHONE, PROD_HEADPHONES, PROD_BOOK_JAVA,
            PROD_BOOK_CLEAN, PROD_COFFEE_BEANS, PROD_MUG, PROD_TSHIRT
    );

    // -------------------------------------------------------------
    // Orders
    // -------------------------------------------------------------
    public static final Order ORDER_1 = new Order(
            1001L, "ORD-2024-001", CUST_JOHN,
            List.of(new OrderItem(PROD_BOOK_JAVA, 1), new OrderItem(PROD_MUG, 2)),
            LocalDate.of(2024, 1, 15), OrderStatus.DELIVERED
    ); // (45*1) + (15*2) = 75.0

    public static final Order ORDER_2 = new Order(
            1002L, "ORD-2024-002", CUST_EMILY,
            List.of(new OrderItem(PROD_LAPTOP, 1), new OrderItem(PROD_HEADPHONES, 1)),
            LocalDate.of(2024, 1, 20), OrderStatus.DELIVERED
    ); // (2200*1) + (350*1) = 2550.0

    public static final Order ORDER_3 = new Order(
            1003L, "ORD-2024-003", CUST_MICHAEL,
            List.of(new OrderItem(PROD_PHONE, 2), new OrderItem(PROD_COFFEE_BEANS, 5), new OrderItem(PROD_TSHIRT, 4)),
            LocalDate.of(2024, 2, 5), OrderStatus.DELIVERED
    ); // (999*2) + (20*5) + (25*4) = 1998 + 100 + 100 = 2198.0

    public static final Order ORDER_4 = new Order(
            1004L, "ORD-2024-004", CUST_SARAH,
            List.of(new OrderItem(PROD_BOOK_JAVA, 2), new OrderItem(PROD_BOOK_CLEAN, 1)),
            LocalDate.of(2024, 2, 14), OrderStatus.CANCELLED
    ); // (45*2) + (40*1) = 130.0

    public static final Order ORDER_5 = new Order(
            1005L, "ORD-2024-005", CUST_JOHN,
            List.of(new OrderItem(PROD_HEADPHONES, 1), new OrderItem(PROD_TSHIRT, 2)),
            LocalDate.of(2024, 3, 10), OrderStatus.PENDING
    ); // (350*1) + (25*2) = 400.0

    public static final Order ORDER_6 = new Order(
            1006L, "ORD-2024-006", CUST_MICHAEL,
            List.of(new OrderItem(PROD_LAPTOP, 2)),
            LocalDate.of(2024, 3, 22), OrderStatus.DELIVERED
    ); // 2200*2 = 4400.0

    public static final List<Order> ORDERS = List.of(
            ORDER_1, ORDER_2, ORDER_3, ORDER_4, ORDER_5, ORDER_6
    );

    // -------------------------------------------------------------
    // Transactions
    // -------------------------------------------------------------
    public static final List<Transaction> TRANSACTIONS = List.of(
            new Transaction(1L, 2022, 1000.0, Transaction.Type.CREDIT, "Salary"),
            new Transaction(2L, 2022, 250.0, Transaction.Type.DEBIT, "Groceries"),
            new Transaction(3L, 2023, 1500.0, Transaction.Type.CREDIT, "Bonus"),
            new Transaction(4L, 2023, 400.0, Transaction.Type.DEBIT, "Electricity"),
            new Transaction(5L, 2023, 750.0, Transaction.Type.DEBIT, "Flight Ticket"),
            new Transaction(6L, 2024, 2000.0, Transaction.Type.CREDIT, "Consulting"),
            new Transaction(7L, 2024, 300.0, Transaction.Type.DEBIT, "Dinner")
    );
}
