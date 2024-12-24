package com.nick.ers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections; 
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;
    
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.EMPLOYEE;

    public enum Role {
        EMPLOYEE("EMPLOYEE"), MANAGER("MANAGER");

        private String type;
        
        private Role(String type) {
            this.type = type;
        }
        
        @Override
        public String toString() {
            return type;
        }
    }

    public User(int userId, String username, String password, String firstname, String lastname, Role role){
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }

    public User() {
        
    }

    public int getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
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

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reimbursement> reimbursements;

    public List<Reimbursement> getReimbursement(){
        return reimbursements;
    }

    public void setReimbursement(List<Reimbursement> reimbursements){
        this.reimbursements = reimbursements;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String toString(){
        return "User{ " + userId + ", username= " + username + ", fistname= " + firstname + ", lastname= " + lastname + ", password= " + password + ", role= " + role + ", reimbursment= " + reimbursements + "}";
    }

  

}

