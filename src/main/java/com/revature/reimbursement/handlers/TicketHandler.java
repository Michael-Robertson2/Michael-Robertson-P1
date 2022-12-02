package com.revature.reimbursement.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursement.dtos.requests.NewTicketRequest;
import com.revature.reimbursement.dtos.responses.Principal;
import com.revature.reimbursement.services.TicketService;
import com.revature.reimbursement.services.TokenService;
import com.revature.reimbursement.utils.custom_exceptions.InvalidAuthException;
import com.revature.reimbursement.utils.custom_exceptions.InvalidTicketException;
import io.javalin.http.Context;

import java.io.IOException;

public class TicketHandler {

    private final TicketService ticketService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;

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
        } catch (InvalidAuthException e) {
            ctx.status(401);
            ctx.json(e);
            return;
        }


        NewTicketRequest req = mapper.readValue(ctx.req.getInputStream(), NewTicketRequest.class);
        req.setAuthor_id(principal.getId());

        try {
            ticketService.submitTicket(req);
            ctx.status(201);
        } catch (InvalidTicketException e) {
            ctx.status(400);
            ctx.json(e);
        }

    }
}
