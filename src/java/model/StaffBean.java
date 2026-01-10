
package model;

public class StaffBean {
    private int staffId;
    private String staffFname;
    private String staffLname;
    private String staffEmail;
    private int staffPhoneNum;
    private String staffUsername;
    private String staffPassword;
    
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
    public String getStaffPassword(){
        return staffPassword;
    }
    
    public void setStaffId(int id){
        this.staffId = id;
    }   
    public void setStaffFname(String fName){
        this.staffFname = fName;
    }
    public void setStaffLname(String lName){
        this.staffLname = lName;
    }
    public void setStaffEmail(String email){
        this.staffEmail = email;
    }
    public void setStaffPhoneNum(int phoneNum){
        this.staffPhoneNum = phoneNum;
    }
    public void setStaffUsername(String username){
        this.staffUsername = username;
    }
    public void setStaffPassword(String password){
        this.staffPassword  = password;
    }
}
