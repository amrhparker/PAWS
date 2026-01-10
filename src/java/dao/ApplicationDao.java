package dao;

import model.*;
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
            ps.setObject(3, null, Types.INTEGER); // staff not assigned yet

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

    /* ================= READ BY ADOPTER ================= */
    public List<ApplicationBean> getApplicationsByAdopter(int adoptId) throws SQLException {
        List<ApplicationBean> list = new ArrayList<>();
        String sql = "SELECT a.*, "
                   + "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, "
                   + "p.PET_NAME "
                   + "FROM APPLICATION a "
                   + "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID "
                   + "JOIN PET p ON a.PET_ID = p.PET_ID "
                   + "WHERE a.ADOPT_ID = ? "
                   + "ORDER BY a.APP_ID DESC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adoptId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapApplication(rs));
            }
        }
        return list;
    }

    /* ================= READ ALL (STAFF) ================= */
    public List<ApplicationBean> getAllApplications() throws SQLException {
        List<ApplicationBean> list = new ArrayList<>();
        String sql = "SELECT a.*, "
                   + "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, "
                   + "p.PET_NAME "
                   + "FROM APPLICATION a "
                   + "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID "
                   + "JOIN PET p ON a.PET_ID = p.PET_ID "
                   + "ORDER BY a.APP_DATE DESC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapApplication(rs));
            }
        }
        return list;
    }

    /* ================= READ BY APP ID ================= */
    public ApplicationBean getApplicationById(int appId) throws SQLException {
        String sql = "SELECT a.*, "
                   + "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, "
                   + "p.PET_NAME "
                   + "FROM APPLICATION a "
                   + "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID "
                   + "JOIN PET p ON a.PET_ID = p.PET_ID "
                   + "WHERE a.APP_ID = ?";

        ApplicationBean app = null;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, appId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                app = mapApplication(rs);
            }
        }
        return app;
    }

    /* ================= UPDATE STATUS ================= */
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

    /* ================= HELPER ================= */
    private ApplicationBean mapApplication(ResultSet rs) throws SQLException {
        ApplicationBean app = new ApplicationBean();

        app.setAppId(rs.getInt("APP_ID"));
        app.setAdoptId(rs.getInt("ADOPT_ID"));
        app.setPetId(rs.getInt("PET_ID"));
        app.setStaffId(rs.getInt("STAFF_ID"));

        app.setAppDate(rs.getDate("APP_DATE"));
        app.setAppStatus(rs.getString("APP_STATUS"));
        app.setAppEligibility(rs.getString("APP_ELIGIBILITY"));
        app.setHasOwnedPet(rs.getString("HAS_OWNED_PET"));
        app.setCaretakerInfo(rs.getString("CARETAKER_INFO"));
        app.setPetEnvironment(rs.getString("PET_ENVIRONMENT"));
        app.setMedicalReady(rs.getString("MEDICAL_READY"));
        app.setAdoptionReason(rs.getString("ADOPTION_REASON"));

        // ===== Adopter =====
        AdopterBean adopter = new AdopterBean();
        adopter.setAdoptId(rs.getInt("ADOPT_ID"));
        adopter.setAdoptFName(rs.getString("ADOPT_FNAME"));
        adopter.setAdoptLName(rs.getString("ADOPT_LNAME"));
        adopter.setAdoptPhoneNum(rs.getString("ADOPT_PHONENUM"));
        adopter.setAdoptAddress(rs.getString("ADOPT_ADDRESS"));
        app.setAdopter(adopter);

        // ===== Pet =====
        PetBean pet = new PetBean();
        pet.setPetId(rs.getInt("PET_ID"));
        pet.setPetName(rs.getString("PET_NAME"));
        app.setPet(pet);

        return app;
    }
}
