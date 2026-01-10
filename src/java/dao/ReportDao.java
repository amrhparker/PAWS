package dao;

import model.ReportBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {

    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";

    //INSERT 
    public void insertReport(ReportBean report) {

        String sql = "INSERT INTO REPORT (RECORD_ID, STAFF_ID, REPORT_TYPE, REPORT_DATE) " +
                     "VALUES (?, ?, ?, CURRENT_DATE)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, report.getRecordId());
            ps.setInt(2, report.getStaffId());
            ps.setString(3, report.getReportType());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GET ALL 
    public List<ReportBean> getAllReports() {

        List<ReportBean> list = new ArrayList<>();
        String sql = "SELECT * FROM REPORT ORDER BY REPORT_DATE DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReportBean r = new ReportBean();
                r.setReportId(rs.getInt("REPORT_ID"));
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setStaffId(rs.getInt("STAFF_ID"));
                r.setReportType(rs.getString("REPORT_TYPE"));
                r.setReportDate(rs.getDate("REPORT_DATE"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //GET BY ID
    public ReportBean getReportById(int reportId) {

        String sql = "SELECT * FROM REPORT WHERE REPORT_ID = ?";
        ReportBean r = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reportId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new ReportBean();
                r.setReportId(rs.getInt("REPORT_ID"));
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setStaffId(rs.getInt("STAFF_ID"));
                r.setReportType(rs.getString("REPORT_TYPE"));
                r.setReportDate(rs.getDate("REPORT_DATE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }
}
