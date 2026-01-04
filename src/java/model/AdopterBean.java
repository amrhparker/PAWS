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
public class AdopterBean {
    private int adoptId;
    private String adoptFname;
    private String adoptLname;
    private String adoptIC;
    private int adoptPhoneNum;
    private String adoptEmail;
    private String adoptAddress;
    private String adoptUsername;
    private String adoptPassword;
    
    public int getAdoptId(){
        return adoptId;
    }
    public String getAdoptFname(){
        return adoptFname;
    }
    public String getAdoptLname(){
        return adoptLname;
    }
    public String getAdoptIC(){
        return adoptIC;
    }
    public int getAdoptPhoneNum(){
        return adoptPhoneNum;
    }
    public String getAdoptEmail(){
        return adoptEmail;
    }
    public String getAdoptAddress(){
        return adoptAddress;
    }
    public String getAdoptUsername(){
        return adoptUsername;
    }
    public String getAdoptPassword(){
        return adoptPassword;
    }
    
    public void setAdoptId(int id){
        this.adoptId = id;
    }    
    public void setAdoptFname(String fName){
        this.adoptFname = fName;
    }
    public void setAdoptLname(String lName){
        this.adoptLname = lName;
    }
    public void setAdoptIC(String IC){
        this.adoptIC  = IC;
    }
    public void setAdoptPhoneNum(int phoneNum){
        this.adoptPhoneNum = phoneNum;
    }
    public void setAdoptEmail(String email){
        this.adoptEmail = email;
    }
    public void setAdoptAddress(String address){
        this.adoptAddress = address;
    }
    public void setAdoptUsername(String username){
        this.adoptUsername = username;
    }
    public void setAdoptPassword(String password){
        this.adoptPassword = password;
    }
}
