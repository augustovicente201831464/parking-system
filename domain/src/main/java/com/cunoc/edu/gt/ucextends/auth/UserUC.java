package com.cunoc.edu.gt.ucextends.auth;

import com.cunoc.edu.gt.annotations.auth.PreAuthorize;
import com.cunoc.edu.gt.annotations.persistence.Transactional;
import com.cunoc.edu.gt.annotations.validation.Valid;
import com.cunoc.edu.gt.data.request.auth.UserLoginRequest;
import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.ports.input.UseCase;

public interface UserUC extends UseCase<UserRequest, UserResponse, Integer> {

    /**
     * Method to register a user in the system
     *
     * @param request the user to register
     * @return UserResponse the user registered
     */
    @Valid
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    UserResponse register(UserRequest request);

    /**
     * Method to log in a user in the system
     *
     * @param loginRequest the user to login
     * @return UserResponse the user logged
     */
    @Valid
    UserResponse login(UserLoginRequest loginRequest);

    /**
     * Method to get a user by username
     *
     * @param username the username to search
     * @param password the password to match
     * @return UserResponse the user found
     */
    @Transactional
    UserResponse getByUsername(String username, String password);
}