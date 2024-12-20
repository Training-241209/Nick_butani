package com.ers.employee_reimbursement_system.entity.dto;

public class RegisterDTO extends LoginDTO {
    private String firstName;
    private String lastName;
    private String role;
    private String email; 

    public RegisterDTO() {
    }

    public RegisterDTO(String username, String password, String firstName, String lastName, String role, String email) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "RegistrationUserDTO{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}