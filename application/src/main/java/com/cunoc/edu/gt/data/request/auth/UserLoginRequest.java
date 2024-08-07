package com.cunoc.edu.gt.data.request.auth;

import com.cunoc.edu.gt.annotations.validation.DataRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request for the login of a user.
 *
 * @Author: Augusto Vicente
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginRequest {

    @DataRequired(message = "Username is required")
    private String username;

    @DataRequired(message = "Password is required")
    private String password;
}