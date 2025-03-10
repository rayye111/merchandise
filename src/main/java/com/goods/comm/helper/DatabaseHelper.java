package com.goods.comm.helper;

import com.goods.comm.po.Location;
import com.goods.comm.po.MapLayout;
import com.goods.comm.po.Route;
import com.goods.comm.po.User;
import com.goods.utils.GenerateVerificationCodeUtil;
import com.goods.utils.PasswordUtil;
import com.goods.utils.SendEmailUtil;

import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/goods-simulation";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    //保存地图到数据库
    public static void saveToDatabase(MapLayout mapLayout) throws SQLException {
        Connection conn = null;
        PreparedStatement nodeStmt = null;
        PreparedStatement edgeStmt = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);

            // 保存节点
            String insertNodeSql = "INSERT INTO nodes (node_type, x_coord, y_coord) VALUES (?, ?, ?)";
            nodeStmt = conn.prepareStatement(insertNodeSql, Statement.RETURN_GENERATED_KEYS);
            Map<Location, Integer> locationIdMap = new HashMap<>();

            for (Location loc : mapLayout.getStoreLocations()) {
                nodeStmt.setString(1, "store");
                nodeStmt.setInt(2, loc.getX());
                nodeStmt.setInt(3, loc.getY());
                nodeStmt.executeUpdate();

                try (ResultSet generatedKeys = nodeStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        locationIdMap.put(loc, generatedKeys.getInt(1));
                    }
                }
            }

            for (Location loc : mapLayout.getTemporaryWarehouseLocations()) {
                nodeStmt.setString(1, "warehouse");
                nodeStmt.setInt(2, loc.getX());
                nodeStmt.setInt(3, loc.getY());
                nodeStmt.executeUpdate();

                try (ResultSet generatedKeys = nodeStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        locationIdMap.put(loc, generatedKeys.getInt(1));
                    }
                }
            }

            // 保存边
            String insertEdgeSql = "INSERT INTO edges (source_node_id, dest_node_id, weight) VALUES (?, ?, ?)";
            edgeStmt = conn.prepareStatement(insertEdgeSql);
            for (Route route : mapLayout.getRoutes()) {
                Integer sourceId = locationIdMap.get(route.getStart());
                Integer destId = locationIdMap.get(route.getEnd());

                if (sourceId != null && destId != null) {
                    edgeStmt.setInt(1, sourceId);
                    edgeStmt.setInt(2, destId);
                    edgeStmt.setDouble(3, route.getWeight());
                    edgeStmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (nodeStmt != null) {
                nodeStmt.close();
            }
            if (edgeStmt != null) {
                edgeStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    //用户登录
    public static boolean login(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String hashPassword = PasswordUtil.hashPassword(password);
        boolean flag = false;
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
            String insertUser = "SELECT * FROM users WHERE email = ? AND password = ?";
            stmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,email);
            stmt.setString(2,hashPassword);
            rs = stmt.executeQuery();
            flag = true;
        }catch (SQLException e){
            if (conn != null) {
                conn.rollback();
            }
        }
        return flag;
    }


    //用户注册--验证码保存到数据库
    public static void sendVerificationCode(String email) {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // 检查邮箱是否已注册
            String checkSql = "SELECT * FROM users WHERE email = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            rs = checkStmt.executeQuery();
            if (rs.next()) {
                // 邮箱已注册，中止发送验证码并提示用户
                JOptionPane.showMessageDialog(null, "该邮箱已经注册");
                return;
            }

            // 邮箱未注册，生成验证码并发送
            String verificationCode = GenerateVerificationCodeUtil.generateVerificationCode(5);
            String insertSql = "INSERT INTO email_verifications (email, verification_code, expires_at) VALUES (?, ?, ADDDATE(NOW(), INTERVAL 5 MINUTE))";
            insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, email);
            insertStmt.setString(2, verificationCode);
            insertStmt.executeUpdate();

            // 发送验证码到用户邮箱的逻辑
            SendEmailUtil.sendMailCode(email, verificationCode);

        } catch (SQLException e) {
            e.printStackTrace();
            // 处理数据库异常
        } finally {
            try {
                if (rs != null) rs.close();
                if (checkStmt != null) checkStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //用户注册
    public static void register(String email,String passward,String userName) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String hashPassword = PasswordUtil.hashPassword(passward);
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
            String insertUser = "INSERT INTO users (email, password, username) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,email);
            stmt.setString(2,hashPassword);
            stmt.setString(3,userName);
            stmt.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            if (conn != null) {
                conn.rollback();
            }
        }
    }

    public static User getUserInfoByEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
            String insertUser = "SELECT * FROM users WHERE email = ?";
            stmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,email);
            rs = stmt.executeQuery();
            while (rs.next()){
                String userName = rs.getString("username");
                String password = rs.getString("password");
                user = new User(email,password,userName);
            }
            conn.commit();
        }catch (SQLException e){
            if (conn != null) {
                conn.rollback();
            }
        }
        return user;
    }

    public static Boolean getOlderPasswordByEmail(String email,String oldPassword) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Boolean flag = false;
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
            String insertUser = "SELECT * FROM users WHERE email = ? AND password = ?";
            stmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,email);
            stmt.setString(2,oldPassword);
            rs = stmt.executeQuery();
            while (rs.next()){
                flag = true;
            }
            conn.commit();
        }catch (SQLException e){
            if (conn != null) {
                conn.rollback();
            }
        }
        return flag;
    }

    public static Boolean updatePasswordByEmail(String email,String newPassword){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Boolean flag = false;
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
            String insertUser = "UPDATE users SET password = ? WHERE email = ?";
            stmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,newPassword);
            stmt.setString(2,email);
            stmt.executeUpdate();
            conn.commit();
            flag = true;
        }catch (SQLException e){
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return flag;
    }

}
