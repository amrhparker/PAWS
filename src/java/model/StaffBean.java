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
public class StaffBean {
    private int staffId;
    private String staffFname;
    private String staffLname;
    private String staffEmail;
    private int staffPhoneNum;
    private String staffUsername;
    private String staffPho;
    
    public int getStaffId(){
        return staffId;
    }
    public String getStaffFname(){
        return staffFname;
    }
    public String getStaffLname(){
        return staffLname;
    }
    public String getStaffEmail(){
        return staffEmail;
    }
    public int getStaffPhoneNum(){
        return staffPhoneNum;
    }
    public String getStaffUsername(){
        return staffUsername;
    }
    public String getStaffPho(){
        return staffPho;
    }
    
    public void setStaffId(int id){
        this.staffId = id;
    }   
    public void setfName(String fName){
        this.staffFname = fName;
    }
    public void setlName(String lName){
        this.staffLname = lName;
    }
    public void setEmail(String email){
        this.staffEmail = email;
    }
    public void setPhoneNum(int phoneNum){
        this.staffPhoneNum = phoneNum;
    }
    public void setUsername(String username){
        this.staffUsername = username;
    }
    public void setPho(String pho){
        this.staffPho  = pho;
    }
}
