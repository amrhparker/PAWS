package dao;

import java.sql.*;
import model.AdopterBean;
import model.StaffBean;
import util.DBConnection;

public class LoginDao {

    public AdopterBean getAdopter(String username, String password) {
        AdopterBean adopter = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM ADOPTER WHERE adopt_username=? AND adopt_password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                adopter = new AdopterBean();
                adopter.setAdoptUsername(rs.getString("username"));
                adopter.setAdoptPassword(rs.getString("password"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adopter; 
    }

    public StaffBean getStaff(String username, String password) {
        StaffBean staff = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM STAFF WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                staff = new StaffBean();
                staff.setStaffUsername(rs.getString("username"));
                staff.setStaffPassword(rs.getString("password"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staff; 
    }
}
