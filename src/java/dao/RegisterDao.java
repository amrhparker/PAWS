/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import model.AdopterBean;
import java.sql.*;
import util.DBConnection;

/**
 *
 * @author amira
 */
public class RegisterDao {
    
    private boolean hasNewColumns(Connection con) throws SQLException {
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet columns = meta.getColumns(null, null, "ADOPTER", "ADOPT_OCCUPATION");
            return columns.next();
        } catch (SQLException e) {
            return false;
        }
    }
    //check if username exists first
    public boolean checkUsernameExists(String username) {
        boolean exists = false;

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                        "SELECT COUNT(*) FROM ADOPTER WHERE adopt_username = ?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    //then, register new adopter
    // Update registration method
    public boolean registerAdopter(AdopterBean adopter) {
        boolean success = false;

        String sql;
        try (Connection con = DBConnection.getConnection()) {
            // Check if table has new columns
            boolean hasNewColumns = hasNewColumns(con);

            if (hasNewColumns) {
                sql = "INSERT INTO ADOPTER (adopt_fname, adopt_lname, adopt_ic, adopt_phoneNum, "
                        + "adopt_email, adopt_address, adopt_occupation, adopt_income, "
                        + "adopt_username, adopt_password) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                // Fallback to old structure if columns don't exist
                sql = "INSERT INTO ADOPTER (adopt_fname, adopt_lname, adopt_ic, adopt_phoneNum, "
                        + "adopt_email, adopt_address, adopt_username, adopt_password) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, adopter.getAdoptFName());
                ps.setString(2, adopter.getAdoptLName());
                ps.setString(3, adopter.getAdoptIC());
                ps.setString(4, adopter.getAdoptPhoneNum());
                ps.setString(5, adopter.getAdoptEmail());
                ps.setString(6, adopter.getAdoptAddress());

                if (hasNewColumns) {
                    ps.setString(7, adopter.getAdoptOccupation() != null ? adopter.getAdoptOccupation() : "");
                    ps.setBigDecimal(8, adopter.getAdoptIncome() != null ? adopter.getAdoptIncome() : java.math.BigDecimal.ZERO);
                    ps.setString(9, adopter.getAdoptUsername());
                    ps.setString(10, adopter.getAdoptPassword());
                } else {
                    ps.setString(7, adopter.getAdoptUsername());
                    ps.setString(8, adopter.getAdoptPassword());
                }

                int rowsAffected = ps.executeUpdate();
                success = rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
    
    //check if IC number exists
    public boolean checkICExists(String ic) {
        boolean exists = false;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT COUNT(*) FROM ADOPTER WHERE adopt_ic = ?")) {

            ps.setString(1, ic);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
    
}