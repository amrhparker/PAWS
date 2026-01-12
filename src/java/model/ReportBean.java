package model;

import java.sql.Date;

public class ReportBean {

    private int reportId;
    private String reportType;
    private Date reportDate;
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

    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
