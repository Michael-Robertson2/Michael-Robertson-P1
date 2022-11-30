package com.revature.reimbursement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursement.daos.UserDao;
import com.revature.reimbursement.handlers.AuthHandler;
import com.revature.reimbursement.handlers.UserHandler;
import com.revature.reimbursement.services.TokenService;
import com.revature.reimbursement.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Router {
    public static void router(Javalin app) {

        UserDao userDao = new UserDao();
        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        UserService userService = new UserService(userDao);
        UserHandler userHandler = new UserHandler(userService, mapper);

        AuthHandler authHandler = new AuthHandler(userService, tokenService, mapper);

        app.routes(() -> {
            path("/users", () -> {
                post(c -> userHandler.signup(c));
            });

            path("/auth", () -> {
                post(c -> authHandler.authenticateUser(c));
            });
        });

    }
}
