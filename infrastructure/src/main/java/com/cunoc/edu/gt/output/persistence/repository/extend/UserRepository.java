package com.cunoc.edu.gt.output.persistence.repository.extend;

import com.cunoc.edu.gt.annotations.persistence.Param;
import com.cunoc.edu.gt.jpa.repository.JpaRepository;
import com.cunoc.edu.gt.annotations.repository.Query;
import com.cunoc.edu.gt.output.persistence.entity.auth.User;

import java.util.Optional;

/**
 * User repository interface
 *
 * @author Augusto Vicente
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by username
     *
     * @param username username of the user
     * @return an optional of user
     */
    //@Query("SELECT * FROM users WHERE username = ?;")
    Optional<User> findByUsername(@Param("username") String username);

    /**
     * Find user by email
     *
     * @param email email of the user
     * @return an optional of user
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by username or email
     *
     * @param username username of the user
     * @param email email of the user
     * @return an optional of user
     */
    @Query("SELECT * FROM user AS u WHERE u.username = ? OR u.email = ?;")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * Check if a user exists by username
     *
     * @param username username of the user
     * @return boolean
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user exists by email
     *
     * @param email email of the user
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * Check if a user exists by username or email
     *
     * @param username username of the user
     * @param email email of the user
     * @return boolean
     */
    boolean existsByUsernameOrEmail(String username, String email);
}
