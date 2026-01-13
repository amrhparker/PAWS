package dao;

import model.RecordBean;
import model.ReportBean;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {

    // ================= GET RECORDS WITH FILTER =================
    public List<RecordBean> getRecordsByFilter(
        String reportType,
        String fromDate,
        String toDate,
        String petType) {

    List<RecordBean> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder();

    boolean isApplicationReport = reportType.contains("Applications");

    if (isApplicationReport) {

        // ================= APPLICATION REPORT =================
        sql.append(
            "SELECT rec.RECORD_ID, rec.RECORD_STATUS, " +
            "ad.ADOPT_FNAME, ad.ADOPT_LNAME, p.PET_NAME " +
            "FROM APPLICATION a " +
            "JOIN RECORD rec ON a.APP_ID = rec.APP_ID " +
            "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
            "JOIN PET p ON a.PET_ID = p.PET_ID " +
            "WHERE 1=1 "
        );

        if (reportType.contains("Pending")) {
            sql.append("AND a.APP_STATUS = 'Pending' ");
        } else if (reportType.contains("Approved")) {
            sql.append("AND a.APP_STATUS = 'Approved' ");
        } else if (reportType.contains("Rejected")) {
            sql.append("AND a.APP_STATUS = 'Rejected' ");
        }

    } else {

        // ================= ADOPTION REPORT =================
        sql.append(
            "SELECT rec.RECORD_ID, rec.RECORD_STATUS, " +
            "ad.ADOPT_FNAME, ad.ADOPT_LNAME, p.PET_NAME " +
            "FROM RECORD rec " +
            "JOIN APPLICATION a ON rec.APP_ID = a.APP_ID " +
            "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
            "JOIN PET p ON a.PET_ID = p.PET_ID " +
            "WHERE 1=1 "
        );

        if (reportType.contains("Pending")) {
            sql.append("AND rec.RECORD_STATUS = 'Pending' ");
        } else if (reportType.contains("Completed")) {
            sql.append("AND rec.RECORD_STATUS = 'Completed' ");
        }
    }

    // DATE FILTER
    if (fromDate != null && !fromDate.isEmpty()) {
        sql.append("AND rec.RECORD_DATE >= ? ");
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql.append("AND rec.RECORD_DATE <= ? ");
    }

    // PET TYPE
    if (!"ALL".equals(petType)) {
        sql.append("AND p.PET_SPECIES = ? ");
    }

    try (Connection conn = DBConnection.getConnection();
     PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        int index = 1;

        if (fromDate != null && !fromDate.isEmpty()) {
            ps.setDate(index++, Date.valueOf(fromDate));
        }
        if (toDate != null && !toDate.isEmpty()) {
            ps.setDate(index++, Date.valueOf(toDate));
        }
        if (!"ALL".equals(petType)) {
            ps.setString(index++, petType);
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            RecordBean r = new RecordBean();
            r.setRecordId(rs.getInt("RECORD_ID"));
            r.setRecordStatus(rs.getString("RECORD_STATUS"));
            r.setAdopterName(
                rs.getString("ADOPT_FNAME") + " " + rs.getString("ADOPT_LNAME")
            );
            r.setPetName(rs.getString("PET_NAME"));
            list.add(r);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}


    // ================= CREATE REPORT =================
    public int createReport(String reportType, List<RecordBean> records) {

        int reportId = 0;

        String insertReport =
            "INSERT INTO REPORT (REPORT_TYPE, REPORT_DATE, TOTAL_COUNT) " +
            "VALUES (?, CURRENT_DATE, ?)";

        String insertRR =
            "INSERT INTO REPORT_RECORD (REPORT_ID, RECORD_ID) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(insertReport, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reportType);
            ps.setInt(2, records.size());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                reportId = rs.getInt(1);
            }

            PreparedStatement ps2 = conn.prepareStatement(insertRR);
            for (RecordBean r : records) {
                ps2.setInt(1, reportId);
                ps2.setInt(2, r.getRecordId());
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportId;
    }

    // ================= LIST REPORTS =================
    public List<ReportBean> getAllReports() {

        List<ReportBean> list = new ArrayList<>();

        String sql =
            "SELECT REPORT_ID, REPORT_TYPE, REPORT_DATE, TOTAL_COUNT " +
            "FROM REPORT ORDER BY REPORT_ID DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReportBean r = new ReportBean();
                r.setReportId(rs.getInt("REPORT_ID"));
                r.setReportType(rs.getString("REPORT_TYPE"));
                r.setReportDate(rs.getDate("REPORT_DATE"));
                r.setTotalCount(rs.getInt("TOTAL_COUNT"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= VIEW REPORT DETAILS =================
public List<RecordBean> getReportDetails(int reportId) {

    List<RecordBean> list = new ArrayList<>();

    String sql =
        "SELECT rec.RECORD_ID, rec.RECORD_STATUS, rec.RECORD_DATE, " +
        "a.APP_DATE, " +
        "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, " +
        "p.PET_NAME, p.PET_SPECIES, p.PET_BREED, p.PET_AGE " +
        "FROM REPORT_RECORD rr " +
        "JOIN RECORD rec ON rr.RECORD_ID = rec.RECORD_ID " +
        "JOIN APPLICATION a ON rec.APP_ID = a.APP_ID " +
        "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
        "JOIN PET p ON a.PET_ID = p.PET_ID " +
        "WHERE rr.REPORT_ID = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, reportId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            RecordBean r = new RecordBean();

            // ===== RECORD =====
            r.setRecordId(rs.getInt("RECORD_ID"));
            r.setRecordStatus(rs.getString("RECORD_STATUS"));
            r.setRecordDate(rs.getDate("RECORD_DATE"));

            // ===== APPLICATION =====
            r.setAppDate(rs.getDate("APP_DATE"));

            // ===== ADOPTER =====
            r.setAdopterName(
                rs.getString("ADOPT_FNAME") + " " + rs.getString("ADOPT_LNAME")
            );
            r.setAdopterPhone(rs.getString("ADOPT_PHONENUM"));
            r.setAdopterAddress(rs.getString("ADOPT_ADDRESS"));

            // ===== PET =====
            r.setPetName(rs.getString("PET_NAME"));
            // kalau RecordBean belum ada field ni, tambah kemudian
            r.setPetSpecies(rs.getString("PET_SPECIES"));
            r.setPetBreed(rs.getString("PET_BREED"));
            r.setPetAge(rs.getInt("PET_AGE"));

            list.add(r);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    // ================= GET REPORT BY ID =================
public ReportBean getReportById(int reportId) {

    ReportBean report = null;

    String sql =
        "SELECT REPORT_ID, REPORT_TYPE, REPORT_DATE, TOTAL_COUNT " +
        "FROM REPORT WHERE REPORT_ID = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, reportId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            report = new ReportBean();
            report.setReportId(rs.getInt("REPORT_ID"));
            report.setReportType(rs.getString("REPORT_TYPE"));
            report.setReportDate(rs.getDate("REPORT_DATE"));
            report.setTotalCount(rs.getInt("TOTAL_COUNT"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return report;
}

public void deleteReport(int reportId) {

    String deleteReportRecord =
        "DELETE FROM REPORT_RECORD WHERE REPORT_ID = ?";

    String deleteReport =
        "DELETE FROM REPORT WHERE REPORT_ID = ?";

    try (Connection conn = DBConnection.getConnection()) {

        conn.setAutoCommit(false);

        try (PreparedStatement ps1 =
                 conn.prepareStatement(deleteReportRecord)) {
            ps1.setInt(1, reportId);
            ps1.executeUpdate();
        }

        try (PreparedStatement ps2 =
                 conn.prepareStatement(deleteReport)) {
            ps2.setInt(1, reportId);
            ps2.executeUpdate();
        }

        conn.commit();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
