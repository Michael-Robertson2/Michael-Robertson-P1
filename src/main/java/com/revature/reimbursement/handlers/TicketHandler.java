package com.revature.reimbursement.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursement.dtos.requests.*;
import com.revature.reimbursement.dtos.responses.Principal;
import com.revature.reimbursement.models.Ticket;
import com.revature.reimbursement.services.TicketService;
import com.revature.reimbursement.services.TokenService;
import com.revature.reimbursement.utils.custom_exceptions.InvalidAuthException;
import com.revature.reimbursement.utils.custom_exceptions.InvalidTicketException;
import com.revature.reimbursement.utils.custom_exceptions.InvalidUpdateException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class TicketHandler {

    private final TicketService ticketService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final Logger logger = LoggerFactory.getLogger(Ticket.class);


    public TicketHandler(TicketService ticketService, ObjectMapper mapper, TokenService tokenService) {
        this.ticketService = ticketService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public void submitTicket(Context ctx) throws IOException {

        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Only standard users can submit tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
            return;
        }

        NewTicketRequest req = mapper.readValue(ctx.req.getInputStream(), NewTicketRequest.class);
        req.setAuthor_id(principal.getId());

        try {
            logger.info("Submitting ticket");

            Ticket createdTicket;
            req.setAmount(Math.round(req.getAmount()*100.0)/100.0);
            req.setType_id(ticketService.getTypeIdByName(req.getType_id()));
            if (ticketService.isValidAmount(req.getAmount())) {
                if (ticketService.isValidDescription(req.getDescription())) {
                    System.out.println(req.getType_id());
                    if(ticketService.isValidType(req.getType_id())) {
                        createdTicket = ticketService.submitTicket(req);
                    } else throw new InvalidTicketException("Invalid type specified");
                } else throw new InvalidTicketException("Ticket description required");
            } else throw new InvalidTicketException("Invalid amount submitted");

            ctx.status(201);
            ctx.json(createdTicket);
            logger.info("Ticket accepted");
        } catch (InvalidTicketException e) {
            ctx.status(400);
            ctx.json(e);
            logger.info("Ticket rejected");
        }
    }

    public void editDescription(Context ctx) throws IOException{
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only edit their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
            return;
        }

        NewEditRequest req = mapper.readValue(ctx.req.getInputStream(), NewEditRequest.class);

        try {
            logger.info("Attempting to edit ticket");
            Ticket currentTicket = ticketService.getTicketById(req.getTicket_id());

            if (ticketService.isValidTicket(currentTicket)) {
                if (ticketService.isPending(currentTicket.getStatus_id())) {
                    ticketService.editDescription(currentTicket.getId(), req.getNew_description());
                } else throw new InvalidUpdateException("Ticket has already been resolved, cannot edit description");
            } else throw new InvalidUpdateException("Invalid ticket");
            logger.info("Edit accepted");
        } catch(InvalidUpdateException e) {
            ctx.status(400);
            ctx.json(e);
            logger.info("Edit rejected");
        }
    }

    public void editAmount(Context ctx) throws IOException {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only edit their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
            return;
        }

        NewAmountRequest req = mapper.readValue(ctx.req.getInputStream(), NewAmountRequest.class);

        try {
            logger.info("Attempting to edit ticket");
            Ticket currentTicket = ticketService.getTicketById(req.getTicket_id());

            if (ticketService.isValidTicket(currentTicket)) {
                if (ticketService.isPending(currentTicket.getStatus_id())) {
                    if (ticketService.isValidAmount(req.getNew_amount())) {
                        ticketService.editAmount(currentTicket.getId(), req.getNew_amount());
                    } else throw new InvalidUpdateException("New amount specified is invalid");
                } else throw new InvalidUpdateException("Ticket has already been resolved, cannot edit");
            } else throw new InvalidUpdateException("Invalid ticket");
            logger.info("Edit accepted");
        } catch(InvalidUpdateException e) {
            ctx.status(400);
            ctx.json(e);
            logger.info("Edit rejected");
        }
    }

    public void deleteTicket(Context ctx) throws IOException {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only delete their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
            return;
        }

        NewDeleteRequest req = mapper.readValue(ctx.req.getInputStream(), NewDeleteRequest.class);

        try {
            logger.info("Attempting to delete ticket");
            Ticket currentTicket = ticketService.getTicketById(req.getTicket_id());

            if (ticketService.isValidTicket(currentTicket)) {
                if (ticketService.isPending(currentTicket.getStatus_id())) {
                    ticketService.deleteTicket(req.getTicket_id());
                } else throw new InvalidUpdateException("Ticket has already been resolved, cannot delete");
            } else throw new InvalidUpdateException("Invalid ticket");
            logger.info("Delete accepted");
        } catch(InvalidUpdateException e) {
            ctx.status(400);
            ctx.json(e);
            logger.info("Delete rejected");
        }
    }

    public void getOwn(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only view their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

            List<Ticket> tickets = ticketService.getOwn(principal.getId());
            ctx.json(tickets);

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }

    }
    public void getOwnPendingRecentFirst(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only view their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

            List<Ticket> tickets = ticketService.getOwnPendingRecentFirst(principal.getId());
            ctx.json(tickets);

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getOwnPendingOldFirst(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only view their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

            List<Ticket> tickets = ticketService.getOwnPendingOldFirst(principal.getId());
            ctx.json(tickets);

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getOwnResolvedRecentFirst(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only view their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

            List<Ticket> tickets = ticketService.getOwnResolvedRecentFirst(principal.getId());
            ctx.json(tickets);

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getOwnResolvedOldFirst(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only view their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

            List<Ticket> tickets = ticketService.getOwnResolvedOldFirst(principal.getId());
            ctx.json(tickets);

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getOwnResolvedRejectedFirst(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only view their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

            List<Ticket> tickets = ticketService.getOwnResolvedRejectedFirst(principal.getId());
            ctx.json(tickets);

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getOwnResolvedApprovedFirst(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120002"))
                throw new InvalidAuthException("Users can only view their own tickets.");

            if(!principal.isActive())
                throw new InvalidAuthException("This account has not been activated.");

            List<Ticket> tickets = ticketService.getOwnResolvedApprovedFirst(principal.getId());
            ctx.json(tickets);

        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void updateTicket(Context ctx) throws IOException {

        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120004"))
                throw new InvalidAuthException("Only finance managers can update tickets.");
        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
            return;
        }

        NewUpdateRequest req = mapper.readValue(ctx.req.getInputStream(), NewUpdateRequest.class);
        req.setResolver_id(principal.getId());

        try {
            logger.info("Updating ticket...");
            Ticket currentTicket = ticketService.getTicketById(req.getTicket_id());
            req.setNew_status(ticketService.getStatusByName(req.getNew_status()));
            Ticket updatedTicket;

            if (ticketService.isValidTicket(currentTicket)) {
                if (ticketService.isPending(currentTicket.getStatus_id())) {
                    if (ticketService.isValidStatus(req.getNew_status())) {
                        updatedTicket = ticketService.updateTicket(currentTicket, req);
                    } else throw new InvalidUpdateException("Requested new status is invalid");
                } else throw new InvalidUpdateException("This ticket has already been resolved");
            } else throw new InvalidUpdateException("Invalid ticket");

            ctx.status(200);
            ctx.json(updatedTicket);
            logger.info("Update accepted");
        } catch (InvalidUpdateException e) {
            ctx.status(400);
            ctx.json(e);
            logger.info("Update rejected");
        }


    }

    public void getAllPending(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120004"))
                throw new InvalidAuthException("Only finance managers can view submitted tickets.");

            List<Ticket> tickets = ticketService.getAllPending();
            ctx.json(tickets);
        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }

    public void getAllResolved(Context ctx) {
        String token;
        Principal principal;
        try {
            token = ctx.req.getHeader("authorization");
            if (token == null || token.equals("")) throw new InvalidAuthException("You are not logged in");

            principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid Token");

            if (!principal.getRole().equals("b06a2afc-702c-11ed-a1eb-0242ac120004"))
                throw new InvalidAuthException("Only finance managers can view submitted tickets.");

            List<Ticket> tickets = ticketService.getAllResolved();
            ctx.json(tickets);
        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
        }
    }
}
