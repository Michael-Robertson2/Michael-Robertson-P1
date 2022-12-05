package com.revature.reimbursement.dtos.requests;

public class NewActivationRequest {
    private String username;

    public NewActivationRequest() {
        super();
    }

    public NewActivationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "NewActivationRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
