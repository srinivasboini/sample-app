package com.srini.demo.repository;

import com.srini.demo.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    List<OrderEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);
}
