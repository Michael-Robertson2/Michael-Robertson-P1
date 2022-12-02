package com.revature.reimbursement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursement.daos.TicketDao;
import com.revature.reimbursement.daos.UserDao;
import com.revature.reimbursement.handlers.AuthHandler;
import com.revature.reimbursement.handlers.TicketHandler;
import com.revature.reimbursement.handlers.UserHandler;
import com.revature.reimbursement.models.Ticket;
import com.revature.reimbursement.services.TicketService;
import com.revature.reimbursement.services.TokenService;
import com.revature.reimbursement.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void router(Javalin app) {

        UserDao userDao = new UserDao();
        TicketDao ticketDao = new TicketDao();
        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();

        TokenService tokenService = new TokenService(jwtConfig);
        UserService userService = new UserService(userDao);
        TicketService ticketService = new TicketService(ticketDao);

        UserHandler userHandler = new UserHandler(userService, mapper, tokenService);
        TicketHandler ticketHandler = new TicketHandler(ticketService, mapper, tokenService);
        AuthHandler authHandler = new AuthHandler(userService, tokenService, mapper);

        app.routes(() -> {
            path("/users", () -> {
                get(c -> userHandler.getAllUsers(c));
                get("/name", c -> userHandler.getAllUsersByUsername(c));
                post(c -> userHandler.signup(c));
            });

            path("/auth", () -> {
                post(c -> authHandler.authenticateUser(c));
            });

            path("/ticket", () -> {
                post(c -> ticketHandler.submitTicket(c));
                put(c -> ticketHandler.updateTicket(c));
            });
        });

    }
}
