-- MySQL 13 Ver 8.0.23 for Linux on x86_64 (MySQL Community Server - GPL)
-- Time: 2021-06-06 20:00:00

--
-- Create database `parking`
--
DROP DATABASE IF EXISTS parking;
CREATE DATABASE IF NOT EXISTS parking;

USE parking;

--
-- Create table `users`
--
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    codigo INT AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(50) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'UPDATED', 'UNKNOWN') DEFAULT 'ACTIVE',

    PRIMARY KEY (codigo)
);

--
-- Create table `roles`
--
DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
    codigo INT AUTO_INCREMENT,
    rol_nombre ENUM('ADMIN', 'CUSTOMER', 'EMPLOYEE', 'AUDITOR') DEFAULT 'CUSTOMER',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'UPDATED', 'UNKNOWN') DEFAULT 'ACTIVE',

    PRIMARY KEY (codigo)
);

--
-- Create table `access
--
DROP TABLE IF EXISTS access;
CREATE TABLE IF NOT EXISTS access (
    codigo INT AUTO_INCREMENT,
    acceso_nombre ENUM('USER', 'ADMIN', 'VEHICLE', 'CUSTOMER', 'CASHIER') DEFAULT 'CASHIER',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'UPDATED', 'UNKNOWN') DEFAULT 'ACTIVE',

    PRIMARY KEY (codigo)
);

--
-- Create table `user_roles`
--
DROP TABLE IF EXISTS user_rol;
CREATE TABLE IF NOT EXISTS user_rol (
    usuario_codigo INT NOT NULL,
    rol_codigo INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'UPDATED', 'UNKNOWN') DEFAULT 'ACTIVE',

    PRIMARY KEY (usuario_codigo, rol_codigo),
    FOREIGN KEY (usuario_codigo) REFERENCES users(codigo),
    FOREIGN KEY (rol_codigo) REFERENCES roles(codigo)
);

--
-- Create table user_access
--
DROP TABLE IF EXISTS user_access;
CREATE TABLE IF NOT EXISTS user_access (
    usuario_codigo INT NOT NULL,
    acceso_codigo INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'UPDATED', 'UNKNOWN') DEFAULT 'ACTIVE',

    PRIMARY KEY (usuario_codigo, acceso_codigo),
    FOREIGN KEY (usuario_codigo) REFERENCES users(codigo),
    FOREIGN KEY (acceso_codigo) REFERENCES access(codigo)
);