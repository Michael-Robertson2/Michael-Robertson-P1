package com.revature.reimbursement.daos;

import com.revature.reimbursement.models.Ticket;
import com.revature.reimbursement.utils.ConnectionFactory;

import javax.naming.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDao implements CrudDao<Ticket> {

    @Override
    public void save(Ticket obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("insert into ers_reimbursements (reimb_id, amount, submitted, resolved, description, receipt, " +
                                                             "payment_id, author_id, resolver_id, status_id, type_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setDouble(2, obj.getAmount());
            ps.setTimestamp(3, obj.getSubmitted());
            ps.setTimestamp(4, obj.getResolved());
            ps.setString(5, obj.getDescription());
            ps.setBytes(6, obj.getReceipt());
            ps.setString(7, obj.getPayment_id());
            ps.setString(8, obj.getAuthor_id());
            ps.setString(9, obj.getResolver_id());
            ps.setString(10, obj.getStatus_id());
            ps.setString(11, obj.getType_id());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Ticket obj) {

    }

    @Override
    public void update(Ticket obj) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE ers_reimbursements SET resolved= ?, resolver_id= ?, status_id= ? " +
                                                        "WHERE reimb_id= ?");

            ps.setTimestamp(1, obj.getResolved());
            ps.setString(2, obj.getResolver_id());
            ps.setString(3, obj.getStatus_id());
            ps.setString(4, obj.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Ticket findById(String id) {
        Ticket ticket = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE reimb_id= ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                ticket = new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                                    rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                    rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                                    rs.getString("status_id"), rs.getString("type_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    public void editDescription(String id, String description) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("update ers_reimbursements set description= ? where reimb_id= ?");
            ps.setString(1, description);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editAmount(String id, double amount) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("update ers_reimbursements set amount= ? where reimb_id= ?");
            ps.setDouble(1, amount);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTicket(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("delete from ers_reimbursements where reimb_id= ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTypeIdByName (String name) {
        String id = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT type_id FROM ers_reimbursement_types WHERE type= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                id = rs.getString("type_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getStatusIdByName (String name) {
        String id = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT status_id FROM ers_reimbursement_statuses WHERE status= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                id = rs.getString("status_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<Ticket> getAllPending() {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE status_id= ?");
            ps.setString(1, getStatusIdByName("PENDING"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getAllResolved() {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE status_id= ? OR status_id= ?");
            ps.setString(1, getStatusIdByName("APPROVED"));
            ps.setString(2, getStatusIdByName("DENIED"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getOwn(String id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursements where author_id= ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getOwnPendingRecentFirst(String id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursements where author_id= ? and status_id= ? order by submitted DESC");
            ps.setString(1, id);
            ps.setString(2, getStatusIdByName("PENDING"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                    rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                    rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                    rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getOwnPendingOldFirst(String id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursements where author_id= ? and status_id= ? order by submitted");
            ps.setString(1, id);
            ps.setString(2, getStatusIdByName("PENDING"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getOwnResolvedRecentFirst(String id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursements where author_id= ? and status_id!= ? order by submitted DESC");
            ps.setString(1, id);
            ps.setString(2, getStatusIdByName("PENDING"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getOwnResolvedOldFirst(String id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursements where author_id= ? and status_id!= ? order by submitted");
            ps.setString(1, id);
            ps.setString(2, getStatusIdByName("PENDING"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getOwnResolvedRejectedFirst(String id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursements where author_id= ? and status_id!= ? order by status_id DESC");
            ps.setString(1, id);
            ps.setString(2, getStatusIdByName("PENDING"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getOwnResolvedApprovedFirst(String id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursements where author_id= ? and status_id!= ? order by status_id");
            ps.setString(1, id);
            ps.setString(2, getStatusIdByName("PENDING"));
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                tickets.add(new Ticket(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
