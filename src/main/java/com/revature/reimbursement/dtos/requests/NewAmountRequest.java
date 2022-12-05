package com.revature.reimbursement.dtos.requests;

public class NewAmountRequest {
    private String ticket_id;
    private double new_amount;

    public NewAmountRequest() {
        super();
    }
    public NewAmountRequest(String ticket_id, double new_amount) {
        this.ticket_id = ticket_id;
        this.new_amount = new_amount;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public double getNew_amount() {
        return new_amount;
    }

    public void setNew_amount(double new_amount) {
        this.new_amount = new_amount;
    }

    @Override
    public String toString() {
        return "NewAmountRequest{" +
                "ticket_id='" + ticket_id + '\'' +
                ", new_amount=" + new_amount +
                '}';
    }
}
