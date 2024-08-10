package com.cunoc.edu.gt.data.validator;

import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.data.response.auth.AccessResponse;
import com.cunoc.edu.gt.data.response.auth.RolResponse;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.enums.AccessName;
import com.cunoc.edu.gt.enums.RolName;
import com.cunoc.edu.gt.exception.ValidationException;

import java.util.List;

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

        if (request.getName() == null || request.getName().isEmpty()) {
            throw new ValidationException("El nombre es requerido");
        }

        if (request.getLastname() == null || request.getLastname().isEmpty()) {
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

        if (request.getPasswordConfirmation() == null || request.getPasswordConfirmation().isEmpty()) {
            throw new ValidationException("La confirmación de la contraseña es requerida");
        }

        if (request.getPhone() == null || request.getPhone().isEmpty()) {
            throw new ValidationException("El teléfono es requerido");
        }
    }

    /**
     * Check if the access is in the list of accesses
     *
     * @param accessName the access name
     * @param accessResponses the list of accesses
     * @return true if the access is in the list of accesses
     */
    public static boolean containAccess(AccessName accessName, List<AccessResponse> accessResponses) {
        if (accessName == null || accessResponses == null) {
            return false;
        }
        return accessResponses
                .stream()
                .anyMatch(accessResponse ->
                        accessResponse.getAccessName().equals(accessName)
                );
    }

    /**
     * Check if the role is in the list of roles
     *
     * @param rolName the rol name
     * @param rolResponses the list of roles
     * @return true if the role is in the list of roles
     */
    public static boolean containRole(RolName rolName, List<RolResponse> rolResponses) {
        if (rolName == null || rolResponses == null) {
            return false;
        }
        return rolResponses
                .stream()
                .anyMatch(rolResponse ->
                        rolResponse.getRolName().equals(rolName)
                );
    }

    /**
     * Check if the user has the access or role
     *
     * @param accessName the access name
     * @param rolName the rol name
     * @param userResponse the user response
     * @return true if the user has the access or role
     */
    public static boolean containAccessOrRole(AccessName accessName, RolName rolName, UserResponse userResponse) {
        return containAccess(accessName, userResponse.getAccessResponses())
                || containRole(rolName, userResponse.getRolResponses());
    }

    /**
     * Check if the user has the access and role
     *
     * @param accessName the access name
     * @param rolName the rol name
     * @param userResponse the user response
     * @return true if the user has the access and role
     */
    public static boolean containAccessAndRole(AccessName accessName, RolName rolName, UserResponse userResponse) {
        return containAccess(accessName, userResponse.getAccessResponses())
                && containRole(rolName, userResponse.getRolResponses());
    }
}
