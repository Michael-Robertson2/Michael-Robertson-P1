package com.revature.reimbursement.dtos.responses;

public class Principal {
    private String id;
    private String username;
    private String email;
    private String givenName;
    private String surname;
    private boolean isActive;
    private String role;


    public Principal() {
        super();
    }

    public Principal(String id, String username, String email, String givenName, String surname, boolean isActive, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.isActive = isActive;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", isActive=" + isActive +
                ", role='" + role + '\'' +
                '}';
    }
}
