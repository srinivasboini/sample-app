package com.srini.demo.repository;

import com.srini.demo.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findBySku(String sku);

    boolean existsBySku(String sku);

    Page<ProductEntity> findByCategoryIgnoreCase(String category, Pageable pageable);
}
