package com.cunoc.edu.gt.data.request.auth;

import com.cunoc.edu.gt.annotations.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request class for User
 *
 * @Author: Augusto Vicente
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {

    @DataRequired(message = "Nombre del usuario es requerido")
    private String name;

    @DataRequired(message = "Apellido del usuario es requerido")
    private String lastname;

    @DataRequired(message = "Nombre de usuario es requerido")
    private String username;

    @DataRequired(message = "Correo electrónico es requerido")
    private String email;

    @DataRequired(message = "Contraseña es requerida")
    private String password;

    @DataRequired(message = "Teléfono es requerido")
    private String phone;
}