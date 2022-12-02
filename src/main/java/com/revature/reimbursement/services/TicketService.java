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

    public void submitTicket(NewTicketRequest req) {
        double amount = Math.round(req.getAmount()*100.0)/100.0;
        if (amount <= 0) throw new InvalidTicketException("Invalid amount submitted");
        String description = req.getDescription();
        if(description == null || description.equals("")) throw new InvalidTicketException("Ticket description required");
        String type_id = ticketDao.getTypeIdByName(req.getType_id());
        if(type_id == null) throw new InvalidTicketException("Invalid type specified");

        Ticket createdTicket = new Ticket(UUID.randomUUID().toString(), amount, Timestamp.from(Instant.now()), null,
                                    description, null, null, req.getAuthor_id(),
                            null, ticketDao.getStatusIdByName("PENDING"), type_id);

        ticketDao.save(createdTicket);
    }

    public void updateTicket(NewUpdateRequest req) {
        Ticket currentTicket = ticketDao.findById(req.getTicket_id());
        if (currentTicket == null) throw new InvalidUpdateException("Invalid ticket");

        String old_status_id = currentTicket.getStatus_id();
        if (!old_status_id.equals(ticketDao.getStatusIdByName("PENDING"))) throw new InvalidUpdateException("This ticket has already been resolved");

        String new_status_id = ticketDao.getStatusIdByName(req.getNew_status());
        if(new_status_id == null || new_status_id.equals("") || new_status_id.equals(ticketDao.getStatusIdByName("PENDING")))
            throw new InvalidUpdateException("Requested new status is invalid");

        Ticket updatedTicket = new Ticket(currentTicket.getId(), currentTicket.getAmount(), currentTicket.getSubmitted(), Timestamp.from(Instant.now()),
                                        currentTicket.getDescription(), currentTicket.getReceipt(), currentTicket.getPayment_id(), currentTicket.getAuthor_id(),
                                        req.getResolver_id(), new_status_id, currentTicket.getType_id());
        ticketDao.update(updatedTicket);

    }
    public List<Ticket> getAllPending() {
        return ticketDao.getAllPending();
    }

    public List<Ticket> getAllResolved() {
        return ticketDao.getAllResolved();
    }
}
