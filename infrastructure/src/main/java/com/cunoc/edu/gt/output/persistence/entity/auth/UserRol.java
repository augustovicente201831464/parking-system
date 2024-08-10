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
@Entity(name = "user_rol")
public class UserRol extends AuditAttributeAuth {

    @Id
    @Column(name = "usuario_codigo")
    private Integer userId;

    @Id
    @Column(name = "rol_codigo")
    private Integer rolId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    private Rol rol;
}