package com.cunoc.edu.gt.model.auth;

import com.cunoc.edu.gt.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO class for AuditAttributeAuth
 *
 * @Author: Augusto Vicente
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditAttributeAuthDTO {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}