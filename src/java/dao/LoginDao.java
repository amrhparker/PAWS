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
             "SELECT * FROM ADOPTER WHERE adopt_username=? AND adopt_password=?"
         )) {

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            adopter = new AdopterBean();

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
                staff.setStaffId(rs.getInt("staff_id"));
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
