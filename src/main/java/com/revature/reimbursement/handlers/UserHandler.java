package com.revature.reimbursement.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursement.dtos.requests.NewActivationRequest;
import com.revature.reimbursement.dtos.requests.NewUpdateRequest;
import com.revature.reimbursement.dtos.requests.NewUserRequest;
import com.revature.reimbursement.dtos.responses.Principal;
import com.revature.reimbursement.models.User;
import com.revature.reimbursement.services.TokenService;
import com.revature.reimbursement.services.UserService;
import com.revature.reimbursement.utils.custom_exceptions.InvalidAuthException;
import com.revature.reimbursement.utils.custom_exceptions.InvalidUserException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class UserHandler {

    private final UserService userService;
    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(User.class);
    private final TokenService tokenService;

    public UserHandler(UserService userService, ObjectMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public void signup(Context ctx) throws IOException {

        NewUserRequest req = mapper.readValue(ctx.req.getInputStream(), NewUserRequest.class);
        try {
            logger.info("Attempting to sign up...");

            User createdUser;
            if (userService.isValidUsername(req.getUsername())) {
                if (!userService.isDuplicateUsername(req.getUsername())) {
                    if (!userService.isDuplicateEmail(req.getEmail())) {
                        if (userService.isValidPassword(req.getPassword1())) {
                            if (userService.isSamePassword(req.getPassword1(), req.getPassword2())) {
                                createdUser = userService.signUp(req, "User");
                            } else throw new InvalidUserException("Passwords do not match.");
                        } else throw new InvalidUserException("Password must have a minimum eight characters, at least one letter and one number");
                    } else throw new InvalidUserException("Email is already in use.");
                } else throw new InvalidUserException("Username is already in use.");
            } else throw new InvalidUserException("Username must be 8-20 characters long.");

            ctx.status(201);
            ctx.json(createdUser.getId());
            logger.info("Signup successful");

        } catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
            logger.info("Signup attempt failed");
        }
    }

    public void managerSignUp(Context ctx) throws IOException {

        NewUserRequest req = mapper.readValue(ctx.req.getInputStream(), NewUserRequest.class);
        try {
            logger.info("Attempting to sign up...");

            User createdUser;
            if (userService.isValidUsername(req.getUsername())) {
                if (!userService.isDuplicateUsername(req.getUsername())) {
                    if (!userService.isDuplicateEmail(req.getEmail())) {
                        if (userService.isValidPassword(req.getPassword1())) {
                            if (userService.isSamePassword(req.getPassword1(), req.getPassword2())) {
                                createdUser = userService.signUp(req, "FinanceManager");
                            } else throw new InvalidUserException("Passwords do not match.");
                        } else throw new InvalidUserException("Password must have a minimum eight characters, at least one letter and one number");
                    } else throw new InvalidUserException("Email is already in use.");
                } else throw new InvalidUserException("Username is already in use.");
            } else throw new InvalidUserException("Username must be 8-20 characters long.");

            ctx.status(201);
            ctx.json(createdUser.getId());
            logger.info("Signup successful");

        } catch (InvalidUserException e) {
            ctx.status(403);
            ctx.json(e);
            logger.info("Signup attempt failed");
        }
    }


    public void getAllUsers(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");
            if (token.equals("") || token == null) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if(!principal.getRole().equals(userService.getRoleIdByName("Admin")))
                throw new InvalidAuthException("You are not authorized to perform this action");
            List<User> users = userService.getAllUsers();
            ctx.json(users);
        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getAllUsersByUsername(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if(!principal.getRole().equals(userService.getRoleIdByName("Admin")))
                throw new InvalidAuthException("You are not authorized to perform this action");

            String username = ctx.req.getParameter("username");
            List<User> users = userService.getAllUserByUsername(username);
            ctx.json(users);
        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void activateAccount(Context ctx) throws IOException {

        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not signed in.");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if(!principal.getRole().equals(userService.getRoleIdByName("Admin")))
                throw new InvalidAuthException("You are not authorized to perform this action");
        } catch (InvalidAuthException e) {
            ctx.json(401);
            ctx.json(e);
            return;
        }

        NewActivationRequest req = mapper.readValue(ctx.req.getInputStream(), NewActivationRequest.class);

        try {
            String username = req.getUsername();
            if(userService.isDuplicateUsername(username)) {
                userService.activateAccount(username);
            } else throw new InvalidUserException("User not found");
        } catch (InvalidUserException e) {
            ctx.status(400);
            ctx.json(e);
        }
    }

    public void deleteAccount(Context ctx) throws IOException {

        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not signed in.");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if(!principal.getRole().equals(userService.getRoleIdByName("Admin")))
                throw new InvalidAuthException("You are not authorized to perform this action");
        } catch (InvalidAuthException e) {
            ctx.json(401);
            ctx.json(e);
            return;
        }

        NewActivationRequest req = mapper.readValue(ctx.req.getInputStream(), NewActivationRequest.class);

        try {
            String username = req.getUsername();
            if(userService.isDuplicateUsername(username)) {
                userService.deleteAccount(username);
            } else throw new InvalidUserException("User not found");
        } catch (InvalidUserException e) {
            ctx.status(400);
            ctx.json(e);
        }
    }
}
