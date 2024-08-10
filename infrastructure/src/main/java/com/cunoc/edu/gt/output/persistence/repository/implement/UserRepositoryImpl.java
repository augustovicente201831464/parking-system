package com.cunoc.edu.gt.output.persistence.repository.implement;

import com.cunoc.edu.gt.jpa.repository.repimpl.JpaRepositoryImpl;
import com.cunoc.edu.gt.output.persistence.entity.auth.User;
import com.cunoc.edu.gt.utils.ReflectionUtils;
import com.cunoc.edu.gt.output.persistence.repository.extend.UserRepository;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl extends JpaRepositoryImpl<User, Integer> implements UserRepository {

    /**
     * Find user by username
     *
     * @param username username of the user
     * @return an optional of user
     */
    @Override
    @SneakyThrows
    public Optional<User> findByUsername(String username) {
        return findByGenericMethod(ReflectionUtils.getMethodName(), UserRepository.class, List.of(username));
    }

    /**
     * Find user by email
     *
     * @param email email of the user
     * @return an optional of user
     */
    @SneakyThrows
    @Override
    public Optional<User> findByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Find user by username or email
     *
     * @param username username of the user
     * @param email    email of the user
     * @return an optional of user
     */
    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return findByGenericMethod(ReflectionUtils.getMethodName(), UserRepository.class, List.of(username, email));
    }

    /**
     * Check if a user exists by username
     *
     * @param username username of the user
     * @return boolean
     */
    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    /**
     * Check if a user exists by email
     *
     * @param email email of the user
     * @return boolean
     */
    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    /**
     * Check if a user exists by username or email
     *
     * @param username username of the user
     * @param email    email of the user
     * @return boolean
     */
    @Override
    public boolean existsByUsernameOrEmail(String username, String email) {
        return false;
    }

    private UserRepositoryImpl(
            Connection connection
    ) {
        super(User.class, connection);
    }

    public static UserRepositoryImpl getInstance(Connection connection) {
        if (instance == null) {
            instance = new UserRepositoryImpl(connection);
        }

        return instance;
    }
    private static UserRepositoryImpl instance;
}