package model;

import java.math.BigDecimal;

public class AdopterBean {

    private int adoptId;
    private String adoptFName;
    private String adoptLName;
    private String adoptIC;
    private String adoptPhoneNum;    // Changed to String
    private String adoptEmail;
    private String adoptAddress;
    private String adoptOccupation;  // NEW
    private double adoptIncome;  // NEW
    private String adoptUsername;
    private String adoptPassword;

    // ===== Constructors =====
    public AdopterBean() {
        // No-arg constructor
    }

    public AdopterBean(int adoptId, String adoptFName, String adoptLName, String adoptIC,
                       String adoptPhoneNum, String adoptEmail, String adoptAddress,
                       String adoptOccupation, double adoptIncome,
                       String adoptUsername, String adoptPassword) {
        this.adoptId = adoptId;
        this.adoptFName = adoptFName;
        this.adoptLName = adoptLName;
        this.adoptIC = adoptIC;
        this.adoptPhoneNum = adoptPhoneNum;
        this.adoptEmail = adoptEmail;
        this.adoptAddress = adoptAddress;
        this.adoptOccupation = adoptOccupation;
        this.adoptIncome = adoptIncome;
        this.adoptUsername = adoptUsername;
        this.adoptPassword = adoptPassword;
    }

    // ===== Getters =====
    public int getAdoptId() { return adoptId; }
    public String getAdoptFName() { return adoptFName; }
    public String getAdoptLName() { return adoptLName; }
    public String getAdoptIC() { return adoptIC; }
    public String getAdoptPhoneNum() { return adoptPhoneNum; }
    public String getAdoptEmail() { return adoptEmail; }
    public String getAdoptAddress() { return adoptAddress; }
    public String getAdoptOccupation() { return adoptOccupation; }
    public double getAdoptIncome() { return adoptIncome; }
    public String getAdoptUsername() { return adoptUsername; }
    public String getAdoptPassword() { return adoptPassword; }

    // ===== Setters =====
    public void setAdoptId(int adoptId) { this.adoptId = adoptId; }
    public void setAdoptFName(String adoptFName) { this.adoptFName = adoptFName; }
    public void setAdoptLName(String adoptLName) { this.adoptLName = adoptLName; }
    public void setAdoptIC(String adoptIC) { this.adoptIC = adoptIC; }
    public void setAdoptPhoneNum(String adoptPhoneNum) { this.adoptPhoneNum = adoptPhoneNum; }
    public void setAdoptEmail(String adoptEmail) { this.adoptEmail = adoptEmail; }
    public void setAdoptAddress(String adoptAddress) { this.adoptAddress = adoptAddress; }
    public void setAdoptOccupation(String adoptOccupation) { this.adoptOccupation = adoptOccupation; }
    public void setAdoptIncome(double adoptIncome) { this.adoptIncome = adoptIncome; }
    public void setAdoptUsername(String adoptUsername) { this.adoptUsername = adoptUsername; }
    public void setAdoptPassword(String adoptPassword) { this.adoptPassword = adoptPassword; }
}
