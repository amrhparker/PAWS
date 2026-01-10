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
             "SELECT adopt_id, adopt_username, adopt_password FROM ADOPTER WHERE adopt_username=? AND adopt_password=?"
         )) {

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            adopter = new AdopterBean();
            adopter.setAdoptId(rs.getInt("adopt_id")); 
            adopter.setAdoptUsername(rs.getString("adopt_username"));
            adopter.setAdoptPassword(rs.getString("adopt_password"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return adopter;
}

    public StaffBean getStaff(String username, String password) {
        StaffBean staff = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM STAFF WHERE staff_username=? AND staff_password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                staff = new StaffBean();
                staff.setStaffUsername(rs.getString("staff_username"));
                staff.setStaffPassword(rs.getString("staff_password"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staff; 
    }
}
