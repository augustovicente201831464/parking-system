package com.cunoc.edu.gt.output.persistence.entity.auth;

import com.cunoc.edu.gt.annotations.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user_access")
public class UserAccess extends AuditAttributeAuth {

    @Id
    @Column(name = "usuario_codigo")
    private Integer userId;

    @Id
    @Column(name = "acceso_codigo")
    private Integer accessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceso_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    private Access access;
}