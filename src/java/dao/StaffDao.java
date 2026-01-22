
package dao;

import util.DBConnection;
import java.sql.*;
import model.StaffBean;

public class StaffDao {

    private static final String authenticateStaffSQL
            = "SELECT * FROM Staff WHERE staffUsername = ? AND staffPassword = ?";

    private static final String getStaffByUsernameSQL
            = "SELECT * FROM Staff WHERE staffUsername = ?";

    private static final String getStaffByIdSQL
            = "SELECT * FROM Staff WHERE staffId = ?";

    private static final String verifyPasswordSQL
            = "SELECT staffId FROM Staff WHERE staffUsername = ? AND staffPassword = ?";

    //check 
    public StaffBean authenticateStaff(String un, String pw) {
        StaffBean staff = new StaffBean();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(authenticateStaffSQL)) {
            st.setString(1, un);
            st.setString(2, pw);
            
            try(ResultSet rs = st.executeQuery()){
                if (rs.next()) {
                    return mapResultSetToBean(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }
    
    //retrieve staffBean by given ID
    public StaffBean getStaffById(int staffId) {
        StaffBean staff = new StaffBean();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(getStaffByIdSQL)) {

            st.setInt(1, staffId);
            try(ResultSet rs = st.executeQuery()){
                if (rs.next()) {
                    return mapResultSetToBean(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    //retrieve staffBean by given username
    public StaffBean getStaffByUsername(String un) {
        StaffBean staff = new StaffBean();

        try(Connection conn = DBConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(getStaffByUsernameSQL)){
            st.setString(1, un);

            try(ResultSet rs = st.executeQuery()){
                if (rs.next()) {
                    return mapResultSetToBean(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    //verify Staff's password for login
    public boolean verifyPassword(String username, String password) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(verifyPasswordSQL)) {
            st.setString(1, username);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //map ResultSet to StaffBean after validation
    private StaffBean mapResultSetToBean(ResultSet rs) throws SQLException {
        StaffBean staff = new StaffBean();
        staff.setStaffId(rs.getInt("staffid"));
        staff.setStaffFname(rs.getString("stafffname"));
        staff.setStaffLname(rs.getString("stafflname"));
        staff.setStaffEmail(rs.getString("staffemail"));
        staff.setStaffPhoneNum(rs.getInt("staffphonenum"));
        staff.setStaffUsername(rs.getString("staffusername"));
        staff.setStaffPassword(rs.getString("staffpassword"));
        return staff;
    }
}
