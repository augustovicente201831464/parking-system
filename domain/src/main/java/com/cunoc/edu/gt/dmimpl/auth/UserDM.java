package com.cunoc.edu.gt.dmimpl.auth;

import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.dm.DomainMapper;
import com.cunoc.edu.gt.mapper.ModelMapperCustomized;
import com.cunoc.edu.gt.model.auth.UserDTO;

/**
 * A class to map a user request to a user dto and a user dto to a user response
 *
 * @Author: Augusto Vicente
 */
public class UserDM implements DomainMapper<UserDTO, UserRequest, UserResponse> {

    /**
     * A method to map a request to a dto
     *
     * @param request the request to be mapped
     * @return the mapped dto
     */
    @Override
    public UserDTO requestToDto(UserRequest request) {
        return modelMapper.map(request, UserDTO.class);
    }

    /**
     * A method to map a dto to a response
     *
     * @param dto the dto to be mapped
     * @return the mapped response
     */
    @Override
    public UserResponse dtoToResponse(UserDTO dto) {
        return modelMapper.map(dto, UserResponse.class);
    }

    private UserDM() {
        this.modelMapper = ModelMapperCustomized.getInstance();
    }

    public static UserDM getInstance() {
        if (instance == null) {
            instance = new UserDM();
        }

        return instance;
    }

    private static UserDM instance;
    private final ModelMapperCustomized modelMapper;
}