package com.nick.ers.model.dto;

public class ReimburseDTO {

    private String description;
    private double ammount;

    public ReimburseDTO(){

    }

    public ReimburseDTO(String description, double ammount){
        this.description = description;
        this.ammount = ammount;
    }

    public String getDescription(){
        return description;
    }

    public double getAmmount(){
        return ammount;
    }

    public void setDescription(String description){
        this.description= description;
    }

    public void setAmmount(double ammount){
        this.ammount = ammount;
    }

    @Override
    public String toString(){
        return "Reimburse { " + "Description= " + description + ", Ammount= " + ammount + " }";
    }
}

