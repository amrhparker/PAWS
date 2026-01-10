package dao;

import model.RecordBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {

    private final String URL = "jdbc:derby://localhost:1527/PAWSdb";
    private final String USER = "app";
    private final String PASS = "app";

    //INSERT
    public void insertRecord(RecordBean record) {

        String sql = "INSERT INTO RECORD (APP_ID, RECORD_DATE) VALUES (?, CURRENT_DATE)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, record.getAppId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GET ALL
    public List<RecordBean> getAllRecords() {

        List<RecordBean> list = new ArrayList<>();
        String sql = "SELECT * FROM RECORD ORDER BY RECORD_DATE DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RecordBean r = new RecordBean();
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setAppId(rs.getInt("APP_ID"));
                r.setRecordDate(rs.getDate("RECORD_DATE"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //GET BY ID 
    public RecordBean getRecordById(int recordId) {

        String sql = "SELECT * FROM RECORD WHERE RECORD_ID = ?";
        RecordBean r = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recordId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new RecordBean();
                r.setRecordId(rs.getInt("RECORD_ID"));
                r.setAppId(rs.getInt("APP_ID"));
                r.setRecordDate(rs.getDate("RECORD_DATE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }
}
