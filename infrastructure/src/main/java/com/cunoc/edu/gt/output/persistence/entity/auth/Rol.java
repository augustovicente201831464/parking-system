package com.cunoc.edu.gt.output.persistence.entity.auth;

import com.cunoc.edu.gt.annotations.persistence.*;
import com.cunoc.edu.gt.enums.RolName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "roles")
public class Rol extends AuditAttributeAuth{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_nombre")
    private RolName rolName;
}