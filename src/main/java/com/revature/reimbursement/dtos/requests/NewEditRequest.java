package com.revature.reimbursement.dtos.requests;

public class NewEditRequest {
    private String ticket_id;
    private String new_description;

    public NewEditRequest() {
        super();
    }
    public NewEditRequest(String ticket_id, String new_description) {
        this.ticket_id = ticket_id;
        this.new_description = new_description;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getNew_description() {
        return new_description;
    }

    public void setNew_description(String new_description) {
        this.new_description = new_description;
    }

    @Override
    public String toString() {
        return "NewEditRequest{" +
                "ticket_id='" + ticket_id + '\'' +
                ", new_description='" + new_description + '\'' +
                '}';
    }
}
