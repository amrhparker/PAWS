package model;

import java.io.Serializable;
import java.sql.Date;

public class ApplicationBean implements Serializable{

    // ===== Primary Key =====
    private int appId;

    private int adoptId;
    private int petId;
    private int staffId;
    private String staffName;

    private AdopterBean adopter;
    private PetBean pet;
    private StaffBean staff;

    // ===== Application Info =====
    private Date appDate;
    private String appStatus;
    private String appEligibility;

    // ===== Eligibility Review =====
    private String hasOwnedPet;        // Yes / No
    private String caretakerInfo;
    private String petEnvironment;     // Indoor / Outdoor
    private String medicalReady;       // Yes / No
    private String adoptionReason;

    // ===== Constructors =====
    public ApplicationBean() {
        // No-arg constructor for frameworks
    }

    public ApplicationBean(int appId, int adoptId, int petId, int staffId, Date appDate, String appStatus, String appEligibility, String hasOwnedPet, String caretakerInfo, String petEnvironment, String medicalReady, String adoptionReason) {
        this.appId = appId;
        this.adoptId = adoptId;
        this.petId = petId;
        this.staffId = staffId;
        this.appDate = appDate;
        this.appStatus = appStatus;
        this.appEligibility = appEligibility;
        this.hasOwnedPet = hasOwnedPet;
        this.caretakerInfo = caretakerInfo;
        this.petEnvironment = petEnvironment;
        this.medicalReady = medicalReady;
        this.adoptionReason = adoptionReason;
    }

    // ===== Getters & Setters =====

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getAdoptId() {
        return adoptId;
    }

    public void setAdoptId(int adoptId) {
        this.adoptId = adoptId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    
    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public AdopterBean getAdopter() {
        return adopter;
    }

    public void setAdopter(AdopterBean adopter) {
        this.adopter = adopter;
        if (adopter != null) {
            this.adoptId = adopter.getAdoptId();  // Keep FK ID in sync
        }
    }

    public PetBean getPet() {
        return pet;
    }

    public void setPet(PetBean pet) {
        this.pet = pet;
        if (pet != null) {
            this.petId = pet.getPetId();  // Keep FK ID in sync
        }
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
        if (staff != null) {
            this.staffId = staff.getStaffId();  // Keep FK ID in sync
        }
    }
    
    public String getApplicantName() {
    if (adopter != null) {
        return adopter.getAdoptFName() + " " + adopter.getAdoptLName();
    }
    return "";
}

    public String getPetName() {
        if (pet != null) {
            return pet.getPetName();
        }
        return "";
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getAppEligibility() {
        return appEligibility;
    }

    public void setAppEligibility(String appEligibility) {
        this.appEligibility = appEligibility;
    }

    public String getHasOwnedPet() {
        return hasOwnedPet;
    }

    public void setHasOwnedPet(String hasOwnedPet) {
        this.hasOwnedPet = hasOwnedPet;
    }

    public String getCaretakerInfo() {
        return caretakerInfo;
    }

    public void setCaretakerInfo(String caretakerInfo) {
        this.caretakerInfo = caretakerInfo;
    }

    public String getPetEnvironment() {
        return petEnvironment;
    }

    public void setPetEnvironment(String petEnvironment) {
        this.petEnvironment = petEnvironment;
    }

    public String getMedicalReady() {
        return medicalReady;
    }

    public void setMedicalReady(String medicalReady) {
        this.medicalReady = medicalReady;
    }

    public String getAdoptionReason() {
        return adoptionReason;
    }

    public void setAdoptionReason(String adoptionReason) {
        this.adoptionReason = adoptionReason;
    }


    
}
