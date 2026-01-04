/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

public class PetBean implements Serializable{

    private int petId;
    private String petName;
    private String petDesc;
    private String petSpecies;
    private String petGender;
    private String petBreed;
    private int petAge;
    private String petHealthStatus;
    private String petAdoptionStatus;

    public PetBean() {
    }
    
    public PetBean(int petId, String petName, String petDesc, String petSpecies, String petGender, String petBreed, int petAge, String petHealthStatus, String petAdoptionStatus) {
        this.petId = petId;
        this.petName = petName;
        this.petDesc = petDesc;
        this.petSpecies = petSpecies;
        this.petGender = petGender;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.petHealthStatus = petHealthStatus;
        this.petAdoptionStatus = petAdoptionStatus;
    }

    public int getPetId() {
        return petId;
    }
    public String getPetName() {
        return petName;
    }
    public String getPetDesc() {
        return petDesc;
    }
    public String getPetSpecies() {
        return petSpecies;
    }    
    public String getPetGender() {
        return petGender;
    }
    public String getPetBreed() {
        return petBreed;
    }
    public int getPetAge() {
        return petAge;
    }
    public String getPetHealthStatus() {
        return petHealthStatus;
    }
    public String getPetAdoptionStatus() {
        return petAdoptionStatus;
    }
    
    public void setPetId(int petId) {
        this.petId = petId;
    }    
    public void setPetName(String petName) {
        this.petName = petName;
    }
    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }
    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }
    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }
    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }
   public void setPetAge(int petAge) {
        this.petAge = petAge;
    }
  public void setPetHealthStatus(String petHealthStatus) {
        this.petHealthStatus = petHealthStatus;
    }
    public void setPetAdoptionStatus(String petAdoptionStatus) {
        this.petAdoptionStatus = petAdoptionStatus;
    }
}
