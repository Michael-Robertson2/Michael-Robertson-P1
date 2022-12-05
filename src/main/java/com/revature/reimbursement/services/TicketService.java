package com.revature.reimbursement.services;

import com.revature.reimbursement.daos.TicketDao;
import com.revature.reimbursement.dtos.requests.NewTicketRequest;
import com.revature.reimbursement.dtos.requests.NewUpdateRequest;
import com.revature.reimbursement.models.Ticket;
import com.revature.reimbursement.utils.custom_exceptions.InvalidTicketException;
import com.revature.reimbursement.utils.custom_exceptions.InvalidUpdateException;
import com.revature.reimbursement.utils.custom_exceptions.InvalidUserException;
import org.eclipse.jetty.util.DateCache;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketService {

    private final TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public Ticket submitTicket(NewTicketRequest req) {

        Ticket createdTicket = new Ticket(UUID.randomUUID().toString(), req.getAmount(), Timestamp.from(Instant.now()), null,
                                    req.getDescription(), null, null, req.getAuthor_id(),
                            null, ticketDao.getStatusIdByName("PENDING"), req.getType_id());

        ticketDao.save(createdTicket);

        return createdTicket;
    }

    public Ticket updateTicket(Ticket currentTicket, NewUpdateRequest req) {

        Ticket updatedTicket = new Ticket(currentTicket.getId(), currentTicket.getAmount(), currentTicket.getSubmitted(), Timestamp.from(Instant.now()),
                                        currentTicket.getDescription(), currentTicket.getReceipt(), currentTicket.getPayment_id(), currentTicket.getAuthor_id(),
                                        req.getResolver_id(), req.getNew_status(), currentTicket.getType_id());
        ticketDao.update(updatedTicket);

        return updatedTicket;
    }

    public void editDescription(String id, String description) {
        ticketDao.editDescription(id, description);
    }

    public void editAmount(String id, double amount) {
        ticketDao.editAmount(id, amount);
    }

    public void deleteTicket(String id) {
        ticketDao.deleteTicket(id);
    }
    public List<Ticket> getAllPending() {
        return ticketDao.getAllPending();
    }

    public List<Ticket> getAllResolved() {
        return ticketDao.getAllResolved();
    }

    public List<Ticket> getOwn(String id) {
        return ticketDao.getOwn(id);
    }

    public List<Ticket> getOwnPendingRecentFirst(String id) {
        return ticketDao.getOwnPendingRecentFirst(id);
    }

    public List<Ticket> getOwnPendingOldFirst(String id) {
        return ticketDao.getOwnPendingOldFirst(id);
    }

    public List<Ticket> getOwnResolvedRecentFirst(String id) {
        return ticketDao.getOwnResolvedRecentFirst(id);
    }

    public List<Ticket> getOwnResolvedOldFirst(String id) {
        return ticketDao.getOwnResolvedOldFirst(id);
    }

    public List<Ticket> getOwnResolvedRejectedFirst(String id) { return ticketDao.getOwnResolvedRejectedFirst(id); }

    public List<Ticket> getOwnResolvedApprovedFirst(String id) { return ticketDao.getOwnResolvedApprovedFirst(id); }

    public boolean isValidAmount(double amount) {
        return (amount > 0 && amount < 10000);
    }

    public boolean isValidDescription(String description) {
        return (description != null && !description.equals(""));
    }

    public boolean isValidType(String id) {
        return (id != null);
    }

    public boolean isValidTicket(Ticket ticket) {
        return (ticket != null);
    }

    public boolean isPending(String id) {
        return id.equals(ticketDao.getStatusIdByName("PENDING"));
    }

    public boolean isValidStatus(String status) {
        return (status != null);
    }

    public String getTypeIdByName(String type) {
        return ticketDao.getTypeIdByName(type);
    }

    public Ticket getTicketById(String id) {
        return ticketDao.findById(id);
    }

    public String getStatusByName(String name) {
        return ticketDao.getStatusIdByName(name);
    }

}
