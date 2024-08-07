package com.cunoc.edu.gt.output.persistence.entity.auth;

import com.cunoc.edu.gt.annotations.persistence.Column;
import com.cunoc.edu.gt.annotations.persistence.EnumType;
import com.cunoc.edu.gt.annotations.persistence.Enumerated;
import com.cunoc.edu.gt.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The class AuditAttributeSecurity
 *
 * @Author: Augusto Vicente
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditAttributeAuth {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}