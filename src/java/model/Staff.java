/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author amira
 */
public class Staff {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private int phoneNum;
    private String username;
    private String pho;
    
    public int getId(){
        return id;
    }
    public String getfName(){
        return fName;
    }
    public String getlName(){
        return lName;
    }
    public String getEmail(){
        return email;
    }
    public int getPhoneNum(){
        return phoneNum;
    }
    public String getUsername(){
        return username;
    }
    public String getPho(){
        return pho;
    }
    
    public void setId(int id){
        this.id = id;
    }   
    public void setfName(String fName){
        this.fName = fName;
    }
    public void setlName(String lName){
        this.lName = lName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPhoneNum(int phoneNum){
        this.phoneNum = phoneNum;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPho(String pho){
        this.pho  = pho;
    }
}
