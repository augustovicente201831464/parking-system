package com.cunoc.edu.gt.service.auth;

import com.cunoc.edu.gt.enums.Status;
import com.cunoc.edu.gt.model.auth.AuditAttributeAuthDTO;

import java.time.LocalDateTime;

/**
 * Service for AuditAttribute
 *
 * @Author: Augusto Vicente
 */
public class AuditAttributeAuthService {

    /**
     * Get AuditAttributeAuthDTO for new
     *
     * @param dto the AuditAttributeAuthDTO to create
     * @return AuditAttributeAuthDTO the AuditAttributeAuthDTO created
     */
    public AuditAttributeAuthDTO getAuditAttributeAuthForNew(AuditAttributeAuthDTO dto) {
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setStatus(Status.ACTIVE);

        return dto;
    }

    /**
     * Get AuditAttributeAuthDTO for update
     *
     * @param dto the AuditAttributeAuthDTO to update
     * @return AuditAttributeAuthDTO the AuditAttributeAuthDTO updated
     */
    public AuditAttributeAuthDTO getAuditAttributeAuthForUpdate(AuditAttributeAuthDTO dto) {
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setStatus(Status.ACTIVE);

        return dto;
    }

    //Singleton Pattern

    private AuditAttributeAuthService() {
    }

    public static AuditAttributeAuthService getInstance() {
        if (instance == null) {
            instance = new AuditAttributeAuthService();
        }
        return instance;
    }

    private static AuditAttributeAuthService instance;
}