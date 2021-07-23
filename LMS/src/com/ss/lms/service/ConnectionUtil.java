package com.ss.lms.service;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//A simple object that gets a connection to my database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {


	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/library";
	private final String username = "root";
	private final String password = "root";

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		
		Class.forName(driver);//register driver
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		return conn;
	}
}
