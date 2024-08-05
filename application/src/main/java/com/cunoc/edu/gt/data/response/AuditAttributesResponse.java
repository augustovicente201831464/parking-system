package com.cunoc.edu.gt.data.response;

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
public class AuditAttributesResponse {
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}