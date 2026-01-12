package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {

    public boolean hasApplied(int adopterId, int petId) {
        String sql = "SELECT COUNT(*) FROM APPLICATION WHERE ADOPT_ID=? AND PET_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adopterId);
            ps.setInt(2, petId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertApplication(ApplicationBean app) throws SQLException {

        String sql =
            "INSERT INTO APPLICATION " +
            "(ADOPT_ID, PET_ID, STAFF_ID, APP_STATUS, APP_ELIGIBILITY, " +
            "HAS_OWNED_PET, CARETAKER_INFO, PET_ENVIRONMENT, MEDICAL_READY, ADOPTION_REASON) " +
            "VALUES (?, ?, NULL, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, app.getAdopter().getAdoptId());
            ps.setInt(2, app.getPetId());
            ps.setString(3, app.getAppStatus());
            ps.setString(4, app.getAppEligibility());
            ps.setString(5, app.getHasOwnedPet());
            ps.setString(6, app.getCaretakerInfo());
            ps.setString(7, app.getPetEnvironment());
            ps.setString(8, app.getMedicalReady());
            ps.setString(9, app.getAdoptionReason());

            ps.executeUpdate();
        }
    }
    
    /* ================= READ BY ADOPTER ================= */
    public List<ApplicationBean> getApplicationsByAdopter(int adoptId) throws SQLException {
        List<ApplicationBean> list = new ArrayList<>();

        String sql =
            "SELECT a.*, " +
            "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, " +
            "p.PET_ID, p.PET_NAME, p.PET_DESC, p.PET_SPECIES, p.PET_GENDER, " +
            "p.PET_BREED, p.PET_AGE, p.PET_HEALTHSTATUS, p.PET_ADOPTIONSTATUS " +
            "FROM APPLICATION a " +
            "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
            "JOIN PET p ON a.PET_ID = p.PET_ID " +
            "WHERE a.ADOPT_ID = ? " +
            "ORDER BY a.APP_ID DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adoptId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapApplication(rs));
            }
        }

        return list;
    }

    /* ================= READ ALL ================= */
    public List<ApplicationBean> getAllApplications() throws SQLException {
        List<ApplicationBean> list = new ArrayList<>();

        String sql =
            "SELECT a.*, " +
            "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, " +
            "p.PET_ID, p.PET_NAME, p.PET_DESC, p.PET_SPECIES, p.PET_GENDER, " +
            "p.PET_BREED, p.PET_AGE, p.PET_HEALTHSTATUS, p.PET_ADOPTIONSTATUS " +
            "FROM APPLICATION a " +
            "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
            "JOIN PET p ON a.PET_ID = p.PET_ID " +
            "ORDER BY a.APP_DATE DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapApplication(rs));
            }
        }

        return list;
    }

    /* ================= READ BY APP ID ================= */
    public ApplicationBean getApplicationById(int appId) throws SQLException {
        String sql =
            "SELECT a.*, " +
            "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, " +
            "p.PET_ID, p.PET_NAME, p.PET_DESC, p.PET_SPECIES, p.PET_GENDER, " +
            "p.PET_BREED, p.PET_AGE, p.PET_HEALTHSTATUS, p.PET_ADOPTIONSTATUS " +
            "FROM APPLICATION a " +
            "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
            "JOIN PET p ON a.PET_ID = p.PET_ID " +
            "WHERE a.APP_ID = ?";

        ApplicationBean app = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                app = mapApplication(rs);
            }
        }

        return app;
    }

    /* ================= UPDATE STATUS ================= */
    public void updateStatus(int appId, String status, String eligibility, int staffId)
        throws SQLException {

    String sql =
        "UPDATE APPLICATION " +
        "SET APP_STATUS=?, APP_ELIGIBILITY=?, STAFF_ID=? " +
        "WHERE APP_ID=?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, status);
        ps.setString(2, eligibility);
        ps.setInt(3, staffId);
        ps.setInt(4, appId);

        ps.executeUpdate();
    }
}


    /* ================= DELETE ================= */
    public void deleteApplication(int appId) throws SQLException {
        String sql = "DELETE FROM APPLICATION WHERE APP_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appId);
            ps.executeUpdate();
        }
    }

    /* ================= EMAIL INFO ================= */
    public String[] getAdopterEmailInfo(int appId) {
        String sql =
            "SELECT ad.ADOPT_EMAIL, ad.ADOPT_FNAME, ad.ADOPT_LNAME, p.PET_NAME " +
            "FROM APPLICATION a " +
            "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
            "JOIN PET p ON a.PET_ID = p.PET_ID " +
            "WHERE a.APP_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new String[]{
                    rs.getString("ADOPT_EMAIL"),
                    rs.getString("ADOPT_FNAME") + " " + rs.getString("ADOPT_LNAME"),
                    rs.getString("PET_NAME")
                };
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /* ================= NEW METHODS FOR APPROVAL ================= */

    // Check if a pet already has an approved application
    public boolean isPetAlreadyApproved(int petId) {
        boolean approved = false;
        String sql = "SELECT COUNT(*) FROM APPLICATION WHERE PET_ID = ? AND APP_STATUS = 'Approved'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                approved = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return approved;
    }

    // Reject all other applications for the same pet
    public void rejectOtherApplications(int petId, int approvedAppId) {
        String sql = "UPDATE APPLICATION SET APP_STATUS='Rejected', APP_ELIGIBILITY='N/A' " +
                     "WHERE PET_ID = ? AND APP_ID <> ? AND APP_STATUS='Pending'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ps.setInt(2, approvedAppId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get pet ID from application ID
    public int getPetIdByApplication(int appId) {
        int petId = -1;
        String sql = "SELECT PET_ID FROM APPLICATION WHERE APP_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                petId = rs.getInt("PET_ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return petId;
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
        pet.setPetDesc(rs.getString("PET_DESC"));
        pet.setPetSpecies(rs.getString("PET_SPECIES"));
        pet.setPetGender(rs.getString("PET_GENDER"));
        pet.setPetBreed(rs.getString("PET_BREED"));
        pet.setPetAge(rs.getInt("PET_AGE"));
        pet.setPetHealthStatus(rs.getString("PET_HEALTHSTATUS"));
        pet.setPetAdoptionStatus(rs.getString("PET_ADOPTIONSTATUS"));

        app.setPet(pet);

        return app;
    }
}
