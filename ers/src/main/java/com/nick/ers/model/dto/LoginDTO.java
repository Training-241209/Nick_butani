package com.nick.ers.model.dto;

public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO(String username, String password){
        this.username= username;
        this.password= password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString(){
        return "Loginservice{ " + " username= " + username + ", password " + password + " }";
    }
}
