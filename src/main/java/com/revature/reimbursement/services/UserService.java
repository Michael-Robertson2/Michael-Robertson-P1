package com.revature.reimbursement.services;

import com.revature.reimbursement.daos.UserDao;
import com.revature.reimbursement.dtos.requests.NewLoginRequest;
import com.revature.reimbursement.dtos.requests.NewUserRequest;
import com.revature.reimbursement.dtos.responses.Principal;
import com.revature.reimbursement.models.User;
import com.revature.reimbursement.utils.custom_exceptions.InvalidAuthException;
import com.revature.reimbursement.utils.custom_exceptions.InvalidUserException;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveUser(NewUserRequest req) {
        List<String> usernames = userDao.findAllUsernames();
        List<String> emails = userDao.findAllEmails();

        if (!isValidUsername(req.getUsername())) throw new InvalidUserException("Username must be 8-20 characters long.");
        if (usernames.contains(req.getUsername())) throw new InvalidUserException("Username is already in use.");
        if (emails.contains(req.getEmail())) throw new InvalidUserException("Email is already in use.");
        if (!isValidPassword(req.getPassword1())) throw new InvalidUserException("Password must have a minimum eight characters, at least one letter and one number");
        if (!req.getPassword1().equals(req.getPassword2())) throw new InvalidUserException("Passwords do not match.");

        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), req.getEmail(), req.getPassword1(),req.getGivenName(), req.getSurname(),
                                    true, "b06a2afc-702c-11ed-a1eb-0242ac120002");
        userDao.save(createdUser);
    }

    public Principal login(NewLoginRequest req) {
        User validUser = userDao.getUserByUsernameAndPassword(req.getUsername(), req.getPassword());
        if (validUser == null) throw new InvalidAuthException("Invalid Username or Password");

        return new Principal(validUser.getId(), validUser.getUsername(), validUser.getEmail(), validUser.getGivenName(), validUser.getSurname(), validUser.isActive(), validUser.getRole());
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public List<User> getAllUserByUsername(String username) {
        return userDao.getAllUsersByUsername(username);
    }

    private boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}
