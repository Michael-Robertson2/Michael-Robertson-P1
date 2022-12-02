package com.revature.reimbursement.services;

import com.revature.reimbursement.daos.TicketDao;
import com.revature.reimbursement.dtos.requests.NewTicketRequest;
import com.revature.reimbursement.models.Ticket;
import com.revature.reimbursement.utils.custom_exceptions.InvalidTicketException;
import org.eclipse.jetty.util.DateCache;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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
}
