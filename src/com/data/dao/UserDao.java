package com.data.dao;

import com.data.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    /*
     * Add user
     */
    public int insert(Connection con, User user) throws Exception {

        /*
         * The first step is to query the database. If the user already exists in the database, the user cannot be added to the database
         */
        String sql1 = "select * from user where username = ?"; //sql
        PreparedStatement pstmt1 = (PreparedStatement) con.prepareStatement(sql1);
        pstmt1.setString(1, user.getUsername());
        ResultSet rs = pstmt1.executeQuery();
        if (rs.next()) {
            return 0;
        }
        String sql = "insert into user values(?,?)";
        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());

        return pstmt.executeUpdate();
    }

    /*
     * Login
     */
    public boolean login(Connection con, User user) throws Exception {
        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        ResultSet resultSet = pstmt.executeQuery();
        return resultSet.next();
    }


}
