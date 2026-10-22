package com.srini.demo.repository;

import com.srini.demo.entity.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {

    List<AuditLogEntity> findByOrderIdOrderByProcessedAtDesc(Long orderId);

    Page<AuditLogEntity> findAllByOrderByProcessedAtDesc(Pageable pageable);
}
