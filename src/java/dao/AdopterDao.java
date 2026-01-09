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
import java.util.*;
import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.AdopterBean;

public class AdopterDao {
    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";
   
    //sql queries
    //insert
    private static final String insertAdopterSql = 
            "INSERT INTO Adopter (adoptFNameadoptLname, adoptIC, adoptPhoneNum, adoptEmail, adoptAddress, adoptUsername, adoptPassword) "  + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    //update
    private static final String updateAdopterSql = 
            "UPDATE Adopter SET adoptFname = ?, adoptLname = ?, adoptIC = ?, adoptPhoneNum = ?, " + "adoptEmail = ?, adoptAddress = ?, adoptUsername = ?, adoptPassword = ? WHERE adoptId = ?";
    //delete
    private static final String deleteAdopterSql = "DELETE FROM Adopter WHERE adoptId = ?";
    //retrieve all
    private static final String selectAdopterAllSql = "SELECT * FROM Adopter";
    //retrieve by ID
    private static final String selectAdopterByIdSql = "SELECT * FROM Adopter WHERE adoptId = ?";
    //retrieve by username
    private static final String selectAdopterByUnSql = "SELECT * FROM Adopter WHERE adoptUsername = ?";
    
    //validate adopter
    private static final String validateAdopterSql = "SELECT * FROM Adopter WHERE adoptUsername = ? AND adoptPassword = ?";
    
    //insert method
    public boolean insertAdopter(AdopterBean adopter){
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(insertAdopterSql);
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
        }catch(SQLException e) {
            handleException(e);
        }finally {
            closeResources(null, ps, conn);
        }
        return success;
    }
    //update method
    public boolean updateAdopter(AdopterBean adopter){
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(updateAdopterSql);
            ps.setString(1, adopter.getAdoptFName());
            ps.setString(2, adopter.getAdoptLName());
            ps.setString(3, adopter.getAdoptIC());
            ps.setInt(4, adopter.getAdoptPhoneNum());
            ps.setString(5, adopter.getAdoptEmail());
            ps.setString(6, adopter.getAdoptAddress());
            ps.setString(7, adopter.getAdoptUsername());
            ps.setString(8, adopter.getAdoptPassword());
            ps.setInt(9, adopter.getAdoptId());
            
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        }catch(SQLException e) {
            handleException(e);
        }finally {
            closeResources(null, ps, conn);
        }
        return success;
    }
    //delete method
    public boolean deleteAdopter(int adoptId){
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(deleteAdopterSql);
            ps.setInt(1, adoptId);
            
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        }catch(SQLException e) {
            handleException(e);
        }finally {
            closeResources(null, ps, conn);
        }
        return success;
    }
    //retrieve methods
    //all adopters
    public List<AdopterBean> getAllAdopters() {
        List<AdopterBean> adopters = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(selectAdopterAllSql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                AdopterBean adopter = mapResultSetToAdopter(rs);
                adopters.add(adopter);
            }
            
        }catch (SQLException e) {
            handleException(e);
        }finally {
            closeResources(rs, ps, conn);
        }
        return adopters;
    }
    //by ID
    public AdopterBean getAdopterById(int id){
        AdopterBean adopter = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(selectAdopterByIdSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()){
                adopter = mapResultSetToAdopter(rs);
            }
        }catch(SQLException e) {
            handleException(e);
        }finally {
            closeResources(null, ps, conn);
        }
        return adopter;
    }
    //by username
    public AdopterBean getAdopterByUn(String un){
        AdopterBean adopter = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(selectAdopterByUnSql);
            ps.setString(1, un);
            rs = ps.executeQuery();
            
            if(rs.next()){
                adopter = mapResultSetToAdopter(rs);
            }
        }catch(SQLException e) {
            handleException(e);
        }finally {
            closeResources(null, ps, conn);
        }
        return adopter;
    }
    //exception handling
    private void handleException(Exception e) {
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
    }
    
    //resources closing
    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing database resources: " + e.getMessage());
        }
    }
    
    //map ResultSet to AdopterBean
    private AdopterBean mapResultSetToAdopter(ResultSet rs) throws SQLException {
        AdopterBean adopter = new AdopterBean();
        adopter.setAdoptId(rs.getInt("adoptId"));
        adopter.setAdoptFName(rs.getString("adoptFName"));
        adopter.setAdoptLName(rs.getString("adoptLName"));
        adopter.setAdoptIC(rs.getString("adoptIC"));
        adopter.setAdoptPhoneNum(rs.getInt("adoptPhoneNum"));
        adopter.setAdoptEmail(rs.getString("adoptEmail"));
        adopter.setAdoptAddress(rs.getString("adoptAddress"));
        adopter.setAdoptUsername(rs.getString("adoptUsername"));
        adopter.setAdoptPassword(rs.getString("adoptPassword"));
        return adopter;
    }
    
    //validate Adopter's login details
    public AdopterBean validateAdopter(String un, String pw){
        AdopterBean adopter = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
            ps = conn.prepareStatement(validateAdopterSql);
            ps.setString(1, un);
            ps.setString(2, pw);
            rs = ps.executeQuery();
            
            if(rs.next()){
                adopter = mapResultSetToAdopter(rs);
            }
        }catch (SQLException e) {
            handleException(e);
        }finally {
            closeResources(rs, ps, conn);
        }
        return adopter;
    }
        
     // Check if username already exists
    public boolean checkUsernameExists(String username) {
        boolean exists = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Adopter WHERE adoptUsername = ?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            handleException(e);
        } finally {
            closeResources(rs, pstmt, conn);
        }
        
        return exists;
    }  
    
}

    
