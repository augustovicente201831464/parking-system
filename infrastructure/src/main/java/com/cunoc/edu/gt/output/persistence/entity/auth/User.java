package com.cunoc.edu.gt.output.persistence.entity.auth;

import com.cunoc.edu.gt.annotations.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity User
 *
 * @Author: Augusto Vicente
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User extends AuditAttributeAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "apellido")
    private String lastname;

    @Column(name = "username")
    private String username;

    @Column(name = "correo_electronico")
    private String email;

    @Column(name = "contrasena")
    private String password;

    @Column(name = "telefono")
    private String phone;
}