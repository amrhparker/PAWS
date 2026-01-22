package model;

import java.sql.Date;

public class ReportBean {

    private int reportId;
    private String reportType;
    private Date reportDate;
    private Date fromDate;
    private Date toDate;
    private String petType;   
    private int totalCount;

    public int getReportId() {
        return reportId;
    }
    public void setReportId(int reportId) {
        this.reportId = reportId;
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

    public Date getFromDate() {
        return fromDate;
    }
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getPetType() {
        return petType;
    }
    public void setPetType(String petType) {
        this.petType = petType;
    }

    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
