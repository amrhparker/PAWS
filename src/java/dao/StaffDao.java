/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author amira
 */
import java.sql.*;
import model.StaffBean;

public class StaffDao {

    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";

    //sql queries
    //insert
    private static final String insertStaffSql
            = "INSERT INTO Staff (staffFname, staffLname, staffEmail, staffPhoneNum, staffUsername, staffPassword) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String updateStaffSql
            = "UPDATE Staff SET staffFname = ?, staffLname = ?, staffEmail = ?, staffPhoneNum = ?, staffUsername = ?, staffPassword = ? WHERE staffId = ?";

    private static final String authenticateStaffSQL
            = "SELECT * FROM Staff WHERE staffUsername = ? AND staffPassword = ?";

    private static final String getStaffByUsernameSQL
            = "SELECT * FROM Staff WHERE staffUsername = ?";

    private static final String getStaffByIdSQL
            = "SELECT * FROM Staff WHERE staffId = ?";

    private static final String verifyPasswordSQL
            = "SELECT staffId FROM Staff WHERE staffUsername = ? AND staffPassword = ?";

    private static final String checkUsernameExistsSQL
            = "SELECT staffId FROM Staff WHERE staffUsername = ?";

    private static final String checkEmailExistsSQL
            = "SELECT staffId FROM Staff WHERE staffEmail = ?";

    public StaffBean authenticateStaff(String un, String pw) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StaffBean staff = new StaffBean();

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(authenticateStaffSQL);
            st.setString(1, un);
            st.setString(2, pw);
            rs = st.executeQuery();

            if (rs.next()) {
                staff = new StaffBean();
                staff.setStaffId(rs.getInt("staffid"));
                staff.setStaffFname(rs.getString("stafffname"));
                staff.setStaffLname(rs.getString("stafflname"));
                staff.setStaffEmail(rs.getString("staffemail"));
                staff.setStaffPhoneNum(rs.getInt("staffphonenum"));
                staff.setStaffUsername(rs.getString("staffusername"));
                staff.setStaffPho(rs.getString("staffPassword"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Always close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return staff;
    }

    public StaffBean getStaffById(int staffId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StaffBean staff = new StaffBean();

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(getStaffByIdSQL);
            st.setInt(1, staffId);

            rs = st.executeQuery();

            if (rs.next()) {
                staff.setStaffId(rs.getInt("staffid"));
                staff.setStaffFname(rs.getString("stafffname"));
                staff.setStaffLname(rs.getString("stafflname"));
                staff.setStaffEmail(rs.getString("staffemail"));
                staff.setStaffPhoneNum(rs.getInt("staffphonenum"));
                staff.setStaffUsername(rs.getString("staffusername"));
                staff.setStaffPho(rs.getString("staffPassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return staff;
    }

    public StaffBean getStaffByUsername(String un) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StaffBean staff = new StaffBean();

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(getStaffByUsernameSQL);
            st.setString(1, un);

            rs = st.executeQuery();

            if (rs.next()) {
                staff.setStaffId(rs.getInt("staffid"));
                staff.setStaffFname(rs.getString("stafffname"));
                staff.setStaffLname(rs.getString("stafflname"));
                staff.setStaffEmail(rs.getString("staffemail"));
                staff.setStaffPhoneNum(rs.getInt("staffphonenum"));
                staff.setStaffUsername(rs.getString("staffusername"));
                staff.setStaffPho(rs.getString("staffPassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return staff;
    }

    //insert staff
    public boolean insertStaff(StaffBean staff) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(insertStaffSql);
            st.setString(1, staff.getStaffFname());
            st.setString(2, staff.getStaffLname());
            st.setString(3, staff.getStaffEmail());
            st.setInt(4, staff.getStaffPhoneNum());
            st.setString(5, staff.getStaffUsername());
            st.setString(6, staff.getStaffPho());

            int rows = st.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Always close resources
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateStaff(StaffBean staff) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(updateStaffSql);
            st.setString(1, staff.getStaffFname());
            st.setString(2, staff.getStaffLname());
            st.setString(3, staff.getStaffEmail());
            st.setInt(4, staff.getStaffPhoneNum());
            st.setString(5, staff.getStaffUsername());
            st.setString(6, staff.getStaffPho());
            st.setInt(7, staff.getStaffId());

            int rows = st.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean verifyPassword(String username, String password) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(verifyPasswordSQL);
            st.setString(1, username);
            st.setString(2, password);

            rs = st.executeQuery();
            return rs.next(); 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean usernameExists(String username) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(checkUsernameExistsSQL);
            st.setString(1, username);

            rs = st.executeQuery();
            return rs.next(); // Returns true if username exists

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Check if email already exists
    public boolean emailExists(String email) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(checkEmailExistsSQL);
            st.setString(1, email);

            rs = st.executeQuery();
            return rs.next(); // Returns true if email exists

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
