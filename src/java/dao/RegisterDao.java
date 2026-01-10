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
    public boolean registerAdopter(AdopterBean adopter) {
        boolean success = false;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO ADOPTER (adopt_fname, adopt_lname, adopt_ic, adopt_phoneNum, adopt_email, adopt_address, adopt_username, adopt_password) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, adopter.getAdoptFName());
            ps.setString(2, adopter.getAdoptLName());
            ps.setString(3, adopter.getAdoptIC());
            ps.setInt(4, adopter.getAdoptPhoneNum());
            ps.setString(5, adopter.getAdoptEmail());
            ps.setString(6, adopter.getAdoptAddress());
            ps.setString(7, adopter.getAdoptUsername());
            ps.setString(8, adopter.getAdoptPassword());

            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;

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

    // Validate phone number format (10 digits)
    public boolean isValidPhoneNumber(String phone) {
        if(phone != null && phone.matches("\\d{10}")){
            return true;
        }else{
            return false;
        }
    }
}
