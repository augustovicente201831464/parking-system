package com.cunoc.edu.gt.output.persistence.adapter.auth;

import com.cunoc.edu.gt.data.pagination.Page;
import com.cunoc.edu.gt.data.pagination.Pageable;
import com.cunoc.edu.gt.model.auth.UserDTO;
import com.cunoc.edu.gt.opextends.UserOP;
import com.cunoc.edu.gt.output.persistence.entity.auth.User;
import com.cunoc.edu.gt.output.persistence.pmimpl.UserPM;
import com.cunoc.edu.gt.output.persistence.repository.extend.UserRepository;
import com.cunoc.edu.gt.output.persistence.repository.implement.UserRepositoryImpl;
import lombok.SneakyThrows;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Persistence Adapter for User
 *
 * @Author: Augusto Vicente
 */
public class UserPA implements UserOP {

    /**
     * Get User by Username
     *
     * @param username the username to search
     * @return UserDTO the user found
     */
    @Override
    @SneakyThrows
    public Optional<UserDTO> getByUsername(String username) {
        return repository.findByUsername(username)
                .map(persistenceMapper::entityToDtoWithRelations);
    }

    /**
     * Method to save a modelDto
     *
     * @param modelDto the modelDto to be saved
     * @return modelDto
     */
    @Override
    public UserDTO save(UserDTO modelDto) {
        return persistenceMapper.entityToDto(
                repository.save(
                        persistenceMapper.dtoToEntity(modelDto)
                )
        );
    }

    /**
     * Method to get a modelDto by id
     *
     * @param id the id of the modelDto to be retrieved
     * @return modelDto with the given id
     */
    @Override
    public Optional<UserDTO> getById(Integer id) {
        return repository.findById(id)
                .map(persistenceMapper::entityToDtoWithRelations);
    }

    /**
     * Method to update a modelDto by id
     *
     * @param modelDto the modelDto to be updated
     * @param id       the id of the modelDto to be updated
     * @return modelDto updated
     */
    @Override
    public UserDTO updateById(UserDTO modelDto, Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method to delete a modelDto by id
     *
     * @param id the id of the modelDto to be deleted
     */
    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method to check if a modelDto exists by id
     *
     * @param id the id of the modelDto to be checked
     * @return true if the modelDto exists, false otherwise
     */
    @Override
    public boolean existsById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method to get all modelDto
     *
     * @return List of modelDto
     */
    @Override
    public List<UserDTO> getAll() {
        return repository.getAll().stream()
                .map(persistenceMapper::entityToDto)
                .toList();
    }

    /**
     * Method to get all modelDto with pagination
     *
     * @param pageable the pagination information
     * @return Page of modelDto
     */
    @Override
    public Page<UserDTO> getPage(Pageable pageable) {
        return repository.getPage(pageable)
                .map(persistenceMapper::entityToDto);
    }

    /**
     * Method to validate hashed password
     *
     * @param entity   the user entity
     * @param password the password to validate
     * @return boolean
     */
    public boolean validPassword(User entity, String password) {
        return BCrypt.checkpw(password, entity.getPassword());
    }

    private UserPA(Connection connection) {
        repository = UserRepositoryImpl.getInstance(connection);
        persistenceMapper = UserPM.getInstance(repository);
    }

    public static UserPA getInstance(Connection connection) {
        if (instance == null) {
            instance = new UserPA(connection);
        }

        return instance;
    }

    private static UserPA instance;

    private final UserRepository repository;
    private final UserPM persistenceMapper;
}