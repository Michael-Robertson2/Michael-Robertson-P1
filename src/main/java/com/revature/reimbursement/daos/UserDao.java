package com.revature.reimbursement.daos;

import com.revature.reimbursement.models.User;
import com.revature.reimbursement.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements CrudDao<User>{
    @Override
    public void save() {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public User findById() {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
