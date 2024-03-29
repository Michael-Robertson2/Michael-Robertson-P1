package com.revature.reimbursement.daos;

import com.revature.reimbursement.models.User;
import com.revature.reimbursement.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements CrudDao<User>{
    @Override
    public void save(User obj) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO ers_users (user_id, username, email, password, given_name, surname, is_active, role_id) " +
                                                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getGivenName());
            ps.setString(6, obj.getSurname());
            ps.setBoolean(7, obj.isActive());
            ps.setString(8, obj.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public void update(User obj) {

    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                users.add(new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"),
                                    rs.getString("password"), rs.getString("given_name"), rs.getString("surname"),
                                    rs.getBoolean("is_active"), rs.getString("role_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }//String id, String username, String email, String password, String givenName, String surname, boolean isActive, String role

    public List<String> findAllUsernames() {
        List<String> usernames = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (username) FROM ers_users");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                usernames.add(rs.getString("username"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from reimbursement.ers_users where username= ? and password= ?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
                user = new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"),
                        rs.getString("password"), rs.getString("given_name"), rs.getString("surname"),
                        rs.getBoolean("is_active"), rs.getString("role_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<String> findAllEmails() {
        List<String> emails = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (email) FROM ers_users");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                emails.add(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    public List<User> getAllUsersByUsername(String username) {
        List<User> users = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_users where username like ?");
            ps.setString(1, "%" + username + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                users.add(new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"),
                        rs.getString("password"), rs.getString("given_name"), rs.getString("surname"),
                        rs.getBoolean("is_active"), rs.getString("role_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public String getRoleIdByName(String name) {
        String id = null;

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select role_id from ers_user_roles where role= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                id = rs.getString("role_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public void activateAccount(String username) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement ps = con.prepareStatement("update ers_users set is_active= true where username= ?");
            ps.setString(1, username);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(String username) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("delete from ers_users where username= ?");
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
