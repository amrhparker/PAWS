/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.ActivityLogBean;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Acer
 */

public class ActivityLogDao {
    
    // INSERT LOG
    public void insertLog(ActivityLogBean log) {

        String sql = "INSERT INTO ACTIVITY_LOG (ACTION, LOG_TIME, LOG_USER, ENTITY) " +
                     "VALUES (?, CURRENT_TIMESTAMP, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, log.getAction());
            ps.setString(2, log.getUser());
            ps.setString(3, log.getEntity());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET ALL LOGS
    public List<ActivityLogBean> getAllLogs() {

        List<ActivityLogBean> list = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVITY_LOG ORDER BY LOG_TIME DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ActivityLogBean log = new ActivityLogBean();
                log.setLogId(rs.getInt("LOG_ID"));
                log.setAction(rs.getString("ACTION"));
                log.setTimestamp(rs.getTimestamp("LOG_TIME"));
                log.setUser(rs.getString("LOG_USER"));
                log.setEntity(rs.getString("ENTITY"));

                list.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
    

