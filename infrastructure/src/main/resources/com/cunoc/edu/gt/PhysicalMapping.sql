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
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(50) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED', 'UPDATED', 'UNKNOWN') DEFAULT 'ACTIVE',

    PRIMARY KEY (codigo)
);