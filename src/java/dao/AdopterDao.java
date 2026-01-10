package dao;

import java.sql.*;
import java.util.*;
import model.AdopterBean;

public class AdopterDao {
    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";

    // SQL Queries
    private static final String insertAdopterSql
            = "INSERT INTO ADOPTER (ADOPT_FNAME, ADOPT_LNAME, ADOPT_IC, ADOPT_PHONENUM, "
            + "ADOPT_EMAIL, ADOPT_ADDRESS, ADOPT_OCCUPATION, ADOPT_INCOME, "
            + "ADOPT_USERNAME, ADOPT_PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String updateAdopterSql
            = "UPDATE ADOPTER SET ADOPT_FNAME=?, ADOPT_LNAME=?, ADOPT_IC=?, ADOPT_PHONENUM=?, "
            + "ADOPT_EMAIL=?, ADOPT_ADDRESS=?, ADOPT_OCCUPATION=?, ADOPT_INCOME=?, "
            + "ADOPT_USERNAME=?, ADOPT_PASSWORD=? WHERE ADOPT_ID=?";

    private static final String deleteAdopterSql
            = "DELETE FROM ADOPTER WHERE ADOPT_ID=?";

    private static final String selectAdopterAllSql
            = "SELECT * FROM ADOPTER";

    private static final String selectAdopterByIdSql
            = "SELECT * FROM ADOPTER WHERE ADOPT_ID=?";

    private static final String selectAdopterByUnSql
            = "SELECT * FROM ADOPTER WHERE ADOPT_USERNAME=?";

    private static final String validateAdopterSql
            = "SELECT * FROM ADOPTER WHERE ADOPT_USERNAME=? AND ADOPT_PASSWORD=?";
    
    private static final String checkUsernameExistsSql = 
            "SELECT COUNT(*) FROM ADOPTER WHERE ADOPT_USERNAME = ?";

    // Insert
    public boolean insertAdopter(AdopterBean adopter) {
        boolean success = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(insertAdopterSql)) {

            ps.setString(1, adopter.getAdoptFName());
            ps.setString(2, adopter.getAdoptLName());
            ps.setString(3, adopter.getAdoptIC());
            ps.setString(4, adopter.getAdoptPhoneNum());
            ps.setString(5, adopter.getAdoptEmail());
            ps.setString(6, adopter.getAdoptAddress());
            ps.setString(7, adopter.getAdoptOccupation());  
            ps.setDouble(8, adopter.getAdoptIncome());
            ps.setString(9, adopter.getAdoptUsername());
            ps.setString(10, adopter.getAdoptPassword());

            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Update - FIXED VERSION (using the correct signature with all fields)
    public boolean updateAdopter(AdopterBean adopter) {
        boolean success = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(updateAdopterSql)) {

            ps.setString(1, adopter.getAdoptFName());
            ps.setString(2, adopter.getAdoptLName());
            ps.setString(3, adopter.getAdoptIC());
            ps.setString(4, adopter.getAdoptPhoneNum());
            ps.setString(5, adopter.getAdoptEmail());
            ps.setString(6, adopter.getAdoptAddress());
            ps.setString(7, adopter.getAdoptOccupation());  
            ps.setDouble(8, adopter.getAdoptIncome());  
            ps.setString(9, adopter.getAdoptUsername());
            ps.setString(10, adopter.getAdoptPassword());
            ps.setInt(11, adopter.getAdoptId());

            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Delete
    public boolean deleteAdopter(int adoptId) {
        boolean success = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(deleteAdopterSql)) {

            ps.setInt(1, adoptId);
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Get by ID
    public AdopterBean getAdopterById(int adoptId) throws SQLException {

    String sql = "SELECT * FROM ADOPTER WHERE ADOPT_ID=?";

    AdopterBean adopter = null;

    try (Connection con = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, adoptId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            adopter = new AdopterBean();
            adopter.setAdoptId(rs.getInt("ADOPT_ID"));
            adopter.setAdoptFName(rs.getString("ADOPT_FNAME"));
            adopter.setAdoptLName(rs.getString("ADOPT_LNAME"));
            adopter.setAdoptPhoneNum(rs.getString("ADOPT_PHONENUM"));
            adopter.setAdoptIC(rs.getString("ADOPT_IC"));
            adopter.setAdoptAddress(rs.getString("ADOPT_ADDRESS"));
            adopter.setAdoptOccupation(rs.getString("ADOPT_OCCUPATION"));
            adopter.setAdoptIncome(rs.getDouble("ADOPT_INCOME"));
        }
    }
    return adopter;
}


    // Validate login
    public AdopterBean validateAdopter(String un, String pw) {
        AdopterBean adopter = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(validateAdopterSql)) {

            ps.setString(1, un);
            ps.setString(2, pw);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    adopter = mapResultSetToAdopter(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adopter;
    }
    
    // Check if username exists
    public boolean checkUsernameExists(String username) {
        boolean exists = false;
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(checkUsernameExistsSql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    // Map ResultSet to AdopterBean
    private AdopterBean mapResultSetToAdopter(ResultSet rs) throws SQLException {
        AdopterBean adopter = new AdopterBean();
        adopter.setAdoptId(rs.getInt("ADOPT_ID"));
        adopter.setAdoptFName(rs.getString("ADOPT_FNAME"));
        adopter.setAdoptLName(rs.getString("ADOPT_LNAME"));
        adopter.setAdoptIC(rs.getString("ADOPT_IC"));
        adopter.setAdoptPhoneNum(rs.getString("ADOPT_PHONENUM"));
        adopter.setAdoptEmail(rs.getString("ADOPT_EMAIL"));
        adopter.setAdoptAddress(rs.getString("ADOPT_ADDRESS"));
        adopter.setAdoptOccupation(rs.getString("ADOPT_OCCUPATION"));
        adopter.setAdoptIncome(rs.getDouble("ADOPT_INCOME"));  
        adopter.setAdoptUsername(rs.getString("ADOPT_USERNAME"));
        adopter.setAdoptPassword(rs.getString("ADOPT_PASSWORD"));
        return adopter;
    }
    
    public boolean updateAdopterApplicationInfo(AdopterBean adopter) {
    boolean success = false;

    String sql = "UPDATE ADOPTER SET ADOPT_OCCUPATION=?, ADOPT_INCOME=? WHERE ADOPT_ID=?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, adopter.getAdoptOccupation());
        ps.setDouble(2, adopter.getAdoptIncome());
        ps.setInt(3, adopter.getAdoptId());

        success = ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return success;
}

}