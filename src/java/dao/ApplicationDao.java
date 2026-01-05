package dao;

import model.ApplicationBean;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {

    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";

    /* ================= CREATE ================= */
    public void insertApplication(ApplicationBean app) throws SQLException {

        String sql = "INSERT INTO APPLICATION "
                   + "(ADOPT_ID, PET_ID, STAFF_ID, "
                   + "APP_STATUS, APP_ELIGIBILITY, "
                   + "HAS_OWNED_PET, CARETAKER_INFO, PET_ENVIRONMENT, MEDICAL_READY, ADOPTION_REASON) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, app.getAdoptId());
            ps.setInt(2, app.getPetId());

            if (app.getStaffId() == 0)
                ps.setNull(3, Types.INTEGER);
            else
                ps.setInt(3, app.getStaffId());

            ps.setString(4, app.getAppStatus());
            ps.setString(5, app.getAppEligibility());
            ps.setString(6, app.getHasOwnedPet());
            ps.setString(7, app.getCaretakerInfo());
            ps.setString(8, app.getPetEnvironment());
            ps.setString(9, app.getMedicalReady());
            ps.setString(10, app.getAdoptionReason());

            ps.executeUpdate();
        }
    }

    /* ================= READ (ALL) ================= */
    public List<ApplicationBean> getAllApplications() throws SQLException {

        List<ApplicationBean> list = new ArrayList<>();
        String sql = "SELECT * FROM APPLICATION";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /* ================= READ (BY ID) ================= */
    public ApplicationBean getApplicationById(int appId) throws SQLException {

        String sql = "SELECT * FROM APPLICATION WHERE APP_ID=?";
        ApplicationBean app = null;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, appId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                app = mapResultSet(rs);
            }
        }
        return app;
    }

    /* ================= UPDATE (FULL) ================= */
    public void updateApplication(ApplicationBean app) throws SQLException {

        String sql = "UPDATE APPLICATION SET "
                   + "ADOPT_ID=?, PET_ID=?, STAFF_ID=?, "
                   + "APP_STATUS=?, APP_ELIGIBILITY=?, "
                   + "HAS_OWNED_PET=?, CARETAKER_INFO=?, "
                   + "PET_ENVIRONMENT=?, MEDICAL_READY=?, ADOPTION_REASON=? "
                   + "WHERE APP_ID=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, app.getAdoptId());
            ps.setInt(2, app.getPetId());

            if (app.getStaffId() == 0)
                ps.setNull(3, Types.INTEGER);
            else
                ps.setInt(3, app.getStaffId());

            ps.setString(4, app.getAppStatus());
            ps.setString(5, app.getAppEligibility());
            ps.setString(6, app.getHasOwnedPet());
            ps.setString(7, app.getCaretakerInfo());
            ps.setString(8, app.getPetEnvironment());
            ps.setString(9, app.getMedicalReady());
            ps.setString(10, app.getAdoptionReason());
            ps.setInt(11, app.getAppId());

            ps.executeUpdate();
        }
    }

    /* ================= UPDATE (STATUS ONLY) ================= */
    public void updateStatus(int appId, String status, String eligibility) throws SQLException {

        String sql = "UPDATE APPLICATION SET APP_STATUS=?, APP_ELIGIBILITY=? WHERE APP_ID=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, eligibility);
            ps.setInt(3, appId);

            ps.executeUpdate();
        }
    }

    /* ================= DELETE ================= */
    public void deleteApplication(int appId) throws SQLException {

        String sql = "DELETE FROM APPLICATION WHERE APP_ID=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, appId);
            ps.executeUpdate();
        }
    }

    /* ================= HELPER (ResultSet → Bean) ================= */
    private ApplicationBean mapResultSet(ResultSet rs) throws SQLException {

        ApplicationBean app = new ApplicationBean();

        app.setAppId(rs.getInt("APP_ID"));
        app.setAdoptId(rs.getInt("ADOPT_ID"));
        app.setPetId(rs.getInt("PET_ID"));
        app.setStaffId(rs.getInt("STAFF_ID"));

        app.setAppStatus(rs.getString("APP_STATUS"));
        app.setAppEligibility(rs.getString("APP_ELIGIBILITY"));
        app.setHasOwnedPet(rs.getString("HAS_OWNED_PET"));
        app.setCaretakerInfo(rs.getString("CARETAKER_INFO"));
        app.setPetEnvironment(rs.getString("PET_ENVIRONMENT"));
        app.setMedicalReady(rs.getString("MEDICAL_READY"));
        app.setAdoptionReason(rs.getString("ADOPTION_REASON"));

        return app;
    }
}
