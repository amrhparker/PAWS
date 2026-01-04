/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;
/**
 *
 * @author amira
 */
public class Adopter {
    private String fName;
    private String lName;
    private String IC;
    private int phoneNum;
    private String email;
    private String address;
    private String username;
    private String password;
    
    public String getfName(){
        return fName;
    }
    public String getlName(){
        return lName;
    }
    public String getIC(){
        return IC;
    }
    public int getPhoneNum(){
        return phoneNum;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    
    public void setfName(String fName){
        this.fName = fName;
    }
    public void setlName(String lName){
        this.lName = lName;
    }
    public void setIC(String IC){
        this.IC  = IC;
    }
    public void setPhoneNum(int phoneNum){
        this.phoneNum = phoneNum;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
