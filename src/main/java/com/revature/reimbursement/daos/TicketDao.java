package com.revature.reimbursement.daos;

import com.revature.reimbursement.models.Ticket;
import com.revature.reimbursement.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    }

    @Override
    public Ticket findById() {
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
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
}
