/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;
/**
 *
 * @author Acer
 */
public class ReportBean implements Serializable{
    
    private int reportId;
    private int recordId;
    private int staffId;
    private RecordBean record;
    private StaffBean staff;
    private String reportType;
    private Date reportDate;

   
    public ReportBean() 
    {}

    public ReportBean(int reportId, int recordId, int staffId,
                      String reportType, Date reportDate) {
        this.reportId = reportId;
        this.recordId = recordId;
        this.staffId = staffId;
        this.reportType = reportType;
        this.reportDate = reportDate;
    }

    //Getters & Setters

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public RecordBean getRecord() {
        return record;
    }

    public void setRecord(RecordBean record) {
        this.record = record;
        if (record != null) {
            this.recordId = record.getRecordId(); // keep FK in sync
        }
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
        if (staff != null) {
            this.staffId = staff.getStaffId(); // keep FK in sync
        }
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
    
}
