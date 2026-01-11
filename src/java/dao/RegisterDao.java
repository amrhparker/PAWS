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

        try (Connection con = DBConnection.getConnection();
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
    // Update registration method with Double support
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
                    // Occupation - handle null
                    if (adopter.getAdoptOccupation() != null && !adopter.getAdoptOccupation().isEmpty()) {
                        ps.setString(7, adopter.getAdoptOccupation());
                    } else {
                        ps.setNull(7, Types.VARCHAR);
                    }

                    // Income - handle null for Double
                    Double income = adopter.getAdoptIncome();
                    if (income != null) {
                        ps.setDouble(8, income);
                    } else {
                        ps.setDouble(8, 0.0); // Default to 0.0 if null
                    }

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

    // Alternative simplified version if you want to always set 0.0 for null income
    public boolean registerAdopterSimplified(AdopterBean adopter) {
        boolean success = false;

        String sql;
        try (Connection con = DBConnection.getConnection()) {
            boolean hasNewColumns = hasNewColumns(con);

            if (hasNewColumns) {
                sql = "INSERT INTO ADOPTER (adopt_fname, adopt_lname, adopt_ic, adopt_phoneNum, "
                        + "adopt_email, adopt_address, adopt_occupation, adopt_income, "
                        + "adopt_username, adopt_password) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
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
                    // Set occupation or empty string
                    ps.setString(7, adopter.getAdoptOccupation() != null ? adopter.getAdoptOccupation() : "");

                    // Set income with default 0.0 if null
                    Double income = adopter.getAdoptIncome();
                    ps.setDouble(8, income != null ? income : 0.0);

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
