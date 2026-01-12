package model;

import java.io.Serializable;
import java.sql.Date;

public class RecordBean implements Serializable {
    
    private int recordId;
    private int appId;
    private int staffId;

    // ===== OBJECT REFERENCES =====
    private ApplicationBean application;
    private StaffBean staff;

    // ===== DATES =====
    private Date recordDate;
    private Date appDate;

    // ===== STATUS =====
    private String recordStatus;

    // ===== ADOPTER INFO =====
    private String adopterName;
    private String adopterPhone;
    private String adopterAddress;

    // ===== PET INFO =====
    private String petName;
    private String petSpecies;   // 🔥 ADDED
    private String petBreed;     // 🔥 ADDED
    private int petAge;          // 🔥 ADDED

    public RecordBean() {}

    public RecordBean(int recordId, int appId, Date recordDate, int staffId) {
        this.recordId = recordId;
        this.appId = appId;
        this.staffId = staffId;
        this.recordDate = recordDate;
    }

    // ===== GETTERS & SETTERS =====

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public ApplicationBean getApplication() {
        return application;
    }

    public void setApplication(ApplicationBean application) {
        this.application = application;
        if (application != null) {
            this.appId = application.getAppId();
        }
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
        if (staff != null) {
            this.staffId = staff.getStaffId();
        }
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getAdopterName() {
        return adopterName;
    }

    public void setAdopterName(String adopterName) {
        this.adopterName = adopterName;
    }

    public String getAdopterPhone() {
        return adopterPhone;
    }

    public void setAdopterPhone(String adopterPhone) {
        this.adopterPhone = adopterPhone;
    }

    public String getAdopterAddress() {
        return adopterAddress;
    }

    public void setAdopterAddress(String adopterAddress) {
        this.adopterAddress = adopterAddress;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }
}
