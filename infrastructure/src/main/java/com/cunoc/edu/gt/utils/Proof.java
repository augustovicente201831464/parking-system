package com.cunoc.edu.gt.utils;

import com.cunoc.edu.gt.data.request.auth.UserLoginRequest;
import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.data.response.UserResponse;
import com.cunoc.edu.gt.output.persistence.adapter.auth.UserPA;
import com.cunoc.edu.gt.output.persistence.connection.ConnectionHelper;
import com.cunoc.edu.gt.output.persistence.connection.CustomizedConnection;
import com.cunoc.edu.gt.service.auth.UserService;
import com.cunoc.edu.gt.ucextends.auth.UserUC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Proof {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        Proof proof = new Proof();
        proof.login();
    }

    void login() {
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword(null);

        UserResponse response = service.login(loginRequest);
        System.out.println(response);
    }

    public Proof() throws SQLException, IOException, ClassNotFoundException {
        Logger.getLogger("UserController").info("Initializing UserController...");

        Connection connection = CustomizedConnection.getInstance().getConnection();
        UserPA outputPort = UserPA.getInstance(connection);

        // Interceptors for the user service
        UserUC userUC = UserService.getInstance(outputPort);
        UserUC validationProxy = ValidatorInterceptor.createProxy(userUC, UserUC.class);
        this.service = TransactionalInterceptor.createProxy(validationProxy, connection, UserUC.class);
    }

    private final UserUC service;
}
