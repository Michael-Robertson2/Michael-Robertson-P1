package com.revature.reimbursement.services;

import com.revature.reimbursement.daos.UserDao;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
