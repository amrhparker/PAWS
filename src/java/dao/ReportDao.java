/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.ReportBean;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Acer
 */
public class ReportDao {
    
    // =========================
    // INSERT REPORT
    // =========================
    public void insertReport(ReportBean report) {

        String sql = "INSERT INTO REPORT (RECORD_ID, STAFF_ID, REPORT_TYPE, REPORT_DATE) " +
                     "VALUES (?, ?, ?, CURRENT_DATE)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, report.getRecordId());
            ps.setInt(2, report.getStaffId());
            ps.setString(3, report.getReportType());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // GET ALL REPORTS
    // =========================
    public List<ReportBean> getAllReports() {

        List<ReportBean> list = new ArrayList<>();
        String sql = "SELECT * FROM REPORT ORDER BY REPORT_DATE DESC";

        try (Connection conn = DBConnection.getConnection();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // GET REPORT BY ID
    // =========================
    public ReportBean getReportById(int reportId) {

        ReportBean r = null;
        String sql = "SELECT * FROM REPORT WHERE REPORT_ID = ?";

        try (Connection conn = DBConnection.getConnection();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    // =========================
    // DELETE REPORT
    // =========================
    public void deleteReport(int reportId) {

        String sql = "DELETE FROM REPORT WHERE REPORT_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reportId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
