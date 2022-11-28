package com.revature.reimbursement.utils;

import com.revature.reimbursement.daos.UserDao;
import com.revature.reimbursement.handlers.UserHandler;
import com.revature.reimbursement.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Router {
    public static void router(Javalin app) {

        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
        UserHandler userHandler = new UserHandler(userService);

        app.routes(() -> {
            path("/users", () -> {
                post(c -> userHandler.signup(c));
            });
        });

    }
}
