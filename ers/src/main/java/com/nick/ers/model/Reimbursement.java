package com.nick.ers.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Reimbursement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reim_id")
    private int reimId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public enum Status{
        PENDING, APPROVED, DENIED
    }

    public Reimbursement(){

    }

    public Reimbursement(int reimId, String description, double amount, Status status){
        this.reimId = reimId;
        this.description = description;
        this.amount = amount;
        this.status = status;
    }

    public int getReimId(){
        return reimId;
    }

    public String getDescription(){
        return description;
    }

    public double getAmount(){
        return amount;
    }

    public User getUser(){
        return user;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setReimId(int reimId){
        this.reimId = reimId;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAmount(Double amount){
        this.amount = amount;
    }

    @Override
    public String toString(){
        return "Reimbursement{ " + reimId + ", description= " + description + ", ammount= " + amount + ", user= " + user + ", Status " + status  + " ]";
    }
}
