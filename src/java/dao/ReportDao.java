package dao;

import model.RecordBean;
import model.ReportBean;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {

    // FILTER RECORDS (USED WHEN GENERATE REPORT)
    public List<RecordBean> getRecordsByFilter(
            String reportType,
            String fromDate,
            String toDate,
            String petType) {

        List<RecordBean> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        boolean isApplicationReport = reportType.contains("Applications");

        if (isApplicationReport) {

            sql.append(
                "SELECT rec.RECORD_ID, rec.RECORD_STATUS, rec.RECORD_DATE, " +
                "a.APP_ID, a.APP_DATE, a.APP_STATUS, " +
                "ad.ADOPT_FNAME, ad.ADOPT_LNAME, " +
                "p.PET_NAME, p.PET_SPECIES " +
                "FROM APPLICATION a " +
                "LEFT JOIN RECORD rec ON a.APP_ID = rec.APP_ID " +
                "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
                "JOIN PET p ON a.PET_ID = p.PET_ID " +
                "WHERE 1=1 "
            );

            if (reportType.contains("Pending")) {
                sql.append("AND a.APP_STATUS = 'Pending' ");
            } else if (reportType.contains("Approved")) {
                sql.append("AND a.APP_STATUS = 'Approved' ");
            } else {
                sql.append("AND a.APP_STATUS = 'Rejected' ");
            }

            if (fromDate != null && !fromDate.isEmpty()) {
                sql.append("AND a.APP_DATE >= ? ");
            }
            if (toDate != null && !toDate.isEmpty()) {
                sql.append("AND a.APP_DATE <= ? ");
            }

        } else {

            sql.append(
                "SELECT rec.RECORD_ID, rec.RECORD_STATUS, rec.RECORD_DATE, " +
                "a.APP_ID, a.APP_DATE, " +
                "ad.ADOPT_FNAME, ad.ADOPT_LNAME, " +
                "p.PET_NAME, p.PET_SPECIES " +
                "FROM RECORD rec " +
                "JOIN APPLICATION a ON rec.APP_ID = a.APP_ID " +
                "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
                "JOIN PET p ON a.PET_ID = p.PET_ID " +
                "WHERE 1=1 "
            );

            if (reportType.contains("Pending")) {
                sql.append("AND rec.RECORD_STATUS = 'Pending' ");
            } else {
                sql.append("AND rec.RECORD_STATUS = 'Completed' ");
            }

            if (fromDate != null && !fromDate.isEmpty()) {
                sql.append("AND rec.RECORD_DATE >= ? ");
            }
            if (toDate != null && !toDate.isEmpty()) {
                sql.append("AND rec.RECORD_DATE <= ? ");
            }
        }

        if (!"ALL".equalsIgnoreCase(petType)) {
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
            if (!"ALL".equalsIgnoreCase(petType)) {
                ps.setString(index++, petType);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                RecordBean r = new RecordBean();

                int recordId = rs.getInt("RECORD_ID");
                if (rs.wasNull()) {
                    r.setRecordId(-1);
                    r.setRecordStatus("Rejected");
                } else {
                    r.setRecordId(recordId);
                    r.setRecordStatus(rs.getString("RECORD_STATUS"));
                    r.setRecordDate(rs.getDate("RECORD_DATE"));
                }

                r.setAppId(rs.getInt("APP_ID"));
                r.setAppDate(rs.getDate("APP_DATE"));
                r.setAdopterName(
                        rs.getString("ADOPT_FNAME") + " " +
                        rs.getString("ADOPT_LNAME")
                );
                r.setPetName(rs.getString("PET_NAME"));
                r.setPetSpecies(rs.getString("PET_SPECIES"));

                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // CREATE REPORT
    public int createReport(
            String reportType,
            String fromDate,
            String toDate,
            String petType,
            List<RecordBean> records) {

        int reportId = 0;

        String insertReport =
            "INSERT INTO REPORT " +
            "(REPORT_TYPE, REPORT_DATE, TOTAL_COUNT, FROM_DATE, TO_DATE, PET_TYPE) " +
            "VALUES (?, CURRENT_DATE, ?, ?, ?, ?)";

        String insertRR =
            "INSERT INTO REPORT_RECORD (REPORT_ID, RECORD_ID) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement ps =
                conn.prepareStatement(insertReport, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, reportType);
            ps.setInt(2, records.size());

            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(3, Date.valueOf(fromDate));
            } else {
                ps.setNull(3, Types.DATE);
            }

            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(4, Date.valueOf(toDate));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, petType);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                reportId = rs.getInt(1);
            }

            PreparedStatement ps2 = conn.prepareStatement(insertRR);

            for (RecordBean r : records) {
                if (r.getRecordId() > 0) {
                    ps2.setInt(1, reportId);
                    ps2.setInt(2, r.getRecordId());
                    ps2.executeUpdate();
                }
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportId;
    }

    // GET ALL REPORTS
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

    // GET REPORT BY ID
    public ReportBean getReportById(int reportId) {

        ReportBean report = null;

        String sql =
            "SELECT REPORT_ID, REPORT_TYPE, REPORT_DATE, TOTAL_COUNT, " +
            "FROM_DATE, TO_DATE, PET_TYPE " +
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
                report.setFromDate(rs.getDate("FROM_DATE"));
                report.setToDate(rs.getDate("TO_DATE"));
                report.setPetType(rs.getString("PET_TYPE"));
                report.setTotalCount(rs.getInt("TOTAL_COUNT"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    // GET REPORT DETAILS (USED WHEN VIEW REPORT)
    public List<RecordBean> getReportDetails(int reportId) {

        List<RecordBean> list = new ArrayList<>();

        String reportType;
        Date fromDate;
        Date toDate;
        String petType;

        String metaSql =
            "SELECT REPORT_TYPE, FROM_DATE, TO_DATE, PET_TYPE " +
            "FROM REPORT WHERE REPORT_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(metaSql)) {

            ps.setInt(1, reportId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return list;

            reportType = rs.getString("REPORT_TYPE");
            fromDate = rs.getDate("FROM_DATE");
            toDate = rs.getDate("TO_DATE");
            petType = rs.getString("PET_TYPE");

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        //APPLICATION REPORT
        if (reportType.contains("Applications")) {

            StringBuilder sql = new StringBuilder(
                "SELECT a.APP_ID, a.APP_DATE, a.APP_STATUS, " +
                "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, " +
                "p.PET_NAME, p.PET_SPECIES, p.PET_BREED " +
                "FROM APPLICATION a " +
                "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
                "JOIN PET p ON a.PET_ID = p.PET_ID " +
                "WHERE a.APP_STATUS = ? "
            );

            if (fromDate != null && toDate != null) {
                sql.append("AND a.APP_DATE BETWEEN ? AND ? ");
            }

            if (!"ALL".equalsIgnoreCase(petType)) {
                sql.append("AND p.PET_SPECIES = ? ");
            }

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql.toString())) {

                int index = 1;

                if (reportType.contains("Pending")) {
                    ps.setString(index++, "Pending");
                } else if (reportType.contains("Approved")) {
                    ps.setString(index++, "Approved");
                } else {
                    ps.setString(index++, "Rejected");
                }

                if (fromDate != null && toDate != null) {
                    ps.setDate(index++, fromDate);
                    ps.setDate(index++, toDate);
                }

                if (!"ALL".equalsIgnoreCase(petType)) {
                    ps.setString(index++, petType);
                }

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    RecordBean r = new RecordBean();
                    r.setAppId(rs.getInt("APP_ID"));
                    r.setRecordStatus(rs.getString("APP_STATUS"));
                    r.setAppDate(rs.getDate("APP_DATE"));
                    r.setAdopterName(
                        rs.getString("ADOPT_FNAME") + " " +
                        rs.getString("ADOPT_LNAME")
                    );
                    r.setAdopterPhone(rs.getString("ADOPT_PHONENUM"));
                    r.setPetName(rs.getString("PET_NAME"));
                    r.setPetSpecies(rs.getString("PET_SPECIES"));
                    r.setPetBreed(rs.getString("PET_BREED"));
                    list.add(r);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return list;
        }

        //ADOPTION RECORD REPORT
        String sql =
            "SELECT rec.RECORD_ID, rec.RECORD_STATUS, rec.RECORD_DATE, " +
            "a.APP_ID, a.APP_DATE, ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, " +
            "p.PET_NAME, p.PET_SPECIES, p.PET_BREED " +
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
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setRecordStatus(rs.getString("RECORD_STATUS"));
                r.setRecordDate(rs.getDate("RECORD_DATE"));
                r.setAppId(rs.getInt("APP_ID"));
                r.setAppDate(rs.getDate("APP_DATE"));
                r.setAdopterName(
                    rs.getString("ADOPT_FNAME") + " " +
                    rs.getString("ADOPT_LNAME")
                );
                r.setAdopterPhone(rs.getString("ADOPT_PHONENUM"));
                r.setPetName(rs.getString("PET_NAME"));
                r.setPetSpecies(rs.getString("PET_SPECIES"));
                r.setPetBreed(rs.getString("PET_BREED"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // DELETE REPORT
    public void deleteReport(int reportId) {

        String deleteRR = "DELETE FROM REPORT_RECORD WHERE REPORT_ID = ?";
        String deleteReport = "DELETE FROM REPORT WHERE REPORT_ID = ?";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(deleteRR);
            ps1.setInt(1, reportId);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(deleteReport);
            ps2.setInt(1, reportId);
            ps2.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
