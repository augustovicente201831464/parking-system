package com.cunoc.edu.gt.data.response.auth;

import com.cunoc.edu.gt.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Audit attributes response.
 *
 * @Author: Augusto Vicente
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditAttributesAuthResponse {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}