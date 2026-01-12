package dao;

import model.RecordBean;
import model.ApplicationBean;
import model.AdopterBean;
import model.PetBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {

    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";

    //INSERT
    public void insertRecord(RecordBean record) {

        String sql = "INSERT INTO RECORD (APP_ID, STAFF_ID, RECORD_DATE, RECORD_STATUS) VALUES (?, ?, CURRENT_DATE, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, record.getAppId());
            ps.setInt(2, record.getStaffId());
            ps.setString(3, record.getRecordStatus());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GET ALL
    public List<RecordBean> getAllRecords() {

        List<RecordBean> list = new ArrayList<>();
        String sql = "SELECT * FROM RECORD ORDER BY RECORD_DATE DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RecordBean r = new RecordBean();
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setAppId(rs.getInt("APP_ID"));
                r.setRecordDate(rs.getDate("RECORD_DATE"));
                r.setRecordStatus(rs.getString("RECORD_STATUS"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // GET RECORDS BY ADOPTER
public List<RecordBean> getRecordsByAdopter(int adoptId) {

    List<RecordBean> list = new ArrayList<>();

    String sql =
        "SELECT r.RECORD_ID, r.RECORD_DATE, r.RECORD_STATUS, " +
        "ad.ADOPT_FNAME, ad.ADOPT_LNAME, " +
        "p.PET_NAME " +
        "FROM RECORD r " +
        "JOIN APPLICATION a ON r.APP_ID = a.APP_ID " +
        "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
        "JOIN PET p ON a.PET_ID = p.PET_ID " +
        "WHERE ad.ADOPT_ID = ? " +
        "ORDER BY r.RECORD_DATE DESC";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, adoptId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            RecordBean r = new RecordBean();
            r.setRecordId(rs.getInt("RECORD_ID"));
            r.setRecordDate(rs.getDate("RECORD_DATE"));
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


    //GET BY ID 
    public RecordBean getRecordById(int recordId) {

    String sql =
        "SELECT r.RECORD_ID, r.RECORD_DATE, r.RECORD_STATUS, " +
        "a.APP_ID, a.APP_DATE, " +
        "ad.ADOPT_FNAME, ad.ADOPT_LNAME, ad.ADOPT_PHONENUM, ad.ADOPT_ADDRESS, " +
        "p.PET_NAME " +
        "FROM RECORD r " +
        "JOIN APPLICATION a ON r.APP_ID = a.APP_ID " +
        "JOIN ADOPTER ad ON a.ADOPT_ID = ad.ADOPT_ID " +
        "JOIN PET p ON a.PET_ID = p.PET_ID " +
        "WHERE r.RECORD_ID = ?";

    RecordBean record = null;

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, recordId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            record = new RecordBean();
            record.setRecordId(rs.getInt("RECORD_ID"));
            record.setRecordDate(rs.getDate("RECORD_DATE"));
            record.setRecordStatus(rs.getString("RECORD_STATUS"));

            // ===== BUILD APPLICATION =====
            ApplicationBean app = new ApplicationBean();
            app.setAppId(rs.getInt("APP_ID"));
            app.setAppDate(rs.getDate("APP_DATE"));

            // ===== BUILD ADOPTER =====
            AdopterBean adopter = new AdopterBean();
            adopter.setAdoptFName(rs.getString("ADOPT_FNAME"));
            adopter.setAdoptLName(rs.getString("ADOPT_LNAME"));
            adopter.setAdoptPhoneNum(rs.getString("ADOPT_PHONENUM"));
            adopter.setAdoptAddress(rs.getString("ADOPT_ADDRESS"));

            // ===== BUILD PET =====
            PetBean pet = new PetBean();
            pet.setPetName(rs.getString("PET_NAME"));

            // ===== LINK OBJECTS =====
            app.setAdopter(adopter);
            app.setPet(pet);
            record.setApplication(app);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return record;
}

    //DELETE 
    public void deleteRecord(int recordId) {

        String sql = "DELETE FROM RECORD WHERE RECORD_ID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recordId);
            ps.executeUpdate();

        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

//update record bila adoption complete
    public void completeRecord(int recordId, int staffId) {

        String sql = "UPDATE RECORD SET RECORD_STATUS = 'Completed', STAFF_ID=?" + "WHERE RECORD_ID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, staffId);
            ps.setInt(2, recordId);
            ps.executeUpdate();

        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
    
    public void updateRecordStatus(int recordId, String status) {

    String sql = "UPDATE RECORD SET RECORD_STATUS=? WHERE RECORD_ID=?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, status);
        ps.setInt(2, recordId);
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}
