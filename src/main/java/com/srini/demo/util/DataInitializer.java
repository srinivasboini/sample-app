package com.srini.demo.util;

import com.srini.demo.entity.ProductEntity;
import com.srini.demo.entity.Role;
import com.srini.demo.entity.UserEntity;
import com.srini.demo.repository.ProductRepository;
import com.srini.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           ProductRepository productRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initializeUsers();
        initializeProducts();
        printStartupBanner();
    }

    private void initializeUsers() {
        if (!userRepository.existsByUsername("admin")) {
            UserEntity admin = new UserEntity(
                    "admin",
                    "admin@orderflow.internal",
                    passwordEncoder.encode("admin123"),
                    Role.ROLE_ADMIN
            );
            userRepository.save(admin);
            log.info("Default ADMIN user seeded: admin / admin123");
        }

        if (!userRepository.existsByUsername("user")) {
            UserEntity user = new UserEntity(
                    "user",
                    "user@orderflow.internal",
                    passwordEncoder.encode("password123"),
                    Role.ROLE_USER
            );
            userRepository.save(user);
            log.info("Default USER seeded: user / password123");
        }
    }

    private void initializeProducts() {
        if (productRepository.count() == 0) {
            List<ProductEntity> products = List.of(
                    new ProductEntity(
                            "LAPTOP-MBP-16",
                            "Apple MacBook Pro 16 M3 Max",
                            "16-inch Liquid Retina XDR display, 36GB RAM, 1TB SSD, Space Black",
                            new BigDecimal("3499.00"),
                            25,
                            "Computers"
                    ),
                    new ProductEntity(
                            "PHONE-IPHONE-15",
                            "iPhone 15 Pro Max 256GB",
                            "Titanium design, A17 Pro chip, 48MP main camera with 5x telephoto",
                            new BigDecimal("1199.00"),
                            50,
                            "Smartphones"
                    ),
                    new ProductEntity(
                            "AUDIO-SONY-XM5",
                            "Sony WH-1000XM5 Noise Canceling Headphones",
                            "Industry-leading noise cancellation, 30 hours battery, crystal clear hands-free calling",
                            new BigDecimal("399.99"),
                            100,
                            "Audio"
                    ),
                    new ProductEntity(
                            "TABLET-IPAD-AIR",
                            "Apple iPad Air 11-inch M2",
                            "Liquid Retina display, M2 chip, 128GB, landscape 12MP front camera",
                            new BigDecimal("599.00"),
                            40,
                            "Tablets"
                    ),
                    new ProductEntity(
                            "WATCH-ULTRA-2",
                            "Apple Watch Ultra 2 GPS + Cellular",
                            "Rugged 49mm titanium case, precision dual-frequency GPS, up to 72 hours low power mode",
                            new BigDecimal("799.00"),
                            30,
                            "Wearables"
                    )
            );
            productRepository.saveAll(products);
            log.info("Seeded {} sample catalog products into H2 database", products.size());
        }
    }

    private void printStartupBanner() {
        log.info("==================================================================================");
        log.info("  OrderFlow Application Ready!");
        log.info("  Swagger OpenAPI UI: http://localhost:8080/swagger-ui/index.html");
        log.info("  OpenAPI JSON Spec : http://localhost:8080/v3/api-docs");
        log.info("  H2 Database Console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:demo_db)");
        log.info("  Pre-seeded Logins :");
        log.info("    -> Admin: admin / admin123 (ROLE_ADMIN)");
        log.info("    -> User : user  / password123 (ROLE_USER)");
        log.info("==================================================================================");
    }
}
