package com.nick.ers.model.dto;

import com.nick.ers.model.User.Role;

public class UserRegisterationDTO extends LoginDTO{

    private String firstname;
    private String lastname;
    private Role role;

    public UserRegisterationDTO(String username, String password, String firstname, String lastname, Role role){
        super(username, password);
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public Role getRole(){
        return role;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setRole(Role role){
        this.role = role;
    }

    @Override
    public String toString(){
        return "UserRegistered{ " + "firstname= " + firstname + ", lastname= " + lastname + " }";
    }
}