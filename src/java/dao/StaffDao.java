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
import java.util.*;

public class StaffDao {
    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";

    //sql queries
    //insert
    private static final String insertStaffSql =
            "INSERT INTO Staff (staffId, staffFname, staffLname, staffEmail, staffPhoneNum, staffUsername, staffPho) "  + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String updateStaffSql =
            "UPDATE Staff SET staffFname = ?, staffLname = ?, staffEmail = ?, staffPhoneNum = ?, staffUsername = ?, staffPho = ? WHERE staffId = ?";
    private static final String deleteStaffSql = 
            "DELETE FROM Staff WHERE staffId = ?";
    private static final String authenticateStaffSQL =
            "SELECT * FROM Staff WHERE staffusername = ? AND staffpho = ?";
    private static final String getStaffByUsernameSQl = 
            "SELECT * FROM Staff WHERE staffusername = ?";
    private static final String getStaffByIdSQL =
            "SELECT * FROM Staff WHERE staffId = ?";
    private static final String verifyPasswordSQL = 
            "SELECT staff_id FROM Staff WHERE staff_username = ? AND staff_pho = ?";
    private static final String checkUsernameExistsSQL =
            "SELECT staff_id FROM Staff WHERE staff_username = ?";
    private static final String checkEmailExistsSQL =
            "SELECT staff_id FROM Staff WHERE staff_email = ?";
    
    public StaffBean authenticateStaff(String un, String pw){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StaffBean staff = new StaffBean();
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(authenticateStaffSQL);
            st.setString(1, un);
            st.setString(2, pw);
            rs = st.executeQuery();
            
            if(rs.next()){
                staff = new StaffBean();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setStaffFname(rs.getString("staff_fname"));      
                staff.setStaffLname(rs.getString("staff_lname"));      
                staff.setStaffEmail(rs.getString("staff_email"));
                staff.setStaffPhoneNum(rs.getInt("staff_phonenum"));
                staff.setStaffUsername(rs.getString("staff_username"));
                staff.setStaffPho(rs.getString("staff_pho"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
    
    public StaffBean getStaffById(int staffId){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StaffBean staff = new StaffBean();
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(getStaffByIdSQL);
            st.setInt(1, staffId);
            
            rs= st.executeQuery();
            
            if(rs.next()){
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setStaffFname(rs.getString("staff_fname"));
                staff.setStaffLname(rs.getString("staff_lname"));
                staff.setStaffEmail(rs.getString("staff_email"));
                staff.setStaffPhoneNum(rs.getInt("staff_phonenum"));
                staff.setStaffUsername(rs.getString("staff_username"));
                staff.setStaffPho(rs.getString("staff_pho")); 
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
    
    public StaffBean getStaffByUsername(String un){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        StaffBean staff = new StaffBean();
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            st = conn.prepareStatement(getStaffByUsernameSQl);
            st.setString(1, un);
            
            rs= st.executeQuery();
            
            if(rs.next()){
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setStaffFname(rs.getString("staff_fname"));
                staff.setStaffLname(rs.getString("staff_lname"));
                staff.setStaffEmail(rs.getString("staff_email"));
                staff.setStaffPhoneNum(rs.getInt("staff_phonenum"));
                staff.setStaffUsername(rs.getString("staff_username"));
                staff.setStaffPho(rs.getString("staff_pho")); 
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
    public boolean insertStaff(StaffBean staff){
        Connection conn = null;
        PreparedStatement st = null;
        
        try{
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

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
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
            st.setInt(6, staff.getStaffId());
            
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
    
    // Verify password (returns true if username/password combination exists)
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
            return rs.next(); // Returns true if username/password combination exists

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
