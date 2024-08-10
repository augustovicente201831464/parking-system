package com.cunoc.edu.gt.opextends;

import com.cunoc.edu.gt.model.auth.UserDTO;
import com.cunoc.edu.gt.ports.output.OutputPort;

import java.util.Optional;

/**
 * Output Port for User
 *
 * @Author: Augusto Vicente
 */
public interface UserOP extends OutputPort<UserDTO, Integer> {

    /**
     * Get User by Username
     *
     * @param username the username to search
     * @return UserDTO the user found
     */
    Optional<UserDTO> getByUsername(String username);
}