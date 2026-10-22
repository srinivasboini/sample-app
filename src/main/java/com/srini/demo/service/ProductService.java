package com.srini.demo.service;

import com.srini.demo.dto.ProductRequest;
import com.srini.demo.dto.ProductResponse;
import com.srini.demo.entity.ProductEntity;
import com.srini.demo.exception.BadRequestException;
import com.srini.demo.exception.ResourceNotFoundException;
import com.srini.demo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating new product with SKU: {}", request.getSku());

        if (productRepository.existsBySku(request.getSku())) {
            throw new BadRequestException("Product with SKU '" + request.getSku() + "' already exists");
        }

        ProductEntity entity = new ProductEntity(
                request.getSku(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getCategory()
        );

        ProductEntity saved = productRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "product-by-id", key = "#id")
    public ProductResponse getProductById(Long id) {
        log.info("Fetching product by ID: {} (Cache Miss - reading from database)", id);
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(String category, Pageable pageable) {
        Page<ProductEntity> page;
        if (category != null && !category.isBlank()) {
            page = productRepository.findByCategoryIgnoreCase(category, pageable);
        } else {
            page = productRepository.findAll(pageable);
        }
        return page.map(this::mapToResponse);
    }

    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product-by-id", key = "#id")
    })
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Updating product id: {}, evicting cache", id);
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        if (!entity.getSku().equals(request.getSku()) && productRepository.existsBySku(request.getSku())) {
            throw new BadRequestException("SKU '" + request.getSku() + "' is already in use by another product");
        }

        entity.setSku(request.getSku());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setStockQuantity(request.getStockQuantity());
        entity.setCategory(request.getCategory());

        ProductEntity updated = productRepository.save(entity);
        return mapToResponse(updated);
    }

    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product-by-id", key = "#id")
    })
    public void deleteProduct(Long id) {
        log.info("Deleting product id: {}, evicting cache", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id);
    }

    public ProductResponse mapToResponse(ProductEntity entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getSku(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStockQuantity(),
                entity.getCategory(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
