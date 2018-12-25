package com.kabryxis.kabutils;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.UUID;

public class MySQLConnection {
	
	private static final UUID uuid = UUID.fromString("4e1c2fb7-27a0-3025-bbee-7d9f605c06a0");
	
	public static void main(String[] args) {
		System.out.println(String.format("[%s] starting", System.currentTimeMillis()));
		connect();
		System.out.println(String.format("[%s] connected", System.currentTimeMillis()));
		disconnect();
		System.out.println(String.format("[%s] disconnected", System.currentTimeMillis()));
	}
	
	private static final String SQL_URL = "jdbc:mysql://localhost:3306/";
	private static final String DATABASE_URL = SQL_URL + "SPIRITCRAFT";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	
	private static Connection connection;
	
	public static Connection connect() {
		if (connection == null) {
			try {
				MysqlDataSource dataSource = new MysqlDataSource();
				dataSource.setUser(USERNAME);
				dataSource.setPassword(PASSWORD);
				dataSource.setUrl(SQL_URL);
				connection = dataSource.getConnection();
				Statement stmt = connection.createStatement();
				String sql = "CREATE DATABASE IF NOT EXISTS SPIRITCRAFT";
				stmt.executeUpdate(sql);
				connection.close();
				dataSource.setUrl(DATABASE_URL);
				connection = dataSource.getConnection();
				stmt = connection.createStatement();
				sql = "CREATE TABLE IF NOT EXISTS USERS " +
						"(uuid VARCHAR(255) not NULL, " +
						" money BIGINT not NULL, " +
						" hspace INTEGER not NULL, " +
						" howns BLOB, " +
						" hselected BLOB, " +
						" geffect VARCHAR(255) not NULL, " +
						" gspace INTEGER not NULL, " +
						" gowns BLOB, " +
						" gselected BLOB, " +
						" PRIMARY KEY ( uuid ))";
				stmt.executeUpdate(sql);
				sql = "SELECT * FROM USERS WHERE uuid = '" + uuid.toString() + "' ";
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next()) {
					System.out.println(rs.getString("uuid"));
				}
				else {
					PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USERS(uuid, money, hspace, howns, hselected, geffect, gspace, gowns, gselected) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
					pstmt.setString(1, uuid.toString());
					pstmt.setLong(2, 100);
					pstmt.setInt(3, 100);
					pstmt.setBlob(4, (Blob)null);
					pstmt.setBlob(5, (Blob)null);
					pstmt.setString(6, "flame");
					pstmt.setInt(7, 100);
					pstmt.setBlob(8, (Blob)null);
					pstmt.setBlob(9, (Blob)null);
					pstmt.execute();
					pstmt.close();
				}
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}
		return connection;
	}
	
	public static void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
