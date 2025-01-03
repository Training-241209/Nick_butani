package com.nick.ers.model.dto;

public class ReimburseDTO {
    private int reimId;
    private String description;
    private double amount;
    private String status;
    private String employeeName;

    public ReimburseDTO(){

    }

    public ReimburseDTO(int reimId,String description, double amount, String status, String employeeName){
        this.reimId = reimId;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.employeeName = employeeName;
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

    public String getStatus(){
        return status;
    }

    public String getEmployeeName(){
        return employeeName;
    }

    public void setReimId(int reimId){
        this.reimId = reimId;
    }

    public void setDescription(String description){
        this.description= description;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setEmployeeName(String employeeName){
        this.employeeName = employeeName;
    }

    @Override
    public String toString(){
        return "Reimburse { " + "Description= " + description + ", Ammount= " + amount + " }";
    }

}

