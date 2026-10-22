package com.srini.demo.service;

import com.srini.demo.entity.AuditLogEntity;
import com.srini.demo.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventAuditService {

    private final AuditLogRepository auditLogRepository;

    public EventAuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public Page<AuditLogEntity> getAllAuditLogs(Pageable pageable) {
        return auditLogRepository.findAllByOrderByProcessedAtDesc(pageable);
    }

    public List<AuditLogEntity> getAuditLogsForOrder(Long orderId) {
        return auditLogRepository.findByOrderIdOrderByProcessedAtDesc(orderId);
    }
}
