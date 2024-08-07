package com.cunoc.edu.gt.output.persistence.pmimpl;

import com.cunoc.edu.gt.mapper.ModelMapperCustomized;
import com.cunoc.edu.gt.model.auth.UserDTO;
import com.cunoc.edu.gt.output.persistence.entity.auth.User;
import com.cunoc.edu.gt.pm.PersistenceMapper;

import java.util.logging.Logger;

/**
 * User persistence mapper
 *
 * @Author: Augusto Vicente
 */
public class UserPM implements PersistenceMapper<User, UserDTO> {
    /**
     * The method to convert DTO to ENTITY
     *
     * @param dto is the data transfer object
     * @return the entity
     */
    @Override
    public User dtoToEntity(UserDTO dto) {
        return this.modelMapper.map(dto, User.class);
    }

    /**
     * The method to convert ENTITY to DTO
     *
     * @param entity is the entity
     * @return the data transfer object
     */
    @Override
    public UserDTO entityToDto(User entity) {
        return this.modelMapper.map(entity, UserDTO.class);
    }

    private UserPM() {
        this.modelMapper = ModelMapperCustomized.getInstance();
    }

    public static UserPM getInstance() {
        if(instance == null) {
            instance = new UserPM();
        }

        return instance;
    }

    private static UserPM instance;
    private final ModelMapperCustomized modelMapper;
}