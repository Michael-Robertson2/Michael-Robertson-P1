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
                get(userHandler::getAllUsers);
                get("/name", userHandler::getAllUsersByUsername);
                post(userHandler::signup);
                post("manager", userHandler::managerSignUp);
                put(userHandler::activateAccount);
                delete(userHandler::deleteAccount);
            });

            path("/auth", () -> {
                post(authHandler::authenticateUser);
            });

            path("/ticket", () -> {
                post(ticketHandler::submitTicket);
                put(ticketHandler::updateTicket);
                put("/self", ticketHandler::editDescription);
                put("/self/amount", ticketHandler::editAmount);
                get("/pending", ticketHandler::getAllPending);
                get("/resolved", ticketHandler::getAllResolved);
                get("/self", ticketHandler::getOwn);
                get("/pending/self/recent", ticketHandler::getOwnPendingRecentFirst);
                get("/pending/self/old", ticketHandler::getOwnPendingOldFirst);
                get("/resolved/self/recent", ticketHandler::getOwnResolvedRecentFirst);
                get("/resolved/self/old", ticketHandler::getOwnResolvedOldFirst);
                get("/resolved/self/rejected", ticketHandler::getOwnResolvedRejectedFirst);
                get("/resolved/self/approved", ticketHandler::getOwnResolvedApprovedFirst);
                delete(ticketHandler::deleteTicket);
            });
        });

    }
}
