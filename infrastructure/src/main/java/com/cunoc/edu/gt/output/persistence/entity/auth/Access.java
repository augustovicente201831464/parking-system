package com.cunoc.edu.gt.output.persistence.entity.auth;

import com.cunoc.edu.gt.annotations.persistence.*;
import com.cunoc.edu.gt.enums.AccessName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "access")
public class Access extends AuditAttributeAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer id;

    @Column(name = "acceso_nombre")
    private AccessName accessName;
}