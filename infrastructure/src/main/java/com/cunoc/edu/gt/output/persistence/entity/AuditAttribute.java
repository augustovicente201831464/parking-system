package com.cunoc.edu.gt.output.persistence.entity;

import com.cunoc.edu.gt.annotations.persistence.Column;
import com.cunoc.edu.gt.annotations.persistence.EnumType;
import com.cunoc.edu.gt.annotations.persistence.Enumerated;
import com.cunoc.edu.gt.enums.Status;

import java.time.LocalDateTime;

public class AuditAttribute {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}