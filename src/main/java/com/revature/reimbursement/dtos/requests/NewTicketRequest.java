package com.revature.reimbursement.dtos.requests;

import java.time.LocalDateTime;

public class NewTicketRequest {
    private double amount;
    private String description;
    private String author_id;
    //private byte[] receipt;
    private String type_id;

    public NewTicketRequest() {
        super();
    }
    public NewTicketRequest(double amount, String description, String type_id) {
        this.amount = amount;
        this.description = description;
        this.author_id = null;
        this.type_id = type_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "NewTicketRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", author_id='" + author_id + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }
}
