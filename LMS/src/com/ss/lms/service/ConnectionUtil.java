package com.ss.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {


	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/library";
	private String username = "root";
	private String password = "root";
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		
		Class.forName(driver);//register driver
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		return conn;
	}
}
