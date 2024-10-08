package com.cunoc.edu.gt.dmimpl.auth;

import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.data.response.auth.AccessResponse;
import com.cunoc.edu.gt.data.response.auth.RolResponse;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.dm.DomainMapper;
import com.cunoc.edu.gt.mapper.ModelMapper;
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

    public UserResponse dtoToResponseWithRelations(UserDTO dto) {
        UserResponse response = modelMapper.map(dto, UserResponse.class);

        if (dto.getRolDTOs() != null && !dto.getRolDTOs().isEmpty()) {
            dto.getRolDTOs().forEach(rol ->
                    response.getRolResponses().add(modelMapper.map(rol, RolResponse.class))
            );
        }

        if (dto.getAccessDTOs() != null && !dto.getAccessDTOs().isEmpty()) {
            dto.getAccessDTOs().forEach(access ->
                    response.getAccessResponses().add(modelMapper.map(access, AccessResponse.class))
            );
        }

        return response;
    }

    private UserDM() {
        this.modelMapper = ModelMapper.getInstance();
    }

    public static UserDM getInstance() {
        if (instance == null) {
            instance = new UserDM();
        }

        return instance;
    }

    private static UserDM instance;
    private final ModelMapper modelMapper;
}