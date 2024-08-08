package com.cunoc.edu.gt.service;

import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.enums.Status;
import com.cunoc.edu.gt.model.AuditAttributeDTO;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class AuditAttributeService {

    public AuditAttributeDTO getAuditAttributeForNew(AuditAttributeDTO dto) {
        dto.setCreatedBy(userResponse.getUsername());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedBy(userResponse.getUsername());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setStatus(Status.ACTIVE);

        return dto;
    }

    public AuditAttributeDTO getAuditAttributeForUpdate(AuditAttributeDTO dto) {
        dto.setUpdatedBy(userResponse.getUsername());
        dto.setUpdatedAt(LocalDateTime.now());

        return dto;
    }

    public AuditAttributeService(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    private UserResponse userResponse;
}