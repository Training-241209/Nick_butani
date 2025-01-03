package com.nick.ers.model.dto;

public class UserDTO {
    private int userId;
    private String firstname;
    private String lastname;
    private String role;

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
        
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}