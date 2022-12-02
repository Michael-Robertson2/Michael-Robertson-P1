package com.revature.reimbursement.dtos.requests;

import java.sql.Timestamp;

public class NewUpdateRequest {

    String ticket_id;
    String new_status;
    String resolver_id;

    public NewUpdateRequest() {
        super();
    }

    public NewUpdateRequest(String ticket_id, String new_status) {
        this.ticket_id = ticket_id;
        this.new_status = new_status;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getNew_status() {
        return new_status;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    @Override
    public String toString() {
        return "NewUpdateRequest{" +
                "ticket_id='" + ticket_id + '\'' +
                ", new_status='" + new_status + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                '}';
    }
}
