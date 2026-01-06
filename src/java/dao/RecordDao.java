/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Acer
 */
import model.RecordBean;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {

    // INSERT RECORD
    public void insertRecord(RecordBean record) {

        String sql = "INSERT INTO RECORD (APP_ID, RECORD_DATE) VALUES (?, CURRENT_DATE)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, record.getAppId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET ALL RECORDS
    public List<RecordBean> getAllRecords() {

        List<RecordBean> list = new ArrayList<>();
        String sql = "SELECT * FROM RECORD ORDER BY RECORD_DATE DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RecordBean r = new RecordBean();
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setAppId(rs.getInt("APP_ID"));
                r.setRecordDate(rs.getDate("RECORD_DATE"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // GET RECORD BY ID
    public RecordBean getRecordById(int recordId) {

        RecordBean r = null;
        String sql = "SELECT * FROM RECORD WHERE RECORD_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recordId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new RecordBean();
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setAppId(rs.getInt("APP_ID"));
                r.setRecordDate(rs.getDate("RECORD_DATE"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
    
    // DELETE RECORD
    public void deleteRecord(int recordId) {

        String sql = "DELETE FROM RECORD WHERE RECORD_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recordId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
