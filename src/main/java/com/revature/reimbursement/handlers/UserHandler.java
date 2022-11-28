package com.revature.reimbursement.handlers;

import com.revature.reimbursement.services.UserService;
import io.javalin.http.Context;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void signup(Context c) {


    }
}
