package com.revature.reimbursement.services;

import com.revature.reimbursement.daos.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService sut;
    private final UserDao mockUserDao = Mockito.mock(UserDao.class);

    @Before
    public void init() {
        sut = new UserService(mockUserDao);
    }

    @Test
    public void test_isValidUsername_givenValidUsername() {

        String username = "tester001";

        boolean condition = sut.isValidUsername(username);

        assertTrue(condition);
    }

    @Test
    public void test_isValidUsername_givenInvalidUsername() {

        String username = "tester";

        boolean condition = sut.isValidUsername(username);

        assertFalse(condition);
    }

    @Test
    public void test_isValidPassword_givenValidPassword() {

        String password = "Passw0rd";

        boolean condition = sut.isValidPassword(password);

        assertTrue(condition);
    }

    @Test
    public void test_isValidPassword_givenInvalidPassword() {

        String password = "password";

        boolean condition = sut.isValidPassword(password);

        assertFalse(condition);
    }

    @Test
    public void test_isDuplicateUsername_givenDuplicateUsername() {
        List<String> stubbedUsernames = Arrays.asList("Tester001", "Tester002", "Tester003");
        String username = "Tester003";
        UserService spySut = Mockito.spy(sut);

        Mockito.when(mockUserDao.findAllUsernames()).thenReturn(stubbedUsernames);

        boolean condition = spySut.isDuplicateUsername(username);

        assertTrue(condition);
    }

    @Test
    public void test_isDuplicateUsername_givenUniqueUsername() {
        List<String> stubbedUsernames = Arrays.asList("Tester001", "Tester002", "Tester003");
        String username = "Tester004";
        UserService spySut = Mockito.spy(sut);

        Mockito.when(mockUserDao.findAllUsernames()).thenReturn(stubbedUsernames);

        boolean condition = spySut.isDuplicateUsername(username);

        assertFalse(condition);
    }

    @Test
    public void test_isDuplicateEmail_givenDuplicateEmail() {
        List<String> stubbedEmails = Arrays.asList("test1@test.com", "test2@test.com", "test3@test.com");
        String email = "test3@test.com";
        UserService spySut = Mockito.spy(sut);

        Mockito.when(mockUserDao.findAllEmails()).thenReturn(stubbedEmails);

        boolean condition = spySut.isDuplicateEmail(email);

        assertTrue(condition);
    }

    @Test
    public void test_isDuplicateEmail_givenUniqueEmail() {
        List<String> stubbedEmails = Arrays.asList("test1@test.com", "test2@test.com", "test3@test.com");
        String email = "test4@test.com";
        UserService spySut = Mockito.spy(sut);

        Mockito.when(mockUserDao.findAllEmails()).thenReturn(stubbedEmails);

        boolean condition = spySut.isDuplicateEmail(email);

        assertFalse(condition);
    }

    @Test
    public void test_isSamePassword_givenSamePasswords() {
        String pass1 = "Passw0rd";
        String pass2 = "Passw0rd";

        boolean condition = sut.isSamePassword(pass1, pass2);

        assertTrue(condition);
    }

    @Test
    public void test_isSamePassword_givenDifferentPasswords() {
        String pass1 = "Passw0rd";
        String pass2 = "Password1";

        boolean condition = sut.isSamePassword(pass1, pass2);

        assertFalse(condition);
    }
}