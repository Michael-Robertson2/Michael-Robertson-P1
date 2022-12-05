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

    public User signUp(NewUserRequest req, String role) {
        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), req.getEmail(), req.getPassword1(),req.getGivenName(), req.getSurname(),
                                    false, getRoleIdByName(role));
        userDao.save(createdUser);
        return createdUser;
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

    public void activateAccount(String username) {
        userDao.activateAccount(username);
    }

    public void deleteAccount(String username) {
        userDao.deleteAccount(username);
    }

    public boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public boolean isDuplicateUsername(String username) {
        List<String> usernames = userDao.findAllUsernames();
        return usernames.contains(username);
    }

    public boolean isDuplicateEmail(String email) {
        List<String> emails = userDao.findAllEmails();
        return emails.contains(email);
    }

    public boolean isSamePassword(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

    public String getRoleIdByName(String role) {
        return userDao.getRoleIdByName(role);
    }

}
