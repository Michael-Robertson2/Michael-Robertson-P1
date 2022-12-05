package com.revature.reimbursement.dtos.requests;

public class NewDeleteRequest {
    private String ticket_id;

    public NewDeleteRequest() {
        super();
    }

    public NewDeleteRequest(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    @Override
    public String toString() {
        return "NewDeleteRequest{" +
                "ticket_id='" + ticket_id + '\'' +
                '}';
    }
}
