package com.cunoc.edu.gt.output.persistence.repository;

import com.cunoc.edu.gt.output.persistence.repository.extend.RolRepository;
import com.cunoc.edu.gt.output.persistence.repository.extend.UserRepository;
import com.cunoc.edu.gt.jpa.repository.repimpl.JpaRepositoryImpl;
import com.cunoc.edu.gt.output.persistence.repository.implement.UserRepositoryImpl;

import java.sql.Connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericRepositoryManager {
    private static final Map<Class<?>, JpaRepositoryImpl<?, ?>> repositories = new ConcurrentHashMap<>();

    private GenericRepositoryManager() {}

    public static <REPO> REPO getRepository(Class<REPO> repositoryClass, Connection connection) {
        return (REPO) repositories.computeIfAbsent(repositoryClass, key -> {
            if (repositoryClass == RolRepository.class) {
                //return RolRepositoryImpl.getInstance(connection);
                throw new UnsupportedOperationException("Not implemented yet");
            } else if (repositoryClass == UserRepository.class) {
                return UserRepositoryImpl.getInstance(connection);
            }else {
                throw new IllegalArgumentException("Unknown repository: " + repositoryClass);
            }
        });
    }
}