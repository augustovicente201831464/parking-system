package com.cunoc.edu.gt.data.validator;

import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.exception.ValidationException;

/**
 * The User Helper
 *
 * @Author: Augusto Vicente
 */
public class UserHelper {

    public static void validateUserRequest(UserRequest request) {
        if (request == null) {
            throw new ValidationException("La solicitud es requerida");
        }

        if(request.getName() == null || request.getName().isEmpty()){
            throw new ValidationException("El nombre es requerido");
        }

        if(request.getLastname() == null || request.getLastname().isEmpty()){
            throw new ValidationException("El apellido es requerido");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new ValidationException("El nombre de usuario es requerido");
        }

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new ValidationException("El correo es requerido");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new ValidationException("La contraseña es requerida");
        }

        if (request.getPhone() == null || request.getPhone().isEmpty()) {
            throw new ValidationException("El teléfono es requerido");
        }
    }
}
