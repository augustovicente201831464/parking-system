package com.cunoc.edu.gt.utils;

import com.cunoc.edu.gt.config.AuthorizationHandler;
import com.cunoc.edu.gt.connection.CustomizedConnection;
import com.cunoc.edu.gt.data.pagination.util.PageRequest;
import com.cunoc.edu.gt.data.pagination.util.Sort;
import com.cunoc.edu.gt.data.request.auth.UserLoginRequest;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.output.persistence.adapter.auth.UserPA;
import com.cunoc.edu.gt.proxies.TransactionalInterceptor;
import com.cunoc.edu.gt.proxies.ValidatorInterceptor;
import com.cunoc.edu.gt.service.auth.UserService;
import com.cunoc.edu.gt.ucextends.auth.UserUC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class
Proof {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        Proof proof = new Proof();
        proof.login();
        //proof.getPage();
        //proof.getById();
    }

    void login() {
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("CZ1163515243176271572447");
        loginRequest.setPassword("2023");

        UserResponse response = service.login(loginRequest);
        System.out.println(response);
    }
    void getById() {
        System.out.println(service.getById(1).toString());
    }

    void getPage() {
        int page = 0;
        int size = 10;
        boolean asc = true;
        String orders = "id";

        System.out.println(service.getPage(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orders))));
    }

    public Proof() throws SQLException, IOException, ClassNotFoundException {
        Logger.getLogger("UserController").info("Initializing UserController...");

        Connection connection = CustomizedConnection.getInstance().getConnection();
        UserPA outputPort = UserPA.getInstance(connection);

        // Interceptors for the user service
        // Interceptors for the user service
        UserUC userUC = UserService.getInstance(outputPort);
        UserUC validationProxy = ValidatorInterceptor.createProxy(userUC, UserUC.class);
        UserUC transactionalProxy = TransactionalInterceptor.createProxy(validationProxy, connection, UserUC.class);
        this.service = AuthorizationHandler.createProxy(transactionalProxy, null, UserUC.class);
    }

    private final UserUC service;
}